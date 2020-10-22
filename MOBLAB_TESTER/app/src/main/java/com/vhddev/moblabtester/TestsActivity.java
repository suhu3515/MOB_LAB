package com.vhddev.moblabtester;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestsActivity extends AppCompatActivity {

    private static final String URL_TASKS = "http://192.168.43.159/moblab/tasks.php";
    //private static final String URL_TASKS = "http://172.20.10.4/moblab/tasks_list.php";

    List<Tests> TestDetailsList;
    RecyclerView recycler_view;
    Tester tester = SharedPrefManager.getInstance(this).getTester();
    String loc_url;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests);

        recycler_view = findViewById(R.id.recycler_view_tests);
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        TestDetailsList = new ArrayList<>();

        user_id = getIntent().getExtras().getString("userid");

        getUserDetails();

    }

    private void getUserDetails()
    {
        class UserDetails extends AsyncTask<Void, Void, String>
        {

            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();

                HashMap<String,String> params = new HashMap<>();

                params.put("tester_id", String.valueOf(tester.getTid()));
                params.put("user_id", user_id);

                return requestHandler.sendPostRequest(URL_TASKS,params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try
                {
                    JSONArray array = new JSONArray(s);
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject tests = array.getJSONObject(i);

                        TestDetailsList.add(new Tests(
                                tests.getString("testname"),
                                tests.getString("testspecimen")
                        ));
                    }

                    TestsListAdapter adapter = new TestsListAdapter(TestsActivity.this, TestDetailsList);
                    recycler_view.setAdapter(adapter);

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        }
        UserDetails ud = new UserDetails();
        ud.execute();
    }
}