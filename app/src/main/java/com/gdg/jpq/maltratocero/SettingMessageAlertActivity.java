/*
 * App MaltratoCero for Study Jam GDG Buenos Aires
 * Developed by Juan Pablo Quiñones in May 2016
 * e-mail: jpq.1987@gmail.com
 *
 */
package com.gdg.jpq.maltratocero;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gdg.jpq.maltratocero.datastorage.AlertPrefs;
import com.gdg.jpq.maltratocero.model.Alert;

/**
 * Corresponds to SettingMessageAlert Class to edit alert
 * messages and add the reference contact to send the alert
 */
public class SettingMessageAlertActivity extends AppCompatActivity {

    static final int PICK_CONTACT_REQUEST = 1 ;

    private AlertPrefs mAlertPrefs;
    private TextView mTextViewReferenceContact;
    private TextView mTextViewReferencePhone;
    private EditText mEditTextMessage1;
    private EditText mEditTextMessage2;
    private EditText mEditTextMessage3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_message_alert);

        mTextViewReferenceContact = (TextView) findViewById(R.id.text_view_referenceContact);
        mTextViewReferencePhone = (TextView) findViewById(R.id.text_view_referencePhone);
        Button mButtonContactSearch = (Button) findViewById(R.id.button_searchContact);
        mButtonContactSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchReferenceContact();
            }
        });

        mEditTextMessage1 = (EditText) findViewById(R.id.edit_text_message1);
        mEditTextMessage2 = (EditText) findViewById(R.id.edit_text_message2);
        mEditTextMessage3 = (EditText) findViewById(R.id.edit_text_message3);

        if(getActionBar() != null){
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // complete input field with stored message data
        mAlertPrefs = new AlertPrefs(getApplicationContext());
        Alert alert = mAlertPrefs.getMessageAlert();
        mEditTextMessage1.setText(alert.getMessage1());
        mEditTextMessage2.setText(alert.getMessage2());
        mEditTextMessage3.setText(alert.getMessage3());
        mTextViewReferenceContact.setText(alert.getReferenceContact());
        mTextViewReferencePhone.setText(alert.getReferencePhone());
        Button mButtonSaveAlertMessages = (Button) findViewById(R.id.button_saveMessageAlert);
        mButtonSaveAlertMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAlertMessages();
            }
        });

    }

    private void saveAlertMessages(){
        mAlertPrefs = new AlertPrefs(getApplicationContext());
        Alert alert = new Alert();
        alert.setMessage1(mEditTextMessage1.getText().toString());
        alert.setMessage2(mEditTextMessage2.getText().toString());
        alert.setMessage3(mEditTextMessage3.getText().toString());
        alert.setReferenceContact(mTextViewReferenceContact.getText().toString());
        alert.setReferencePhone(mTextViewReferencePhone.getText().toString());
        mAlertPrefs.setMessageAlert(alert);
        Toast.makeText(getApplicationContext(),"Los mensajes fueron registrados",Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settingmessagealert, menu);
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

    private void searchReferenceContact(){
        // intent for select reference contact
        Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(i, PICK_CONTACT_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == PICK_CONTACT_REQUEST) {
            if (resultCode == RESULT_OK) {
                // capture uri value
                Uri mContactUri = intent.getData();
                // proccess uri
                String contactName = getName(mContactUri);
                String contactMobilephone = getPhone(mContactUri);
                if (contactMobilephone != null) {
                    mTextViewReferenceContact.setText(contactName);
                    mTextViewReferencePhone.setText(contactMobilephone);
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Importante!");
                    builder.setMessage(contactName + " no tiene un número de celular asignado, por favor seleccione otro contacto con celular!");
                    builder.setPositiveButton("OK", null);
                    builder.setCancelable(false);
                    builder.create();
                    builder.show();
                }
            }
        }
    }

    private String getName(Uri uri) {
        // variable to the contact name
        String name = null;
        // get content resolver instance
        ContentResolver contentResolver = getContentResolver();
        // cursor to trace the data query
        Cursor c = contentResolver.query(uri, new String[]{ContactsContract.Contacts.DISPLAY_NAME}, null, null, null);
        // querying result first and only selected
        if(c != null && c.moveToFirst()){
            name = c.getString(0);
            c.close();
        }
        return name;
    }

    private String getPhone(Uri uri) {
        // temporary variables for the id and phone
        String id = null;
        String phone = null;

        // get the contact ID (first query)
        Cursor contactCursor = getContentResolver().query(uri, new String[]{ContactsContract.Contacts._ID}, null, null, null);
        if (contactCursor != null && contactCursor.moveToFirst()) {
            id = contactCursor.getString(0);
            contactCursor.close();
        }

        // WHERE statement to specify that you only want mobile phone numbers (second query)
        String selectionArgs =
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE+"= " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE;
        // get phone number
        Cursor phoneCursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[] { ContactsContract.CommonDataKinds.Phone.NUMBER },
                selectionArgs                ,
                new String[] { id },
                null
        );
        if (phoneCursor != null && phoneCursor.moveToFirst()) {
            phone = phoneCursor.getString(0);
            phoneCursor.close();
        }
        return phone;
    }

}