package com.tolentino.dragonsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ViewProducts extends AppCompatActivity {

    ImageView img_back_view_inventory2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_products2);

        img_back_view_inventory2 = findViewById(R.id.img_back_view_inventory2);

        img_back_view_inventory2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ViewProducts.this, ManagerMenu.class);
                startActivity(i);
            }
        });


    }
}