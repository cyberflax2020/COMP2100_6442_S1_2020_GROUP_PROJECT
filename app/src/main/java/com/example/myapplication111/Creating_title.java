package com.example.myapplication111;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Creating_title extends AppCompatActivity {
    Users user=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_title);
        this.user = (Users)getIntent().getSerializableExtra("user");



    }

    public void saveTitle(View v){

        TextView TitleView = findViewById(R.id.Title);
        String surveyTitle=TitleView.getText().toString();

        Surveys survey=new Surveys(surveyTitle);                 //create a new survey and set survey title
        survey.publisherId=user.username;

        Questions question=new Questions("A",0);
        question.questionId=1;

        survey.addQuestion(question);

        question.addOption(new Questions.Option(0,""));

        Intent intent = new Intent(Creating_title.this, Survey_view.class);
        intent.putExtra("survey", survey);
        intent.putExtra("user",user);
        startActivity(intent);
    }
}
