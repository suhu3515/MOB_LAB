package com.vhddev.moblab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    Button btn_logout,btn_tr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setTitle("MOBLAB HOME");

        btn_logout = findViewById(R.id.btn_logout);
        btn_tr = findViewById(R.id.btn_booktr);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPrefManager.getInstance(getApplicationContext()).logout();

            }
        });

        btn_tr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent reqTestIntent = new Intent(HomeActivity.this,BookRequestActivity.class);
                startActivity(reqTestIntent);

            }
        });

    }
}