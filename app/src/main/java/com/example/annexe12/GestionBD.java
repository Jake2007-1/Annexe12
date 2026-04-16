package com.example.annexe12;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GestionBD  extends SQLiteOpenHelper {
    // C'est un singleton car on a bersoin d'un seuil objet de ce type pour tout le projet

    private static GestionBD instance;
    private SQLiteDatabase database;
    public static GestionBD getInstance(Context context){
        if (instance == null)
            instance = new GestionBD(context);
        return instance;
    }
    // constructeur doit être privé
    private GestionBD(@Nullable Context context) {
        super(context, "annexe 12", null, 1);
        ouvrirConnexionBD(); // ou on aurait ou le faire dans le onCreate de l'activité
    }

    // exécuté une seule fois lorsqu'on installe l'app sur un téléphone
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE inventeur( id INTEGER PRIMARY KEY AUTOINCREMENT, nom TEXT, origine TEXT, invention TEXT, annee INTEGER);");

        ajouteurInventeur(new Inventeur("Laszlo Biro", "Hongrie", "stylo à bille", 1938), db);
        ajouteurInventeur(new Inventeur("Benjamin Franklin", "États-Unis", "Paratonnerre", 1752), db);
        ajouteurInventeur(new Inventeur("Mary Anderson", "États-Unis", "Essuie-glace", 1903), db);
        ajouteurInventeur(new Inventeur("Grace Hopper", "États-Unis", "Compilateur", 1952), db);
        ajouteurInventeur(new Inventeur("benoit Rouquayrot", "France", "Scaphandre", 1864), db);
    }

    public void ajouteurInventeur( Inventeur i, SQLiteDatabase db){
        ContentValues cv = new ContentValues();
        cv.put("nom", i.getNom());
        cv.put("origine",i.getOrigine());
        cv.put("invention", i.getInvention());
        cv.put("annee", i.getAnnee());
        db.insert("inventeur", null, cv);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS inventeur;");
        onCreate(db);
    }

    public void ouvrirConnexionBD(){
        database = this.getWritableDatabase();
    }
    public ArrayList<String> retournerInventions(){
        ArrayList<String> listeInventions = new ArrayList<>();
        Cursor cursor = database.rawQuery("select invention from inventeur",null);
        while ( cursor.moveToNext()){
            listeInventions.add(cursor.getString(0));
        }
        cursor.close();
        return listeInventions;
    }
    public boolean retournerSiMatch(String nom, String invention){
        String[] parametres = {nom,invention};
        Cursor cursor = database.rawQuery("SELECT nom, invention FROM inventeur WHERE nom = ? AND invention = ?", parametres);
        boolean r =  cursor.moveToFirst();
        cursor.close();
        return r;

    }
}
