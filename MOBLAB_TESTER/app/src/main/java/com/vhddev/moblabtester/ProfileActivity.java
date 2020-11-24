package com.vhddev.moblabtester;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    TextView tv_name,tv_dob,tv_mob,tv_mail,tv_place,tv_pin,tv_hname;
    Button btn_pass, btn_logout;
    Tester tester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setTitle("Profile");

        tester = SharedPrefManager.getInstance(this).getTester();

        tv_name = findViewById(R.id.tv_uname_value);
        tv_dob = findViewById(R.id.tv_dob_value);
        tv_mob = findViewById(R.id.tv_mob_value);
        tv_mail = findViewById(R.id.tv_mail_value);
        tv_place = findViewById(R.id.tv_place_value);
        tv_pin = findViewById(R.id.tv_pin_value);
        tv_hname = findViewById(R.id.tv_hname_value);

        btn_pass = findViewById(R.id.btn_change_pass);
        btn_logout = findViewById(R.id.btn_logout);

        tv_name.setText(tester.getTname());
        tv_dob.setText(tester.getTdob());
        tv_mob.setText(tester.getTmobile());
        tv_mail.setText(tester.getTemail());
        tv_place.setText(tester.getTplace());
        tv_pin.setText(tester.getTpin());
        tv_hname.setText(tester.getThname());

        btn_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent passIntent = new Intent(ProfileActivity.this,ChangePasswordActivity.class);
                startActivity(passIntent);

            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPrefManager.getInstance(getApplicationContext()).logout();

            }
        });

    }
}