package com.tolentino.dragonsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btn_login;
    Button btn_manager_login;
    Spinner spinner;
    String[] numberArray = {"Employee","Manager"};
    EditText a1,a2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_login = (Button) findViewById(R.id.mainlogin);
        spinner = (Spinner)findViewById(R.id.spinner);
        a1=(EditText)findViewById(R.id.mainusername);
        a2=(EditText)findViewById(R.id.mainpassword);


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,numberArray);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinner.getSelectedItem().equals("Employee")){
                    btn_login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            employee_login();
                        }
                    });
                }
                if (spinner.getSelectedItem().equals("Manager")){
                    btn_login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            manager_login();
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void employee_login(){
        String user=a1.getText().toString().trim();
        String pass=a2.getText().toString().trim();
        if(((user.equals("")))){
            Toast.makeText(this,"Please fill up the Username!!!",Toast.LENGTH_LONG).show();
        }else if (((pass.equals("")))){
            Toast.makeText(this,"Please fill up the Password!!!",Toast.LENGTH_LONG).show();
        }else if(user.equals("employee") && pass.equals("employee123")) {
            Toast.makeText(this,"Logging in!",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, EmployeeMenu.class);
            startActivity(intent);
        }else {
            Toast.makeText(this,"Invalid Details!!",Toast.LENGTH_LONG).show();
        }
    }

    public void manager_login(){
        String user=a1.getText().toString().trim();
        String pass=a2.getText().toString().trim();
        if(((user.equals("")))){
            Toast.makeText(this,"Please fill up the Username!!!",Toast.LENGTH_LONG).show();
        }else if (((pass.equals("")))){
            Toast.makeText(this,"Please fill up the Password!!!",Toast.LENGTH_LONG).show();
        }else if(user.equals("manager") && pass.equals("manager123")) {
            Toast.makeText(this,"Logging in!",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, ManagerMenu.class);
            startActivity(intent);
        }else {
            Toast.makeText(this,"Invalid Details!!",Toast.LENGTH_LONG).show();
        }
    }
}