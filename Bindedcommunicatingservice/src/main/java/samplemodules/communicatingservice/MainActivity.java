package samplemodules.communicatingservice;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

     private ToggleButton TgnButton;
     private Button BtnSendtoService;
     private TextView TxtEcho;
    private EditText EdtSendToService;
    EchoService mService = null;

    /** Flag indicating whether we have called bind on the service. */
    boolean mBound;
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the object we can use to
            // interact with the service.  We are communicating with the
            // service using a Messenger, so here we get a client-side
            // representation of that from the raw IBinder object.
            EchoService.MyBinder binder = (EchoService.MyBinder) service;
            mService = binder.getService();
            Log.i(" Service Connection", "enteren an d binded");
            mBound = true;
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            mService = null;
            mBound = false;
            Log.i(" Service Connection", "unbinded");

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EdtSendToService=(EditText) findViewById(R.id.EdtSentoService);
        //starting service
        TxtEcho=(TextView) findViewById(R.id.TxtDataFromService);
        TgnButton=(ToggleButton) findViewById(R.id.tglBtnStartStopService);
        TgnButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    Intent intent = new Intent(MainActivity.this, EchoService.class);
                   // startService(intent);
                    boolean flag = bindService(intent,mConnection,Context.BIND_AUTO_CREATE);
                    Log.d("Result of bindservice",":"+flag);
                } else {
                    // The toggle is disabled
//                    Intent intent = new Intent(MainActivity.this, EchoService.class);
//                    stopService(intent);
                    unbindService(mConnection);
                    mBound = false;

                }
            }
        });

        //service onDestroy callback method will be called
        BtnSendtoService=(Button) findViewById(R.id.BtnSendData);
        BtnSendtoService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Log.d(" buttonclicked", "mbound is "+mBound);
                if (mBound)
                {
                    mService.newstring(EdtSendToService.getText().toString());
                }
               // Intent intent = new Intent(MainActivity.this, EchoService.class);
                //stopService(intent);
               // publishResults(EdtSendToService.getText().toString(),RESULT_OK);
            }
        });

    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String string = bundle.getString(EchoService.EchoString);
                int resultCode = bundle.getInt(EchoService.RESULT);
                if (resultCode == RESULT_OK) {
                //    Toast.makeText(MainActivity.this,
                  //          "Download complete. Download URI: " + string,
                    //        Toast.LENGTH_LONG).show();
                    TxtEcho.setText(string);
                } else {
                    Toast.makeText(MainActivity.this, "Download failed",
                            Toast.LENGTH_LONG).show();
                    TxtEcho.setText("No Answer");
                }
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(
                EchoService.NOTIFICATION));
        Log.d("BroadCastReceiver","Registered");
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }



}
