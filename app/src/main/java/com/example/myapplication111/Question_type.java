package com.example.myapplication111;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Question_type extends AppCompatActivity {

    Surveys survey=null;
    int questionId;
    Users user=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_type);

        this.questionId=getIntent().getIntExtra("questionId",0);
        this.survey = (Surveys) getIntent().getSerializableExtra("survey");//get survey from pre page.
        this.user=(Users)getIntent().getSerializableExtra("user");



        if(questionId>1){
            if(survey.questions.get(questionId) == null){
                Questions question=new Questions("",0);

                question.addOption(new Questions.Option(0,""));

                question.questionId=questionId;
                this.survey.addQuestion(question);}

        }

    }

    public void pressToEdit(View V){
        TextView textView=findViewById(R.id.allowednum);
        int num=Integer.parseInt(textView.getText().toString());
        if(num != 0){
            this.survey.questions.get(questionId).allowedChoiceNum=num;     //set allow choice num

            Intent intent = new Intent(Question_type.this, Edit_questions.class);
            intent.putExtra("survey", this.survey);
            intent.putExtra("user",this.user);
            intent.putExtra("questionId",questionId);

            startActivity(intent);
        }


    }


    public void deleteQuestion(View v){


        this.survey.questions.remove(this.questionId);
        Intent intent = new Intent(Question_type.this, Survey_view.class);
        intent.putExtra("survey", this.survey);
        intent.putExtra("user",this.user);
        startActivity(intent);
        finish();

    }


}
