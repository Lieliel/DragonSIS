package com.tolentino.dragonsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class UserViewInventoryUpdates extends AppCompatActivity {

    ImageView img_back_user_inventory4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_inventory_updates);

        img_back_user_inventory4 = findViewById(R.id.img_back_user_inventory4);

        img_back_user_inventory4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserViewInventoryUpdates.this, EmployeeMenu.class);
                startActivity(i);
            }
        });

    }
}