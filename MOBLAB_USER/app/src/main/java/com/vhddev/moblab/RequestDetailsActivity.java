package com.vhddev.moblab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RequestDetailsActivity extends AppCompatActivity {

    TextView tv_doc,tv_date,tv_pay_stat,tv_status_val,tv_tester_caps,tv_tester_val;
    Button btn_add_feedback, btn_call,btn_resubmit;
    String doc_name,tr_date,pay_stat,status,tester_name,tester_mob;
    long mob;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_details);

        tv_doc = findViewById(R.id.textview_doctor_val);
        tv_date = findViewById(R.id.textview_date_val);
        tv_pay_stat = findViewById(R.id.textview_payment_val);
        tv_status_val = findViewById(R.id.textview_status_val);
        tv_tester_caps = findViewById(R.id.textview_tester_cap);
        tv_tester_val = findViewById(R.id.textview_tester_val);
        btn_add_feedback = findViewById(R.id.btn_add_feedback);
        btn_call = findViewById(R.id.btn_call_tester);
        btn_resubmit = findViewById(R.id.btn_resubmit);

        user = SharedPrefManager.getInstance(this).getUser();

        doc_name = getIntent().getExtras().getString("doc_name");
        tr_date = getIntent().getExtras().getString("request_date");
        pay_stat = getIntent().getExtras().getString("p_stat");
        status = getIntent().getExtras().getString("stat");
        tester_name = getIntent().getExtras().getString("tester_name");
        tester_mob = getIntent().getExtras().getString("tester_mob");

        mob = Long.parseLong(tester_mob);

        tv_doc.setText(doc_name);
        tv_date.setText(tr_date);
        tv_pay_stat.setText(pay_stat);
        tv_status_val.setText(status);
        switch (status)
        {
            case "Active":
                btn_call.setVisibility(View.GONE);
                btn_add_feedback.setVisibility(View.GONE);
                btn_resubmit.setVisibility(View.GONE);
                tv_tester_caps.setVisibility(View.GONE);
                tv_tester_val.setVisibility(View.GONE);
                break;

            case "Assigned Tester":
                btn_call.setVisibility(View.VISIBLE);
                btn_resubmit.setVisibility(View.GONE);
                btn_add_feedback.setVisibility(View.GONE);
                tv_tester_caps.setVisibility(View.VISIBLE);
                tv_tester_val.setVisibility(View.VISIBLE);
                tv_tester_val.setText(tester_name);
                break;

            case "Sample collected":

            case "Generating Report":
                btn_call.setVisibility(View.GONE);
                btn_resubmit.setVisibility(View.GONE);
                btn_add_feedback.setVisibility(View.GONE);
                tv_tester_caps.setVisibility(View.VISIBLE);
                tv_tester_val.setVisibility(View.VISIBLE);
                break;

            case "Completed":
                btn_call.setVisibility(View.GONE);
                btn_resubmit.setVisibility(View.GONE);
                btn_add_feedback.setVisibility(View.VISIBLE);
                tv_tester_caps.setVisibility(View.VISIBLE);
                tv_tester_val.setVisibility(View.VISIBLE);
                break;

            case "Rejected":
                tv_status_val.setTextColor(Color.parseColor("#FF0000"));
                btn_call.setVisibility(View.GONE);
                btn_resubmit.setVisibility(View.VISIBLE);
                btn_add_feedback.setVisibility(View.GONE);
                tv_tester_caps.setVisibility(View.GONE);
                tv_tester_val.setVisibility(View.GONE);
                break;
        }

        btn_add_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent feedbackIntent = new Intent(RequestDetailsActivity.this,FeedbackActivity.class);
                feedbackIntent.putExtra("user_id", user.getUid());
                feedbackIntent.putExtra("tester_name", tester_name);
                feedbackIntent.putExtra("doc_name", doc_name);
                feedbackIntent.putExtra("tr_date", tr_date);
                startActivity(feedbackIntent);
                finish();

            }
        });

        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+mob));
                startActivity(callIntent);

            }
        });

        btn_resubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent reqTestIntent = new Intent(RequestDetailsActivity.this,BookRequestActivity.class);
                startActivity(reqTestIntent);
                finish();

            }
        });

    }
}