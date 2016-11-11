package samplemodules.fileaccess;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Button btnSavetofile;
    private Button btnLoadFromfile;
    private EditText EdtFileContents;
    private EditText EdtFileName;
    private final int  MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 23546;
    private boolean haswrite_permission = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSavetofile=(Button) findViewById(R.id.BtnSaveFile);
        btnLoadFromfile = (Button)findViewById(R.id.BtnReadFromFile) ;
        EdtFileContents= (EditText) findViewById(R.id.EdtFileText) ;
        EdtFileName= (EditText) findViewById(R.id.EdtFilename) ;

        btnSavetofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!haswrite_permission) {
                    Toast.makeText(getBaseContext(), " Cannot write, no permission", Toast.LENGTH_LONG).show();
                } else {
                    if (isExternalStorageReadable() && isExternalStorageWritable()) {
                        String filepath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();

            /*Write to file*/
                        try {
                            String filename = EdtFileName.getText().toString();
                            File file = new File(filepath,filename );
                            FileWriter fileWriter = new FileWriter(file);
                            String tempstr = EdtFileContents.getText().toString();
                            fileWriter.append(tempstr);
                            fileWriter.flush();
                            fileWriter.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(getBaseContext(), " Error in writing to file", Toast.LENGTH_LONG).show();

                        }


                    }
                    else{
                        Toast.makeText(getBaseContext(), " Cannot write, External Storage Not Writable", Toast.LENGTH_LONG).show();

                    }

                }
            }
        });

        btnLoadFromfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!haswrite_permission) {
                    Toast.makeText(getBaseContext(), " Cannot write, no permission", Toast.LENGTH_LONG).show();
                } else {
                    if (isExternalStorageReadable()) {
                        String filepath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();

                        try {
                            char [] filetext = new char[500];
                            File file = new File(filepath, EdtFileName.getText().toString());
                            FileReader fileReader= new FileReader(file);
                            fileReader.read(filetext);
                            String FiletextString = new String(filetext);
                            EdtFileContents.setText(FiletextString);
                            fileReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(getBaseContext(), " Error in writing to file", Toast.LENGTH_LONG).show();

                        }


                    }
                    else{
                        Toast.makeText(getBaseContext(), " Cannot write, External Storage Not Writable", Toast.LENGTH_LONG).show();

                    }

                }
            }
        });


        AskPermission();

    }


    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    protected void AskPermission()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
                // Show an expanation to the user *asynchronously* -- don't block this thread waiting for the user's response! After the user sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the result of the request.
            }
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)      	{
                    // permission was granted, yay! Do the contacts-related task you need to do.
                    haswrite_permission=true;
                } else {
                    // permission denied, boo! Disable the functionality that depends on this permission.
                }
                return;
            }
// other 'case' lines to check for other permissions this app might request
        }
    }
}
