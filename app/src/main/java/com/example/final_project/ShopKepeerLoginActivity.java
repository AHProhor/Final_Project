package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ShopKepeerLoginActivity extends AppCompatActivity {

    private Button createAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_kepeer_login);

        //create new account for shopkeeper
        createAccountButton = findViewById(R.id.createNewAccountButton_id);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ShopKepeerLoginActivity.this,"Carefully fill up the form",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ShopKepeerLoginActivity.this, ShopkeeperRegistration.class);
                //intent.putExtra();
                startActivity(intent);
            }
        });

    }
}
