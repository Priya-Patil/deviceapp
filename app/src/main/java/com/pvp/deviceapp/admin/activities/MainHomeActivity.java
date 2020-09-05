package com.pvp.deviceapp.admin.activities;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.NavUtils;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pvp.deviceapp.MainActivity;

import com.pvp.deviceapp.R;
import com.pvp.deviceapp.admin.fragment.DataListActivity;
import com.pvp.deviceapp.utils.PrefManager;
import com.pvp.deviceapp.utils.Utility;

import java.lang.ref.WeakReference;

public class MainHomeActivity extends AppCompatActivity  implements View.OnClickListener{

    PrefManager prefManager;
    Activity context;
    TextView et_title;
    CardView cv_bluetooth,cv_selectSetting,cv_repeatemood,cv_send,cv_start, cv_stop;
    BluetoothService bluetoothService;
    ChatAdapter chatAdapter;
    ListView chatListView;

    TextView emptyListTextView;;

    CoordinatorLayout coordinatorLayout;

    BluetoothDevice device;

    private boolean showMessagesIsChecked = true;
    private boolean autoScrollIsChecked = true;
    public static boolean showTimeIsChecked = true;
    //Snackbar snackTurnOn;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                bluetoothService.stop();
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_reconnect:
                reconnect();
                return true;
            case R.id.action_clear:
                chatAdapter.clear();
                return true;
            case R.id.checkable_auto_scroll:
                autoScrollIsChecked = !item.isChecked();
                item.setChecked(autoScrollIsChecked);
                return true;
            case R.id.checkable_show_messages:
                showMessagesIsChecked = !item.isChecked();
                item.setChecked(showMessagesIsChecked);
                return true;
            case R.id.checkable_show_time:
                showTimeIsChecked = !item.isChecked();
                item.setChecked(showTimeIsChecked);
                chatAdapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        // bindView();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        bindView();
        listener();

      /*  snackTurnOn = Snackbar.make(coordinatorLayout, "Bluetooth turned off", Snackbar.LENGTH_INDEFINITE)
                .setAction("Turn On", new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        enableBluetooth();
                    }
                });*/

        et_title.setSelected(true);


        ToggleButton toggle = (ToggleButton) findViewById(R.id.toggle);
        // Set a checked change listener for toggle button
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    Toast.makeText(MainHomeActivity.this,
                            "on", Toast.LENGTH_SHORT).show();

                    sendMessage("i"+System.getProperty("line.separator"));

                  /*  prefManager.setAction("repeate");
                    Utility.launchActivity(MainHomeActivity.this, MainActivity.class, false);
*/
                }
                else{

                    Toast.makeText(MainHomeActivity.this,
                            "off", Toast.LENGTH_SHORT).show();
                    sendMessage("j"+System.getProperty("line.separator"));


                }
            }
        });

        ToggleButton toggle2 = (ToggleButton) findViewById(R.id.toggle2);
        // Set a checked change listener for toggle button
        toggle2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(MainHomeActivity.this,
                            "on", Toast.LENGTH_SHORT).show();

                    sendMessage("d"+System.getProperty("line.separator"));

                    /*prefManager.setAction("feeder");
                    Utility.launchActivity(MainHomeActivity.this, MainActivity.class, false);
*/
                }
                else{
                    Toast.makeText(MainHomeActivity.this,
                            "off", Toast.LENGTH_SHORT).show();
                    sendMessage("e"+System.getProperty("line.separator"));

                }
            }
        });

        chatAdapter = new ChatAdapter(this);
        chatListView.setEmptyView(emptyListTextView);
        chatListView.setAdapter(chatAdapter);


        myHandler handler = new myHandler(MainHomeActivity.this);
        assert getSupportActionBar() != null; // won't be null, lint error
