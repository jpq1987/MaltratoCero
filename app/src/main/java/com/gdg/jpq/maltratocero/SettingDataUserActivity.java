/*
 * App MaltratoCero for Study Jam GDG Buenos Aires
 * Developed by Juan Pablo Quiñones in May 2016
 * e-mail: jpq.1987@gmail.com
 *
 */
package com.gdg.jpq.maltratocero;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.gdg.jpq.maltratocero.datastorage.UserPrefs;
import com.gdg.jpq.maltratocero.model.User;
import com.gdg.jpq.maltratocero.tools.Validation;

/**
 * Corresponds to SettingDataUser Class to register
 * or edit the user's personal data of the application
 */
public class SettingDataUserActivity extends AppCompatActivity {

    private UserPrefs mUserPrefs;
    private EditText mEditTextFullname;
    private EditText mEditTextDni;
    private EditText mEditTextBirthday;
    private EditText mEditTextMobilephone;
    private EditText mEditTextTelephone;
    private EditText mEditTextAddress;
    private EditText mEditTextEmail;
    private Spinner mSpinnerParties;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_data_user);

        mEditTextFullname = (EditText) findViewById(R.id.edit_text_fullname);
        mEditTextAddress = (EditText) findViewById(R.id.edit_text_address);
        mEditTextBirthday = (EditText) findViewById(R.id.edit_text_birthday);
        mEditTextDni = (EditText) findViewById(R.id.edit_text_dni);
        mEditTextEmail = (EditText) findViewById(R.id.edit_text_email);
        mEditTextMobilephone = (EditText) findViewById(R.id.edit_text_mobilephone);
        mEditTextTelephone = (EditText) findViewById(R.id.edit_text_telephone);
        mSpinnerParties = (Spinner) findViewById(R.id.spinner_parties);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.contactParties, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerParties.setAdapter(adapter);

        if(getActionBar() != null){
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // complete input fields with stored user data
        mUserPrefs = new UserPrefs(getApplicationContext());
        if (mUserPrefs.isCompleted()) {
            User userData = mUserPrefs.getUser();
            mEditTextFullname.setText(userData.getFullname());
            mEditTextAddress.setText(userData.getAddress());
            mEditTextBirthday.setText(userData.getBirthdate());
            mEditTextDni.setText(userData.getDni());
            mEditTextEmail.setText(userData.getEmail());
            mEditTextMobilephone.setText(userData.getMobilephone());
            mEditTextTelephone.setText(userData.getTelephone());
            if (!userData.getParty().equals("")) {
                mSpinnerParties.setSelection(adapter.getPosition(userData.getParty()));
            }
        }

        Button btnSaveUserData = (Button) findViewById(R.id.button_saveUserData);
        btnSaveUserData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataUser();
            }
        });
    }

    @Override
    public void onBackPressed() {
        mUserPrefs = new UserPrefs(getApplicationContext());
        if (!mUserPrefs.isCompleted()){
            createIncompleteWarning();
        }
        else{
            super.onBackPressed();
        }
    }

    private void saveDataUser(){
        mUserPrefs = new UserPrefs(getApplicationContext());
        User user = new User();
        user.setFullname(mEditTextFullname.getText().toString());
        user.setAddress(mEditTextAddress.getText().toString());
        user.setBirthdate(mEditTextBirthday.getText().toString());
        user.setDni(mEditTextDni.getText().toString());
        user.setEmail(mEditTextEmail.getText().toString());
        user.setMobilephone(mEditTextMobilephone.getText().toString());
        user.setTelephone(mEditTextTelephone.getText().toString());
        user.setParty(mSpinnerParties.getSelectedItem().toString());

        if(user.getFullname().equals("") || (user.getDni().equals(""))){
            createIncompleteWarning();
        }else{
            mUserPrefs.setUser(user);
            Toast.makeText(getApplicationContext(), "Su perfil fue actualizado", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void createIncompleteWarning(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Importante!");
        builder.setMessage("Debe completar al menos su nombre y dni para utilizar la aplicación!");
        builder.setPositiveButton("OK", null);
        builder.setCancelable(false);
        builder.create();
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settingdatauser, menu);
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
