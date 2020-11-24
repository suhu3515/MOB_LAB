package com.vhddev.moblabtester;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText et_pass,et_n_pass,et_c_pass;
    Button btn_pass;
    Tester tester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        setTitle("Change password");

        tester = SharedPrefManager.getInstance(this).getTester();

        et_pass = findViewById(R.id.et_password);
        et_n_pass = findViewById(R.id.et_new_password);
        et_c_pass = findViewById(R.id.et_confirm_password);
        btn_pass = findViewById(R.id.btn_change_password);

        btn_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validate_pass();

            }
        });
    }

    private void validate_pass()
    {
        if(et_pass.getText().toString().isEmpty())
        {
            et_pass.setError("Please enter your current password");
            et_pass.requestFocus();
        }
        else if(et_n_pass.getText().toString().isEmpty())
        {
            et_n_pass.setError("PLease enter your new password");
            et_n_pass.requestFocus();
        }
        else if(et_c_pass.getText().toString().isEmpty())
        {
            et_c_pass.setError("Please confirm your password");
            et_c_pass.requestFocus();
        }
        else if(et_pass.getText().toString().equals(et_n_pass.getText().toString()) || et_pass.getText().toString().equals(et_c_pass.getText().toString()))
        {
            et_n_pass.setError("New password can't be old password");
            et_n_pass.requestFocus();
        }
        else if(!et_n_pass.getText().toString().equals(et_c_pass.getText().toString()))
        {
            et_c_pass.setError("new passwords mismatch");
            et_c_pass.requestFocus();
        }
        else
        {
            change_pass();
        }
    }

    private void change_pass()
    {
        class ChangePass extends AsyncTask<Void, Void, String>
        {

            @Override
            protected String doInBackground(Void... voids)
            {
                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();
                params.put("mobile", tester.getTmobile());
                params.put("pass", et_pass.getText().toString());
                params.put("new_pass", et_n_pass.getText().toString());
                params.put("role", "TESTER");

                return requestHandler.sendPostRequest(URLs.URL_CHANGE_PASSWORD,params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try
                {
                    JSONObject obj = new JSONObject(s);
                    if (!obj.getBoolean("error"))
                    {
                        Intent prof_intent = new Intent(ChangePasswordActivity.this,ProfileActivity.class);
                        Toast.makeText(ChangePasswordActivity.this,obj.getString("message"),Toast.LENGTH_SHORT).show();
                        startActivity(prof_intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(ChangePasswordActivity.this,obj.getString("message"),Toast.LENGTH_SHORT).show();
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        }
        ChangePass cp = new ChangePass();
        cp.execute();
    }
}