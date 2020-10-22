package com.vhddev.moblabtester;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class TasksActivity extends AppCompatActivity {

    private static final String URL_TASKS_LIST = "http://192.168.43.159/moblab/tasks_list.php";
    //private static final String URL_TASKS_LIST = "http://172.20.10.4/moblab/tasks_list.php";

    List<UsersList> usersLists;
    RecyclerView recyclerView;
    Tester tester,tester1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tester = new Tester(4,"aslam k", "1998-07-17", "kallingal house", "edappal", "679590", "9567105860", "aslamkedpl12@gmail.com");
        SharedPrefManager.getInstance(getApplicationContext()).testerLogin(tester);

        usersLists = new ArrayList<>();
        tester1 = SharedPrefManager.getInstance(this).getTester();

        loadUsers();
    }

    private void loadUsers()
    {
        class LoadUsers extends AsyncTask<Void, Void, String>
        {
            ProgressBar progressBar = findViewById(R.id.progess_bar);

            @Override
            protected String doInBackground(Void... voids)
            {

                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params  = new HashMap<>();
                params.put("tester_id", String.valueOf(tester1.getTid()));

                return requestHandler.sendPostRequest(URL_TASKS_LIST, params);
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
                    for(int i=0; i < array.length(); i++)
                    {

                        JSONObject users = array.getJSONObject(i);

                        usersLists.add(new UsersList(
                                users.getInt("userid"),
                                users.getString("username"),
                                users.getString("userdob"),
                                users.getString("userloc")
                        ));
                    }


                    UsersListAdapter adapter = new UsersListAdapter(TasksActivity.this, usersLists);
                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }

        LoadUsers lu = new LoadUsers();
        lu.execute();
    }
}