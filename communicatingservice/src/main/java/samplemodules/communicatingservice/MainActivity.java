package samplemodules.communicatingservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
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
    private void publishResults(String EchoStringData, int result) {
        Intent intent = new Intent(EchoService.NOTIFICATIONToService);
        intent.putExtra(EchoService.EchoString, EchoStringData);
        intent.putExtra(EchoService.RESULT, result);
        Log.i("APP", "Click and sand");
        sendBroadcast(intent);
    }

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
                    startService(intent);
                } else {
                    // The toggle is disabled
                    Intent intent = new Intent(MainActivity.this, EchoService.class);
                    stopService(intent);

                }
            }
        });

        //service onDestroy callback method will be called
        BtnSendtoService=(Button) findViewById(R.id.BtnSendData);
        BtnSendtoService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent = new Intent(MainActivity.this, EchoService.class);
                //stopService(intent);
                publishResults(EdtSendToService.getText().toString(),RESULT_OK);
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
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }



}
