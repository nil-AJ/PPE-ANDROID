package com.example.wayzone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class UserActivity extends AppCompatActivity {

    private TextView nom;
    private TextView password;
    private TextView phone_number;
    private TextView classe;
    private TextView date;
    private Button change_info;
    final JSONObject json = ApiTools.USER_INFO;
    HttpRequest httpRequest = new HttpRequest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user2);

        nom = findViewById(R.id.text_name);
        password = findViewById(R.id.text_password);
        phone_number = findViewById(R.id.text_pnumber);
        classe = findViewById(R.id.text_class);
        date = findViewById(R.id.text_date);
        change_info = findViewById(R.id.btn_change);


        try {
            final String old_nom = json.getString("name");
            final String old_pNumber= json.getString("phoneNumber");
            final String old_password = json.getString("password");
            String old_classe = json.getString("class");
            String Odate = json.getString("creationDate");
            final String email = json.getString("email");

            nom.setText(old_nom);
            classe.setText("Vous etes dans la classe n° "+old_classe);
            phone_number.setText(old_pNumber);
            date.setText("Compte créer le "+Odate);

            change_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String new_nom = nom.getText().toString();
                    String new_password="";
                    if(!password.getText().toString().matches("")) {
                         new_password = Hashing.sha1().hashString(password.getText().toString(), Charsets.UTF_8).toString();
                    }
                    else {
                        try {
                            new_password = json.getString("password");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    String new_pNumber = phone_number.getText().toString();

                    if(new_nom.matches(old_nom) && new_password.matches(old_password) && new_pNumber.matches(old_pNumber))
                    {
                        Toast.makeText(UserActivity.this,"Aucune données changée",Toast.LENGTH_SHORT).show();
                    }else
                    {
                        if(!new_nom.matches(old_nom))
                        {
                            String[] arr = {"membre","name",new_nom,"email",email};
                            ArrayList<String> par = new ArrayList<>(Arrays.asList(arr));
                            updateData(email,old_password,par);
                            Log.d("Nom",old_nom+" "+new_nom);
                        }

                        if(!new_password.matches(old_password))
                        {
                            String[] arr = {"membre","password",new_password,"email",email};
                            ArrayList<String> par = new ArrayList<>(Arrays.asList(arr));
                            Log.d("Password",old_password+" "+new_password);
                            updateData(email,old_password,par);
                        }

                        if(!new_pNumber.matches(old_pNumber))
                        {
                            String[] arr = {"membre","phoneNumber",new_pNumber,"email",email};
                            ArrayList<String> par = new ArrayList<>(Arrays.asList(arr));
                            Log.d("Nom",old_pNumber+" "+new_pNumber);
                            updateData(email,new_password,par);
                        }

                        Toast.makeText(UserActivity.this,"Donnée actualiser",Toast.LENGTH_SHORT).show();
                        Log.d("Change","Chnagement");
                    }
                }
            });




        }catch (JSONException jsio)
        {
            //Mettre une erreur en log plus tard
        }


    }


    public void updateData(String user, String password, ArrayList<String> par)
    {
        ApiTools api = new ApiTools();
        String url = api.urlCreator(user,password,api.UPDATE_DATA,par,api.HASHING_FALSE);
        try {
            json.put(par.get(1),par.get(2));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        httpRequest.getJsonResponse(url);
    }
}
