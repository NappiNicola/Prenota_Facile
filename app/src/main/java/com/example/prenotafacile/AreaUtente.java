package com.example.prenotafacile;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class AreaUtente extends AppCompatActivity {

    String cf;
    TextView cronologiaPrenotazioni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_utente);
        MyDataBaseHelper mydb = new MyDataBaseHelper(AreaUtente.this);

        cronologiaPrenotazioni = (TextView) findViewById(R.id.cronologia);
        cronologiaPrenotazioni.setText("");

        Intent i = getIntent();
        cf = i.getStringExtra("CF");
        Cursor cursor = mydb.getListaVisita(cf);

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(cursor != null){
            while(cursor.moveToNext()){
                cronologiaPrenotazioni.append(cursor.getString(1) + " --> " + cursor.getString(2) + " : " + cursor.getString(3) +"\n");
                cronologiaPrenotazioni.append("------------------------------------------------\n");
            }
        }

    }

    public void prenota(View v){
        Intent i = new Intent(getApplicationContext(), Prenotazione.class);
        i.putExtra("CF", cf);
        i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(i);
    }

    public void logOut(View v){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }

    public void deleteP(View v){
        Intent i = new Intent(getApplicationContext(), Cancellazione.class);
        i.putExtra("CF", cf);
        i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(i);
    }

}