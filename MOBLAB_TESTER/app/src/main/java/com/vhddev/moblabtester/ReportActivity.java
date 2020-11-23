package com.vhddev.moblabtester;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReportActivity extends AppCompatActivity {

    String user_name,user_dob,user_id;
    Tester tester;
    TextView user_name_tv,user_dob_tv;
    List<Results> resultsList;
    TableLayout table;
    TableRow tr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        user_name_tv = findViewById(R.id.tv_uname_report_val);
        user_dob_tv = findViewById(R.id.tv_dob_report_val);

        resultsList = new ArrayList<>();

        SharedPreferences sharedPreferences = getSharedPreferences("user_details",MODE_PRIVATE);
        user_name = sharedPreferences.getString("user_name", null);
        user_dob = sharedPreferences.getString("user_dob", null);
        user_id = String.valueOf(sharedPreferences.getInt("user_id", 0));

        user_name_tv.setText(user_name);
        user_dob_tv.setText(user_dob);

        tester = SharedPrefManager.getInstance(this).getTester();

        get_report();

    }

    private void get_report()
    {
        class GetReport extends AsyncTask<Void, Void, String>
        {

            @Override
            protected String doInBackground(Void... voids)
            {
                RequestHandler requestHandler = new RequestHandler();

                HashMap<String,String> params = new HashMap<>();

                params.put("user_id", user_id);
                params.put("tester_id", String.valueOf(tester.getTid()));

                return requestHandler.sendPostRequest(URLs.URL_RESULTS,params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try
                {
                    JSONArray array = new JSONArray(s);
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject object = array.getJSONObject(i);

                        resultsList.add(new Results(
                                object.getString("name"),
                                object.getString("value"),
                                object.getString("ref"),
                                object.getString("specimen")
                        ));
                    }
                    init();
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        }
        GetReport gr = new GetReport();
        gr.execute();
    }

    private void init()
    {
        table = findViewById(R.id.table_report);

        tr = new TableRow(this);
        TextView tv_name = new TextView(this);
        tv_name.setText(" Test ");
        tv_name.setBackgroundResource(R.drawable.table_cell);
        tv_name.setPadding(5,5,5,5);
        tv_name.setTextColor(Color.BLACK);
        tv_name.setGravity(Gravity.CENTER);
        tr.addView(tv_name);
        TextView tv_val = new TextView(this);
        tv_val.setText(" Value ");
        tv_val.setTextColor(Color.BLACK);
        tv_val.setBackgroundResource(R.drawable.table_cell);
        tv_val.setPadding(5,5,5,5);
        tv_val.setGravity(Gravity.CENTER);
        tr.addView(tv_val);
        TextView tv_ref = new TextView(this);
        tv_ref.setText(" Reference ");
        tv_ref.setGravity(Gravity.CENTER);
        tv_ref.setBackgroundResource(R.drawable.table_cell);
        tv_ref.setPadding(5,5,5,5);
        tv_ref.setTextColor(Color.BLACK);
        tr.addView(tv_ref);
        TextView tv_spec = new TextView(this);
        tv_spec.setText(" Specimen ");
        tv_spec.setBackgroundResource(R.drawable.table_cell);
        tv_spec.setGravity(Gravity.CENTER);
        tv_spec.setPadding(5,5,5,5);
        tv_spec.setTextColor(Color.BLACK);
        tr.addView(tv_spec);
        table.addView(tr);
        for (int j=0;j<resultsList.size();j++)
        {
            Results result = resultsList.get(j);
            TableRow tr_d = new TableRow(this);
            TextView tv_d_name = new TextView(this);
            tv_d_name.setGravity(Gravity.CENTER);
            tv_d_name.setBackgroundResource(R.drawable.table_cell);
            tv_d_name.setTextColor(Color.BLACK);
            tv_d_name.setPadding(5,5,5,5);
            tv_d_name.setText(" "+ result.getTest_name());
            tr_d.addView(tv_d_name);
            TextView tv_d_val = new TextView(this);
            tv_d_val.setGravity(Gravity.CENTER);
            tv_d_val.setBackgroundResource(R.drawable.table_cell);
            tv_d_val.setPadding(5,5,5,5);
            tv_d_val.setTextColor(Color.BLACK);
            tv_d_val.setText(" "+ result.getValue());
            tr_d.addView(tv_d_val);
            TextView tv_d_ref = new TextView(this);
            tv_d_ref.setGravity(Gravity.CENTER);
            tv_d_ref.setBackgroundResource(R.drawable.table_cell);
            tv_d_ref.setPadding(5,5,5,5);
            tv_d_ref.setTextColor(Color.BLACK);
            tv_d_ref.setText(" "+result.getRef());
            tr_d.addView(tv_d_ref);
            TextView tv_d_spec = new TextView(this);
            tv_d_spec.setGravity(Gravity.CENTER);
            tv_d_spec.setBackgroundResource(R.drawable.table_cell);
            tv_d_spec.setPadding(5,5,5,5);
            tv_d_spec.setTextColor(Color.BLACK);
            tv_d_spec.setText(result.getSpecimen());
            tr_d.addView(tv_d_spec);
            table.addView(tr_d);
        }

    }
}