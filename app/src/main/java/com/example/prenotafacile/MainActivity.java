package com.example.prenotafacile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    EditText codiceFiscale, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        codiceFiscale = (EditText) findViewById(R.id.CFInput);
        password = (EditText) findViewById(R.id.PassInput);

        Intent intent = getIntent();
        if(intent.getStringExtra("CF") != null && intent.getStringExtra("PSW") != null){
            codiceFiscale.setText(intent.getStringExtra("CF"));
            password.setText(intent.getStringExtra("PSW"));
        }

    }

    /*********************************************************************/
    /*                          REGISTRAZIONE                            */
    /*********************************************************************/
    public void registrazion(View v){
        Intent i = new Intent(getApplicationContext(), Registrazione.class);
        startActivity(i);
    }

    /*********************************************************************/
    /*                               LOGIN                               */
    /*********************************************************************/
    public void login(View v){

        if(codiceFiscale.getText().toString().equals("") || password.getText().toString().equals("")){
            Toast.makeText(this, "Codice fiscale o password errati o mancanti", Toast.LENGTH_SHORT).show();
            return;
        }

        String cf = codiceFiscale.getText().toString();
        String p = password.getText().toString();

        MyDataBaseHelper mydb = new MyDataBaseHelper(MainActivity.this);
        if(mydb.checkUser(cf,p) != 1){
            Toast.makeText(this, "Codice fiscale o password errati o mancanti", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent i = new Intent(getApplicationContext(), AreaUtente.class);
        i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.putExtra("CF", cf);
        startActivity(i);
    }
}