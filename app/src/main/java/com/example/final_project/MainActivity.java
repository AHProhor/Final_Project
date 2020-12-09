package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private Button customerButton,shopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customerButton = findViewById(R.id.customerButton_id);
        shopButton = findViewById(R.id.shopButton_id);


        //Shopkeeper Button Work
        shopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this,"Thank you,Be with us",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,ShopKepeerLoginActivity.class);
                //intent.putExtra();
                startActivity(intent);
            }
        });


        //patient Button Work
        customerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this,"Thank you",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, CustomerVerification.class);
                //intent.putExtra()
                startActivity(intent);
            }
        });
    }
}
