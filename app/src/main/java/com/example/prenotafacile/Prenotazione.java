package com.example.prenotafacile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class Prenotazione extends AppCompatActivity {

    String cf;
    TextView cfText, confermaTesto, confermaData;
    Spinner spinner;
    String[] type = {"Medico di base", "Cardiologia", "Radiologia"};
    CalendarView cw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prenotazione);

        cfText = (TextView) findViewById(R.id.CFText);
        spinner = (Spinner) findViewById(R.id.tipoVisita);
        //button = (Button) findViewById(R.id.confermaTipo);
        confermaTesto = (TextView) findViewById(R.id.conferma);
        confermaData = findViewById(R.id.confermaData);
        cw = findViewById(R.id.dataVisita);

        Intent i = getIntent();
        cf = i.getStringExtra("CF");

        cfText.setText(cf.toUpperCase(Locale.ROOT));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.support.v7.appcompat.R.layout.support_simple_spinner_dropdown_item, type);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Ottieni l'elemento selezionato
                String selectedValue = (String) parentView.getItemAtPosition(position);
                confermaTesto.setText(selectedValue.toUpperCase(Locale.ROOT));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Chiamato quando non viene selezionato alcun elemento
            }
        });

        cw.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                confermaData.setText(selectedDate);
            }
        });

    }

    public void confermaPrenotazione(View v){

        MyDataBaseHelper mydb = new MyDataBaseHelper(Prenotazione.this);
        String CF = cf;
        String type = confermaTesto.getText().toString().trim();
        String date = confermaData.getText().toString().trim();

        confermaTesto.setText("");
        confermaData.setText("");
        mydb.newVisita(CF, type, date);
    }

    public void toUserPage(View v){
        Intent i = new Intent(getApplicationContext(), AreaUtente.class);
        i.putExtra("CF", cf);
        i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(i);
    }

}