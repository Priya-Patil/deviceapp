package com.pvp.deviceapp.admin.activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.pvp.database.DatabaseHelper;
import com.pvp.deviceapp.R;
import com.pvp.deviceapp.utils.Utility;


import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BluetoothActivity extends AppCompatActivity implements View.OnClickListener {

    BluetoothService bluetoothService;
    BluetoothDevice device;

    @BindView(R.id.edit_text)
    EditText editText;
    @BindView(R.id.send_button)
    Button sendButton;
    @BindView(R.id.chat_list_view)
    ListView chatListView;
    //@BindView(R.id.toolbar)
    // Toolbar toolbar;
    @BindView(R.id.empty_list_item)
    TextView emptyListTextView;
    //  @BindView(R.id.toolbar_progress_bar)
    //  ProgressBar toolbalProgressBar;
    @BindView(R.id.coordinator_layout_bluetooth)
    CoordinatorLayout coordinatorLayout;

    MenuItem reconnectButton;
    ChatAdapter chatAdapter;

    Snackbar snackTurnOn;

    private boolean showMessagesIsChecked = true;
    private boolean autoScrollIsChecked = true;
    public static boolean showTimeIsChecked = true;


    @OnClick(R.id.send_button) void send() {
        // Send a item_message using content of the edit text widget
        String message = editText.getText().toString();
        if (message.trim().length() == 0) {
            editText.setError("Enter text first");
        } else {
            sendMessage(message);
            editText.setText("");
        }
    }

    DatabaseHelper dbHelper;
    Button btn_New_Save_setting;
    EditText et_Title, et_Internal_diameter,et_Number_of_terms, et_Wire_diameter, et_Start_wait_time,
            et_Stop_wait_time, et_Feed_speed,et_Die_Diameter;
    Fragment fragment;

    ImageView iv_backprofile;
    Button btn_Cancel, btn_Send;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);


        ButterKnife.bind(this);

        bindView();
        listener();
        editText.setError("Enter text first");

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    send();
                    return true;
                }
                return false;
            }
        });

        snackTurnOn = Snackbar.make(coordinatorLayout, "Bluetooth turned off", Snackbar.LENGTH_INDEFINITE)
                .setAction("Turn On", new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        enableBluetooth();
                    }
                });



        chatAdapter = new ChatAdapter(this);
        chatListView.setEmptyView(emptyListTextView);
        chatListView.setAdapter(chatAdapter);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //  setSupportActionBar(toolbar);

        myHandler handler = new myHandler(BluetoothActivity.this);


        assert getSupportActionBar() != null; // won't be null, lint error
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        device = getIntent().getExtras().getParcelable(NewConstants.EXTRA_DEVICE);

        bluetoothService = new BluetoothService(handler, device);

        setTitle(device.getName());
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
                Snackbar.make(coordinatorLayout, "Failed to enable bluetooth", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Try Again", new View.OnClickListener() {
                            @Override public void onClick(View v) {
                                enableBluetooth();
                            }
                        }).show();
            }
        }

    }


    private void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (bluetoothService.getState() != NewConstants.STATE_CONNECTED) {
            Snackbar.make(coordinatorLayout, "You are not connected", Snackbar.LENGTH_LONG)
                    .setAction("Connect", new View.OnClickListener() {
                        @Override public void onClick(View v) {
                            reconnect();
                        }
                    }).show();
            return;
        } else {
            byte[] send = message.getBytes();
            bluetoothService.write(send);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            default:

            case R.id.btn_New_Save_setting:
                if (et_Title.getText().toString().trim().length() == 0 ||
                        et_Title.getText().toString().trim().length() == 0 ||
                        et_Internal_diameter.getText().toString().trim().length() == 0 ||
                        et_Number_of_terms.getText().toString().trim().length() == 0 ||
                        et_Wire_diameter.getText().toString().trim().length() == 0 ||
                        et_Start_wait_time.getText().toString().trim().length() == 0 ||
                        et_Stop_wait_time.getText().toString().trim().length() == 0 ||
                        et_Feed_speed.getText().toString().trim().length() == 0 ||
                        et_Die_Diameter.getText().toString().trim().length() == 0
                ) {
                    //editText.setError("Enter text first");
                    Toast.makeText(this, "Enter text", Toast.LENGTH_SHORT).show();
                } else {

                    addCustomer();
                }
                break;

            case R.id.btn_Cancel:

                Utility.launchActivity(BluetoothActivity.this, AdminHomeActivity.class, true);
               /* if(mConnectedThread != null) //First check to make sure thread created
                    mConnectedThread.write( et_Title.getText().toString());*/

                break;

            case R.id.iv_backprofile:

                break;

            case R.id.btn_Send:

                // String message = editText.getText().toString();
                if (et_Title.getText().toString().trim().length() == 0 ||
                        et_Title.getText().toString().trim().length() == 0 ||
                        et_Internal_diameter.getText().toString().trim().length() == 0 ||
                        et_Number_of_terms.getText().toString().trim().length() == 0 ||
                        et_Wire_diameter.getText().toString().trim().length() == 0 ||
                        et_Start_wait_time.getText().toString().trim().length() == 0 ||
                        et_Stop_wait_time.getText().toString().trim().length() == 0 ||
                        et_Feed_speed.getText().toString().trim().length() == 0 ||
                        et_Die_Diameter.getText().toString().trim().length() == 0
                ) {
                    //editText.setError("Enter text first");
                    Toast.makeText(this, "Enter text", Toast.LENGTH_SHORT).show();
                } else {

                    // c`IN01 10`IN02 5`IN03 6`IN04 0.20`IN05 0.3`IN06 50`IN10 10`
                    String fullstring= "c`IN01 "+et_Internal_diameter.getText().toString()+
                            "`IN02 "+et_Number_of_terms.getText().toString()+
                            "`IN03 "+ et_Wire_diameter.getText().toString()+
                            "`IN04 "+et_Start_wait_time.getText().toString()+
                            "`IN05 "+et_Stop_wait_time.getText().toString()+
                            "`IN06 "+et_Feed_speed.getText().toString()+
                            "`IN10 "+et_Die_Diameter.getText().toString()+"`" ;

                    sendMessage(fullstring+System.getProperty("line.separator"));
                    editText.setText("");

                  /*  String fullstring= et_Title.getText().toString()+" "+et_Internal_diameter.getText().toString()+
                           " "+et_Number_of_terms.getText().toString()+" "+ et_Wire_diameter.getText().toString()
                           +" "+et_Start_wait_time.getText().toString()+" "+et_Stop_wait_time.getText().toString()+
                           " "+et_Feed_speed.getText().toString()+" "+et_Die_Diameter.getText().toString()+" " ;
                  */

                }
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
            Toast.makeText(BluetoothActivity.this, "Added Successfully.. " + id, Toast.LENGTH_LONG).show();
           /* fragment = new DetailsListFragment();
            loadFragment(fragment);
*/
            Utility.launchActivity(BluetoothActivity.this, AdminHomeActivity.class, true);

        } else {
            Toast.makeText(BluetoothActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bluetooth_menu, menu);
        reconnectButton = menu.findItem(R.id.action_reconnect);
        return true;
    }


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

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        snackTurnOn.show();
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        if (snackTurnOn.isShownOrQueued()) snackTurnOn.dismiss();
                        break;
                    case BluetoothAdapter.STATE_ON:
                        reconnect();
                }
            }
        }
    };

    private void setStatus(String status) {
        // toolbar.setSubtitle(status);
    }

    private void enableBluetooth() {
        setStatus("Enabling Bluetooth");
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, NewConstants.REQUEST_ENABLE_BT);
    }

    private void reconnect() {
        reconnectButton.setVisible(false);
        bluetoothService.stop();
        bluetoothService.connect();
    }



    private void listener() {
        btn_New_Save_setting.setOnClickListener(this);
        btn_Cancel.setOnClickListener(this);
        btn_Send.setOnClickListener(this);

    }


    private void bindView() {
        dbHelper = new DatabaseHelper(BluetoothActivity.this);
        btn_New_Save_setting = findViewById(R.id.btn_New_Save_setting);
        et_Internal_diameter = findViewById(R.id.et_Internal_diameter);
        et_Number_of_terms = findViewById(R.id.et_Number_of_terms);
        et_Wire_diameter = findViewById(R.id.et_Wire_diameter);
        et_Start_wait_time = findViewById(R.id.et_Start_wait_time);
        et_Stop_wait_time = findViewById(R.id.et_Stop_wait_time);
        et_Feed_speed = findViewById(R.id.et_Feed_speed);
        et_Die_Diameter = findViewById(R.id.et_Die_Diameter);
        et_Title = findViewById(R.id.et_Title);
        btn_Cancel = findViewById(R.id.btn_Cancel);
        iv_backprofile = findViewById(R.id.iv_backprofile);
        btn_Send = findViewById(R.id.btn_Send);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        bluetoothService.stop();

    }


    private static class myHandler extends Handler {
        private final WeakReference<BluetoothActivity> mActivity;

        public myHandler(BluetoothActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {

            final BluetoothActivity activity = mActivity.get();

            switch (msg.what) {
                case NewConstants.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case NewConstants.STATE_CONNECTED:
                            activity.setStatus("Connected");
                            //   activity.reconnectButton.setVisible(false);
                            // activity.toolbalProgressBar.setVisibility(View.GONE);
                            break;
                        case NewConstants.STATE_CONNECTING:
                            activity.setStatus("Connecting");
                            // activity.toolbalProgressBar.setVisibility(View.VISIBLE);
                            break;
                        case NewConstants.STATE_NONE:
                            activity.setStatus("Not Connected");
                            // activity.toolbalProgressBar.setVisibility(View.GONE);
                            break;
                        case NewConstants.STATE_ERROR:
                            activity.setStatus("Error");
                            try
                            {
                                activity.reconnectButton.setVisible(true);

                            }
                            catch (Exception e)
                            {

                            }

                            // activity.toolbalProgressBar.setVisibility(View.GONE);
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
                    Snackbar.make(activity.coordinatorLayout, msg.getData().getString(NewConstants.SNACKBAR), Snackbar.LENGTH_LONG)
                            .setAction("Connect", new View.OnClickListener() {
                                @Override public void onClick(View v) {
                                    activity.reconnect();
                                }
                            }).show();

                    break;
            }
        }


    }

}
