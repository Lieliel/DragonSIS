package com.tolentino.dragonsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class EmployeeViewInventory extends AppCompatActivity {

    ImageView img_back_user_inventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinventory);

        img_back_user_inventory = findViewById(R.id.img_back_user_inventory);

        img_back_user_inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EmployeeViewInventory.this, EmployeeMenu.class);
                startActivity(i);
            }
        });
    }
}