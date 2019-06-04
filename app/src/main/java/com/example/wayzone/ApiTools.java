package com.example.wayzone;

import android.util.Log;

import com.google.common.hash.Hashing;

import org.json.JSONObject;

import java.util.ArrayList;

import kotlin.text.Charsets;

//Cette classe rassemblera toutes methodes utiles pour la bonne communication entre l'api et l'application
public class ApiTools {


    //Constante pour la creation de l'url
    public static int GET_DATA = 0;
    public static  int INSERT_DATA =1;
    public static int DELETE_DATA=2;
    public static int UPDATE_DATA=3;

    //Si l'utlisateur souhaite hashé le mot de passe
    public static boolean HASHING_TRUE=true;
    public static boolean HASHING_FALSE=false;

    //On sauvegarde les info utilisateur dans cette variable
    public static JSONObject USER_INFO;

    //url de la requete
    private static String URL = "https://serveur-web.nil-ct.fr/PPE-WEB/";





    //Creer l'url de la requete vers l'api
    public String urlCreator(String mail, String mp, int choice, ArrayList<String> parametre, boolean hash)
    {
        //On utilise la bibliotheque guava sinon on doit creer le code de hashing sha1 nous meme. Langage obsoléte
        //sha1 est barré car considere comme non securiser actuellement.
        if(hash)
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
                Log.d("Api/Case3",URL+"?call_api=true&user="+mail+"&password="+mp+"&updateData="+parametre.get(0)+","+parametre.get(1)+","+parametre.get(2)+"&clause="+parametre.get(3)+","+parametre.get(4));
                return URL+"?call_api=true&user="+mail+"&password="+mp+"&updateData="+parametre.get(0)+","+parametre.get(1)+","+parametre.get(2)+"&clause="+parametre.get(3)+","+parametre.get(4);
                default:
                return "";


        }
    }

    //Si l'on rentre uniquement l'email et le mp l'url retourner nou renvois l'existence de l'utilisateur ou non
    public String urlCreator(String mail, String mp)
    {
        mp = Hashing.sha1().hashString(mp, Charsets.UTF_8).toString();

        Log.d("Url/Case2",URL+"?call_api=true&user="+mail+"&password="+mp+"&user_exist=true");
        return URL+"?call_api=true&user="+mail+"&password="+mp+"&user_exist=true";
    }


    public void setUser_info(JSONObject user_info) {
        this.USER_INFO = user_info;
    }

    public static void setURL(String URL) {
        ApiTools.URL = URL;
    }

    public static String getURL() {
        return URL;
    }
}
