/*
 * App MaltratoCero for Study Jam GDG Buenos Aires
 * Developed by Juan Pablo Quiñones in May 2016
 * e-mail: jpq.1987@gmail.com
 *
 */
package com.gdg.jpq.maltratocero;

import android.app.ProgressDialog;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.gdg.jpq.maltratocero.tools.Validation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.apache.commons.codec.binary.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;

import com.gdg.jpq.maltratocero.conectivity.Constant;
import com.gdg.jpq.maltratocero.conectivity.VolleySingleton;
import com.gdg.jpq.maltratocero.datastorage.AlertPrefs;
import com.gdg.jpq.maltratocero.datastorage.UserPrefs;
import com.gdg.jpq.maltratocero.model.Alert;
import com.gdg.jpq.maltratocero.model.User;

/**
 * Corresponds to AlertActivity Class that allows
 * send Email and SMS messages to a predetermined contact
 * with personal information and geographic location
 */
public class AlertActivity extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {

    static final String TAG = AlertActivity.class.getSimpleName();

    private User mUser;
    private Alert mAlert;
    private Button mButtonSendAlertMessage1;
    private Button mButtonSendAlertMessage2;
    private Button mButtonSendAlertMessage3;
    private ProgressDialog mProgressDialog;

    // vars to interact with Google API
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private double mLat, mLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        // first we need to check availability of play services
        if (checkPlayServices()) {
            // building the GoogleApi client
            buildGoogleApiClient();
            createLocationRequest();
        }

        if(getActionBar() != null){
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // search the stored user data and message of predefined alert
        AlertPrefs mAlertPrefs = new AlertPrefs(getApplicationContext());
        mAlert = mAlertPrefs.getMessageAlert();
        UserPrefs mUserPrefs = new UserPrefs(getApplicationContext());
        mUser = mUserPrefs.getUser();

        // complete the parameters of the button with the stored predefined data
        mButtonSendAlertMessage1 = (Button) findViewById(R.id.button_sendAlertMessage1);
        mButtonSendAlertMessage1.setText(mAlert.getMessage1());

        mButtonSendAlertMessage2 = (Button) findViewById(R.id.button_sendAlertMessage2);
        mButtonSendAlertMessage2.setText(mAlert.getMessage2());

        mButtonSendAlertMessage3 = (Button) findViewById(R.id.button_sendAlertMessage3);
        mButtonSendAlertMessage3.setText(mAlert.getMessage3());

        mButtonSendAlertMessage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendAlert(mButtonSendAlertMessage1.getText().toString(),mUser,mAlert);
            }
        });

        mButtonSendAlertMessage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendAlert(mButtonSendAlertMessage2.getText().toString(),mUser,mAlert);
            }
        });

        mButtonSendAlertMessage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendAlert(mButtonSendAlertMessage3.getText().toString(),mUser,mAlert);
            }
        });
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
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onConnected(Bundle bundle) {
        displayLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
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

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Se produjo un fallo en la conexión: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }

    /**
     * Send an alert message to an email and a SMS contact
     */
    public void sendAlert(String message, User user, Alert alert) {
        mProgressDialog = new ProgressDialog(AlertActivity.this);
        mProgressDialog.setMessage("Enviando alerta vía mail...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        // get user data and geographic coordinates
        HashMap<String, String> map = new HashMap<>();
        map.put("emisor", user.getFullname());
        map.put("mail", Constant.EMAIL_ALERT);
        map.put("mensaje", message);
        map.put("latitud", Double.toString(this.mLat));
        map.put("longitud", Double.toString(this.mLng));

        // create JSON object
        JSONObject jobject = new JSONObject(map);

        // JSON object debugging
        Log.d(TAG, jobject.toString());

        // create JSON request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                Constant.SEND_ALERT,
                jobject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // proccess the response
                        try {
                            if (response.getString("estado").equals("1")) {
                                mProgressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Mail enviado!", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        } catch (JSONException e) {
                            mProgressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "No se puedo enviar el alerta por mail", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // proccess the error response
                        mProgressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
                        Log.d(TAG, "Error Volley: " + volleyError.toString());
                    }
                }

        ){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Accept", "application/json");
                return headers;
            }
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8" + getParamsEncoding();
            }
         };

        // connect to the webservice and sending the mail
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);

        // prepare SMS
        String phoneNro = alert.getReferencePhone();
        if (phoneNro.equals("")){
            phoneNro = Constant.PHONE_ALERT;
        }

        String lat = Double.toString(mLat);
        String lng = Double.toString(mLng);
        String ufname = user.getFullname();
        String udni = user.getDni();
        String umessage = message;
        String textSms = "Alerta app MaltratoCero, "+ufname+" ("+udni+"): "+umessage+" - desde http://maps.google.com/maps?q="+lat+","+lng;
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNro, null,Validation.replaceSpecialCharacters(textSms), null, null);
            mProgressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "SMS enviado!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            mProgressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "No se pudo enviar el alerta por SMS", Toast.LENGTH_LONG).show();
            Log.d(TAG, "Error SMS: " + e);
        }

    }

    /**
     * Display the location on UI
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
     * Verify google play services on the device
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, Constant.PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),"Su dispositivo no funciona con en esta aplicación!!", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alert, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle presses on the action bar items
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
