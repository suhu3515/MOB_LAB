package com.vhddev.moblab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResultsActivity extends AppCompatActivity {

    RecyclerView recyclerView_results;
    List<ResultList> resultsList;
    User user = SharedPrefManager.getInstance(this).getUser();
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        user_id = String.valueOf(user.getUid());

        recyclerView_results = findViewById(R.id.recyclerview_results);
        recyclerView_results.setHasFixedSize(true);
        recyclerView_results.setLayoutManager(new LinearLayoutManager(this));
        resultsList = new ArrayList<>();

        getTestResultList();

    }

    private void getTestResultList()
    {
        class GetTestResultList extends AsyncTask<Void, Void, String>
        {

            ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids)
            {
                RequestHandler requestHandler = new RequestHandler();

                HashMap<String,String> params = new HashMap<>();

                params.put("user_id",user_id);

                return requestHandler.sendPostRequest(URLs.URL_USER_RESULTS,params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressBar = findViewById(R.id.progress_results);
                progressBar.setVisibility(View.VISIBLE);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try
                {
                    JSONArray array = new JSONArray(s);
                    for (int i=0;i<array.length();i++)
                    {
                        JSONObject results = array.getJSONObject(i);

                        resultsList.add(new ResultList(
                            results.getString("test_date"),
                            results.getString("doctor_name")
                        ));
                    }

                    progressBar.setVisibility(View.GONE);
                    ResultsAdapterActivity adapter = new ResultsAdapterActivity(ResultsActivity.this,resultsList);
                    recyclerView_results.setAdapter(adapter);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }
        GetTestResultList getTestResultList = new GetTestResultList();
        getTestResultList.execute();
    }
}