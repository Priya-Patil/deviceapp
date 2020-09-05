package com.pvp.deviceapp.admin.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.pvp.deviceapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeSetupFragment extends Fragment implements View.OnClickListener {


    Activity context;

    CardView cv_student,cv_staff,cv_subject,cv_class,cv_assignSubject,cv_non_teachingstaff, cv_fee,cv_assignTeacher;

    public HomeSetupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        View view = inflater.inflate(R.layout.fragment_home_setup, container, false);

        bindView(view);
        btnListener();

        return view;
    }
    private void bindView(View view) {
        cv_student = view.findViewById(R.id.cv_student);
        cv_staff = view.findViewById(R.id.cv_staff);
        cv_subject= view.findViewById(R.id.cv_subject);
        cv_class=view.findViewById(R.id.cv_class);
        cv_non_teachingstaff=view.findViewById(R.id.cv_non_teachingstaff);
        cv_fee=view.findViewById(R.id.cv_fee);
        cv_assignSubject=view.findViewById(R.id.cv_assignSubject);
        cv_assignTeacher=view.findViewById(R.id.cv_assignTeacher);
      //  cv_batch=view.findViewById(R.id.cv_batch);
    }


    private void btnListener() {
        cv_student.setOnClickListener(this);
        cv_staff.setOnClickListener(this);
        cv_subject.setOnClickListener(this);
        cv_class.setOnClickListener(this);
        cv_non_teachingstaff.setOnClickListener(this);
        cv_fee.setOnClickListener(this);
        cv_assignSubject.setOnClickListener(this);
        cv_assignTeacher.setOnClickListener(this);
       // cv_batch.setOnClickListener(this);
    }



    public void onStart() {
        super.onStart();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {

        }
    }
}
