package com.example.myapplication111;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class Logout extends AppCompatActivity {

    Users user=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.user = (Users)getIntent().getSerializableExtra("user");
        setContentView(R.layout.activity_logout);
        TextView username = findViewById(R.id.Userid);
        String s1;
        String s2;
        if (user.publishedSurvey.size()<2){s1="";}else {s1="s";}
        if (user.engagedSurvey.size()<2){s2="";}else {s2="s";}
        String User_info="Hi "+user.username+", you have published "+user.publishedSurvey.size()+" survey"+s1+" and engaged in "+user.engagedSurvey.size()+" survey"+s2+"!";
        username.setText(User_info);

    }

    public void logout(View v){
        Intent intent = new Intent(Logout.this, Login.class);
        startActivity(intent);
        finish();
    }

    public void home(View v){
        Intent intent = new Intent(Logout.this, Home_page.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
}
