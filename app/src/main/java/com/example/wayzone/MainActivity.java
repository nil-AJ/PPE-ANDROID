package com.example.wayzone;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView email;
    private TextView password;
    private TextView error;
    private TextView edit_url;
    private ImageView logo ;
    private Button login;
    private Button btn_edit_url;


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
        edit_url = findViewById(R.id.edit_url);
        login = findViewById(R.id.btn_login);
        btn_edit_url = findViewById(R.id.btn_edit_url);

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

                       /**
                        * Ne marche pas pour une raison inconnue
                        * error.setTextColor(Color.GREEN);
                        error.setText("Connexion en cours au serveur...");
                        login.setVisibility(View.INVISIBLE);*/


                        //On recupere l'url pour la requete
                        String url = api.urlCreator(Vemail, Vpassword);

                        //On envoit la requete et recupere la reponse
                        JSONObject jsonRep = http.getJsonResponse(url);
                        Log.d("****************",jsonRep.toString());

                        if (jsonRep.getString("Response") != "500")
                        {


                            //Si l'utilisateur existe
                            if (jsonRep.getString("User").matches("true")) {

                                //On sauvegarde les données utilisateur
                                api.setUser_info(jsonRep.getJSONObject("0"));

                                //On change d'activité
                                Intent user_activity = new Intent(MainActivity.this, MenuActivity.class);
                                startActivity(user_activity);
                            } else {
                                error.setTextColor(Color.RED);
                                error.setText("Vous n'etes pas autoriser à vous connnecter");
                                login.setVisibility(View.VISIBLE);
                                Toast.makeText(MainActivity.this,"Penser a verifier votre connexion internet",Toast.LENGTH_SHORT);
                            }
                    }else {
                            //Ici on permet a l'utilisateur de rentrer un nouvelle url pour la base de donnée si celle ci n'est pas correct
                            error.setTextColor(Color.RED);
                            error.setText("Impossible de ce connecter a la base de donnée");
                            login.setVisibility(View.INVISIBLE);
                            edit_url.setText(api.getURL());
                            edit_url.setVisibility(View.VISIBLE);
                            btn_edit_url.setVisibility(View.VISIBLE);

                            btn_edit_url.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    String new_url =edit_url.getText().toString();
                                    api.setURL(new_url);
                                    edit_url.setVisibility(View.INVISIBLE);
                                    btn_edit_url.setVisibility(View.INVISIBLE);
                                    error.setVisibility(View.INVISIBLE);
                                    login.setVisibility(View.VISIBLE);
                                }
                            });

                        }
                    }else {
                        error.setTextColor(Color.RED);
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
