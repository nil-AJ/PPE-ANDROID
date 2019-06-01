package com.example.wayzone;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpRequest {
    /*
    Cette classe utitlise la bibliotheque okHttp trés simple d"utilisation parceque java ne sait pas faire les chose simplement et que les connexions http en java sont
    une horreur pour rien. Langage obsoléte.
     */
    OkHttpClient client =new OkHttpClient();

    //Retourne le contenue html d'une page
    public String getContent(String url)
    {
        //Creer un autre thread ( solution a la va vite pour eviter le crash)
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if(response.isSuccessful()) {
                    return response.body().string();
                }else {
                    Log.d("HttpRequest/getContent ","Aucune reponse du serveur");
                    return "";

                }
            }
        }catch (IOException io)
        {
            Log.d("HttpRequest/getContent ",io.toString());
            return "";
        }

    }

    //Rerourne la reponse json d'une page
    public JSONObject getJsonResponse(String url)
    {
        //Creer un autre thread ( solution a la va vite pour eviter le crash)
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        JSONObject json = new JSONObject();
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if(response.isSuccessful()) {
                    String rep = response.body().string();
                    json = new JSONObject(rep);
                    return json;
                }else {
                    Log.d("HttpRequest/geJsonRep ","Aucune reponse du serveur");
                    json.put("Response","500");
                    return json;
                }
            }
        }catch (IOException | JSONException io)
        {
            Log.d("HttpRequest/getJson:",io.toString());
            try {
                json.put("Response","500");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return json;
        }
    }


}
