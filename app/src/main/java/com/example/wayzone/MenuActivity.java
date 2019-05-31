package com.example.wayzone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONObject;

//A premiere vu cette activity peut sembler inutile car elle ne fait que la transition entre d'autre activité mais en realité
// elle servira de memoire tampon entre les differentes vues et permettre de stocké des données de l'utilisateur
public class MenuActivity extends AppCompatActivity {

    Button deconnexion;
    Button news;
    Button user_session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        deconnexion = findViewById(R.id.btn_deconnexion);
        news = findViewById(R.id.btn_news);
        user_session = findViewById(R.id.btn_utilisateur);

        deconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MenuActivity.this,NewsActivity.class);
                startActivity(intent);
            }
        });

        user_session.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this,UserActivity.class);
                startActivity(intent);
            }
        });

    }

}
