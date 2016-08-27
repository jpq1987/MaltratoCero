/*
 * App MaltratoCero for Study Jam GDG Buenos Aires
 * Developed by Juan Pablo Quiñones in May 2016
 * e-mail: jpq.1987@gmail.com
 *
 */
package com.gdg.jpq.maltratocero;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.gdg.jpq.maltratocero.datastorage.UserPrefs;
/**
 * Corresponds to MainActivity Class showing
 * the main actions that can be performed
 */
public class MainActivity extends AppCompatActivity {

    UserPrefs mUserPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserPrefs = new UserPrefs(getApplicationContext());
        if (!mUserPrefs.isCompleted()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Complete su Perfil");
            builder.setMessage("Para poder utilizar la aplicación deberá completar su nombre y dni en la sección Perfil");
            builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    editDataUserAction();
                }
            });
            builder.setNegativeButton("Salir", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setCancelable(false);
            builder.create();
            builder.show();
        }

        ImageButton mBtnSearchByGPS = (ImageButton) findViewById(R.id.image_button_searchByGPS);
        mBtnSearchByGPS.setOnClickListener(new View.OnClickListener() {
                                                  @Override
                                                  public void onClick(View v) {
                                                      searchByGPSAction(v);
                                                  }
                                              }
        );

        ImageButton mBtnSearchByParty = (ImageButton) findViewById(R.id.image_button_searchByParty);
        mBtnSearchByParty.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        searchByPartyAction(v);
                                                    }
                                                }
        );

        ImageButton mBtnUsefulInfo = (ImageButton) findViewById(R.id.image_button_FAQs);
        mBtnUsefulInfo.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            usefulInformationAction(v);
                                                        }
                                                      }
        );

        ImageButton mBtnSendAlert = (ImageButton) findViewById(R.id.image_button_sendAlert);
        mBtnSendAlert.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  sendAlertAction(v);
                                              }
                                          }
        );

        ImageButton mBtnContact = (ImageButton) findViewById(R.id.image_button_contact);
        mBtnContact.setOnClickListener(new View.OnClickListener(){
                                                @Override
                                                public void onClick(View view){
                                                    contactAction(view);
                                                }
                                            }
        );
    }

    // start alert Activity
    public void sendAlertAction(View v) {
        Intent i = new Intent(this, AlertActivity.class);
        startActivity(i);
    }

    // start searchGPS Activity
    public void searchByGPSAction(View v) {
        Intent i = new Intent(this, GpsSearchActivity.class);
        startActivity(i);
    }

    // start searchParty Activity
    public void searchByPartyAction(View v) {
        Intent i = new Intent(this, PartySearchActivity.class);
        startActivity(i);
    }

    // start usefulInformation Activity
    public void usefulInformationAction(View v) {
        Intent i = new Intent(this, UsefulInformationActivity.class);
        startActivity(i);
    }

    // start contact Activity
    public void contactAction(View view){
        Intent i = new Intent(this, ContactActivity.class);
        startActivity(i);
    }

    // start editDataUser Activity
    public void editDataUserAction(){
        Intent i = new Intent(this, SettingDataUserActivity.class);
        startActivity(i);
    }

    // start editMessageAlerts Activity
    public void editMessageAlertsAction(){
        Intent i = new Intent(this, SettingMessageAlertActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_edituser_menu:
                editDataUserAction();
                return true;
            case R.id.action_editalerts_menu:
                editMessageAlertsAction();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}