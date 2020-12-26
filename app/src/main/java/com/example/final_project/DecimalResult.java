package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DecimalResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decimal_result);

        TextView textView = findViewById(R.id.text_result);
        TextView textView1 = findViewById(R.id.text_result1);
        TextView textView2 = findViewById(R.id.text_result2);

        ResultResponse result = getIntent().getParcelableExtra("decimal_code_medicine_result");
        if (result != null) {
            textView1.setText("Production Date: " + result.getProductionDate());
            textView2.setText("Expire Date: " + result.getExpireDate());
            textView.setText("Details: " + result.getDetails()+"\nPrice: "+result.getPrice());
        }
    }
}