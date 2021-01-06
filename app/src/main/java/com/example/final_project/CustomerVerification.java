package com.example.final_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.HashMap;
import java.util.Map;

public class CustomerVerification extends AppCompatActivity {
    private static final String TAG = "CustomerVerification";
    private Button qrCodeButton, decimalCodeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_verification);

        final EditText code = findViewById(R.id.decimalCodeEditText_id);

        qrCodeButton = findViewById(R.id.qr_br_code_id);
        decimalCodeButton = findViewById(R.id.decimalCodeButton_id);

        decimalCodeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String decimalCode = code.getText().toString();

                // checking the value is empty or null.
                if (decimalCode == null || decimalCode.trim().isEmpty()) {
                    Toast.makeText(CustomerVerification.this, "Please enter code first!", Toast.LENGTH_SHORT).show();
                    return;
                }

                final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                firestore.collection("medicineDecimalCodes").document(decimalCode)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        ResultResponse response = document.toObject(ResultResponse.class);
                                        if (response != null) {
                                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                            int numberOfTimeReads = response.getNumberOfTimeReads();
                                            if (numberOfTimeReads <= 2) {
                                                Log.i(TAG, "onComplete: NumberOfTimeReads: " + numberOfTimeReads);
                                            }

                                            Map<String, Object> updateNumberOfTImeReads = new HashMap<>();
                                            updateNumberOfTImeReads.put("numberOfTimeReads", numberOfTimeReads + 1);
                                            firestore.collection("medicineDecimalCodes").document(decimalCode)
                                                    .update(updateNumberOfTImeReads);

                                            response.setNumberOfTimeReads(numberOfTimeReads + 1);
                                            Intent intent = new Intent(getApplicationContext(), DecimalResult.class);
                                            intent.putExtra("decimal_code_medicine_result", response);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(getApplicationContext(), "null", Toast.LENGTH_SHORT).show();
                                        }
//                                        Toast.makeText(CustomerVerification.this, "Medicine found. Data :" + document.getData(), Toast.LENGTH_LONG).show();
                                    } else {
                                        Log.d(TAG, "No such document");
                                        Toast.makeText(CustomerVerification.this, "No medicine info found", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(CustomerVerification.this, "Failed to check", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


        //qr or br code button

        qrCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanCode();
            }
        });

    }

    private void scanCode() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(Qr_Br_Code.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scan QR or BR Code");
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {

                String qrResult = result.getContents();
                Intent intent = new Intent(getApplicationContext(), Qr_Br_CodeResult.class);
                intent.putExtra("bar_code_medicine_result", qrResult);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Nothing Found! Try with valid one!", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
