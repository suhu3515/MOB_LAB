package com.vhddev.moblab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    Button btn_tr,btn_res,btn_all_tr;
    TextView txt_profile, txt_username_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setTitle("MOBLAB HOME");

        btn_tr = findViewById(R.id.btn_booktr);
        txt_profile = findViewById(R.id.txt_complete);
        txt_username_profile = findViewById(R.id.txt_uname_prof);
        btn_res = findViewById(R.id.btn_viewtestres);
        btn_all_tr = findViewById(R.id.btn_viewtr);

        User user = SharedPrefManager.getInstance(this).getUser();

        txt_username_profile.setText(user.getUname());
        txt_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent profileIntent = new Intent(HomeActivity.this,ProfileActivity.class);
                startActivity(profileIntent);

            }
        });

        btn_tr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent reqTestIntent = new Intent(HomeActivity.this,BookRequestActivity.class);
                startActivity(reqTestIntent);

            }
        });

        btn_res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent resultIntent = new Intent(HomeActivity.this,ResultsActivity.class);
                startActivity(resultIntent);

            }
        });

        btn_all_tr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent alltrintent = new Intent(HomeActivity.this,TestRequestsActivity.class);
                startActivity(alltrintent);

            }
        });

    }
}