package com.vhddev.moblab;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddTestsActivity extends AppCompatActivity{

    Button button_add_tests,button_submit_tests;
    String user_id, tests,specimen_name,test_name,doctor_name,request_date;
    Spinner specimen,test;

    List<String> specimen_list = new ArrayList<String>();
    List<String> test_list = new ArrayList<String>();

    User user = SharedPrefManager.getInstance(this).getUser();

    StringBuilder sb = new StringBuilder("");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tests);


        setTitle("Add Tests");
        getSpecimen();

        sb = new StringBuilder();

        button_add_tests = findViewById(R.id.btn_addTest);
        button_submit_tests = findViewById(R.id.btn_submitTest);
        specimen = findViewById(R.id.spinner_specimen);
        test = findViewById(R.id.spinner_tests);

        user_id = String.valueOf(user.getUid());
        doctor_name = getIntent().getExtras().getString("doctor_name");
        request_date = getIntent().getExtras().getString("req_date");

        button_add_tests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                add_user_tests();

            }
        });

        button_submit_tests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //submit_tests();
                tests = sb.toString();
                Toast.makeText(AddTestsActivity.this, tests, Toast.LENGTH_SHORT).show();
            }
        });

        specimen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                specimen_name = parent.getItemAtPosition(position).toString();
                test_list.clear();
                getTests();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        test.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                test_name = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void getTests()
    {
        class GetTests extends AsyncTask<Void,Void,String>
        {

            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();

                HashMap<String,String> params = new HashMap<>();
                params.put("specimen", specimen_name);

                return requestHandler.sendPostRequest(URLs.URL_GET_TESTS,params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try
                {
                    JSONArray jsonArray = new JSONArray(s);
                    for (int j=0;j<jsonArray.length();j++)
                    {
                        JSONObject test = jsonArray.getJSONObject(j);
                        test_list.add(test.getString("test"));
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddTestsActivity.this, android.R.layout.simple_spinner_item,test_list);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    test.setAdapter(adapter);

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }
        GetTests gt = new GetTests();
        gt.execute();
    }

    private void submit_tests()
    {
        class AddUserTests extends AsyncTask<Void,Void,String>
        {

            ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();
                params.put("user_id",user_id);
                params.put("user_test", tests);
                params.put("doctor_name",doctor_name);
                params.put("tr_date", request_date);

                return requestHandler.sendPostRequest(URLs.URL_ADD_USER_TESTS,params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressBar = findViewById(R.id.progress_submit);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try
                {
                    JSONObject obj = new JSONObject(s);

                    if (!obj.getBoolean("error"))
                    {
                        Toast.makeText(AddTestsActivity.this,obj.getString("message"),Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        goHome();
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }
        AddUserTests aut = new AddUserTests();
        aut.execute();
    }

    private void goHome()
    {
        Intent homeIntent = new Intent(AddTestsActivity.this,HomeActivity.class);
        startActivity(homeIntent);
        finish();
    }

    private void getSpecimen()
    {

        class GetSpecimen extends AsyncTask<Void,Void,String>
        {

            @Override
            protected String doInBackground(Void... voids)
            {

                RequestHandler requestHandler = new RequestHandler();
                HashMap<String,String> params = new HashMap<>();

                return requestHandler.sendPostRequest(URLs.URL_GET_SPECIMEN,params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try
                {
                    JSONArray array = new JSONArray(s);
                    for (int i=0;i<array.length();i++)
                    {
                        JSONObject specimen = array.getJSONObject(i);
                        specimen_list.add(specimen.getString("specimen"));
                    }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddTestsActivity.this, android.R.layout.simple_spinner_item, specimen_list);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    specimen.setAdapter(arrayAdapter);

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }
        GetSpecimen gs = new GetSpecimen();
        gs.execute();
    }

    private void add_user_tests()
    {
        sb.append(test_name).append(" - ").append(specimen_name).append("\n");
        specimen_list.clear();
        test_list.clear();
        Toast.makeText(AddTestsActivity.this, "Added tests...", Toast.LENGTH_SHORT).show();
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

}