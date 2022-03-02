package com.tolentino.dragonsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class UpdateAccount extends AppCompatActivity {

    SharedPreferences pref;
    TextView txt_upd_acc_username;
    TextView txt_upd_acc_password;
    TextView txt_upd_acc_email;
    DbManager db;

    EditText edit_upd_acc_username;
    EditText edit_upd_acc_password;
    EditText edit_upd_acc_email;
    Spinner spin_upd_acc_usertype;
    String[] userTypeArray = {"Employee", "Manager"};

    Button btn_upd_acc_update;
    Button btn_upd_acc_remove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);

        db = new DbManager(this);
        edit_upd_acc_username = findViewById(R.id.edit_upd_acc_username);
        edit_upd_acc_password = findViewById(R.id.edit_upd_acc_password);
        edit_upd_acc_email = findViewById(R.id.edit_upd_acc_email);
        spin_upd_acc_usertype = findViewById(R.id.spin_upd_acc_usertype);

        btn_upd_acc_remove = findViewById(R.id.btn_upd_acc_remove);
        btn_upd_acc_update = findViewById(R.id.btn_upd_acc_update);

        pref = getSharedPreferences("user_list", MODE_PRIVATE);
        edit_upd_acc_username.setText(pref.getString("username",null));
        edit_upd_acc_password.setText(pref.getString("password",null));
        edit_upd_acc_email.setText(pref.getString("email",null));

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, userTypeArray);
        spin_upd_acc_usertype.setAdapter(adapter);

        if(pref.getString("usertype", null).equals("Employee")){
            spin_upd_acc_usertype.setSelection(0);
        }else{
            spin_upd_acc_usertype.setSelection(1);
        }

        btn_upd_acc_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteUser(pref.getString("username", null));
                Log.i("USER TABLE:", "Successfully deleted Account");
                Intent i = new Intent(UpdateAccount.this, UserAccounts.class);
                startActivity(i);
            }
        });

        btn_upd_acc_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Add Values in input objects into String
                String user_name = edit_upd_acc_username.getText().toString();
                String password = edit_upd_acc_password.getText().toString();
                String email = edit_upd_acc_email.getText().toString();
                String user_type = spin_upd_acc_usertype.getSelectedItem().toString();

                db.updateUser(user_name, password, email, user_type);
                Intent i = new Intent(UpdateAccount.this, UserAccounts.class);
                startActivity(i);
            }
        });


    }
}