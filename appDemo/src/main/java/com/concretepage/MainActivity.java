package com.concretepage;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Toast;

import com.concretepage.MyLocalService.LocalBinder;

import java.util.Date;
//Sourcecode from http://www.concretepage.com/android/android-local-bound-service-example-with-binder-and-serviceconnection
public class MainActivity extends Activity {
    MyLocalService localService;
    private boolean isBound = false; 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MyLocalService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (isBound) {
        	unbindService(connection);
        	isBound = false;
        }
    }
    public void dispalyDate(View v) {
    	if (isBound) {
	    	Date date = localService.getCurrentDate();
	        Toast.makeText(this, String.valueOf(date), Toast.LENGTH_SHORT).show();
    	}
    }
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
	        LocalBinder binder = (LocalBinder) service;
	        localService = binder.getService();
	        isBound = true;
        }
        @Override
        public void onServiceDisconnected(ComponentName arg0) {
        	isBound = false;
        }
    };
}