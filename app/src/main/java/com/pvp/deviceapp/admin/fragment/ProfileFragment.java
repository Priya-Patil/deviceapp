package com.pvp.deviceapp.admin.fragment;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pvp.deviceapp.R;

import com.pvp.deviceapp.utils.PrefManager;


import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {


    private static final String TAG = "ProfileFragment";
    FloatingActionButton fab_edit_profile;
    TextView tv_name,tv_mobile,tv_email,tv_address,tv_setting,tv_aboutus,tv_logout;

    TextView tv_classname;

    PrefManager prefManager;
    ProgressDialog progressDialog;
    Dialog resultbox;
    CircleImageView iv_male_avatar;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_profile, container, false);

        bindView(view);
        listener();


        return view;
    }

    private void listener() {
        fab_edit_profile.setOnClickListener(this);
        tv_setting.setOnClickListener(this);
        tv_aboutus.setOnClickListener(this);
        tv_logout.setOnClickListener(this);
    }

    private void bindView(View view) {
        fab_edit_profile= view.findViewById(R.id.fab_edit_profile);
        tv_name= view.findViewById(R.id.tv_name);
      //  iv_male_avatar= view.findViewById(R.id.iv_male_avatar);
        tv_mobile= view.findViewById(R.id.tv_mobile);
        tv_email= view.findViewById(R.id.tv_email);
        tv_address= view.findViewById(R.id.tv_address);
        tv_classname= view.findViewById(R.id.tv_classname);
        tv_setting= view.findViewById(R.id.tv_setting);
        tv_aboutus= view.findViewById(R.id.tv_aboutus);
        tv_logout= view.findViewById(R.id.tv_logout);
        prefManager=new PrefManager(getActivity());
        progressDialog = new ProgressDialog(getActivity());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
          /*  case R.id.fab_edit_profile:
                if (prefManager.getROLE_ID()==5)
                {
                    Utility.launchActivity(getActivity(), GuestProfileActivity.class, false);
                }
                else {
                    Utility.launchActivity(getActivity(), ProfileActivity.class, false);
                }
                break;

            case R.id.tv_setting:

                break;
            case R.id.tv_aboutus:
                Utility.launchActivity(getActivity(), AboutUsActivity.class,false);

                break;
            case R.id.tv_logout:

                showCustomDialogLogout();

                break;
*/
        }
    }

}
