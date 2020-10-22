package com.vhddev.moblabtester;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class TestDetailsActivity extends AppCompatActivity {

    String test_name, test_spec,user_name,user_loc,user_dob;
    int user_id;
    TextView tv_username,tv_userdob,tv_userloc,tv_test_name,tv_test_spec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_details);

        test_name = getIntent().getExtras().getString("test_name");
        test_spec = getIntent().getExtras().getString("test_specimen");

        SharedPreferences sharedPreferences = getSharedPreferences("user_details",MODE_PRIVATE);
        user_name = sharedPreferences.getString("user_name", null);
        user_loc = sharedPreferences.getString("user_loc", null);
        user_dob = sharedPreferences.getString("user_dob", null);
        user_id = sharedPreferences.getInt("user_id", 0);

        tv_username = findViewById(R.id.text_username_val);
        tv_userdob = findViewById(R.id.text_userdob_val);
        tv_userloc = findViewById(R.id.text_userloc_val);
        tv_test_name = findViewById(R.id.text_testn_val);
        tv_test_spec = findViewById(R.id.text_testspec_val);

        tv_username.setText(user_name);
        tv_userdob.setText(user_dob);
        tv_test_name.setText(test_name);
        tv_test_spec.setText(test_spec);

        if (tv_userloc==null)
        {
            Toast.makeText(TestDetailsActivity.this, "User didn't set the location", Toast.LENGTH_LONG).show();
        }
        else
        {
            tv_userloc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Uri uri_loc = Uri.parse(user_loc);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri_loc);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
            });
        }
    }
}