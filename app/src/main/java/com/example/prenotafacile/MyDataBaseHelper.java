package com.example.prenotafacile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;


public class MyDataBaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Prenota_DB";
    private static final int DATABASE_VERSION = 1;

    //Campi tabella utente
    private static final String TABELE_NAME = "user";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "nome";
    private static final String COLUMN_SURNAME = "cognome";
    private static final String COLUMN_PASSWORD = "pass";
    private static final String COLUMN_CF = "cf";

    //campi tabella visita
    private static final String TABELE_VISITA = "visita";
    private static final String COLONNA_ID_VISITA = "_id";
    private static final String COLONNA_TIPO_VISITA = "tipo";
    private static final String COLONNA_CF_VISITA = "cf";
    private static final String COLONNA_DATA_VISITA = "data";
    private static final String COLONNA_ORA_VISITA = "orario";

    public MyDataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creazione tabella
        String creaTabella = "CREATE TABLE " + TABELE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_NAME + " TEXT, " + COLUMN_SURNAME + " TEXT, " + COLUMN_PASSWORD + " TEXT, "+
                COLUMN_CF + " TEXT " +
                ");";
        db.execSQL(creaTabella);

        //creazione tabella visita
        String creaTabellaVisita = "CREATE TABLE " + TABELE_VISITA + " (" +
                COLONNA_ID_VISITA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLONNA_TIPO_VISITA + " TEXT, " + COLONNA_CF_VISITA + " TEXT, " + COLONNA_DATA_VISITA + " TEXT, "
                + COLONNA_ORA_VISITA + " TEXT " + ");";
        db.execSQL(creaTabellaVisita);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELE_NAME);
        onCreate(db);
    }

    public void addUser(String nome, String cognome, String password, String cf){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, nome);
        cv.put(COLUMN_SURNAME, cognome);
        cv.put(COLUMN_PASSWORD, password);
        cv.put(COLUMN_CF, cf);

        long result = db.insert(TABELE_NAME, null, cv);
        if(result == -1){
            Toast.makeText(context, "Registrazione fallita", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Registrazione effettuata", Toast.LENGTH_SHORT).show();
        }
    }

    public int checkUser(String Cf, String passW){
        int i = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM user WHERE cf = '" + Cf + "' AND pass = '" + passW + "'";
        Cursor cursor = db.rawQuery(query, null);
            if (cursor.getCount() > 0)
                i = 1;
        return i;
    }

    public void newVisita(String cf, String tipoVisita, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLONNA_TIPO_VISITA, tipoVisita);
        cv.put(COLONNA_CF_VISITA, cf);
        cv.put(COLONNA_DATA_VISITA, date);
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime = time.format(formatter);
        cv.put(COLONNA_ORA_VISITA, formattedTime);

        long result = db.insert(TABELE_VISITA, null, cv);
        if(result == -1){
            Toast.makeText(context, "Prenotazione fallita", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Prenotazione effettuata", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor getListaVisita(String cf){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLONNA_ID_VISITA + ", " + COLONNA_TIPO_VISITA + ", " + COLONNA_DATA_VISITA + ", " + COLONNA_ORA_VISITA + " FROM " + TABELE_VISITA +
                " WHERE " + COLONNA_CF_VISITA + " = '" + cf + "';";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount() == 0){
            return  null;
        }
        return cursor;
    }

    public void DeletePrenotazioneByID(String id){

        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABELE_VISITA, "_id=?", new String[]{id});
        if(result == -1)
            Toast.makeText(context, "CANCELLAZIONE FALLITA", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "CANCELLAZIONE EFFETTUATA", Toast.LENGTH_SHORT).show();

    }

}
