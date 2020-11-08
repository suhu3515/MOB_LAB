package com.vhddev.moblabtester;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AddTestResultsActivity extends AppCompatActivity {

    String test_name,test_spec,value;
    EditText et_test_value;
    TextView tv_tname;
    int tester_id,user_id;
    Tester tester;
    Button btn_add_res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_test_results);

        setTitle("Add result");

        tester = SharedPrefManager.getInstance(this).getTester();
        tester_id = tester.getTid();

        SharedPreferences preferences = getSharedPreferences("user_details",MODE_PRIVATE);
        user_id = preferences.getInt("user_id", 0);

        et_test_value = findViewById(R.id.et_obs_value);
        tv_tname = findViewById(R.id.txtview_test_name);
        btn_add_res = findViewById(R.id.btn_add_tresult);

        test_name = getIntent().getExtras().getString("test_name");
        test_spec = getIntent().getExtras().getString("test_specimen");

        tv_tname.setText(test_name);

        btn_add_res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                add_results();

            }
        });
    }

    private void add_results()
    {
        class AddResults extends AsyncTask<Void, Void, String>
        {

            @Override
            protected String doInBackground(Void... voids) {

                value = et_test_value.getText().toString();

                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();
                params.put("test_name", test_name);
                params.put("test_specimen", test_spec);
                params.put("tester_id", String.valueOf(tester_id));
                params.put("user_id", String.valueOf(user_id));
                params.put("t_value", value);

                return requestHandler.sendPostRequest(URLs.URL_TESTS_RESULTS,params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try
                {
                    JSONObject object = new JSONObject(s);
                    if (!object.getBoolean("error"))
                    {
                        Toast.makeText(AddTestResultsActivity.this,object.getString("message"),Toast.LENGTH_SHORT).show();
                        et_test_value.getText().clear();
                        go_back();
                    }
                    else
                    {
                        Toast.makeText(AddTestResultsActivity.this,object.getString("message"),Toast.LENGTH_SHORT).show();
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }
        AddResults ar = new AddResults();
        ar.execute();
    }

    private void go_back()
    {
        Intent testDetailsIntent = new Intent(AddTestResultsActivity.this,TestDetailsActivity.class);
        testDetailsIntent.putExtra("results_added",true);
        startActivity(testDetailsIntent);
        finish();
    }
}