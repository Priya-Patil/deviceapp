package com.pvp.deviceapp.admin.fragment;


import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pvp.database.DatabaseHelper;
import com.pvp.deviceapp.R;


import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsListFragment extends Fragment implements View.OnClickListener {

    DatabaseHelper dbHelper;
    RecyclerView recycler_view_home;
    private ArrayList<DetailsModel> model;
    DetailsAdapter detailsAdapter;
    ProgressDialog progressDialog;
    private ArrayList<DetailsModel> mCustList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_details_listt, container, false);

        bindView(view);
        listener();
        BindCustomerData();


        return view;
    }

    private void listener() {

    }


    private void bindView(View view) {
        dbHelper=new DatabaseHelper(getActivity());
        progressDialog=new ProgressDialog(getActivity());
        recycler_view_home =  view.findViewById(R.id.recycler_view_home);


    }

    private ArrayList<DetailsModel> arrayListProduct() {

        progressDialog.dismiss();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String query = "SELECT * FROM " +dbHelper.TABLE_NEW_CUSTOMER+ " where isactive='1' " ;

        Cursor cursor = db.rawQuery(query, null);

        ArrayList<DetailsModel> list = new ArrayList<>();

        mCustList=list;

        while (cursor.moveToNext()) {

            int index = cursor.getColumnIndex(dbHelper.CUST_1);
            int index2 = cursor.getColumnIndex(dbHelper.CUST_2);
            int index3 = cursor.getColumnIndex(dbHelper.CUST_3);
            int index4 = cursor.getColumnIndex(dbHelper.CUST_4);
            int index5 = cursor.getColumnIndex(dbHelper.CUST_5);
            int index11 = cursor.getColumnIndex(dbHelper.CUST_11);

            int id = cursor.getInt(index);
            String customerName =cursor.getString(index2);
            String mobileNo = cursor.getString(index3);
            String address = cursor.getString(index4);
            String productName = cursor.getString(index5);
            String title = cursor.getString(index11);


            //DataBean bean = new DataBean(id,userId,serviceName,cityName,workingHours,monthlyCharges);
            //list.add(bean);

            DetailsModel detailsModel =new DetailsModel(id,customerName,
                    mobileNo,address,productName, productName,productName,productName,productName,productName, title);
            list.add(detailsModel);

        }

        return list;
    }

    private  void  BindCustomerData()
    {
        model=arrayListProduct();

        recycler_view_home.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());//,LinearLayoutManager.VERTICAL,true);
        recycler_view_home.setLayoutManager(mLayoutManager);
        recycler_view_home.setItemAnimator(new DefaultItemAnimator());

      //  detailsAdapter =new DetailsAdapter(getActivity(),model);
        recycler_view_home.setAdapter(detailsAdapter);
    }

    @Override
    public void onClick(View v) {

            switch (v.getId()) {
                default:

                case R.id.btn_submit:
                    break;

        }
    }


}
