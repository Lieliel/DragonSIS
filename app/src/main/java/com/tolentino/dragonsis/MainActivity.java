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
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btn_login;
    EditText a1,a2;
    DbManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_login = (Button) findViewById(R.id.mainlogin);
        a1=(EditText)findViewById(R.id.mainusername);
        a2=(EditText)findViewById(R.id.mainpassword);
        db=new DbManager(this);

        DbManager db = new DbManager(MainActivity.this);

        //Add dummy account if no manager record exists
        boolean managerExists = db.checkUserValues("manager");
        if(!managerExists) {
            Log.i("DATABASE TAG", String.valueOf(managerExists));
            db.insertUser("manager123", "manager", "manager@gmail.com", "Manager");
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();

                //Intent intent = new Intent(MainActivity.this, ManagerMenu.class);
                //startActivity(intent);
            }
        });

    }

    public void login(){
        String user=a1.getText().toString().trim();
        String pass=a2.getText().toString().trim();
        if((!(user.equals("")))&&(!(pass.equals("")))){
            Boolean ifexisting=db.LoginCheck(user,pass);
            if(ifexisting){
                if(db.getUserByUsername(user).get(0).get("user_type").toString().equals("Employee")){
                    Toast.makeText(this,"Login Successful",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(this, EmployeeMenu.class);
                    intent.putExtra("user_ID",user);
                    startActivity(intent);
                }else if(db.getUserByUsername(user).get(0).get("user_type").toString().equals("Manager")){
                    Toast.makeText(this,"Login Successful",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(this, ManagerMenu.class);
                    intent.putExtra("user_ID",user);
                    startActivity(intent);
                }else{
                    Toast.makeText(this,"Error in determining User Type", Toast.LENGTH_LONG).show();
                }




            }else{
                Toast.makeText(this,"Not registered or Invalid details",Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this,"All fields are required",Toast.LENGTH_LONG).show();
        }
    }

}