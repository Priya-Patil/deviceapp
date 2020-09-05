package com.pvp.deviceapp.admin.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pvp.deviceapp.R;
import com.pvp.deviceapp.utils.PrefManager;


/**
 * Created by s on 8/20/2018.
 */

public class bluetooth_Dialog extends Dialog implements View.OnClickListener {

    public Activity activity;

    PrefManager prefManager;
    static TextView btn_start_exam;
    static RelativeLayout rl_start_exam;
    Bundle bundle;

    public bluetooth_Dialog() {
        super(null);
    }
    public bluetooth_Dialog(Activity activity, Bundle bundle) {
        super(activity);
        this.activity = activity;
        this.bundle=bundle;

      }

      public bluetooth_Dialog(Activity activity) {
        super(activity);
        this.activity = activity;

      }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        /*this.setCancelable(false);*/
        setContentView(R.layout.bluetooth_dialog);
        prefManager=new PrefManager(activity);

        bindViews();
        btnListeners();


    }


    void bindViews() {
/*
        prefManager = new PrefManager(activity);
        btn_start_exam=findViewById(R.id.btn_start_exam);
        rl_start_exam=findViewById(R.id.rl_start_exam);
*/
    }

    void btnListeners() {
       /* rl_start_exam.setOnClickListener(this);
        btn_start_exam.setOnClickListener(this);
*/
    }

    @Override
    public void onClick(View view) {

        Log.e("onClick","Clicked");

        if(!btn_start_exam.getText().toString().equals("Resume")) {

        }
        else {

        }
    }
}
