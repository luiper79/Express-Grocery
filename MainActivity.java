package com.example.expressgrocery;

import  android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    DatabaseHelper myDb;
    Button bLogin;
    EditText editUsername, editPassword;
    Button blinkReg, bShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editUsername = (EditText) findViewById(R.id.editTextUsername);
        editPassword = (EditText) findViewById(R.id.editTextPassword);
        bLogin = (Button) findViewById(R.id.bLogin);
        blinkReg = (Button) findViewById(R.id.blinkReg);
        bShow = (Button) findViewById(R.id.bShow);

        bLogin.setOnClickListener(this);
        blinkReg.setOnClickListener(this);
        bShow.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bLogin: {

                boolean isLogin = myDb.login(editUsername.getText().toString(), editPassword.getText().toString());
                if (isLogin) {
                    Toast.makeText(MainActivity.this, "Username and Password found", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Username and Password not found", Toast.LENGTH_LONG).show();
                }
            }

            case R.id.blinkReg: {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), Register.class);
                startActivity(intent);
            }

            case R.id.bShow: {
                Cursor res = myDb.getAllData();
                if (res.getCount() == 0) {
                    showMessage("Error", "Nothing found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("UserName :" + res.getString(1) + "\n");
                    buffer.append("Password :" + res.getString(2) + "\n");
                    buffer.append("Name :" + res.getString(3) + "\n");
                    buffer.append("LastName :" + res.getString(4) + "\n\n");

                    showMessage("Data", buffer.toString());
                }
            }

        }
    }
    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();

    }

}
