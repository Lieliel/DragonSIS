package com.tolentino.dragonsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class UpdateAccount extends AppCompatActivity {

    SharedPreferences pref;
    //TextView sample;
    TextView txt_upd_acc_username;
    TextView txt_upd_acc_password;
    TextView txt_upd_acc_email;
    TextView txt_upd_acc_usertype;

    EditText edit_upd_acc_username;
    EditText edit_upd_acc_password;
    EditText edit_upd_acc_email;
    Spinner spin_upd_acc_usertype;

    Button btn_upd_acc_update;
    Button btn_upd_acc_remove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);

        //sample = findViewById(R.id.txt_sample);
        edit_upd_acc_username = findViewById(R.id.edit_upd_acc_username);
        edit_upd_acc_password = findViewById(R.id.edit_upd_acc_password);
        edit_upd_acc_email = findViewById(R.id.edit_upd_acc_email);
        txt_upd_acc_usertype = findViewById(R.id.txt_upd_acc_usertype);

        pref = getSharedPreferences("user_list", MODE_PRIVATE);
        edit_upd_acc_username.setText(pref.getString("username",null));
        edit_upd_acc_password.setText(pref.getString("password",null));
        edit_upd_acc_email.setText(pref.getString("email",null));
        txt_upd_acc_usertype.setText(pref.getString("usertype",null));


    }
}