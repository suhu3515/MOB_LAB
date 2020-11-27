package com.vhddev.moblabtester;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

import java.net.URL;
import java.util.HashMap;

public class TestDetailsActivity extends AppCompatActivity {

    String test_name, test_spec,user_name,user_loc,user_dob;
    int user_id,sub_count,tester_id,pay,stat;
    TextView tv_username,tv_userdob,tv_userloc,tv_test_name,tv_test_spec,tv_test_pay,tv_test_status;
    Button btn_add_result,btn_update_status,btn_view_res;
    boolean isSubTestExists,isResultsAdded;
    Tester tester;
    SwipeRefreshLayout pullToRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_details);

        pullToRefresh = findViewById(R.id.pulltorefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                refreshData();
                pullToRefresh.setRefreshing(false);
            }
        });

        test_name = getIntent().getExtras().getString("test_name");
        test_spec = getIntent().getExtras().getString("test_specimen");
        isResultsAdded = getIntent().getExtras().getBoolean("results_added");

        setTitle("Test Details");

        tester = SharedPrefManager.getInstance(this).getTester();
        tester_id = tester.getTid();

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
        tv_test_pay = findViewById(R.id.text_testpay_val);
        tv_test_status = findViewById(R.id.text_teststat_val);
        btn_update_status = findViewById(R.id.btn_update_stat);
        btn_view_res = findViewById(R.id.btn_view_res);

        tv_username.setText(user_name);
        tv_userdob.setText(user_dob);
        tv_test_name.setText(test_name);
        tv_test_spec.setText(test_spec);

        if (isResultsAdded)
        {
            btn_add_result.setEnabled(false);
        }

        checkForSubTests();
        checkStatus();

        tv_userloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (user_loc!=null)
                {
                    Uri uri_loc = Uri.parse(user_loc);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri_loc);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
                else
                {
                    Toast.makeText(TestDetailsActivity.this, "User didn't set the location", Toast.LENGTH_LONG).show();
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

        btn_update_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent statusIntent = new Intent(TestDetailsActivity.this,UpdateStatusActivity.class);
                startActivity(statusIntent);

            }
        });

        btn_view_res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent resultIntent = new Intent(TestDetailsActivity.this,ReportActivity.class);
                startActivity(resultIntent);

            }
        });
    }

    private void refreshData()
    {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    private void checkStatus()
    {
        class CheckStatus extends AsyncTask<Void, Void, String>
        {

            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();
                params.put("tester_id", String.valueOf(tester_id));
                params.put("user_id", String.valueOf(user_id));

                return requestHandler.sendPostRequest(URLs.URL_CHECK_STATUS,params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try
                {
                    JSONObject object = new JSONObject(s);
                    if (object.getBoolean("error") == false)
                    {
                           pay = Integer.parseInt(object.getString("pay_stat"));
                           stat = Integer.parseInt(object.getString("tr_status"));

                           if (pay == 0)
                           {
                               tv_test_pay.setText("NOT PAID");
                           }
                           if (pay == 1)
                           {
                               tv_test_pay.setText("PAID");
                           }

                           switch (stat)
                           {
                               case 2:

                                   tv_test_status.setText("ASSIGNED TESTER");
                                   break;

                               case 3:

                                   tv_test_status.setText("SAMPLE COLLECTED");
                                   break;

                               case 4:

                                   tv_test_status.setText("GENERATING REPORT");
                                   btn_add_result.setEnabled(true);
                                   break;

                               case 5:

                                   tv_test_status.setText("COMPLETED");
                                   btn_add_result.setVisibility(View.GONE);
                                   btn_view_res.setVisibility(View.VISIBLE);
                                   btn_view_res.setEnabled(true);
                                   break;

                               default:

                                   Toast.makeText(TestDetailsActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                                   break;
                           }

                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }
        CheckStatus cs = new CheckStatus();
        cs.execute();
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