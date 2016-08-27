/*
 * App MaltratoCero for Study Jam GDG Buenos Aires
 * Developed by Juan Pablo Quiñones in May 2016
 * e-mail: jpq.1987@gmail.com
 *
 */
package com.gdg.jpq.maltratocero;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.gdg.jpq.maltratocero.conectivity.Constant;
import com.gdg.jpq.maltratocero.conectivity.VolleySingleton;
import com.gdg.jpq.maltratocero.gson.GsonRequest;
import com.gdg.jpq.maltratocero.model.CenterList;
import com.gdg.jpq.maltratocero.model.CenterParty;
import com.gdg.jpq.maltratocero.model.Party;

/**
 * Corresponds to CenterDetail Class showing
 * detail of the specific center
 */
public class CenterDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    static final String TAG = CenterDetailActivity.class.getSimpleName();

    private TextView mTextViewTipoInstitucion;
    private TextView mTextViewNombre;
    private TextView mTextViewDescripcion;
    private TextView mTextViewHorariosAtencion;
    private TextView mTextViewDireccion;
    private TextView mTextViewLocalidadProvincia;
    private TextView mTextViewTelefono;
    private TextView mTextViewMail;
    private TextView mTextViewWeb;
    private TextView mTextViewTitular;
    private CenterParty mCenterParty;
    private MapFragment mMapFragment;
    private ProgressDialog mProgressDialog;
    private Party mSelectedParty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_detail);

        if(getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Cargando datos...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        CenterParty selectedCenter = (CenterParty) getIntent().getExtras().get("selectedCenter");
        mSelectedParty = (Party) getIntent().getExtras().get("selectedParty");

        GsonRequest gsonRequest = new GsonRequest<>(
                Constant.GET_CENTER_BY_ID + "?cen=" + selectedCenter.getId(),
                CenterList.class,
                null,
                new Response.Listener<CenterList>() {
                    @Override
                    public void onResponse(CenterList centersReq) {
                        if (centersReq.getState().equals("1")) {
                            mCenterParty = centersReq.getItems().get(0);
                            mTextViewTipoInstitucion = (TextView) findViewById(R.id.text_view_centerPartyTypeD);
                            mTextViewTipoInstitucion.setText(mCenterParty.getTipoinstitucion());
                            mTextViewNombre = (TextView) findViewById(R.id.text_view_centerPartyNameD);
                            mTextViewNombre.setText(mCenterParty.getNombre());
                            mTextViewDescripcion = (TextView) findViewById(R.id.text_view_centerPartyDescriptionD);
                            mTextViewDescripcion.setText(mCenterParty.getDescripcion());
                            mTextViewHorariosAtencion = (TextView) findViewById(R.id.text_view_centerPartyAttentionD);
                            mTextViewHorariosAtencion.setText(mCenterParty.getHorariosAtencion());
                            mTextViewDireccion = (TextView) findViewById(R.id.text_view_centerPartyAddressD);
                            mTextViewDireccion.setText(mCenterParty.getDireccion());
                            mTextViewLocalidadProvincia = (TextView) findViewById(R.id.text_view_centerPartyLocprovD);
                            mTextViewLocalidadProvincia.setText(mCenterParty.getLocalidad()+", "+ mCenterParty.getProvincia());
                            mTextViewTelefono = (TextView) findViewById(R.id.text_view_centerPartyTelephoneD);
                            mTextViewTelefono.setText(mCenterParty.getTelefonos());
                            mTextViewMail = (TextView) findViewById(R.id.text_view_centerPartyMailD);
                            mTextViewMail.setText(mCenterParty.getEmail());
                            mTextViewWeb = (TextView) findViewById(R.id.text_view_centerPartyWebD);
                            mTextViewWeb.setText(mCenterParty.getWeb());
                            mTextViewTitular = (TextView) findViewById(R.id.text_view_centerPartyTitularD);
                            mTextViewTitular.setText(mCenterParty.getTitular());

                            mMapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment_detail);
                            mMapFragment.getMapAsync(CenterDetailActivity.this);

                            mProgressDialog.dismiss();
                        }
                        else{
                            mProgressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), centersReq.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError != null) {
                    mProgressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"No se pudo buscar el centro. Verifique su conexión a internet e intentelo nuevamente.",Toast.LENGTH_LONG).show();
                    Log.e(TAG, "VolleyError: " + volleyError.toString());
                }
            }
        }
        );
        gsonRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(gsonRequest);
    }

    @Override
    public void onMapReady(GoogleMap map){
        LatLng centro = new LatLng(mCenterParty.getLatitud(), mCenterParty.getLongitud());
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.getUiSettings().setAllGesturesEnabled(false);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(centro, 14));
        map.addMarker(new MarkerOptions()
                .title(mCenterParty.getNombre())
                .position(centro)
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_centerdetail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle presses on the action bar items
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
