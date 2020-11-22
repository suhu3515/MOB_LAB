package com.vhddev.moblab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    EditText et_mob, et_pwd;
    TextView tv_reg_link;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_mob = findViewById(R.id.et_mob);
        et_pwd = findViewById(R.id.et_pass);
        tv_reg_link = findViewById(R.id.txt_reg);
        btn_login = findViewById(R.id.btn_login);



        if (SharedPrefManager.getInstance(this).isLoggedIn())
        {
            finish();
            startActivity(new Intent(this, HomeActivity.class));
            return;
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validate();

            }
        });

        tv_reg_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent regIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(regIntent);

            }
        });

    }

    private void validate()
    {

        if (et_mob.getText().toString().isEmpty())
        {
            et_mob.setError("Please enter mobile number!");
            et_mob.requestFocus();
        }
        else if (et_mob.getText().toString().length()>10 || et_mob.getText().toString().length()<10)
        {
            et_mob.setError("Not a valid mobile number!");
            et_mob.requestFocus();
        }
        else if (et_pwd.getText().toString().isEmpty())
        {
            et_pwd.setError("Please enter your password!");
            et_pwd.requestFocus();
        }
        else
        {
            user_login();
        }
    }

    private void user_login()
    {
        class UserLogin extends AsyncTask<Void, Void, String>
        {

            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();

                HashMap<String,String> params = new HashMap<>();

                params.put("mobile",et_mob.getText().toString());
                params.put("password",et_pwd.getText().toString());

                return requestHandler.sendPostRequest(URLs.URL_LOGIN,params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try
                {
                    JSONObject object = new JSONObject(s);
                    if (!object.getBoolean("error"))
                    {
                        JSONObject userJson = object.getJSONObject("USER");
                        User user = new User(

                                userJson.getInt("user_id"),
                                userJson.getString("user_name"),
                                userJson.getString("dob"),
                                userJson.getString("h_name"),
                                userJson.getString("place"),
                                userJson.getString("pin"),
                                userJson.getString("mobile"),
                                userJson.getString("email")
                        );

                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                        Toast.makeText(LoginActivity.this,object.getString("message"),Toast.LENGTH_SHORT).show();

                        Intent homeIntent = new Intent(LoginActivity.this,HomeActivity.class);
                        startActivity(homeIntent);
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this,object.getString("message"),Toast.LENGTH_SHORT).show();
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }
        UserLogin ul = new UserLogin();
        ul.execute();
    }
}