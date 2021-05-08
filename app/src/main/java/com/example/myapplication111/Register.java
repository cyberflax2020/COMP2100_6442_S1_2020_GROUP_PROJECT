package com.example.myapplication111;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.ExecutionException;

public class Register extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void RegisterButtonClicked(View view) throws ExecutionException, InterruptedException {
        TextView username_text = findViewById(R.id.editText3);
        TextView pwd_text = findViewById(R.id.editText4);
        TextView confirm_text = findViewById(R.id.editText5);
        String username = username_text.getText().toString();
        String pwd = pwd_text.getText().toString();
        String confirm = confirm_text.getText().toString();

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

        if (confirm.equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle("Error!")
                    .setMessage("Please input password again")
                    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //do nothing - it will close on its own
                        }
            })
            .show();
            return;
        }

        if (pwd.equals(confirm) == true) {
            HttpPost RegPost = new HttpPost();
            RegPost.execute("Register", username, pwd);
            String result = RegPost.get();
            if (result.equals("The username has already existed") == true) {
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
            else if (result.equals("registration failed") == true) {
                new AlertDialog.Builder(this)
                    .setTitle("Error!")
                    .setMessage(result + " because of server fault")
                    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //do nothing - it will close on its own
                        }
                })
                .show();
            }
            else if (result.equals("registration succeeded") == true) {
                Users new_user = new Users(username);
                Gson gson = new GsonBuilder().create();
                String toSave = gson.toJson(new_user);
                new AlertDialog.Builder(this)
                    .setTitle("Success!")
                    .setMessage(result)
                    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {
                            //do nothing - it will close on its own
                         }
                })
                .show();
                HttpPost SavePost = new HttpPost();
                SavePost.execute("Save User Class", username, toSave);
                String saveResult = SavePost.get();
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
                finish();
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
        else {
            new AlertDialog.Builder(this)
                .setTitle("Error!")
                .setMessage("Two passwords are inconsistent.")
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do nothing - it will close on its own
                    }
            })
            .show();
        }
    }

    public void backToMain (View v){
        Intent intent = new Intent(Register.this, Login.class);
        startActivity(intent);
        finish();

    }}
