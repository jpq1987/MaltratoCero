/*
 * App MaltratoCero for Study Jam GDG Buenos Aires
 * Developed by Juan Pablo Quiñones in May 2016
 * e-mail: jpq.1987@gmail.com
 *
 */
package com.gdg.jpq.maltratocero;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.gdg.jpq.maltratocero.adapter.PartyAdapter;
import com.gdg.jpq.maltratocero.gson.PartyGsonParser;
import com.gdg.jpq.maltratocero.model.Party;

/**
 * Corresponds to PartySearchActivity Class showing
 * 135 parties of the province of Buenos Aires
 */
public class PartySearchActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private PartyAdapter mPartyAdaptader;
    private List<Party> mListPartiesData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_search);

        if(getActionBar() != null){
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ListView partiesList = (ListView) findViewById(R.id.list_view_parties);
        partiesInitialize();
        mPartyAdaptader = new PartyAdapter(this, mListPartiesData);

        partiesList.setAdapter(mPartyAdaptader);
        partiesList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Party selectedParty = mPartyAdaptader.getItem(position);
        Intent i = new Intent(this,PartyCentersActivity.class);
        i.putExtra("selectedParty", selectedParty);
        startActivity(i);
    }

    // loading the parties list
    public void partiesInitialize() {
        try {
            // parse JSON
            InputStream in = getResources().openRawResource(R.raw.parties);
            PartyGsonParser parser = new PartyGsonParser();
            List<Party> partidos = parser.leerFlujoJson(in);
            Iterator iterator;
            for (iterator = partidos.iterator(); iterator.hasNext(); ) {
                Party party = (Party) iterator.next();
                mListPartiesData.add(party);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_partysearch, menu);
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
