package com.example.wayzone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class NewsActivity extends AppCompatActivity {

    private TextView edit_news;
    private TextView edit_title;
    private Button publish;
    JSONObject json = ApiTools.USER_INFO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);



        edit_news = findViewById(R.id.edit_news);
        edit_title = findViewById(R.id.edit_title_news);
        publish = findViewById(R.id.btn_publish_news);

        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String news =edit_news.getText().toString();
                String title = edit_title.getText().toString();

                if(!news.matches("") && !title.matches(""))
                {
                    ApiTools api = new ApiTools();
                    HttpRequest http = new HttpRequest();
                    news.replaceAll("\\n","<br\\>");

                    Date dte =new java.sql.Date(Calendar.getInstance().getTimeInMillis());
                    String[] str = {"news","",title,news,"1",dte.toString()};
                    ArrayList<String> arr = new ArrayList<>(Arrays.asList(str));

                    try {
                        String url = api.urlCreator(json.getString("email"),json.getString("password") ,1,arr,api.HASHING_FALSE);
                        http.getJsonResponse(url);
                        Log.d("*****************",url);
                        edit_news.setText("");
                        edit_title.setText("");
                        Toast.makeText(NewsActivity.this,"Nouvelle news crée",Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else
                {
                    Toast.makeText(NewsActivity.this,"Le titre et la news ne doivent pas etre vide",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
