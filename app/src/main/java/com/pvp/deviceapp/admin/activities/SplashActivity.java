package com.pvp.deviceapp.admin.activities;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.pvp.deviceapp.R;
import com.pvp.deviceapp.utils.PrefManager;
import com.pvp.deviceapp.utils.SessionHelper;
import com.pvp.deviceapp.utils.Utility;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();

    SessionHelper sessionHelper;
    PrefManager prefManager;

    String sversion;
    double version_code;
    Handler handler;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sessionHelper=new SessionHelper(SplashActivity.this);
        prefManager=new PrefManager(SplashActivity.this);

       // FirebaseApp.initializeApp(SplashActivity.this);

        //getTokan();

        animation();
        Log.e("versionName", sversion +" "+version_code);
    }


    public void animation() {
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                    Utility.launchActivity(SplashActivity.this, AdminHomeActivity.class, true);


            }
        }, 2000);
    }

}
