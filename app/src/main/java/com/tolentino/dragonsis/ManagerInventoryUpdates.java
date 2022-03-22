package com.tolentino.dragonsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ManagerInventoryUpdates extends AppCompatActivity {

    ImageView img_back_man_view_inventory_updates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_view_inventory_updates);

        img_back_man_view_inventory_updates = findViewById(R.id.img_back_man_view_inventory_updates);

        img_back_man_view_inventory_updates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ManagerInventoryUpdates.this, ManagerMenu.class);
                startActivity(i);
                finish();
            }
        });

    }
}