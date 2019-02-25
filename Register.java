package com.example.expressgrocery;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity  implements View.OnClickListener {

    DatabaseHelper myDb;
    EditText editUsername, editPassword, editName, editLastName;
    Button blinkReg, bShow;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        myDb = new DatabaseHelper(this);
        editUsername = (EditText) findViewById(R.id.editTextUsername);
        editPassword = (EditText) findViewById(R.id.editTextPassword);
        editName = (EditText) findViewById(R.id.editTextName);
        editLastName = (EditText) findViewById(R.id.editTextLastName);
        blinkReg = (Button) findViewById(R.id.blinkReg);
        blinkReg.setOnClickListener(this);
        bShow = (Button) findViewById(R.id.bShow);
        bShow.setOnClickListener(this);
    }

    @Override
    public void onClick(View V) {
        switch (V.getId()) {
            case R.id.blinkReg:

                boolean isInserted = myDb.insertData(editUsername.getText().toString(), editPassword.getText().toString(), editName.getText().toString(), editLastName.getText().toString());
                if (isInserted = true) {
                    Toast.makeText(Register.this, "Data inserted", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(Register.this, "Data not inserted", Toast.LENGTH_LONG).show();
                }
                Intent intent2 = new Intent();
                intent2.setClass(getApplicationContext(), MainActivity.class);
                startActivity(intent2);

            case R.id.bShow:

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

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();

    }
}
