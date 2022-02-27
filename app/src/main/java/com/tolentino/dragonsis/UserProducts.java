package com.tolentino.dragonsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class UserProducts extends AppCompatActivity {

    ImageView img_back_user_inventory2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userproducts);

        img_back_user_inventory2 = findViewById(R.id.img_back_user_inventory2);

        img_back_user_inventory2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserProducts.this, EmployeeMenu.class);
                startActivity(i);
            }
        });


    }
}