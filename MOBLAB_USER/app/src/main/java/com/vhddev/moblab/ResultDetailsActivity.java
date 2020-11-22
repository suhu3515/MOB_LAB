package com.vhddev.moblab;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResultDetailsActivity extends AppCompatActivity {

    String doc_name, test_date;
    TextView tv_uname,tv_udob,tv_docname,tv_date;
    User user;
    TableLayout table;
    TableRow tr;
    List<Result> resultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_details);

        doc_name = getIntent().getExtras().getString("doc_name");
        test_date = getIntent().getExtras().getString("test_date");

        tv_uname = findViewById(R.id.tv_uname_report_val);
        tv_udob = findViewById(R.id.tv_dob_report_val);
        tv_docname = findViewById(R.id.tv_doctor_report_val);
        tv_date = findViewById(R.id.tv_date_report_val);

        user = SharedPrefManager.getInstance(this).getUser();

        tv_uname.setText(user.getUname());
        tv_udob.setText(user.getUdob());
        tv_docname.setText(doc_name);
        tv_date.setText(test_date);
        resultList = new ArrayList<>();

        get_results();

    }

    private void get_results()
    {
        class GetResults extends AsyncTask<Void, Void, String>
        {

            @Override
            protected String doInBackground(Void... voids)
            {
                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();

                params.put("user_id", String.valueOf(user.getUid()));
                params.put("doc_name",doc_name);
                params.put("test_date", test_date);

                return requestHandler.sendPostRequest(URLs.URL_USER_RESULT,params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try
                {
                    JSONArray array = new JSONArray(s);
                    for (int i=0;i<array.length();i++)
                    {
                        JSONObject object = array.getJSONObject(i);

                        resultList.add(new Result(
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
        GetResults gr = new GetResults();
        gr.execute();
    }

    public void init()
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
        for (int j=0;j<resultList.size();j++)
        {
            Result result = resultList.get(j);
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
            tv_d_ref.setText(" "+result.getReference());
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