package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class customer_verification extends AppCompatActivity {

    private Button  qrCodeButton,decimalCodeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_verification);

        qrCodeButton = findViewById(R.id.qr_br_code_id);
        decimalCodeButton = findViewById(R.id.decimalCodeButton_id);

        //qr or br code button

        qrCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),Qr_Br_Code.class));

            }
        });
    }
}
