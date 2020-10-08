package com.vhddev.moblab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
        else if (et_pwd.getText().toString().isEmpty())
        {
            et_pwd.setError("Please enter your password!");
            et_pwd.requestFocus();
        }

    }
}