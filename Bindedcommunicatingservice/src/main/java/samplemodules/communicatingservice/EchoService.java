package samplemodules.communicatingservice;

/**
 * Created by fbald on 03/11/2016.
 */

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import static android.app.Activity.RESULT_OK;

public class EchoService extends Service {

    private static final String TAG = "EchoService";
    public static final String NOTIFICATION = "samplemodules.communicatingservice.receiver";
    public static final String EchoString = "EchoString";
    public static final String NOTIFICATIONToService= "samplemodules.communicatingservice.ServiceReceiveraa";
    public static final String MessageToService = "EchoString";
    private final IBinder mBinder = new MyBinder();

    public String NextString;
    private boolean newnextstring;
    public static final String RESULT = "Result";
    public boolean StopNow=false;

    private boolean isRunning  = false;

    @Override
    public void onCreate() {
        Log.i(TAG, "Service onCreate");

        isRunning = true;
   //     registerReceiver(Servicereceiver, new IntentFilter(
   //             EchoService.NOTIFICATIONToService));
        newnextstring=false;

    }
    public void newstring(String nextString)
    {
        NextString=nextString;
        newnextstring=true;
        publishResults(NextString, RESULT_OK);
        Log.d("EchoService","sending nexstring");

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i("EchoService", "Service onStartCommand");

        //Creating new thread for my service
        //Always write your long running tasks in a separate thread, to avoid ANR
        new Thread(new Runnable() {
            @Override
            public void run() {


                //Your logic that service will perform will be placed here
                //In this example we are just looping and waits for 1000 milliseconds in each loop.
                for (int i = 0; i < 5; i++) {
                    try {
                        Thread.sleep(1000);
                        if (newnextstring) {
                            Log.d("EchoService","sending nexstring");
                            publishResults(NextString, RESULT_OK);
                            newnextstring=false;
                        }
                        else {
                            publishResults("counting " + i, RESULT_OK);
                            Log.d("EchoService","counting " + i);

                        }
                        if (StopNow)
                            i=5;
                    } catch (Exception e) {
                    }

                    if(isRunning){
                        Log.i(TAG, "Service running");
                    }
                }

                //Stop service once it finishes its task
                stopSelf();
            }
        }).start();

        return Service.START_STICKY;
    }


    @Override
    public void onDestroy() {

        isRunning = false;
      //  unregisterReceiver(Servicereceiver);
        Log.i(TAG, "Service onDestroy");
    }
    private void publishResults(String EchoStringData, int result) {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(EchoString, EchoStringData);
        intent.putExtra(RESULT, result);
        sendBroadcast(intent);
    }

    public class MyBinder extends Binder {
        EchoService getService() {
            return EchoService.this;
        }
    }
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
