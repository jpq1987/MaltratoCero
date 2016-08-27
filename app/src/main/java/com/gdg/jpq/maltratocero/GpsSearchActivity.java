/*
 * App MaltratoCero for Study Jam GDG Buenos Aires
 * Developed by Juan Pablo Quiñones in May 2016
 * e-mail: jpq.1987@gmail.com
 *
 */
package com.gdg.jpq.maltratocero;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.gdg.jpq.maltratocero.conectivity.Constant;
import com.gdg.jpq.maltratocero.conectivity.VolleySingleton;
import com.gdg.jpq.maltratocero.gson.GsonRequest;
import com.gdg.jpq.maltratocero.model.CenterList;
import com.gdg.jpq.maltratocero.model.CenterParty;

/**
 * Corresponds to GPSSearchActivity Class that locates the
 * nearest centers according to current geographic location
 */
public class GpsSearchActivity extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {

    static final String TAG = GpsSearchActivity.class.getSimpleName();

    private ProgressDialog mProgressDialog;

    // variables to interact with Google API
    private GoogleApiClient mGoogleApiClient;
    private GoogleMap gMap;
    private double mLat, mLng;
    private Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_search);

        if(getActionBar() != null){
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ImageButton mButonCallphone = (ImageButton) findViewById(R.id.image_button_callphone);
        mButonCallphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+Constant.GPS_SEARCH_PHONE));
                startActivity(callIntent);
            }
        });

        // first we need to check availability of play services
        if (checkPlayServices()) {
            // building the GoogleApi client
            buildGoogleApiClient();
            createLocationRequest();
        }

        // create a map
        MapFragment mMapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment);
        gMap = mMapFragment.getMap();
        gMap.setMyLocationEnabled(true);
        gMap.getUiSettings().setZoomControlsEnabled(true);
        gMap.getUiSettings().setMyLocationButtonEnabled(true);
        gMap.getUiSettings().setMapToolbarEnabled(false);
        gMap.setPadding(0,0,0, mButonCallphone.getLayoutParams().height);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPlayServices();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onPause() { super.onPause(); }

    @Override
    public void onConnected(Bundle bundle) {
        // once connected with google api, get the location
        displayLocation();

        // locate and zoom
        LatLng latLng = new LatLng(this.mLat, this.mLng);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 12);
        gMap.animateCamera(cameraUpdate);

        // locate centers
        locateCenters();
    }

    @Override
    public void onConnectionSuspended(int i) { mGoogleApiClient.connect(); }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Se produjo un fallo en la conexión: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }

    @Override
    public void onLocationChanged(Location location) {
        // assign the new location
        mLastLocation = location;
        // displaying the new location on UI
        displayLocation();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}

    /**
     * method to display the location on UI
     */
    private void displayLocation() {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            mLat = mLastLocation.getLatitude();
            mLng = mLastLocation.getLongitude();
        } else {
            Toast.makeText(getApplicationContext(), "Su ubicación no pudo ser localizada!!", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * creating google api client object
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    /**
     * creating location request object
     */
    protected void createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        // set rate in miliseconds at which your app prefers to receive location updates
        mLocationRequest.setInterval(Constant.UPDATE_INTERVAL);
        // set the fastest rate in miliseconds at which your app can handle location updates
        mLocationRequest.setFastestInterval(Constant.FATEST_INTERVAL);
        // set the priority of the request. HIGH_ACCURACY use ACCESS_FINE_LOCATION
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /**
     * verify google play services on the device
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, Constant.PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(), "Su dispositivo no es soportado!!", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }

    /**
     * centers located nearby
     */
    private void locateCenters(){
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Buscando centros cercanos...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        final GsonRequest gsonRequest = new GsonRequest<>(
                Constant.GET_CENTERS_BY_LAT_LNG + "?lat=" + this.mLat + "&lng=" + this.mLng + "&distance=" + 100,
                CenterList.class,
                null,
                new Response.Listener<CenterList>() {
                    @Override
                    public void onResponse(CenterList centersReq) {
                        if (centersReq.getState().equals("1")) {
                            for (int i = 0; i < centersReq.getItems().size(); i++) {
                                CenterParty centroAtencion = centersReq.getItems().get(i);
                                gMap.addMarker(new MarkerOptions()
                                                .position(new LatLng(centroAtencion.getLatitud(), centroAtencion.getLongitud()))
                                                .title(centroAtencion.getNombre())
                                                .snippet(centroAtencion.getDireccion())
                                                .icon(BitmapDescriptorFactory.fromResource(CenterParty.obtenerIcono(centroAtencion.getTipoinstitucion())))
                                );
                                mProgressDialog.dismiss();
                            }
                        }
                        else{
                            mProgressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),centersReq.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError != null) {
                    mProgressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"No se pudo localizar centros de atención cercanos. Verifique su conexión a internet e inténtelo nuevamente.",Toast.LENGTH_LONG).show();
                    Log.e(TAG, "VolleyError:" + volleyError.toString());
                }
            }
        }
        );
        gsonRequest.setRetryPolicy(new DefaultRetryPolicy(30000,1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(gsonRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gpssearch, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_refreshmap_menu:
                locateCenters();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}