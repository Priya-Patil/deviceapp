package com.pvp.deviceapp.admin.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.pvp.database.DatabaseHelper;

import com.pvp.deviceapp.R;
import com.pvp.deviceapp.utils.Utility;

/**
 * A simple {@link Fragment} subclass.
 */
public class FillDetailsFragment extends Fragment implements View.OnClickListener {

    DatabaseHelper dbHelper;
    Button btn_New_Save_setting;
    EditText et_Title, et_Internal_diameter,et_Number_of_terms, et_Wire_diameter, et_Start_wait_time,
            et_Stop_wait_time, et_Feed_speed,et_Die_Diameter;
    Fragment fragment;

    ImageView iv_backprofile;
    Button btn_Cancel;

    public FillDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_create_enquiry, container, false);

        bindView(view);
        listener();

        return view;
    }

    private void listener() {
        btn_New_Save_setting.setOnClickListener(this);
        btn_Cancel.setOnClickListener(this);

    }


    private void bindView(View view) {
        dbHelper = new DatabaseHelper(getActivity());
        btn_New_Save_setting = view.findViewById(R.id.btn_New_Save_setting);
        et_Internal_diameter = view.findViewById(R.id.et_Internal_diameter);
        et_Number_of_terms = view.findViewById(R.id.et_Number_of_terms);
        et_Wire_diameter = view.findViewById(R.id.et_Wire_diameter);
        et_Start_wait_time = view.findViewById(R.id.et_Start_wait_time);
        et_Stop_wait_time = view.findViewById(R.id.et_Stop_wait_time);
        et_Feed_speed = view.findViewById(R.id.et_Feed_speed);
        et_Die_Diameter = view.findViewById(R.id.et_Die_Diameter);
        et_Title = view.findViewById(R.id.et_Title);
        btn_Cancel = view.findViewById(R.id.btn_Cancel);
        iv_backprofile = view.findViewById(R.id.iv_backprofile);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            default:

            case R.id.btn_submit:
                addCustomer();
                break;

            case R.id.btn_Cancel:

                Toast.makeText(getActivity(), "vvvv", Toast.LENGTH_SHORT).show();
               /* if(mConnectedThread != null) //First check to make sure thread created
                    mConnectedThread.write( et_Title.getText().toString());*/

                break;

            case R.id.iv_backprofile:

                break;

            case R.id.btn_New_Save_setting:
               // Toast.makeText(getActivity(), "qqqqqqqqqq", Toast.LENGTH_SHORT).show();
                addCustomer();
                break;

        }
    }

    private void addCustomer() {
        //   if (!dbHelper.checkNumber(mobile)) {
        //  Log.e("Mobi: ", mobile);
        boolean isInserted = dbHelper.insertData(
                et_Internal_diameter.getText().toString(),
                et_Number_of_terms.getText().toString(),
                et_Wire_diameter.getText().toString(),
                et_Start_wait_time.getText().toString(),
                et_Stop_wait_time.getText().toString(),
                et_Feed_speed.getText().toString(),
                et_Die_Diameter.getText().toString(),
                "1",
                Utility.getCurrentDate(),
                et_Title.getText().toString()
        );


        if (isInserted == true) {

            //Toast.makeText(this, "Data Inserted Successfully..", Toast.LENGTH_SHORT).show();

            // code to show inserted record id and name
            int id = 0;
            String name = "";

            SQLiteDatabase db = dbHelper.getWritableDatabase();

            Cursor cursor = db.rawQuery("SELECT  * FROM " + dbHelper.TABLE_NEW_CUSTOMER,
                    null);

            if (cursor.moveToLast()) {

                id = cursor.getInt(0);//to get id, 0 is the column index
                name = cursor.getString(1);
            }
            Toast.makeText(getActivity(), "Added Successfully.. " + id, Toast.LENGTH_LONG).show();
            //startActivity(new Intent(this, HomepageActivity.class));
            fragment = new DetailsListFragment();
            loadFragment(fragment);
         //   Utility.launchActivity(getActivity(), AdminHomeActivity.class, true);


            //  Utility.launchActivity(getActivity(), MainActivity.class, true);
            //onBackPressed();

        } else {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }


    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        //  transaction.addToBackStack(null);
        transaction.commit();
    }


    public void history( View v) {

        Intent intent = new Intent(getActivity(),Main2Activity.class);
        startActivity(intent);
    }



}


