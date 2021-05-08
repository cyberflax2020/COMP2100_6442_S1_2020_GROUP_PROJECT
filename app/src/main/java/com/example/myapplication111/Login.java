package com.example.myapplication111;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.ExecutionException;

public class Login extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void LoginButtonClicked(View view) throws ExecutionException, InterruptedException {
        TextView username_text = findViewById(R.id.editText);
        TextView pwd_text = findViewById(R.id.editText2);
        String username = username_text.getText().toString();
        String pwd = pwd_text.getText().toString();

        if (username.equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle("Error!")
                    .setMessage("Please input username")
                    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //do nothing - it will close on its own
                        }
                    })
                    .show();
            return;
        }

        if (pwd.equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle("Error!")
                    .setMessage("Please input password")
                    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //do nothing - it will close on its own
                        }
                    })
                    .show();
            return;
        }

        HttpPost LoginPost = new HttpPost();
        LoginPost.execute("Login", username, pwd);
        String result = LoginPost.get();
        if (result.equals("Wrong username or password") == true) {
            new AlertDialog.Builder(this)
                    .setTitle("Error!")
                    .setMessage(result)
                    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //do nothing - it will close on its own
                        }
                    })
                    .show();
        }
        else if (result.equals("login succeeded") == true) {


            HttpPost GetPost = new HttpPost();
            GetPost.execute("Get User Class", username);
            String userclass = GetPost.get();
            Users user = null;
            if (userclass.equals("No user class found") == true) {
                new AlertDialog.Builder(this)
                        .setTitle("Error!")
                        .setMessage("Server fault: no user class found")
                        .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //do nothing - it will close on its own
                            }
                        })
                        .show();
            }
            else if (userclass.equals("") == false) {
                Gson gson = new GsonBuilder().create();
                user = gson.fromJson(userclass, Users.class);
                Intent intent = new Intent(Login.this, Home_page.class);
                intent.putExtra("user", user);
                startActivity(intent);
                finish();
            }
            else {
                new AlertDialog.Builder(this)
                        .setTitle("Error!")
                        .setMessage("Server fault: no response")
                        .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //do nothing - it will close on its own
                            }
                        })
                        .show();
            }
        }
        else {
            new AlertDialog.Builder(this)
                    .setTitle("Error!")
                    .setMessage(result)
                    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //do nothing - it will close on its own
                        }
                    })
                    .show();
        }
    }

    public void RegisterButtonClicked(View view){
        Intent intent = new Intent(Login.this, Register.class);
        startActivity(intent);
    }

    public void SearchButtonClicked(View view){
        Toast.makeText(this, "Start searching", Toast.LENGTH_SHORT).show();
        AutoCompleteTextView search_text_view = findViewById(R.id.idActvSearch);
        String sid = search_text_view.getText().toString();

        if (sid.equals("") == true) {
            new AlertDialog.Builder(this)
                    .setTitle("Error!")
                    .setMessage("Please enter a survey ID")
                    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //do nothing - it will close on its own
                        }
                    })
                    .show();
                    return;
        }

        HttpPost SearchPost = new HttpPost();
        SearchPost.execute("Search Survey", sid);
        String search_result = "";
        try {
            search_result = SearchPost.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (search_result.equals("no such survey") == true) {
            new AlertDialog.Builder(this)
                    .setTitle("Error!")
                    .setMessage("Survey not found")
                    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //do nothing - it will close on its own
                        }
                    })
                    .show();
        }
        else {
            Users survey_publisher = new Users("default");
            Gson gson = new GsonBuilder().create();
            survey_publisher = gson.fromJson(search_result, Users.class);
            Surveys survey_found = null;
            int survey_index = 0;
            for (int i = 0; i < survey_publisher.publishedSurvey.size(); i++) {
                survey_found = survey_publisher.publishedSurvey.get(i);
                if ((survey_found.SurveyID == Integer.parseInt(sid))) {
                    survey_index = i;
                    Intent intent = new Intent(Login.this, Search_result.class);
                    //intent.putExtra("survey", survey_found);
                    intent.putExtra("publisher", survey_publisher.username);
                    intent.putExtra("index", survey_index);
                    intent.putExtra("engager", "guest:guest");
                    startActivity(intent);
                    finish();
                    break;
                }
            }
        }
    }
}
