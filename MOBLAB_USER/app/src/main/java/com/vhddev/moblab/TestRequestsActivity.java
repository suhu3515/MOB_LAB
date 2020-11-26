package com.vhddev.moblab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.VoiceInteractor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestRequestsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Requests> requestsList;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_requests);

        user = SharedPrefManager.getInstance(this).getUser();

        recyclerView = findViewById(R.id.recycler_tr);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        requestsList = new ArrayList<>();

        get_test_requests();
    }

    private void get_test_requests()
    {
        class GetTestRequests extends AsyncTask<Void, Void, String>
        {

            ProgressBar progressBar = findViewById(R.id.progress_tests);

            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(user.getUid()));

                return requestHandler.sendPostRequest(URLs.URL_USER_REQUESTS, params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressBar.setVisibility(View.VISIBLE);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try
                {
                    JSONArray array = new JSONArray(s);
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject req = array.getJSONObject(i);

                        requestsList.add(new Requests(
                                req.getString("doctor_name"),
                                req.getString("tester_name"),
                                req.getString("tester_mob"),
                                req.getString("req_date"),
                                req.getString("pay_status"),
                                req.getString("status")));
                    }

                    progressBar.setVisibility(View.GONE);
                    RequestAdapterActivity adapterActivity = new RequestAdapterActivity(TestRequestsActivity.this,requestsList);
                    recyclerView.setAdapter(adapterActivity);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }
        GetTestRequests getTestRequests = new GetTestRequests();
        getTestRequests.execute();
    }
}