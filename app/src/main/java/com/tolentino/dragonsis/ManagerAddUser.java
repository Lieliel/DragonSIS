package com.tolentino.dragonsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class ManagerAddUser extends AppCompatActivity {

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
        setContentView(R.layout.activity_manager_add_user);

        img_back_add_user = findViewById(R.id.img_back_add_user);
        edit_add_username = findViewById(R.id.edit_add_username);
        edit_add_password = findViewById(R.id.edit_add_password);
        edit_confirm_password = findViewById(R.id.edit_confirm_password);
        spin_add_usertype = findViewById(R.id.spin_add_usertype);
        btn_user_submit = findViewById(R.id.btn_user_submit);
        db = new DbManager(this);

        //Show Hide password using eye icon
        ImageView imageViewShowHidePwd = findViewById(R.id.imageview_show_hide_pwd);
        imageViewShowHidePwd.setImageResource(R.drawable.hide_pass);
        imageViewShowHidePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edit_add_password.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    edit_add_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    //Change Icon
                    imageViewShowHidePwd.setImageResource(R.drawable.hide_pass);
                }
                else {
                    edit_add_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageViewShowHidePwd.setImageResource(R.drawable.show_pass);
                }
            }
        });


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, userTypeArray);
        spin_add_usertype.setAdapter(adapter);

        img_back_add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_user_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_username = edit_add_username.getText().toString();
                String txt_password = edit_add_password.getText().toString();
                String txt_confirm = edit_confirm_password.getText().toString();
                String spin_usertype = spin_add_usertype.getSelectedItem().toString();

                if (validateCredentials()) {
                    db.insertUser(txt_password, txt_username, spin_usertype);
                    Log.i("ACCOUNTS TABLE", "User Inserted: " + txt_username + ", " + txt_password + ", " + txt_confirm + ", " + spin_usertype);

                    Intent i = new Intent(ManagerAddUser.this, ManagerUserAccounts.class);
                    Intent endActivity = new Intent("finish_activity_man_accounts");
                    sendBroadcast(endActivity);
                    startActivity(i);
                    finish();
                }
            }
        });

    }

    private boolean validateCredentials() {
        String txt_username = edit_add_username.getText().toString();
        String txt_password = edit_add_password.getText().toString().trim();
        String txt_confirm = edit_confirm_password.getText().toString().trim();
        try {

            if (txt_username.equals(db.getUserByUsername(txt_username).get(0).get("user_name"))) {
                Toast.makeText(this, "Username already used", Toast.LENGTH_LONG).show();
                return false;
            }

        } catch (Exception e) {
            if (txt_password.isEmpty()) {
                Toast.makeText(this, "Field can't be empty", Toast.LENGTH_LONG).show();
                return false;
            }
            if (!txt_password.equals(txt_confirm)) {
                Toast.makeText(this, "Password did not match", Toast.LENGTH_LONG).show();
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}

