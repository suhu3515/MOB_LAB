package com.vhddev.moblab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.loader.content.AsyncTaskLoader;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class LocationActivity extends AppCompatActivity {

    TextView tv_addr;
    Button btn_getLoc;
    String s_latitude, s_longitude, location_str;
    int logged_user;

    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();

    private final static int ALL_PERMISSIONS_RESULT = 101;
    LocationTrack locationTrack;

    User user = SharedPrefManager.getInstance(this).getUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        permissionsToRequest = findUnAskedPermissions(permissions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (permissionsToRequest.size() > 0)
            {
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
            }
        }

        tv_addr = findViewById(R.id.txt_useaddr);
        btn_getLoc = findViewById(R.id.btn_location);

        logged_user = user.getUid();

        btn_getLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                locationTrack = new LocationTrack(LocationActivity.this);

                if (locationTrack.canGetLocation)
                {
                    double longitude = locationTrack.getLongitude();
                    double latitude = locationTrack.getLatitude();
                    Toast.makeText(getApplicationContext(),"Longitude:" + Double.toString(longitude) +
                            "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();

                    if (longitude != 0.0 && latitude != 0.0)
                    {
                        s_latitude = Double.toString(latitude);
                        s_longitude = Double.toString(longitude);
                        location_str = "https://maps.google.com/maps?q=" + s_latitude + "," + s_longitude;
                        addURL();
                    }
                }
                else
                {
                    locationTrack.showSettingsAlert();
                }
            }
        });

        tv_addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                go_home(false);

            }
        });

    }

    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted)
    {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted)
        {
            if (!hasPermission(perm))
            {
                result.add(perm);
            }
        }
        return result;
    }

    private boolean hasPermission(String permission)
    {
        if (canMakeSmores())
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return  true;
    }

    private boolean canMakeSmores()
    {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        switch (requestCode)
        {
            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest)
                {
                    if (!hasPermission(perms))
                    {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0)
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0)))
                        {
                            showMessageOKCancel("These permissions are mandatory for the application, Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                                            {
                                                requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]),ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener)
    {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("Ok", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationTrack.stopListener();
    }

    private void addURL()
    {
        class AddURL extends AsyncTask<Void,Void,String>
        {


            @Override
            protected String doInBackground(Void... voids)
            {
                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();
                params.put("user_loc",location_str);
                params.put("logged_user",String.valueOf(logged_user));

                return requestHandler.sendPostRequest(URLs.URL_ADD_LOCATION, params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                go_home(true);
            }
        }

        AddURL au = new AddURL();
        au.execute();
    }

    private void go_home(boolean loc_update)
    {
        Intent homeIntent = new Intent(LocationActivity.this,HomeActivity.class);
        homeIntent.putExtra("LOCATION_UPDATED", loc_update);
        startActivity(homeIntent);
    }
}