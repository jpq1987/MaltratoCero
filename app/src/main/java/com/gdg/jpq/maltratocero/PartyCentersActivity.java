/*
 * App MaltratoCero for Study Jam GDG Buenos Aires
 * Developed by Juan Pablo Quiñones in May 2016
 * e-mail: jpq.1987@gmail.com
 *
 */
package com.gdg.jpq.maltratocero;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import com.gdg.jpq.maltratocero.adapter.CenterPartyAdapter;
import com.gdg.jpq.maltratocero.conectivity.Constant;
import com.gdg.jpq.maltratocero.conectivity.VolleySingleton;
import com.gdg.jpq.maltratocero.gson.GsonRequest;
import com.gdg.jpq.maltratocero.model.CenterList;
import com.gdg.jpq.maltratocero.model.CenterParty;
import com.gdg.jpq.maltratocero.model.Party;


/**
 * Corresponds to PartyCenters Class showing
 * all care centers found in a specific party
 */
public class PartyCentersActivity extends AppCompatActivity {

    static final String TAG = PartyCentersActivity.class.getSimpleName();

    private ListView mListCenters;
    private CenterPartyAdapter mCentersAdapter;
    private List<CenterParty> mListCentersData = new ArrayList<>();
    private Party mSelectedParty;
    private ImageView mImageViewDontSearch;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_centers);

        mSelectedParty = (Party) getIntent().getExtras().get("selectedParty");
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setTitle(mSelectedParty.getNombre());
        }

        mListCenters = (ListView) findViewById(R.id.list_view_partyCenters);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Buscando Centros...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        GsonRequest gsonRequest = new GsonRequest<>(
                Constant.GET_CENTERS_BY_PARTY + "?loc=" + this.mSelectedParty.getId(),
                CenterList.class,
                null,
                new Response.Listener<CenterList>() {
                    @Override
                    public void onResponse(CenterList centersReq) {
                        // verified that found centers
                        if (centersReq.getState().equals("1")) {
                            // if found centers, show centers list
                            for (int i = 0; i < centersReq.getItems().size(); i++) {
                                CenterParty cp = centersReq.getItems().get(i);
                                mListCentersData.add(cp);
                            }
                            mCentersAdapter.notifyDataSetChanged();
                            mProgressDialog.dismiss();
                        }
                        else{
                            // if not found centers, show message
                            mImageViewDontSearch = (ImageView) findViewById(R.id.image_view_dontSearch);
                            mImageViewDontSearch.setVisibility(View.VISIBLE);
                            mProgressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if (volleyError != null) {
                            mProgressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"No se pudo localizar centros de atención. Verifique su conexión a internet e inténtelo nuevamente.",Toast.LENGTH_LONG).show();
                            Log.e(TAG, "VolleyError: " + volleyError.toString());
                        }
                    }
                }
        );
        gsonRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(gsonRequest);

        mCentersAdapter = new CenterPartyAdapter(this, mListCentersData);
        mListCenters.setAdapter(mCentersAdapter);
    }

    // start CenterDetail Activity
    public void viewCenterDetail(View v){
        final int position = mListCenters.getPositionForView((View) v.getParent());
        CenterParty selectedCenter = mCentersAdapter.getItem(position);
        Intent i = new Intent(this,CenterDetailActivity.class);
        i.putExtra("selectedCenter", selectedCenter);
        i.putExtra("selectedParty", mSelectedParty);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_partycenters, menu);
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
