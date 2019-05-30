package com.example.wayzone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.common.hash.Hashing;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.ArrayList;

import kotlin.text.Charsets;

public class MainActivity extends AppCompatActivity {

    private TextView email;
    private TextView password;
    private TextView error;
    private ImageView logo ;
    private Button login;

    //Constante pour la creation de l'url
    private static int GET_DATA = 0;
    private static int INSERT_DATA =1;
    private static int DELETE_DATA=2;
    private static int VERIFICATION=3;

    //url de la requete
    private static String URL = "http://192.168.0.23/PPE-WEB/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //On appelle la class qui nous sert pour les connexions internet
        final HttpRequest http = new HttpRequest();

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
                        String url = urlCreator(Vemail, Vpassword, VERIFICATION, p);

                        //On envoit et recupere la reponse
                        JSONObject jsonRep = http.getJsonResponse(url);

                        //Si l'utilisateur existe
                        if (jsonRep.getString("User").matches("true")) {
                            Intent user_activity = new Intent(MainActivity.this, UserActivity.class);
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


    public String urlCreator(String mail, String mp, int choice, ArrayList<String> parametre)
    {
        //On utilise la bibliotheque guava sinon on doit creer le code de hashing sha1 nous meme. Langage obsoléte
        //sha1 est barré car considere comme non securiser actuellement.
        mp = Hashing.sha1().hashString(mp, Charsets.UTF_8).toString();

        //Ce switch ne contient pas de break car le return fait office de break
        switch (choice)
        {
            case 0:
                return URL+"?call_api=true&user="+mail+"&password="+mp+"&getData="+parametre.get(0);

            case 1:
                return URL+"?call_api=true&user="+mail+"&password="+mp+"&insertData="+parametre.toString().replaceAll("[\\[\\]]","");
            case 2:
                Log.d("Url/Case2",URL+"?call_api=true&user="+mail+"&password="+mp+"&deleteData="+parametre.toString().replaceAll("[\\[\\]]",""));
                return URL+"?call_api=true&user="+mail+"&password="+mp+"&deleteData="+parametre.toString().replaceAll("[\\[\\]]","");
            case 3:
                Log.d("Url/Case2",URL+"?call_api=true&user="+mail+"&password="+mp+"&user_exist=true");
                return URL+"?call_api=true&user="+mail+"&password="+mp+"&user_exist=true";


            default:
                    return "";


        }
    }

}
