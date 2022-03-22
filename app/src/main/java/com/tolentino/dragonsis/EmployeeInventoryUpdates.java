package com.tolentino.dragonsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class EmployeeInventoryUpdates extends AppCompatActivity {

    ImageView img_back_emp_inv_upd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_inventory_updates);

        img_back_emp_inv_upd = findViewById(R.id.img_back_emp_inv_upd);

        img_back_emp_inv_upd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EmployeeInventoryUpdates.this, EmployeeMenu.class);
                startActivity(i);
                finish();
            }
        });

    }
}