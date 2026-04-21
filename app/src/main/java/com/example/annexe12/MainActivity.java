package com.example.annexe12;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    Ecouteur ec;
    TextView champReponse;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Je crée mon singleton
        GestionBD instance = GestionBD.getInstance(getApplicationContext());
        System.out.println(instance.retournerInventions().size());

        listView = findViewById(R.id.listView);
        ArrayAdapter<String> apapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, instance.retournerInventions());
        listView.setAdapter(apapter);

        champReponse = findViewById(R.id.reponse);

        ec = new Ecouteur();
        listView.setOnItemClickListener(ec);
    }
    public class Ecouteur implements AdapterView.OnItemClickListener {


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            if(GestionBD.getInstance(getApplicationContext()).retournerSiMatch("Mary Anderson", listView.getItemAtPosition(position).toString()))
                champReponse.setText("Bonne réponse");
            else{
                champReponse.setText("Mauvaise réponse");
                champReponse.setBackgroundColor(Color.RED);
            }

        }
    }
}