package com.vhddev.moblab;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
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

public class BookRequestActivity extends AppCompatActivity {

    String loggedIn_username, user_tr_date, doc_name, loggedIn_dob;
    int loggedIn_user;
    Button btn_uploadPrescr, btn_addTests;
    TextView tv_tr_status, tv_user_name, tv_user_dob;
    EditText et_doc_name,et_test_date;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_request);

        setTitle("Book Test Request");

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        User user = SharedPrefManager.getInstance(this).getUser();
        loggedIn_user = user.getUid();
        loggedIn_username = user.getUname();
        loggedIn_dob = user.getUdob();


        btn_uploadPrescr = findViewById(R.id.btn_upload_btn);
        btn_addTests = findViewById(R.id.btn_addtests);
        tv_tr_status = findViewById(R.id.txt_tr_status);
        tv_user_name = findViewById(R.id.tv_username);
        tv_user_dob = findViewById(R.id.tv_userdob);
        et_doc_name = findViewById(R.id.et_userdoc);
        et_test_date = findViewById(R.id.et_test_date);

        tv_user_name.setText(loggedIn_username);
        tv_user_dob.setText(loggedIn_dob);

        et_test_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(BookRequestActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        //user_tr_date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());



        btn_uploadPrescr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validation_1();

            }
        });

        btn_addTests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validation_2();

            }
        });
    }

    private void validation_2()
    {
        if (et_doc_name.getText().toString().isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(BookRequestActivity.this);
            builder.setMessage("Doctor's name is empty. Are you doing the test by yourself?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            et_doc_name.setText("SELF");
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.cancel();
                            et_doc_name.requestFocus();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
        else if (et_test_date.getText().toString().isEmpty())
        {
            et_test_date.setError("Please select a date");
            et_test_date.requestFocus();
        }
        else
        {
            goToAddTests();
        }
    }

    private void goToAddTests()
    {
        user_tr_date = et_test_date.getText().toString();
        doc_name = et_doc_name.getText().toString();
        Intent testAddIntent = new Intent(BookRequestActivity.this, AddTestsActivity.class);
        testAddIntent.putExtra("doctor_name", doc_name);
        testAddIntent.putExtra("req_date", user_tr_date);
        startActivity(testAddIntent);

    }

    private void validation_1()
    {
        if (et_doc_name.getText().toString().isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(BookRequestActivity.this);
            builder.setMessage("Doctor's name is empty. Are you doing the test by yourself?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            et_doc_name.setText("SELF");
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.cancel();
                            et_doc_name.requestFocus();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
        else if (et_test_date.getText().toString().isEmpty())
        {
            et_test_date.setError("Please select a date");
            et_test_date.requestFocus();
        }
        else
        {
            goToUpload();
        }
    }

    private void goToUpload()
    {
        user_tr_date = et_test_date.getText().toString();
        doc_name = et_doc_name.getText().toString();
        Intent uploadIntent = new Intent(BookRequestActivity.this,UploadPrescriptionActivity.class);
        uploadIntent.putExtra("user_id", loggedIn_user);
        uploadIntent.putExtra("doctor_name", doc_name);
        uploadIntent.putExtra("req_date", user_tr_date);
        startActivity(uploadIntent);
    }

    private void updateLabel()
    {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        et_test_date.setText(sdf.format(myCalendar.getTime()));
    }

}
