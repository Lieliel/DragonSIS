package com.tolentino.dragonsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_login = (Button) findViewById(R.id.mainlogin);
        a1=(EditText)findViewById(R.id.mainusername);
        a2=(EditText)findViewById(R.id.mainpassword);
        db=new DbManager(this);
        pref = getSharedPreferences("acc_details", MODE_PRIVATE);

        //Login to the app
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();

            }
        });

    }

    //login function
    public void login(){
        String user=a1.getText().toString().trim();
        String pass=a2.getText().toString().trim();

        //check if Login EditTexts are filled
        if((!(user.equals("")))&&(!(pass.equals("")))){

            //check if credentials exist
            Boolean ifexisting=db.LoginCheck(user,pass);

            if(ifexisting){
                //determines user type of the user that logs in
                if(db.getUserByUsername(user).get(0).get("user_type").toString().equals("Employee")){
                    Toast.makeText(this,"Login Successful",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(this, EmployeeMenu.class);
                    intent.putExtra("user_name",user);

                    //store user type for later use
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("user_name",db.getUserByUsername(user).get(0).get("user_name").toString());
                    editor.putString("user_type",db.getUserByUsername(user).get(0).get("user_type").toString());
                    editor.commit();
                    Log.i("LOGIN TAG", pref.getString("user_name", null));
                    Log.i("LOGIN TAG", pref.getString("user_type", null));

                    startActivity(intent);
                    finish();
                }else if(db.getUserByUsername(user).get(0).get("user_type").toString().equals("Manager")){
                    Toast.makeText(this,"Login Successful",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(this, ManagerMenu.class);
                    intent.putExtra("user_name",user);

                    //store user type for later use
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("user_name",db.getUserByUsername(user).get(0).get("user_name").toString());
                    editor.putString("user_type",db.getUserByUsername(user).get(0).get("user_type").toString());
                    editor.commit();
                    Log.i("LOGIN TAG", pref.getString("user_name", null));
                    Log.i("LOGIN TAG", pref.getString("user_type", null));

                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(this,"Error in determining User Type", Toast.LENGTH_LONG).show();
                }




            }else{
                //Developer Account Login
                if(user.equals("mandev")&&pass.equals("mandev123")){
                    Toast.makeText(this,"Developer Login Successful",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(this, ManagerMenu.class);
                    intent.putExtra("user_ID",user);

                    //store user type for later use
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("user_name","mandev");
                    editor.putString("user_type","Manager");
                    editor.commit();
                    Log.i("LOGIN TAG", pref.getString("user_name", null));
                    Log.i("LOGIN TAG", pref.getString("user_type", null));

                    startActivity(intent);
                    finish();
                }else if(user.equals("empdev")&&pass.equals("empdev123")) {
                    Toast.makeText(this, "Developer Login Successful", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(this, EmployeeMenu.class);
                    intent.putExtra("user_ID", user);

                    //store user type for later use
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("user_name","empdev");
                    editor.putString("user_type","Employee");
                    editor.commit();
                    Log.i("LOGIN TAG", pref.getString("user_name", null));
                    Log.i("LOGIN TAG", pref.getString("user_type", null));

                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(this,"Not registered or Invalid details",Toast.LENGTH_LONG).show();
                }

            }
        }else{
            Toast.makeText(this,"All fields are required",Toast.LENGTH_LONG).show();
        }
    }

}