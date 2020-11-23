package com.vhddev.moblabtester;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    TextView tv_tester_name;
    ImageView profile, assigned;
    Tester tester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tester = SharedPrefManager.getInstance(this).getTester();
        tv_tester_name = findViewById(R.id.tester_name);
        profile = findViewById(R.id.shape_profile);
        assigned = findViewById(R.id.shape_tasks);

        tv_tester_name.setText(tester.getTname());

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent profileIntent = new Intent(HomeActivity.this,ProfileActivity.class);
                startActivity(profileIntent);

            }
        });

        assigned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent tasksIntent = new Intent(HomeActivity.this,TasksActivity.class);
                startActivity(tasksIntent);

            }
        });
    }
}