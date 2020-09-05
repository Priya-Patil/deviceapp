package com.pvp.deviceapp.admin.activities;

import android.Manifest;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import com.pvp.deviceapp.MainActivity;
import com.pvp.deviceapp.R;
import com.pvp.deviceapp.admin.fragment.HomeFragment;
import com.pvp.deviceapp.admin.fragment.ProfileFragment;
import com.pvp.deviceapp.utils.PrefManager;
import com.pvp.deviceapp.utils.Utility;


import java.util.List;

public class AdminHomeActivity extends AppCompatActivity {

    Fragment fragment;
    PrefManager prefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        prefManager=new PrefManager(AdminHomeActivity.this);
        // bindView();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(new HomeFragment());
        //  requestPermission();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {

                default:
                    break;
                case R.id.navigation_home:
                    //  toolbar.setTitle("Shop");
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.navigation_bl:
                    prefManager.setAction("add_data");
                    Utility.launchActivity(AdminHomeActivity.this, MainActivity.class,false);
                    /* fragment = new HomeSetupFragment();
                    loadFragment(fragment);*/
                    return true;


                case R.id.navigation_add:
                    // toolbar.setTitle("Profile");
                  /*  fragment = new FillDetailsFragment();
                    loadFragment(fragment);
                  */
                    Utility.launchActivity(AdminHomeActivity.this, BluetoothActivity.class,false);

                    return true;

                // Utility.launchActivity(AdminHomeActivity.this, BluetoothActivity.class,false);


                case R.id.navigation_list:
                    /*fragment = new DetailsListFragment();
                    loadFragment(fragment);
                    return true;*/
                    prefManager.setAction("list");
                    Utility.launchActivity(AdminHomeActivity.this, MainActivity.class,false);
                    return true;

                case R.id.navigation_setting:
                    // toolbar.setTitle("Profile");
                    fragment = new ProfileFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        //  transaction.addToBackStack(null);
        transaction.commit();
    }


    //  Runtime permission
    private void requestPermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            //Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            // showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }


                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! "+error, Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }



}
