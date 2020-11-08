package com.vhddev.moblab;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.util.HashMap;

public class UploadPrescriptionActivity extends AppCompatActivity {

    String logged_user_id, doctor_name, request_date;
    int user_id;
    Button btn_choose, btn_upload;
    ImageView prescription_img;

    private int PICK_IMAGE_REQUEST = 1;

    private static final int STORAGE_PERMISSION_CODE = 123;

    private Bitmap bitmap;

    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_prescription);

        setTitle("Upload Prescription");

        requestStoragePermission();

        btn_choose = findViewById(R.id.btn_select);
        btn_upload = findViewById(R.id.btn_upload);
        prescription_img = findViewById(R.id.img_prescription);

        doctor_name = getIntent().getExtras().getString("doctor_name");
        request_date = getIntent().getExtras().getString("req_date");
        user_id = getIntent().getExtras().getInt("user_id");
        logged_user_id = String.valueOf(user_id);

        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showFileChooser();

            }
        });

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadImage();

            }
        });

    }

    private void uploadImage()
    {
        class UploadImage extends AsyncTask<Bitmap,Void,String>
        {
            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();


            @Override
            protected String doInBackground(Bitmap... params) {

                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String,String> data = new HashMap<>();

                data.put("image", uploadImage);
                data.put("user_id", logged_user_id);
                data.put("doctor_name", doctor_name);
                data.put("test_date", request_date);

                String result = rh.sendPostRequest(URLs.URL_UPLOAD_PRESCRIPTION,data);

                return result;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = ProgressDialog.show(UploadPrescriptionActivity.this, "Uploading", "Your prescription is being uploaded...",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                goHome();
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }

    private void goHome()
    {
        Intent homeIntent = new Intent(UploadPrescriptionActivity.this, HomeActivity.class);
        startActivity(homeIntent);
    }

    private String getStringImage(Bitmap bmp)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        return encodedImage;
    }


    private void showFileChooser()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            filePath = data.getData();

            try
            {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                prescription_img.setImageBitmap(bitmap);
                btn_upload.setEnabled(true);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

    }

    private void requestStoragePermission()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
        return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE))
        {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission

        }

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }
}