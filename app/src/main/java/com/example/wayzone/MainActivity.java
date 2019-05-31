package com.example.wayzone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView email;
    private TextView password;
    private TextView error;
    private ImageView logo ;
    private Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //On appelle la class qui nous sert pour les connexions internet
        final HttpRequest http = new HttpRequest();
        //On appelle la classe qui nous sert d'outil de conversation avec l'api
        final ApiTools api = new ApiTools();

        //On bind les variable avec leurs equivalent graphique
        email = findViewById(R.id.input_email);
        password= findViewById(R.id.input_password);
        error = findViewById(R.id.error_message);
        logo = findViewById(R.id.img_logo);
        login = findViewById(R.id.btn_login);

        //Listeneur du bouton login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Les variables qui vont recuperer la valeur de email et password
                String Vemail = email.getText().toString();
                String Vpassword = password.getText().toString();
                try {
                    //Si les valeurs rentrée par l'utilisateur ne sont pas vide
                    if(!Vemail.isEmpty() || !Vpassword.isEmpty()) {
                        ArrayList<String> p = new ArrayList<>();

                        //On recupere l'url pour la requete
                        String url = api.urlCreator(Vemail, Vpassword);

                        //On envoit et recupere la reponse
                        JSONObject jsonRep = http.getJsonResponse(url);

                        //Si l'utilisateur existe
                        if (jsonRep.getString("User").matches("true")) {

                            //On sauvegarde les données utilisateur
                            api.setUser_info(jsonRep.getJSONObject("0"));

                            //On change d'activité
                            Intent user_activity = new Intent(MainActivity.this, MenuActivity.class);
                            startActivity(user_activity);
                        }else{
                            error.setText("Vous n'etes pas autoriser à vous connnecter");
                        }
                    }else {
                        error.setText("Veuiller rentrer un mot de passe et un email");
                    }
                }catch (JSONException js)
                {
                    Log.d("MainActicity/JsonRep",js.toString());

                }

            }
        });
    }



}
