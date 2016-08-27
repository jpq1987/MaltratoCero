package com.gdg.jpq.maltratocero.datastorage;

import android.content.Context;
import android.content.SharedPreferences;

import com.gdg.jpq.maltratocero.model.User;

public class UserPrefs {

    // application's preferences label
    private static final String DATAUSER_PREFERENCE_FILE = "jpq.maltratocero.DATAUSER_SHPREF_FILE";

    // application's preferences
    private static SharedPreferences settings;

    // application's settings editor
    private static SharedPreferences.Editor editor;

    public UserPrefs(Context context){
        if (settings == null){
            settings = context.getSharedPreferences(DATAUSER_PREFERENCE_FILE, Context.MODE_PRIVATE);
        }
        // get a sharedpreferences editor instance
        editor = settings.edit();
    }

    public void setUser(User user){
        if (user == null)
            return;

        editor.putString("address",user.getAddress());
        editor.putString("birthdate",user.getBirthdate());
        editor.putString("dni",user.getDni());
        editor.putString("email",user.getEmail());
        editor.putString("fullname",user.getFullname());
        editor.putString("mobilephone",user.getMobilephone());
        editor.putString("telephone",user.getTelephone());
        editor.putString("party", user.getParty());
        editor.commit();
    }

    public User getUser(){
        String address = settings.getString("address","");
        String birthdate = settings.getString("birthdate","");
        String dni = settings.getString("dni","");
        String email = settings.getString("email","");
        String fullname = settings.getString("fullname","");
        String mobilephone = settings.getString("mobilephone","");
        String telephone = settings.getString("telephone","");
        String party = settings.getString("party","");

        return new User(fullname,dni,birthdate,telephone,mobilephone,address,email,party);
    }

    public boolean isCompleted(){
        User userData = this.getUser();
        return !((userData.getFullname().equals("")) || (userData.getDni().equals("")));
    }
}
