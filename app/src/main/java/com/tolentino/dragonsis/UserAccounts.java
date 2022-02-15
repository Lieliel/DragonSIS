package com.tolentino.dragonsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

public class UserAccounts extends AppCompatActivity {

    ImageView img_back_user_accounts;
    SearchView srch_user;
    ImageView img_add_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_accounts);

        img_back_user_accounts = findViewById(R.id.img_back_user_accounts);
        srch_user = findViewById(R.id.srch_user);
        img_add_user = findViewById(R.id.img_add_user);

        img_back_user_accounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserAccounts.this, ManagerMenu.class);
                startActivity(i);

            }
        });

        img_add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserAccounts.this, AddUser.class);
                startActivity(i);

            }
        });

    }
}