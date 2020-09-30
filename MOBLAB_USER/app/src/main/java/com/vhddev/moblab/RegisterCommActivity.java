package com.vhddev.moblab;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class RegisterCommActivity extends AppCompatActivity {

    EditText et_mob, et_mail, et_pass, et_cpass;
    Button btn_reg;
    String name,dob,hname,place,pin,mob,mail,password;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_comm);

        et_mob = findViewById(R.id.reg_et_mob);
        et_mail = findViewById(R.id.reg_et_mail);
        et_pass = findViewById(R.id.reg_et_pwd);
        et_cpass = findViewById(R.id.reg_et_cpwd);

        btn_reg = findViewById(R.id.btn_verify);


        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validate();
            }
        });


    }

    private void validate()
    {
        if(et_mob.getText().toString().isEmpty())
        {
            et_mob.setError("Please enter your mobile number.");
            et_mob.requestFocus();
        }
        else if(et_mob.getText().toString().length()<10)
        {
            et_mob.setError("Mobile number must be 10 digits.");
            et_mob.requestFocus();
        }
        else if(et_mail.getText().toString().isEmpty())
        {
            et_mail.setError("Please enter your email");
            et_mail.requestFocus();
        }
        else if (!(et_mail.getText().toString().matches(emailPattern)))
        {
            et_mail.setError("Email doesn't match the format.");
            et_mail.getText().clear();
            et_mail.requestFocus();
        }
        else if(et_pass.getText().toString().isEmpty())
        {
            et_pass.setError("Please enter your password");
            et_pass.requestFocus();
        }
        else if(et_pass.getText().toString().length()<6)
        {
            et_pass.setError("Password must have minimum 6 characters ");
            et_pass.requestFocus();
        }
        else if (et_cpass.getText().toString().isEmpty())
        {
            et_cpass.setError("Please confirm your password");
            et_cpass.requestFocus();
        }
        else if (!(et_cpass.getText().toString().equals(et_pass.getText().toString())))
        {
            et_cpass.setError("Please use same password given");
            et_cpass.requestFocus();
        }
        else
        {
            registerUser();
        }
    }

    private void registerUser()
    {
        name = getIntent().getExtras().getString("uname");
        dob = getIntent().getExtras().getString("udob");
        hname = getIntent().getExtras().getString("uhname");
        place = getIntent().getExtras().getString("uplace");
        pin = getIntent().getExtras().getString("upin");
        mob = et_mob.getText().toString().trim();
        mail = et_mail.getText().toString().trim();
        password = et_pass.getText().toString().trim();

        class RegisterUser extends AsyncTask<Void, Void, String>
        {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids)
            {
                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();
                params.put("user_name", name);
                params.put("dob", dob);
                params.put("hname", hname);
                params.put("place", place);
                params.put("pin", pin);
                params.put("mobile", mob);
                params.put("email", mail);
                params.put("password", password);

                return requestHandler.sendPostRequest(URLs.URL_REGISTER, params);
            }


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressBar = (ProgressBar) findViewById(R.id.prog_bar);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                progressBar.setVisibility(View.GONE);

                try
                {
                    JSONObject obj = new JSONObject(s);

                    if (!obj.getBoolean("error"))
                    {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        JSONObject userJson = obj.getJSONObject("user");

                        User user = new User(
                                userJson.getInt("user_id"),
                                userJson.getString("user_name"),
                                userJson.getString("user_dob"),
                                userJson.getString("user_hname"),
                                userJson.getString("user_place"),
                                userJson.getString("user_pin"),
                                userJson.getString("user_mobile"),
                                userJson.getString("user_email")
                        );

                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                        finish();
                        Intent verifyIntent = new Intent(getApplicationContext(), VerifyActivity.class);
                        verifyIntent.putExtra("mob_num", mob);
                        startActivity(verifyIntent);

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Some error occured", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute();
    }
}