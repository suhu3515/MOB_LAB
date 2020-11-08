package com.vhddev.moblabtester;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class TestDetailsActivity extends AppCompatActivity {

    String test_name, test_spec,user_name,user_loc,user_dob;
    int user_id,sub_count;
    TextView tv_username,tv_userdob,tv_userloc,tv_test_name,tv_test_spec;
    Button btn_add_result;
    boolean isSubTestExists,isResultsAdded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_details);

        test_name = getIntent().getExtras().getString("test_name");
        test_spec = getIntent().getExtras().getString("test_specimen");
        isResultsAdded = getIntent().getExtras().getBoolean("results_added");

        setTitle("Test Details");

        SharedPreferences sharedPreferences = getSharedPreferences("user_details",MODE_PRIVATE);
        user_name = sharedPreferences.getString("user_name", null);
        user_loc = sharedPreferences.getString("user_loc", null);
        user_dob = sharedPreferences.getString("user_dob", null);
        user_id = sharedPreferences.getInt("user_id", 0);

        tv_username = findViewById(R.id.text_username_val);
        tv_userdob = findViewById(R.id.text_userdob_val);
        tv_userloc = findViewById(R.id.text_userloc_val);
        tv_test_name = findViewById(R.id.text_testn_val);
        tv_test_spec = findViewById(R.id.text_testspec_val);
        btn_add_result = findViewById(R.id.btn_generate_res);

        tv_username.setText(user_name);
        tv_userdob.setText(user_dob);
        tv_test_name.setText(test_name);
        tv_test_spec.setText(test_spec);

        if (isResultsAdded)
        {
            btn_add_result.setEnabled(false);
        }

        checkForSubTests();

        tv_userloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (user_loc.isEmpty())
                {
                    Toast.makeText(TestDetailsActivity.this, "User didn't set the location", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Uri uri_loc = Uri.parse(user_loc);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri_loc);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
            }
        });

        btn_add_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSubTestExists==true)
                {
                    Intent resultIntent = new Intent(TestDetailsActivity.this, AddResultsActivity.class);
                    resultIntent.putExtra("test_name", test_name);
                    resultIntent.putExtra("test_specimen", test_spec);
                    resultIntent.putExtra("sub_count",sub_count);
                    startActivity(resultIntent);
                    finish();
                }
                else
                {
                    Intent resultTestIntent = new Intent(TestDetailsActivity.this,AddTestResultsActivity.class);
                    resultTestIntent.putExtra("test_name", test_name);
                    resultTestIntent.putExtra("test_specimen", test_spec);
                    startActivity(resultTestIntent);
                    finish();
                }
            }
        });
    }

    private void checkForSubTests()
    {
        class CheckForSubtests extends AsyncTask<Void, Void, String>
        {
            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();

                HashMap<String , String> params = new HashMap<>();
                params.put("test", test_name);
                params.put("specimen", test_spec);

                return requestHandler.sendPostRequest(URLs.URL_CHECK_SUBTESTS,params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try
                {
                    JSONObject obj = new JSONObject(s);
                    sub_count = obj.getInt("subtests_count");
                    if (obj.getBoolean("subtests")==false)
                    {
                        isSubTestExists = false;
                    }
                    else
                    {
                        isSubTestExists = true;
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }
        CheckForSubtests cfs = new CheckForSubtests();
        cfs.execute();
    }
}