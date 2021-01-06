package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Qr_Br_CodeResult extends AppCompatActivity {
    private static final String TAG = "Qr_Br_CodeResult";
    private TextView qr_br_codeResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr__br__code_result);

        qr_br_codeResult = findViewById(R.id.qr_br_result_id);

        String result = getIntent().getStringExtra("bar_code_medicine_result");
        if(result!=null){
            Log.i(TAG, "Qr Code Result: "+result);
            qr_br_codeResult.setText(result);
        }
    }
}
