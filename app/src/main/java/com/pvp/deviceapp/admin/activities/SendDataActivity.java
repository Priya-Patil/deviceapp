package com.pvp.deviceapp.admin.activities;

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
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.NavUtils;

import com.google.android.material.snackbar.Snackbar;
import com.pvp.deviceapp.R;
import com.pvp.deviceapp.utils.PrefManager;
import com.pvp.deviceapp.utils.Utility;


import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SendDataActivity extends AppCompatActivity implements View.OnClickListener {

    PrefManager prefManager;
    BluetoothService bluetoothService;
    BluetoothDevice device;

    @BindView(R.id.edit_text)
    EditText editText;
    @BindView(R.id.send_button)
    Button sendButton;
    @BindView(R.id.chat_list_view)
    ListView chatListView;
  /*  @BindView(R.id.toolbar)
    Toolbar toolbar;
 */   @BindView(R.id.empty_list_item)
    TextView emptyListTextView;
   /* @BindView(R.id.toolbar_progress_bar)
    ProgressBar toolbalProgressBar;
   */ @BindView(R.id.coordinator_layout_bluetooth)
    CoordinatorLayout coordinatorLayout;

    MenuItem reconnectButton;
    ChatAdapter chatAdapter;

    Snackbar snackTurnOn;
    Button send_button;
    Button send_button2;

    private boolean showMessagesIsChecked = true;
    private boolean autoScrollIsChecked = true;
    public static boolean showTimeIsChecked = true;

    @OnClick(R.id.send_button) void send() {
        // Send a item_message using content of the edit text widget
           // sendMessage(message);

            if(prefManager.getAction().equals("repeate"))
            {
                sendMessage("i/j"+System.getProperty("line.separator"));
               // Toast.makeText(this, "repeate", Toast.LENGTH_SHORT).show();
            }
            else if(prefManager.getAction().equals("feeder"))
            {
                sendMessage("d/e"+System.getProperty("line.separator"));
              //  Toast.makeText(this, "feeder", Toast.LENGTH_SHORT).show();
            }
            else if(prefManager.getAction().equals("start"))
            {
                sendMessage("b"+System.getProperty("line.separator"));
              //  Toast.makeText(this, "start", Toast.LENGTH_SHORT).show();
            }
            else if(prefManager.getAction().equals("stop"))
            {
                sendMessage("a"+System.getProperty("line.separator"));
               // Toast.makeText(this, "stop", Toast.LENGTH_SHORT).show();
            } else if(prefManager.getAction().equals("senddata"))
            {
                sendMessage(prefManager.getTIME());
               // Toast.makeText(this, "stop", Toast.LENGTH_SHORT).show();
            }
          //  editText.setText("");

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senddata);


        ButterKnife.bind(this);
        prefManager=new PrefManager(SendDataActivity.this);
        editText.setError("Enter text first");
        editText.setVisibility(View.GONE);
        send_button2=findViewById(R.id.send_button2);

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

        myHandler handler = new myHandler(SendDataActivity.this);


        assert getSupportActionBar() != null; // won't be null, lint error
//      getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        device = getIntent().getExtras().getParcelable(NewConstants.EXTRA_DEVICE);

        bluetoothService = new BluetoothService(handler, device);

        setTitle(device.getName());

        send_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.launchActivity(SendDataActivity.this,AdminHomeActivity.class,true);

            }
        });

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
        Log.e( "chk: ", String.valueOf(bluetoothService.getState()));

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
    public void onClick(View view) {
        
    }


    private static class myHandler extends Handler {
        private final WeakReference<SendDataActivity> mActivity;

        public myHandler(SendDataActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {

            final SendDataActivity activity = mActivity.get();

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
      //  toolbar.setSubtitle(status);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        bluetoothService.stop();
        Utility.launchActivity(SendDataActivity.this,AdminHomeActivity.class,true);
    }
}
