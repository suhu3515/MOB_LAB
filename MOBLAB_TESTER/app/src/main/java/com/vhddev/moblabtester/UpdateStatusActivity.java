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
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class UpdateStatusActivity extends AppCompatActivity {

    String[] statuses = {"Select Status","Sample Collected","Generating Report","Completed"};
    RadioGroup radio_group;
    Spinner spinner_stat;
    Button btn_update_status;
    int pay_stat, tr_stat, tester_id, user_id;
    Tester tester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_status);

        setTitle("Update Status");

        btn_update_status = findViewById(R.id.btn_update);
        spinner_stat = findViewById(R.id.spinner_status);
        radio_group = findViewById(R.id.radio_group);

        tester = SharedPrefManager.getInstance(this).getTester();
        tester_id = tester.getTid();

        SharedPreferences sharedPreferences = getSharedPreferences("user_details",MODE_PRIVATE);
        user_id = sharedPreferences.getInt("user_id", 0);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,statuses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_stat.setAdapter(adapter);

        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio_paid)
                {
                    pay_stat = 1;
                }

                if (checkedId == R.id.radio_notpaid)
                {
                    pay_stat = 0;
                }
            }
        });

        spinner_stat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (spinner_stat.getSelectedItem().toString())
                {
                    case "Select Status":

                        Toast.makeText(UpdateStatusActivity.this,"Please select a status", Toast.LENGTH_SHORT).show();

                    break;

                    case "Sample Collected":

                        tr_stat = 3;
                        break;

                    case "Generating Report":

                        tr_stat = 4;
                        break;

                    case "Completed":

                        tr_stat = 5;
                        break;

                    default:

                        Toast.makeText(UpdateStatusActivity.this,"Some error occurred..", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_update_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                update_status();

            }
        });

    }

    private void update_status()
    {
        class UpdateStatus extends AsyncTask<Void, Void, String>
        {

            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(user_id));
                params.put("tester_id", String.valueOf(tester_id));
                params.put("payment", String.valueOf(pay_stat));
                params.put("tr_stat", String.valueOf(tr_stat));

                return requestHandler.sendPostRequest(URLs.URL_UPDATE_STATUS,params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try
                {
                    JSONObject object = new JSONObject(s);

                    if (!object.getBoolean("error"))
                    {
                        Toast.makeText(UpdateStatusActivity.this, object.getString("message"),Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(UpdateStatusActivity.this, object.getString("message"),Toast.LENGTH_SHORT).show();
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }
        UpdateStatus us = new UpdateStatus();
        us.execute();
    }
}