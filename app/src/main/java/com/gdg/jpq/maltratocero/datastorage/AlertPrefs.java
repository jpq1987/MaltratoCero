package com.gdg.jpq.maltratocero.datastorage;

import android.content.Context;
import android.content.SharedPreferences;

import com.gdg.jpq.maltratocero.model.Alert;

public class AlertPrefs {

    // application's preferences label
    private static final String ALERTMSG_PREFERENCE_FILE = "jpq.maltratocero.ALERTMSG_SHPREF_FILE";

    // application's preferences
    private static SharedPreferences settings;

    // application's settings editor
    private static SharedPreferences.Editor editor;

    public AlertPrefs(Context context){
        if (settings == null){
            settings = context.getSharedPreferences(ALERTMSG_PREFERENCE_FILE, Context.MODE_PRIVATE);
        }
        // get a sharedpreferences editor instance
        editor = settings.edit();
    }

    public void setMessageAlert(Alert alert){
        if (alert == null)
            return;

        editor.putString("message1", alert.getMessage1());
        editor.putString("message2", alert.getMessage2());
        editor.putString("message3", alert.getMessage3());
        editor.putString("referenceContact", alert.getReferenceContact());
        editor.putString("referencePhone", alert.getReferencePhone());
        editor.commit();
    }

    public Alert getMessageAlert(){
        String message1 = settings.getString("message1","Mi expareja me está acosando en el trabajo!");
        String message2 = settings.getString("message2","Estoy escondida porque me está esperando afuera!");
        String message3 = settings.getString("message3","Necesito ayuda urgente!");
        String referenceContact = settings.getString("referenceContact","No asignado");
        String referencePhone = settings.getString("referencePhone","");

        return new Alert(message1, message2, message3, referenceContact, referencePhone);
    }
}
