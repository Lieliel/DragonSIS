package com.tolentino.dragonsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ViewInventory extends AppCompatActivity {

    ImageView img_back_view_inventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_inventory);

        img_back_view_inventory = findViewById(R.id.img_back_view_inventory);

        img_back_view_inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ViewInventory.this, ManagerMenu.class);
                startActivity(i);
            }
        });
    }
}