package com.vhddev.moblab;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    EditText edt_name, edt_dob, edt_hname, edt_place, edt_pin;
    Button btn_commn;
    TextView tv_commn;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        edt_name = findViewById(R.id.reg_et_name);
        edt_dob = findViewById(R.id.reg_et_dob);
        edt_hname = findViewById(R.id.reg_et_hname);
        edt_place = findViewById(R.id.reg_et_place);
        edt_pin = findViewById(R.id.reg_et_pincode);


        btn_commn = findViewById(R.id.btn_comm);
        tv_commn = findViewById(R.id.txt_login_msg);

        edt_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(RegisterActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        btn_commn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validate();

            }
        });

        tv_commn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();

            }
        });

    }


    private void updateLabel()
    {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edt_dob.setText(sdf.format(myCalendar.getTime()));
    }

    private void validate()
    {
        if(edt_name.getText().toString().isEmpty())
        {
            edt_name.setError("Please enter your full name!");
            edt_name.requestFocus();
        }
        else if (edt_dob.getText().toString().isEmpty())
        {
            edt_dob.setError("Please select your date of birth!");
            edt_dob.requestFocus();
        }
        else if (edt_hname.getText().toString().isEmpty())
        {
            edt_hname.setError("Please enter your house name!");
            edt_hname.requestFocus();
        }
        else if (edt_place.getText().toString().isEmpty())
        {
            edt_place.setError("Please enter your place!");
            edt_place.requestFocus();
        }
        else if (edt_pin.getText().toString().isEmpty())
        {
            edt_pin.setError("please enter your pin number");
            edt_pin.requestFocus();
        }
        else
        {
            nextPage();
        }
    }

    private void nextPage()
    {
        Intent communicationIntent = new Intent(RegisterActivity.this, RegisterCommActivity.class);
        communicationIntent.putExtra("uname",edt_name.getText().toString().trim());
        communicationIntent.putExtra("udob",edt_dob.getText().toString().trim());
        communicationIntent.putExtra("uhname",edt_hname.getText().toString().trim());
        communicationIntent.putExtra("uplace",edt_place.getText().toString().trim());
        communicationIntent.putExtra("upin",edt_pin.getText().toString().trim());
        startActivity(communicationIntent);
        finish();
    }
}