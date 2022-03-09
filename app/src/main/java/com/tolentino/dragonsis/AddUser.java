package com.tolentino.dragonsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

public class AddUser extends AppCompatActivity {

    ImageView img_back_add_user;
    EditText edit_add_username;
    EditText edit_add_password;
    EditText edit_confirm_password;
    Spinner spin_add_usertype;
    String[] userTypeArray = {"Employee", "Manager"};
    Button btn_user_submit;
    DbManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        img_back_add_user = findViewById(R.id.img_back_add_user);
        spin_add_usertype = findViewById(R.id.spin_add_usertype);
        edit_add_username = findViewById(R.id.edit_add_username);
        edit_add_password = findViewById(R.id.edit_add_password);
        //findviewbyID ng confirm pass
        edit_confirm_password = findViewById(R.id.edit_confirm_password);
        btn_user_submit = findViewById(R.id.btn_user_submit);
        db = new DbManager(this);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, userTypeArray);
        spin_add_usertype.setAdapter(adapter);

        img_back_add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddUser.this,UserAccounts.class);
                startActivity(i);
            }
        });

        btn_user_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_username = edit_add_username.getText().toString();
                String txt_password = edit_add_password.getText().toString();
                String txt_confirm = edit_confirm_password.getText().toString();
                //String txt_confirm_password = gettext.tostring;
                String spin_usertype = spin_add_usertype.getSelectedItem().toString();

                //if statement(parehas yung txt pass tsaka txt confirm pass){

                db.insertUser(txt_password,txt_username,txt_confirm,spin_usertype);
                Log.i("ACCOUNTS TABLE", "User Inserted: " + txt_username + ", " + txt_password + ", " + txt_confirm + ", " + spin_usertype);

                Intent i = new Intent(AddUser.this, UserAccounts.class);
                startActivity(i);
            //}else{Toast error/incorrect password}

            }
        });

        }
    }