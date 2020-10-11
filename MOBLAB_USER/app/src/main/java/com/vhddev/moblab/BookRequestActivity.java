package com.vhddev.moblab;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.content.DialogInterface;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class BookRequestActivity extends AppCompatActivity {

    String loggedIn_username, user_tr_date,doc_name, loggedIn_dob;
    int loggedIn_user;
    Button btn_addTestReq, btn_addTests;
    TextView tv_tr_status, tv_user_name, tv_user_dob;
    EditText et_doc_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_request);

        setTitle("Book Test Request");

        User user = SharedPrefManager.getInstance(this).getUser();
        loggedIn_user = user.getUid();
        loggedIn_username = user.getUname();
        loggedIn_dob = user.getUdob();


        btn_addTestReq = findViewById(R.id.btn_addtr);
        btn_addTests = findViewById(R.id.btn_addtests);
        tv_tr_status = findViewById(R.id.txt_tr_status);
        tv_user_name = findViewById(R.id.tv_username);
        tv_user_dob = findViewById(R.id.tv_userdob);
        et_doc_name = findViewById(R.id.et_userdoc);

        tv_user_name.setText(loggedIn_username);
        tv_user_dob.setText(loggedIn_dob);

        user_tr_date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

         btn_addTestReq.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {


                 add_test_request();

             }
         });

        btn_addTests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent testAddIntent = new Intent(BookRequestActivity.this,AddTestsActivity.class);
                startActivity(testAddIntent);
            }
        });
    }

    private void add_test_request()
    {
        if (et_doc_name.getText().toString().isEmpty())
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Doctor's name is empty. Are you doing the test by yourself?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            et_doc_name.setText("SELF");
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.cancel();
                            et_doc_name.requestFocus();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
        else
        {
            doc_name = et_doc_name.getText().toString();

            class AddTestRequest extends AsyncTask<Void, Void, String>
            {

                @Override
                protected String doInBackground(Void... voids)
                {
                    RequestHandler requestHandler = new RequestHandler();

                    HashMap<String, String> params = new HashMap<>();
                    params.put("logged_user",String.valueOf(loggedIn_user));
                    params.put("doc_name",doc_name);
                    params.put("tr_date",user_tr_date);

                    return requestHandler.sendPostRequest(URLs.URL_ADD_TEST_REQUEST,params);
                }

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);

                    try
                    {
                        JSONObject obj = new JSONObject(s);

                        if (obj.getBoolean("error"))
                        {
                            Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            tv_tr_status.setText("Test Booked Successfully");
                            tv_tr_status.setVisibility(View.VISIBLE);
                            Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_SHORT).show();
                            btn_addTests.setVisibility(View.VISIBLE);
                            btn_addTestReq.setEnabled(false);
                            btn_addTestReq.setVisibility(View.INVISIBLE);

                            SharedPreferences sharedPreferences = getSharedPreferences("TestInfo", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("TestRequestAdded",true);
                            editor.putBoolean("TestsAdded",false);
                            editor.apply();
                        }

                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }

                }
            }

            AddTestRequest atr = new AddTestRequest();
            atr.execute();
        }
    }
}