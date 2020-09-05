package com.pvp.deviceapp;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;
import com.pvp.deviceapp.admin.activities.AdminHomeActivity;
import com.pvp.deviceapp.admin.activities.BluetoothActivity;
import com.pvp.deviceapp.admin.activities.BluetoothDevicesAdapter;
import com.pvp.deviceapp.admin.activities.MainHomeActivity;
import com.pvp.deviceapp.admin.activities.NewConstants;
import com.pvp.deviceapp.admin.activities.SendDataActivity;
import com.pvp.deviceapp.admin.fragment.DataListActivity;
import com.pvp.deviceapp.utils.PrefManager;
import com.pvp.deviceapp.utils.Utility;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class MainActivity extends AppCompatActivity {


    BluetoothAdapter bluetoothAdapter;

    BluetoothDevicesAdapter bluetoothDevicesAdapter;

    // @BindView(R.id.toolbar)
    // Toolbar toolbar;
    @BindView(R.id.devices_list_view)
    ListView devicesListView;
    @BindView(R.id.empty_list_item)
    TextView emptyListTextView;
    // @BindView(R.id.toolbar_progress_bar)
    // ProgressBar toolbarProgressCircle;
    @BindView(R.id.coordinator_layout_main)
    CoordinatorLayout coordinatorLayout;

    private Button mScanBtn;
    private Button mOffBtn, mDiscoverBtn;

    private ArrayAdapter<String> mBTArrayAdapter;
    private final static int REQUEST_ENABLE_BT = 1; // used to identify adding bluetooth names

    PrefManager prefManager;
    @OnClick(R.id.search_button) void search() {

        if (bluetoothAdapter.isEnabled()) {
            // Bluetooth enabled
            startSearching();
        } else {

            enableBluetooth();
        }
    }
    private void enableBluetooth() {
        setStatus("Enabling Bluetooth");
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, NewConstants.REQUEST_ENABLE_BT);
    }


    @OnItemClick(R.id.devices_list_view) void onItemClick(int position) {
        setStatus("Asking to connect");
        final BluetoothDevice device = bluetoothDevicesAdapter.getItem(position);

        new AlertDialog.Builder(MainActivity.this)
                .setCancelable(false)
                .setTitle("Connect")
                .setMessage("Do you want to connect to: " + device.getName() + " - " + device.getAddress())
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        Log.d(NewConstants.TAG, "Opening new Activity");
                        bluetoothAdapter.cancelDiscovery();
                        //  toolbarProgressCircle.setVisibility(View.INVISIBLE);

                        if(prefManager.getAction().equals("add"))
                        {
                           // Intent intent = new Intent(MainActivity.this, BluetoothActivity.class);
                            Intent intent = new Intent(MainActivity.this, MainHomeActivity.class);
                            intent.putExtra(NewConstants.EXTRA_DEVICE, device);
                            startActivity(intent);
                            finish();
                        }
                        if(prefManager.getAction().equals("add_data"))
                        {
                            Intent intent = new Intent(MainActivity.this, BluetoothActivity.class);
                            intent.putExtra(NewConstants.EXTRA_DEVICE, device);
                            startActivity(intent);
                            finish();
                        }
                        else  if(prefManager.getAction().equals("repeate"))
                        {
                            Intent intent = new Intent(MainActivity.this, SendDataActivity.class);
                            intent.putExtra(NewConstants.EXTRA_DEVICE, device);
                            startActivity(intent);
                            finish();
                           // Utility.launchActivity(MainActivity.this, SendDataActivity.class, true);
                        }
                        else  if(prefManager.getAction().equals("feeder"))
                        {
                            Intent intent = new Intent(MainActivity.this, SendDataActivity.class);
                            intent.putExtra(NewConstants.EXTRA_DEVICE, device);
                            startActivity(intent);
                            finish();
                           // Utility.launchActivity(MainActivity.this, SendDataActivity.class, true);
                        }
                        else  if(prefManager.getAction().equals("start"))
                        {
                            Intent intent = new Intent(MainActivity.this, SendDataActivity.class);
                            intent.putExtra(NewConstants.EXTRA_DEVICE, device);
                            startActivity(intent);
                            finish();
                           // Utility.launchActivity(MainActivity.this, SendDataActivity.class, true);
                        }
                        else  if(prefManager.getAction().equals("stop"))
                        {
                            Intent intent = new Intent(MainActivity.this, SendDataActivity.class);
                            intent.putExtra(NewConstants.EXTRA_DEVICE, device);
                            startActivity(intent);
                            finish();
                           // Utility.launchActivity(MainActivity.this, SendDataActivity.class, true);
                        }

                        else  if(prefManager.getAction().equals("senddata"))
                        {
                            Intent intent = new Intent(MainActivity.this, SendDataActivity.class);
                            intent.putExtra(NewConstants.EXTRA_DEVICE, device);
                            startActivity(intent);
                            finish();
                           // Utility.launchActivity(MainActivity.this, SendDataActivity.class, true);
                        }

                        else  if(prefManager.getAction().equals("list"))
                        {
                            Intent intent = new Intent(MainActivity.this, DataListActivity.class);
                            intent.putExtra(NewConstants.EXTRA_DEVICE, device);
                            startActivity(intent);
                            finish();
                           // Utility.launchActivity(MainActivity.this, SendDataActivity.class, true);
                        }

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        setStatus("Cancelled connection");
                        Log.d(NewConstants.TAG, "Cancelled ");
                    }
                }).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        //   setSupportActionBar(toolbar);

        setStatus("None");
        mScanBtn = (Button)findViewById(R.id.scan);
        mOffBtn = (Button)findViewById(R.id.off);
        mDiscoverBtn = (Button)findViewById(R.id.discover);

        bluetoothDevicesAdapter = new BluetoothDevicesAdapter(this);
        prefManager = new PrefManager(MainActivity.this);
        mBTArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);

        devicesListView.setAdapter(bluetoothDevicesAdapter);
        devicesListView.setEmptyView(emptyListTextView);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {

            Log.e(NewConstants.TAG, "Device has no bluetooth");
            new AlertDialog.Builder(MainActivity.this)
                    .setCancelable(false)
                    .setTitle("No Bluetooth")
                    .setMessage("Your device has no bluetooth")
                    .setPositiveButton("Close app", new DialogInterface.OnClickListener() {
                        @Override public void onClick(DialogInterface dialog, int which) {
                            Log.d(NewConstants.TAG, "App closed");
                            finish();
                        }
                    }).show();

        }

        mScanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bluetoothOn(v);
            }
        });

        mOffBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                bluetoothOff(v); ;
            }
        });

        mDiscoverBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                discover(v);
            }
        });

    }

    private void discover(View view){
        // Check if the device is already discovering
        if(bluetoothAdapter.isDiscovering()){
            bluetoothAdapter.cancelDiscovery();
            Toast.makeText(getApplicationContext(),"Discovery stopped", Toast.LENGTH_SHORT).show();
        }
        else{
            if(bluetoothAdapter.isEnabled()) {
                mBTArrayAdapter.clear(); // clear items
                bluetoothAdapter.startDiscovery();
                Toast.makeText(getApplicationContext(), "Discovery started", Toast.LENGTH_SHORT).show();
                registerReceiver(blReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));

                devicesListView.setAdapter(mBTArrayAdapter);

            }
            else{
                Toast.makeText(getApplicationContext(), "Bluetooth not on", Toast.LENGTH_SHORT).show();
            }
        }
    }

    final BroadcastReceiver blReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // add the name to the list
                mBTArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                mBTArrayAdapter.notifyDataSetChanged();
            }
        }
    };
    private void bluetoothOn(View view){
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            //  mBluetoothStatus.setText("Bluetooth enabled");
            Toast.makeText(getApplicationContext(),"Bluetooth turned on", Toast.LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(getApplicationContext(),"Bluetooth is already on", Toast.LENGTH_SHORT).show();
        }
    }



    private void bluetoothOff(View view){
        bluetoothAdapter.disable(); // turn off
        // mBluetoothStatus.setText("Bluetooth disabled");
        Toast.makeText(getApplicationContext(),"Bluetooth turned Off", Toast.LENGTH_SHORT).show();
    }

    @Override protected void onStart() {
        super.onStart();

        Log.d(NewConstants.TAG, "Registering receiver");
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mReceiver, filter);
    }

    @Override protected void onStop() {
        super.onStop();
        Log.d(NewConstants.TAG, "Receiver unregistered");
        unregisterReceiver(mReceiver);
    }


    private void setStatus(String status) {
        // toolbar.setSubtitle(status);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NewConstants.REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                startSearching();
            } else {
                setStatus("Error");
                Snackbar.make(coordinatorLayout, "Failed to enable bluetooth", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Try Again", new View.OnClickListener() {
                            @Override public void onClick(View v) {
                                enableBluetooth();
                            }
                        }).show();
            }
        }

    }

    private void startSearching() {
        if (bluetoothAdapter.startDiscovery()) {
            //   toolbarProgressCircle.setVisibility(View.VISIBLE);
            //  setStatus("Searching for devices");
        } else {
            // setStatus("Error");
            Snackbar.make(coordinatorLayout, "Failed to start searching", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Try Again", new View.OnClickListener() {
                        @Override public void onClick(View v) {
                            startSearching();
                        }
                    }).show();
        }
    }


    // Create a BroadcastReceiver for ACTION_FOUND
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                device.fetchUuidsWithSdp();

                if (bluetoothDevicesAdapter.getPosition(device) == -1) {
                    // -1 is returned when the item is not in the adapter
                    bluetoothDevicesAdapter.add(device);
                    bluetoothDevicesAdapter.notifyDataSetChanged();
                }

            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //  toolbarProgressCircle.setVisibility(View.INVISIBLE);
                setStatus("None");

            } else if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                      /*  10-08 Snackbar.make(coordinatorLayout, "Bluetooth turned off", Snackbar.LENGTH_INDEFINITE)
                                .setAction("Turn on", new View.OnClickListener() {
                                    @Override public void onClick(View v) {
                                        enableBluetooth();
                                    }
                                }).show();
                      */  break;
                }
            }
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utility.launchActivity(MainActivity.this, AdminHomeActivity.class, true);
    }
}


