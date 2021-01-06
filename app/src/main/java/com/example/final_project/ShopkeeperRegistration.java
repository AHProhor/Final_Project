package com.example.final_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.HashAccumulator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ShopkeeperRegistration extends AppCompatActivity {
    private static final String TAG = "ShopkeeperRegistration";

    private EditText shopeName, licanseNumber, ownerName, phoneNumber, gmail, password;
    private Button registrationOkButton;

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeper_registration);
        // EditText
        shopeName = findViewById(R.id.shopeNameId);
        licanseNumber = findViewById(R.id.licanseNumberId);
        ownerName = findViewById(R.id.ownerNameId);
        phoneNumber = findViewById(R.id.phoneNumberId);
        gmail = findViewById(R.id.gmailId);

        // registration Button
        registrationOkButton = findViewById(R.id.registrationOkId);

        registrationOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String rShopName = shopeName.getText().toString();
                final String rlicanseNumber = licanseNumber.getText().toString();
                final String rownerName = ownerName.getText().toString();
                final String rphoneNumber = phoneNumber.getText().toString();
                final String rgmail = gmail.getText().toString();

                if (isValid(rShopName) &&
                        isValid(rlicanseNumber) &&
                        isValid(rownerName) &&
                        isValid(rphoneNumber) &&
                        isValid(rgmail)){

                    firebaseFirestore.collection("shops")
                            .document(rlicanseNumber)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        if (task.getResult().getData() == null) {
                                            Map<String, String> shop = new HashMap<>();

                                            shop.put("ShopName", rShopName);
                                            shop.put("licanseNumber", rlicanseNumber);
                                            shop.put("ownerName", rownerName);
                                            shop.put("phoneNumber", rphoneNumber);
                                            shop.put("gmail", rgmail);
                                            firebaseFirestore.collection("shops")
                                                    .document(rlicanseNumber)
                                                    .set(shop);

                                            Toast.makeText(ShopkeeperRegistration.this, "Sending Verification Code to "+"+88" + rphoneNumber, Toast.LENGTH_SHORT).show();
                                            createNewAccount("+88" + rphoneNumber);
                                        } else {
                                            Toast.makeText(ShopkeeperRegistration.this, "Account already exist", Toast.LENGTH_SHORT).show();
                                        }
                                    }else{
                                        Toast.makeText(ShopkeeperRegistration.this, "An error to create account!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }


    // phone number verification
    private void createNewAccount(String phoneNumber) {

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
                        Toast.makeText(getApplicationContext(), "Failed to verify mobile number.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(String verificationId,
                                           PhoneAuthProvider.ForceResendingToken token) {
                        // The SMS verification code has been sent to the provided phone number, we
                        // now need to ask the user to enter the code and then construct a credential
                        // by combining the code with a verification ID.
                        Log.d(TAG, "onCodeSent:" + verificationId);
                        Toast.makeText(ShopkeeperRegistration.this, "Verification Code Sent!", Toast.LENGTH_SHORT).show();

                        // Save verification ID and resending token so we can use them later
                        mVerificationId = verificationId;
                        mResendToken = token;

                        showOTPVerificationEditText();
                    }

                });
    }

    private void showOTPVerificationEditText() {

        LinearLayout layout = findViewById(R.id.registration_layout);
        layout.setVisibility(View.GONE);

        LinearLayout verificationLayout = findViewById(R.id.verification_layout);
        verificationLayout.setVisibility(View.VISIBLE);

        findViewById(R.id.verify_mobile_otp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText mCode = findViewById(R.id.verification_code);
                String code = mCode.getText().toString();

                if (code != null && code.length() == 6)
                    verifyMobile(code, mVerificationId);
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
                            Intent intent = new Intent(ShopkeeperRegistration.this, CustomerVerification.class);
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

    private boolean isValid(String str) {
        return str != null && !str.isEmpty();
    }
}
