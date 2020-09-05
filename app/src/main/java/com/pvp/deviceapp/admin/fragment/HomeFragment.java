package com.pvp.deviceapp.admin.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.pvp.deviceapp.MainActivity;

import com.pvp.deviceapp.R;
import com.pvp.deviceapp.utils.PrefManager;
import com.pvp.deviceapp.utils.Utility;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    PrefManager prefManager;
    Activity context;
    TextView et_title;
    CardView cv_bluetooth,cv_selectSetting,cv_repeatemood,cv_send,cv_start, cv_stop;

    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context=getActivity();
        View view = inflater.inflate(R.layout.fragment_new_home, container, false);

        bindView(view);
        listener();

        et_title.setSelected(true);


        ToggleButton toggle = (ToggleButton) view.findViewById(R.id.toggle);
        // Set a checked change listener for toggle button
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    Toast.makeText(context, "please connect bluetooth to device", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(context, "please connect bluetooth to device", Toast.LENGTH_SHORT).show();

                }
            }
        });

        ToggleButton toggle2 = (ToggleButton) view.findViewById(R.id.toggle2);
        // Set a checked change listener for toggle button
        toggle2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    // if toggle button is enabled/on
                    Toast.makeText(context, "please connect bluetooth to device", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(context, "please connect bluetooth to device", Toast.LENGTH_SHORT).show();

                }
            }
        });

        return view;
    }

    private void listener() {
        cv_send.setOnClickListener(this);
        cv_stop.setOnClickListener(this);
        cv_start.setOnClickListener(this);
        cv_selectSetting.setOnClickListener(this);
        cv_repeatemood.setOnClickListener(this);
        cv_bluetooth.setOnClickListener(this);


    }

    private void bindView(View view) {
        et_title = view.findViewById(R.id.et_title);
        cv_bluetooth = view.findViewById(R.id.cv_bluetooth);
        cv_repeatemood = view.findViewById(R.id.cv_repeatemood);
        cv_selectSetting = view.findViewById(R.id.cv_selectSetting);
        cv_start = view.findViewById(R.id.cv_start);
        cv_stop = view.findViewById(R.id.cv_stop);
        cv_send = view.findViewById(R.id.cv_send);
        prefManager=new PrefManager(getActivity());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ll_homework:
                //  Utility.launchActivity(getActivity(), HomeworkListActivity.class, false);
                break;
            case R.id.cv_bluetooth:

                prefManager.setAction("add");
                Utility.launchActivity(getActivity(), MainActivity.class, false);
                break;
            case R.id.cv_repeatemood:
                Toast.makeText(context, "please connect bluetooth ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cv_start:
                Toast.makeText(context, "please connect bluetooth ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cv_stop:
                Toast.makeText(context, "please connect bluetooth ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cv_selectSetting:
                Toast.makeText(context, "please connect bluetooth ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cv_send:
                //  Utility.launchActivity(getActivity(), HomeworkListActivity.class, false);
                break;


        }
    }
}
