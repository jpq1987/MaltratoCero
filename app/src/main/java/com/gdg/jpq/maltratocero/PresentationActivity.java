/*
 * App MaltratoCero for Study Jam GDG Buenos Aires
 * Developed by Juan Pablo Quiñones in May 2016
 * e-mail: jpq.1987@gmail.com
 *
 */
package com.gdg.jpq.maltratocero;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.gdg.jpq.maltratocero.conectivity.Constant;

/**
 * Corresponds to Presentation Class showing
 * a presentation with the slogan of the Maltrato Cero
 */
public class PresentationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(PresentationActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, Constant.DURATION_PRESENTATION);
    }
}
