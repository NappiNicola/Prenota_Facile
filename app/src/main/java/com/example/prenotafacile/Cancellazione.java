package com.example.prenotafacile;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Cancellazione extends AppCompatActivity {

    private MyDataBaseHelper mydb;

    private String cf;
    private EditText idInsert;
    private TextView listaP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancellazione);
        mydb = new MyDataBaseHelper(Cancellazione.this);

        Intent i = getIntent();
        cf = i.getStringExtra("CF");

        idInsert = findViewById(R.id.Id_p);
        listaP = findViewById(R.id.cronologia);

        Cursor cursor = mydb.getListaVisita(cf);

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(cursor != null){
            while(cursor.moveToNext()){
                listaP.append("ID] " + cursor.getString(0) + " --> " + cursor.getString(1) + " : " + cursor.getString(2) +
                        cursor.getString(3) + "\n");
                listaP.append("------------------------------------------------\n");
            }
        }

    }

    public void elimina(View v){

        String id_ = idInsert.getText().toString().trim();
        mydb.DeletePrenotazioneByID(id_);

        idInsert.setText("");
        listaP.setText("");
        Cursor cursor = mydb.getListaVisita(cf);
        if(cursor != null){
            while(cursor.moveToNext()){
                listaP.append("ID] " + cursor.getString(0) + " --> " + cursor.getString(1) + " : " + cursor.getString(2) +
                        cursor.getString(3) + "\n");
                listaP.append("------------------------------------------------\n");
            }
        }

    }

    public void back(View v){

        Intent i = new Intent(getApplicationContext(), AreaUtente.class);
        i.putExtra("CF", cf);
        i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(i);

    }

}