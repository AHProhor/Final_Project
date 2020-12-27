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
        TextView textView3 = findViewById(R.id.text_result3);
        TextView textView4 = findViewById(R.id.text_result4);
        TextView textView5 = findViewById(R.id.text_result5);
        TextView textView6 = findViewById(R.id.text_result6);


        ResultResponse result = getIntent().getParcelableExtra("decimal_code_medicine_result");
        if (result != null) {
            textView1.setText("Details : " + result.getDetails());
            textView2.setText("Production Date: " + result.getProductionDate());
            textView.setText("Expire Date: " + result.getExpireDate());
            textView3.setText("Price: "+result.getPrice());
            textView4.setText("Name: "+result.getName());
            textView5.setText("Type: "+result.getType());
            textView6.setText("Company: "+result.getCompany());
        }
    }
}