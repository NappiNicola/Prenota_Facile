package com.example.prenotafacile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Registrazione extends AppCompatActivity {

    EditText nome, cognome, codiceFiscale, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);

        nome = (EditText) findViewById(R.id.NomeInput);
        cognome = (EditText) findViewById(R.id.CognomeInput);
        codiceFiscale = (EditText) findViewById(R.id.CFInput);
        password = (EditText) findViewById(R.id.PasswordInput);

    }

    public void login(View v){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }

    public void registrazion(View v){
        String name = nome.getText().toString().trim();
        String surname = cognome.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String cf = codiceFiscale.getText().toString().trim();

        nome.setText("");
        cognome.setText("");
        password.setText("");
        codiceFiscale.setText("");

        MyDataBaseHelper mydb = new MyDataBaseHelper(Registrazione.this);
        mydb.addUser(name, surname, pass, cf);

        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.putExtra("CF", cf);
        i.putExtra("PSW", pass);
        startActivity(i);
    }

}