//      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        device = getIntent().getExtras().getParcelable(NewConstants.EXTRA_DEVICE);

        Log.e( "onCreate: ", device.toString() );

        bluetoothService = new BluetoothService(handler, device);

    }

    @Override protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mReceiver, filter);

        bluetoothService.connect();


        Log.d(NewConstants.TAG, "Connecting");
    }

    @Override protected void onStop() {
        super.onStop();
        if (bluetoothService != null) {
            bluetoothService.stop();
            Log.d(NewConstants.TAG, "Stopping");
        }

        unregisterReceiver(mReceiver);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NewConstants.REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                setStatus("None");
            } else {
                setStatus("Error");
               /* Snackbar.make(coordinatorLayout, "Failed to enable bluetooth", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Try Again", new View.OnClickListener() {
                            @Override public void onClick(View v) {
                                enableBluetooth();
                            }
                        }).show();*/
            }
        }

    }

    private void listener() {
        cv_send.setOnClickListener(this);
        cv_stop.setOnClickListener(this);
        cv_start.setOnClickListener(this);
        cv_selectSetting.setOnClickListener(this);
        cv_repeatemood.setOnClickListener(this);
        cv_bluetooth.setOnClickListener(this);


    }

    private void bindView() {
        et_title = findViewById(R.id.et_title);
        cv_bluetooth = findViewById(R.id.cv_bluetooth);
        cv_repeatemood = findViewById(R.id.cv_repeatemood);
        cv_selectSetting = findViewById(R.id.cv_selectSetting);
        cv_start = findViewById(R.id.cv_start);
        cv_stop = findViewById(R.id.cv_stop);
        cv_send = findViewById(R.id.cv_send);
        chatListView = findViewById(R.id.chat_list_view);
        prefManager=new PrefManager(MainHomeActivity.this);
    }




    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {

                default:
                    break;
                case R.id.navigation_home:
                    Utility.launchActivity(MainHomeActivity.this, MainHomeActivity.class,true);

                    return true;

                case R.id.navigation_bl:
                    Utility.launchActivity(MainHomeActivity.this, MainActivity.class,false);
                    return true;
                case R.id.navigation_add:

                    Utility.launchActivity(MainHomeActivity.this, BluetoothActivity.class,false);

                    return true;

                case R.id.navigation_list:

                    Utility.launchActivity(MainHomeActivity.this, DataListActivity.class,false);
                    return true;

                case R.id.navigation_setting:
                     return true;
            }
            return false;
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ll_homework:
                //  Utility.launchActivity(getActivity(), HomeworkListActivity.class, false);
                break;
            case R.id.cv_bluetooth:

                prefManager.setAction("add");
                Utility.launchActivity(MainHomeActivity.this, MainActivity.class, false);
                break;
            case R.id.cv_repeatemood:
               // sendMessage("i/j"+System.getProperty("line.separator"));
                break;
            case R.id.cv_start:
                Toast.makeText(MainHomeActivity.this,
                        "Started", Toast.LENGTH_SHORT).show();

                sendMessage("b"+System.getProperty("line.separator"));
                break;
            case R.id.cv_stop:
                Toast.makeText(MainHomeActivity.this,
                        "Stopped", Toast.LENGTH_SHORT).show();

                sendMessage("a"+System.getProperty("line.separator"));
                break;
            case R.id.cv_selectSetting:

                Intent intent = new Intent(MainHomeActivity.this, DataListActivity.class);
                intent.putExtra(NewConstants.EXTRA_DEVICE, device);
                startActivity(intent);
                finish();
               // Utility.launchActivity(MainHomeActivity.this, DataListActivity.class, false);
                break;
            case R.id.cv_send:
               // sendMessage("d/e"+System.getProperty("line.separator"));
                break;


        }
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                       // snackTurnOn.show();
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                      //  if (snackTurnOn.isShownOrQueued()) snackTurnOn.dismiss();
                        break;
                    case BluetoothAdapter.STATE_ON:
                        reconnect();
                }
            }
        }
    };


    private void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        Log.e( "chk: ", String.valueOf(bluetoothService.getState()));

        if (bluetoothService.getState() != NewConstants.STATE_CONNECTED) {
          /*  Snackbar.make(coordinatorLayout, "You are not connected", Snackbar.LENGTH_LONG)
                    .setAction("Connect", new View.OnClickListener() {
                        @Override public void onClick(View v) {
                            reconnect();
                        }
                    }).show();
*/
            return;
        } else {
            byte[] send = message.getBytes();
            bluetoothService.write(send);
        }
    }
    private static class myHandler extends Handler {
        private final WeakReference<MainHomeActivity> mActivity;

        public myHandler(MainHomeActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {

            final MainHomeActivity activity = mActivity.get();

            switch (msg.what) {
                case NewConstants.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case NewConstants.STATE_CONNECTED:
                            activity.setStatus("Connected");
                            //   activity.reconnectButton.setVisible(false);
                            //   activity.toolbalProgressBar.setVisibility(View.GONE);
                            break;
                        case NewConstants.STATE_CONNECTING:
                            activity.setStatus("Connecting");
                            // activity.toolbalProgressBar.setVisibility(View.VISIBLE);
                            break;
                        case NewConstants.STATE_NONE:
                            activity.setStatus("Not Connected");
                            //  activity.toolbalProgressBar.setVisibility(View.GONE);
                            break;
                        case NewConstants.STATE_ERROR:
                            activity.setStatus("Error");
//                            activity.reconnectButton.setVisible(true);
                            //   activity.toolbalProgressBar.setVisibility(View.GONE);
                            break;
                    }
                    break;
                case NewConstants.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    ChatMessage messageWrite = new ChatMessage("Me", writeMessage);
                    activity.addMessageToAdapter(messageWrite);
                    break;
                case NewConstants.MESSAGE_READ:

                    String readMessage = (String) msg.obj;

                    if (readMessage != null && activity.showMessagesIsChecked) {
                        ChatMessage messageRead = new ChatMessage(activity.device.getName(), readMessage.trim());
                        activity.addMessageToAdapter(messageRead);

                    }
                    break;

                case NewConstants.MESSAGE_SNACKBAR:
                   /* Snackbar.make(activity.coordinatorLayout, msg.getData().getString(NewConstants.SNACKBAR), Snackbar.LENGTH_LONG)
                            .setAction("Connect", new View.OnClickListener() {
                                @Override public void onClick(View v) {
                                    activity.reconnect();
                                }
                            }).show();
*/
                    break;
            }
        }



    }


    private void reconnect() {
       // reconnectButton.setVisible(false);
        bluetoothService.stop();
        bluetoothService.connect();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        bluetoothService.stop();
        Utility.launchActivity(MainHomeActivity.this,AdminHomeActivity.class,true);
    }


    private void addMessageToAdapter(ChatMessage chatMessage) {
        chatAdapter.add(chatMessage);
        if (autoScrollIsChecked) scrollChatListViewToBottom();
    }


    private void scrollChatListViewToBottom() {
        chatListView.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                chatListView.smoothScrollToPosition(chatAdapter.getCount() - 1);
            }
        });
    }


    private void setStatus(String status) {
        //  toolbar.setSubtitle(status);
    }


    private void enableBluetooth() {
        setStatus("Enabling Bluetooth");
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, NewConstants.REQUEST_ENABLE_BT);
    }


}
