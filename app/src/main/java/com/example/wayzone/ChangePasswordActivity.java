package com.example.wayzone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;

public class ChangePasswordActivity extends AppCompatActivity {

    private TextView old_pass;
    private TextView new_pass;
    private Button change_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ApiTools api = new ApiTools();

                old_pass = findViewById(R.id.edit_old_password);
                new_pass = findViewById(R.id.edit_new_password);

                if(!old_pass.getText().toString().isEmpty() && !new_pass.getText().toString().isEmpty())
                {
                    String Opass = Hashing.sha1().hashString(old_pass.getText().toString(), Charsets.UTF_8).toString();
                    String Npass = Hashing.sha1().hashString(new_pass.getText().toString(), Charsets.UTF_8).toString();

                    try {
                        if(api.USER_INFO.getString("password") == Opass)
                        {
                            String[] arr = {"membre","password",Npass,"email",api.USER_INFO.getString("email")};
                            ArrayList<String> par = new ArrayList<>(Arrays.asList(arr));
                            String url = api.urlCreator(api.USER_INFO.getString("email"),Opass,api.UPDATE_DATA,par,false);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }
        });


    }
}
