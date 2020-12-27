package com.example.final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ShopKepeerLoginActivity extends AppCompatActivity {
    private static final String TAG = "ShopKepeerLoginActivity";

    private Button createAccountButton;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_kepeer_login);

        //create new account for shopkeeper
        createAccountButton = findViewById(R.id.createNewAccountButton_id);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ShopKepeerLoginActivity.this, "Carefully fill up the form", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ShopKepeerLoginActivity.this, ShopkeeperRegistration.class);
                //intent.putExtra();
                startActivity(intent);
            }
        });

        findViewById(R.id.LoginButton_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text = findViewById(R.id.phone_number_id);

                String phoneNumber = text.getText().toString();

                if (phoneNumber != null && phoneNumber.length() == 11) {
                    loginWithPhoneNumber("+88" + phoneNumber);
                    Toast.makeText(ShopKepeerLoginActivity.this, "Please wait", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ShopKepeerLoginActivity.this, "Please enter a valid mobile number", Toast.LENGTH_SHORT).show();
                }

            }


        });

        findViewById(R.id.otp_verification_btn_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text = findViewById(R.id.otp_verification_code_id);

                String otpCode = text.getText().toString();

                if (otpCode != null && otpCode.length() == 6)
                    verifyMobile(otpCode, mVerificationId);
                else
                    Toast.makeText(ShopKepeerLoginActivity.this, "Please enter a valid OTP number", Toast.LENGTH_SHORT).show();

            }
        });

    }


    // phone number verification
    private void loginWithPhoneNumber(String phoneNumber) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential credential) {
                        Log.d(TAG, "onVerificationCompleted:" + credential);
                        signInWithPhoneAuthCredential(credential);
                    }


                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        // This callback is invoked in an invalid request for verification is made,
                        // for instance if the the phone number format is not valid.
                        Log.w(TAG, "onVerificationFailed", e);
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(String verificationId,
                                           PhoneAuthProvider.ForceResendingToken token) {
                        // The SMS verification code has been sent to the provided phone number, we
                        // now need to ask the user to enter the code and then construct a credential
                        // by combining the code with a verification ID.
                        Log.d(TAG, "onCodeSent:" + verificationId);

                        // Save verification ID and resending token so we can use them later
                        mVerificationId = verificationId;
                        mResendToken = token;
                    }

                });
    }

    private void verifyMobile(String code, String verificationId) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            Intent intent = new Intent(getApplicationContext(), CustomerVerification.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

}
