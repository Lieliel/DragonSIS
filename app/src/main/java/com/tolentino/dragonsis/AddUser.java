package com.tolentino.dragonsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

public class AddUser extends AppCompatActivity {

    ImageView img_back_add_user;
    EditText edit_add_username;
    EditText edit_add_password;
    EditText edit_add_email;
    Spinner spin_add_usertype;
    String[] userTypeArray = {"Employee", "Manager"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        img_back_add_user = findViewById(R.id.img_back_add_user);
        spin_add_usertype = findViewById(R.id.spin_add_usertype);
        edit_add_username = findViewById(R.id.edit_add_username);
        edit_add_password = findViewById(R.id.edit_add_password);
        edit_add_email = findViewById(R.id.edit_add_email);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, userTypeArray);
        spin_add_usertype.setAdapter(adapter);

        img_back_add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddUser.this,UserAccounts.class);
                startActivity(i);
            }
        });

        spin_add_usertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spin_add_usertype.getSelectedItem().equals("Employee")){
                    //if Employee naselect
                }
                if (spin_add_usertype.getSelectedItem().equals("Manager")){
                    //if Manager naselect
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        edit_add_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        edit_add_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        edit_add_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        }
    }