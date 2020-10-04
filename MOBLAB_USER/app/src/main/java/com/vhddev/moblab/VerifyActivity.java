package com.vhddev.moblab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyActivity extends AppCompatActivity {

    TextView tv_num;
    EditText et_otp;
    Button btn_loc;
    String mob_number;

    private  String mVerificationId;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        tv_num = findViewById(R.id.txt_mob_num);
        et_otp = findViewById(R.id.veri_et_otp);
        btn_loc = findViewById(R.id.btn_loc);

        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();

        mob_number = intent.getStringExtra("mob_num");
        tv_num.setText(mob_number);
        sendVerificationCode(mob_number);

        btn_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                check_otp();

            }
        });

    }

    private void sendVerificationCode(String mobile)
    {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();

            if (code != null)
            {
                et_otp.setText(code);
                verifyVerificationCode(code);
            }

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

            Toast.makeText(VerifyActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();

        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            mVerificationId = s;

        }
    };

    private void verifyVerificationCode(String code)
    {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        signInWithPhoneAuthCredential(credential);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential)
    {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(VerifyActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Intent intent = new Intent(VerifyActivity.this, LocationActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                        else
                        {
                            String message = "Something went wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                            {
                                message = "invalid OTP entered";
                            }
                            Toast.makeText(VerifyActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void check_otp()
    {
        String otp = et_otp.getText().toString().trim();
        if (otp.length()<6 || et_otp.getText().toString().isEmpty())
        {
            et_otp.setError("OTP will be 6 digits.");
            et_otp.requestFocus();
        }
        else
        {
            verifyVerificationCode(otp);
        }
    }
}