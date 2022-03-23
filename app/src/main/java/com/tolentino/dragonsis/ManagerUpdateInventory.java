package com.tolentino.dragonsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class ManagerUpdateInventory extends AppCompatActivity {

    ImageView img_man_upd_inv_back;
    TextView txt_man_upd_inv_id;
    TextView txt_man_upd_inv_prod_name;
    TextView txt_man_upd_inv_quantity;
    EditText edit_man_upd_inv_quantity_change;
    RadioButton radio_man_upd_inv_add;
    RadioButton radio_man_upd_inv_sold;
    RadioButton radio_man_upd_inv_remove;
    EditText edit_man_upd_inv_remarks;
    Button btn_man_upd_inv_update;
    SharedPreferences pref;
    DbManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_update_inventory);

        img_man_upd_inv_back = findViewById(R.id.img_man_upd_inv_back);
        txt_man_upd_inv_id = findViewById(R.id.txt_man_upd_inv_id);
        txt_man_upd_inv_prod_name = findViewById(R.id.txt_man_upd_inv_prod_name);
        txt_man_upd_inv_quantity = findViewById(R.id.txt_man_upd_inv_quantity);
        edit_man_upd_inv_quantity_change = findViewById(R.id.edit_man_upd_inv_quantity_change);
        radio_man_upd_inv_add = findViewById(R.id.radiobtn_man_upd_inv_add);
        radio_man_upd_inv_sold = findViewById(R.id.radiobtn_man_upd_inv_sold);
        radio_man_upd_inv_remove = findViewById(R.id.radiobtn_man_upd_inv_remove);
        edit_man_upd_inv_remarks = findViewById(R.id.edit_man_upd_inv_remarks);
        btn_man_upd_inv_update = findViewById(R.id.btn_man_upd_inv_update);

        db = new DbManager(this);
        pref = getSharedPreferences("inventory_list", MODE_PRIVATE);

        //Back to Manager View Inventory Page
        img_man_upd_inv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ManagerUpdateInventory.this, ManagerViewInventory.class);
                startActivity(i);
                finish();
            }
        });

        //Set Values of TextViews
        txt_man_upd_inv_id.setText(pref.getString("inventory_ID", null));
        txt_man_upd_inv_prod_name.setText(pref.getString("prod_name", null));
        txt_man_upd_inv_quantity.setText(pref.getString("inventory_quantity", null));
        edit_man_upd_inv_remarks.setText(pref.getString("inventory_remark", null));

    }
}