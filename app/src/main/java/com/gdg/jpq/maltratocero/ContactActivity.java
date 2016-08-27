/*
 * App MaltratoCero for Study Jam GDG Buenos Aires
 * Developed by Juan Pablo Quiñones in May 2016
 * e-mail: jpq.1987@gmail.com
 *
 */
package com.gdg.jpq.maltratocero;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.app.ProgressDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.gdg.jpq.maltratocero.conectivity.Constant;
import com.gdg.jpq.maltratocero.conectivity.VolleySingleton;
import com.gdg.jpq.maltratocero.datastorage.UserPrefs;
import com.gdg.jpq.maltratocero.tools.Validation;

/**
 * Corresponds to ContactActivity Class to
 * allow send an email from the app
 */
public class ContactActivity extends AppCompatActivity {

    static final String TAG = ContactActivity.class.getSimpleName();

    private Spinner mSpinnerParties;
    private ProgressDialog mProgressDialog;
    private EditText mEditTextFullname;
    private EditText mEditTextEmail;
    private EditText mEditTextMessage;
    private UserPrefs mUserPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        if(getActionBar() != null){
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mEditTextFullname = (EditText) findViewById(R.id.edit_text_contactFullname);
        mEditTextEmail = (EditText) findViewById(R.id.edit_text_contactEmail);
        mEditTextMessage = (EditText) findViewById(R.id.edit_text_contactMessage);

        mUserPrefs = new UserPrefs(getApplicationContext());

        mEditTextFullname.setText(mUserPrefs.getUser().getFullname());
        mEditTextFullname.setEnabled(false);

        if(!mUserPrefs.getUser().getEmail().equals("")){
            mEditTextEmail.setText(mUserPrefs.getUser().getEmail());
            mEditTextEmail.setEnabled(false);
        }
        else {
            mEditTextEmail.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    Validation.isEmailAddress(mEditTextEmail, true);
                }
            });
        }

        mEditTextMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                Validation.hasText(mEditTextMessage);
            }
        });

        mSpinnerParties = (Spinner) findViewById(R.id.spinner_contactParties);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.contactParties, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerParties.setAdapter(adapter);

        if(!mUserPrefs.getUser().getParty().equals("")) {
            int pos = adapter.getPosition(mUserPrefs.getUser().getParty());
            mSpinnerParties.setSelection(pos);
            mSpinnerParties.setEnabled(false);
        }

        Button mButtonSendMessage = (Button) findViewById(R.id.button_sendMessage);
        mButtonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mProgressDialog = new ProgressDialog(ContactActivity.this);
                mProgressDialog.setMessage("Validando los datos ingresados...");
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();

                // get the parameters to send email
                if (checkValidation()) {
                    mProgressDialog.setMessage("Enviando el mensaje...");
                    String partido = mSpinnerParties.getSelectedItem().toString();
                    HashMap<String, String> map = new HashMap<>();
                    map.put("emisor", mEditTextFullname.getText().toString());
                    map.put("mail", mEditTextEmail.getText().toString());
                    map.put("mensaje", mEditTextMessage.getText().toString());
                    map.put("partido", partido);

                    mUserPrefs.getUser().setEmail(mEditTextEmail.getText().toString());
                    mUserPrefs.getUser().setParty(partido);

                    // create the JSON object based on the HashMap map
                    JSONObject jobject = new JSONObject(map);

                    // Logging the JSON object
                    Log.d(TAG, jobject.toString());

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                            Request.Method.POST,
                            Constant.SEND_MAIL,
                            jobject,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    // proccesing server response
                                    try {
                                        if (response.getString("estado").equals("1")) {
                                            Toast.makeText(getApplicationContext(), "Su mensaje fue enviado", Toast.LENGTH_LONG).show();
                                            mProgressDialog.dismiss();
                                            finish();
                                        }
                                    } catch (JSONException e) {
                                        mProgressDialog.dismiss();
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    mProgressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "VolleyError:" + volleyError.toString(), Toast.LENGTH_LONG).show();
                                    Log.d(TAG, "Error Volley: " + volleyError.toString());
                                }
                            }

                    ) {
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
                    jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
                }
                else{
                    mProgressDialog.dismiss();
                }
            }
        });
    }

    private boolean checkValidation() {
        boolean ret = true;
        if (!Validation.isName(mEditTextFullname, true)) ret = false;
        if (!Validation.isEmailAddress(mEditTextEmail, true)) ret = false;
        if (!Validation.hasText(mEditTextMessage)) ret = false;
        return ret;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact, menu);
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
