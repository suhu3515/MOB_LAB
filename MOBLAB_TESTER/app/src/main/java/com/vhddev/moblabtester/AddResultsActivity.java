package com.vhddev.moblabtester;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddResultsActivity extends AppCompatActivity {

    String test_name,test_spec,subtestName,value;
    int sub_count,sub_position, sub_no=1, userId, tester_id;
    Spinner spinner_subtest;
    List<String> subtestList;
    EditText et_obs_value;
    Button btn_add_stres;
    ArrayAdapter<String> arrayAdapter;
    Tester tester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_results);

        setTitle("Add Subtest Result");

        tester = SharedPrefManager.getInstance(this).getTester();
        tester_id = tester.getTid();

        test_name = getIntent().getExtras().getString("test_name");
        test_spec = getIntent().getExtras().getString("test_specimen");
        sub_count = getIntent().getExtras().getInt("sub_count");
        spinner_subtest = findViewById(R.id.txt_spinner_seltest);
        btn_add_stres = findViewById(R.id.btn_add_test);
        et_obs_value = findViewById(R.id.txt_obs_value);

        SharedPreferences preferences = getSharedPreferences("user_details",MODE_PRIVATE);
        userId = preferences.getInt("user_id", 0);

        subtestList = new ArrayList<>();
        get_subtests();

        spinner_subtest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                subtestName = parent.getItemAtPosition(position).toString();
                sub_position = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_add_stres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                add_subtest_values();

            }
        });
    }


    private void add_subtest_values()
    {
        class AddSubtestResult extends AsyncTask<Void, Void, String>
        {

            @Override
            protected String doInBackground(Void... voids) {

                value = et_obs_value.getText().toString();

                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();

                params.put("subname", subtestName);
                params.put("st_value",  value);
                params.put("test_name", test_name);
                params.put("test_specimen", test_spec);
                params.put("user_id", String.valueOf(userId));
                params.put("tester_id", String.valueOf(tester_id));
                params.put("subtest_num", String.valueOf(sub_no));

                return requestHandler.sendPostRequest(URLs.URL_SUBTESTS_RESULTS, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try
                {
                    JSONObject object = new JSONObject(s);
                    if (!object.getBoolean("error"))
                    {
                        Toast.makeText(AddResultsActivity.this,object.getString("message"),Toast.LENGTH_SHORT).show();
                        et_obs_value.getText().clear();
                        subtestList.remove(spinner_subtest.getItemAtPosition(sub_position));
                        arrayAdapter.notifyDataSetChanged();
                        if (subtestList.isEmpty())
                        {
                            subtestList.add("No more subtests");
                            arrayAdapter.notifyDataSetChanged();
                            spinner_subtest.setEnabled(false);
                            et_obs_value.setEnabled(false);
                            Toast.makeText(AddResultsActivity.this, "Added all results", Toast.LENGTH_LONG).show();
                            btn_add_stres.setEnabled(false);
                            go_back();
                        }
                        sub_no++;
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }
        AddSubtestResult asr = new AddSubtestResult();
        asr.execute();
    }

    private void go_back()
    {
        Intent testDetailsIntent = new Intent(AddResultsActivity.this,TestDetailsActivity.class);
        testDetailsIntent.putExtra("results_added",true);
        startActivity(testDetailsIntent);
        finish();
    }


    private void get_subtests()
    {
        class GetSubtests extends AsyncTask<Void, Void, String>
        {

            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();

                HashMap<String,String> params = new HashMap<>();

                params.put("test_name",test_name);
                params.put("test_spec", test_spec);

                return requestHandler.sendPostRequest(URLs.URL_GET_SUBTEST,params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try
                {
                    JSONArray array = new JSONArray(s);
                    for (int i=0;i<array.length();i++)
                    {
                        JSONObject subtests = array.getJSONObject(i);
                        subtestList.add(subtests.getString("subtest"));
                    }

                    arrayAdapter = new ArrayAdapter<>(AddResultsActivity.this, android.R.layout.simple_spinner_item, subtestList);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_subtest.setAdapter(arrayAdapter);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }
        GetSubtests gs = new GetSubtests();
        gs.execute();
    }
}