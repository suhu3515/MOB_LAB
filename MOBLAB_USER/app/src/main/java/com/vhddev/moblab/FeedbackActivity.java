package com.vhddev.moblab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class FeedbackActivity extends AppCompatActivity {

    EditText et_fdbk;
    RatingBar ratingBar;
    Button btn_fdbk;
    float rating;
    String tr_date,doc_name,user_id,tester_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        setTitle("Feedback");

        user_id = String.valueOf(getIntent().getExtras().getInt("user_id"));
        tester_name = getIntent().getExtras().getString("tester_name");
        doc_name = getIntent().getExtras().getString("doc_name");
        tr_date = getIntent().getExtras().getString("tr_date");

        et_fdbk = findViewById(R.id.edt_feedback);
        ratingBar = findViewById(R.id.rating);
        btn_fdbk = findViewById(R.id.btn_submitfdbk);

        btn_fdbk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rating = ratingBar.getRating();

                if (rating == 0)
                {
                    Toast.makeText(FeedbackActivity.this, "Please rate using stars", Toast.LENGTH_SHORT).show();
                }
                else if (et_fdbk.getText().toString().isEmpty())
                {
                    et_fdbk.setError("Please leave us a feedback!");
                    et_fdbk.requestFocus();
                }
                else
                {
                    submit_feedback();
                }
            }
        });
    }

    private void submit_feedback()
    {
        class SubmitFeedback extends AsyncTask<Void,Void,String>
        {

            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();
                params.put("tester_name", tester_name);
                params.put("user_id", user_id);
                params.put("doctor_name", doc_name);
                params.put("tr_date", tr_date);
                params.put("rating_star", String.valueOf(rating));
                params.put("feedback",et_fdbk.getText().toString());

                return requestHandler.sendPostRequest(URLs.URL_USER_FEEDBACK,params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try
                {
                    JSONObject object = new JSONObject(s);
                    if (!object.getBoolean("error"))
                    {
                         Toast.makeText(FeedbackActivity.this,object.getString("message"),Toast.LENGTH_SHORT).show();

                        Intent thanksIntent = new Intent(FeedbackActivity.this,ThankYouActivity.class);
                        startActivity(thanksIntent);
                        finish();

                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }
        SubmitFeedback sf = new SubmitFeedback();
        sf.execute();
    }
}