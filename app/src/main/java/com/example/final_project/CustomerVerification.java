package com.example.final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
                if(
                        decimalCode==null || decimalCode.trim().isEmpty()
                ) return;

                FirebaseFirestore firestore = FirebaseFirestore.getInstance();
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
                startActivity(new Intent(getApplicationContext(), Qr_Br_Code.class));
            }
        });

    }
}
