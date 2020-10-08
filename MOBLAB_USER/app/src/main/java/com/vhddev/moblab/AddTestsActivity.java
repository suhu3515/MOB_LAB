package com.vhddev.moblab;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddTestsActivity extends AppCompatActivity{

    Button button_select_img, button_upload_img,button_add_tests;
    String user_id;
    EditText et_user_test;

    private int PICK_IMAGE_REQUEST = 1;


    private Uri filePath;

    private Bitmap bitmap;
    List<String> specimen_list = new ArrayList<String>();

    User user = SharedPrefManager.getInstance(this).getUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tests);


        setTitle("Add Tests");

        button_select_img = findViewById(R.id.btn_select);
        button_upload_img = findViewById(R.id.btn_upload);
        button_add_tests = findViewById(R.id.btn_submitTest);
        et_user_test = findViewById(R.id.et_user_test);

        user_id = String.valueOf(user.getUid());


        button_upload_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadImage();

            }
        });

        button_select_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showFileChooser();
                button_select_img.setVisibility(View.INVISIBLE);

            }
        });

        button_add_tests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                add_user_tests();

            }
        });

    }

    private void add_user_tests()
    {
        class AddUserTests extends AsyncTask<Void,Void,String>
        {
            String user_test = et_user_test.getText().toString().trim();

            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();
                params.put("user_id",user_id);
                params.put("user_test",user_test);

                return requestHandler.sendPostRequest(URLs.URL_ADD_USER_TESTS,params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                Toast.makeText(getApplicationContext(),"Added tests successfully...",Toast.LENGTH_LONG).show();
                addTests();
                goBack();

            }
        }

        AddUserTests aut  = new AddUserTests();
        aut.execute();

    }


    private void uploadImage()
    {
        class UploadImage extends AsyncTask<Bitmap,Void,String>
        {
            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected String doInBackground(Bitmap... params)
            {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String,String> data = new HashMap<>();

                data.put("image", uploadImage);
                data.put("user_id", user_id);

                String result = rh.sendPostRequest(URLs.URL_UPLOAD_PRESCRIPTION,data);

                return result;
            }




            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                button_select_img.setVisibility(View.INVISIBLE);
                loading = ProgressDialog.show(AddTestsActivity.this, "Uploading", "Your prescription is being uploaded...", true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                addTests();
                goBack();
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }

    private void goBack()
    {
        Intent homeintent = new Intent(AddTestsActivity.this,HomeActivity.class);
        startActivity(homeintent);
    }

    private void addTests()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("TestInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("TestsAdded",false);
        editor.apply();
    }

    private void showFileChooser()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            filePath = data.getData();
            try
            {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

}