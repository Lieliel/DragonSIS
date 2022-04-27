package com.tolentino.dragonsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class UserAddProduct extends AppCompatActivity {

    ImageView img_back_add_product;
    EditText edit_add_productname;
    EditText edit_add_crit_num;
    EditText edit_add_price;
    Spinner spin_add_category;
    String[] productCategoryArray = {"Lumber", "Nails", "Screws", "Cement", "Gravel", "Sand", "Steel Bars", "Varnish", "Paint", "Brush/Roller", "PVC", "Special"};
    Button btn_product_submit;
    DbManager db;
    SharedPreferences user_pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_product);

        img_back_add_product = findViewById(R.id.img_back_add_product);
        edit_add_productname = findViewById(R.id.edit_add_productname);
        edit_add_crit_num = findViewById(R.id.edit_add_crit_num);
        edit_add_price = findViewById(R.id.edit_add_price);
        spin_add_category = findViewById(R.id.spin_add_category);
        btn_product_submit = findViewById((R.id.btn_product_submit));
        db = new DbManager(this);
        user_pref = getSharedPreferences("acc_details", MODE_PRIVATE);

        //Adapt Array to Spinner
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, productCategoryArray);
        spin_add_category.setAdapter(adapter);

        //Back to View Products
        img_back_add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(user_pref.getString("user_type", null).equals("Manager")){
                    Intent i = new Intent(UserAddProduct.this, ManagerViewProducts.class);
                    startActivity(i);
                    finish();
                }else{
                    Intent i = new Intent(UserAddProduct.this, EmployeeViewProducts.class);
                    startActivity(i);
                    finish();
                }

            }
        });

        //Add Product
        btn_product_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Convert to EditText Values to String
                String txt_prod_name = edit_add_productname.getText().toString();
                int txt_prod_crit_num = Integer.parseInt(edit_add_crit_num.getText().toString());
                int txt_prod_price = Integer.parseInt(edit_add_price.getText().toString());
                String spin_category = spin_add_category.getSelectedItem().toString();

                if (TextUtils.isEmpty(txt_prod_name) || TextUtils.isEmpty(spin_category)){
                    Toast.makeText(UserAddProduct.this, "Please make sure to input in all fields.", Toast.LENGTH_SHORT).show();
                }else {

                    //Use db function to add record to products table
                    db.insertProduct(txt_prod_name, 0, txt_prod_crit_num, txt_prod_price, spin_category);
                    Log.i("ACCOUNTS TABLE", "User Inserted: " + txt_prod_name + ", " + txt_prod_crit_num + ", " + txt_prod_price + ", " + spin_category);
                }
                if(user_pref.getString("user_type", null).equals("Manager")){
                    Intent i = new Intent(UserAddProduct.this, ManagerViewProducts.class);
                    startActivity(i);
                    finish();
                }else{
                    Intent i = new Intent(UserAddProduct.this, EmployeeViewProducts.class);
                    startActivity(i);
                    finish();
                }
            }
        });

    }
}