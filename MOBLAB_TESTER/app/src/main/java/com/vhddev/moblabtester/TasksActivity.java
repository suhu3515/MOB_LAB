package com.vhddev.moblabtester;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class TasksActivity extends AppCompatActivity {



    List<UsersList> usersLists;
    RecyclerView recyclerView;
    Tester tester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        setTitle("Assigned Tasks");

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        usersLists = new ArrayList<>();
        tester = SharedPrefManager.getInstance(this).getTester();

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
                params.put("tester_id", String.valueOf(tester.getTid()));

                return requestHandler.sendPostRequest(URLs.URL_TASKS_LIST, params);
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

                    if (usersLists.isEmpty())
                    {
                        Toast.makeText(TasksActivity.this, "No Assigned Tasks", Toast.LENGTH_LONG).show();
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