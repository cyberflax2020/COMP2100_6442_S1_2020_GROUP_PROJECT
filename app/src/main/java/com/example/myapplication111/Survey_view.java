package com.example.myapplication111;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Survey_view extends AppCompatActivity {

    //TODO 编辑按钮位置

    // LinearLayout container
    private LinearLayout ContentView;

    private Users user=null;
    private Surveys survey = null;
    // new question somewhere and add into this survey
    // finally we pass the survey data to the sending page

    private ArrayList<Integer> choiceNum = new ArrayList<Integer>();
    private ArrayList<ImageButton> listbtnEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_view_page);



        this.survey = (Surveys) getIntent().getSerializableExtra("survey");   //load the survey
        this.user=(Users)getIntent().getSerializableExtra("user");   //load user



        if (this.survey.questions.size()==0){
            Intent intent = new Intent(Survey_view.this, Creating_title.class);
            startActivity(intent);
            finish();
        }

        int btnIDIndex = 0;
        listbtnEdit = new ArrayList<ImageButton>();

        ContentView = (LinearLayout) this.findViewById(R.id.content_view);
        for (int i = 0; i < survey.questions.size(); i++) {

            btnIDIndex = i;
            choiceNum.add(0);
            LinearLayout layout = new LinearLayout(Survey_view.this);
            LinearLayout.LayoutParams LayoutlayoutParams_next = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            // set margin
            LayoutlayoutParams_next.setMargins(0, 50, 0, 0);
            layout.setLayoutParams(LayoutlayoutParams_next);
            // set background color
            layout.setBackgroundColor(Color.argb(255, 28, 134, 238));
            layout.setPadding(20, 20,20, 20);
            layout.setOrientation(LinearLayout.VERTICAL);

            // Create text view
            TextView questionContent = new TextView(Survey_view.this);
            LinearLayout.LayoutParams questionParam = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            questionContent.setLayoutParams(questionParam);
            questionContent.setBackgroundColor(Color.argb(255, 255, 255, 255));
            questionContent.setGravity(Gravity.LEFT);
            questionContent.setSingleLine(false);
            questionContent.setPadding(20, 0, 20, 0);
            questionContent.setHint("Edit your new question by clicking the button below");
            questionContent.setTextSize(14);
            System.out.println(i);
            if(this.survey.questions.get(i+1).allowedChoiceNum == 1) {
                questionContent.setText("Single Choice question: "+survey.questions.get(i + 1).questionContent);
            }
            else if(this.survey.questions.get(i+1).allowedChoiceNum > 1){
                questionContent.setText("You may choose "+survey.questions.get(i+1).allowedChoiceNum+
                        " options for this question: " + survey.questions.get(i + 1).questionContent);
            }

            layout.addView(questionContent);

            ImageButton btnEdit = new ImageButton(Survey_view.this);

            RelativeLayout.LayoutParams btnAddParam = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            btnAddParam.addRule(RelativeLayout.RIGHT_OF,btnIDIndex);
            btnEdit.setLayoutParams(btnAddParam);
            btnEdit.setId(btnIDIndex);
            btnEdit.setBackgroundResource(R.mipmap.edit);
            final int finalI1 = i;


            btnEdit.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    int questionId= finalI1 +1;

                    Intent intent = new Intent(Survey_view.this, Question_type.class);
                    intent.putExtra("survey",survey);
                    intent.putExtra("questionId",questionId);
                    intent.putExtra("user",user);
                    startActivity(intent);

                }
            });
            // put“edit”button into RelativeLayout
            layout.addView(btnEdit);
            for (int j = 1; j <= survey.questions.get(i+1).optionNum(); j++) {

                RelativeLayout rlOption = new RelativeLayout(Survey_view.this);
                RelativeLayout.LayoutParams rlParam = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                rlParam.setMargins(0, 20, 0,0);
                rlOption.setPadding(20, 20, 20, 20);
                rlOption.setLayoutParams(rlParam);

                //listbtnAdd.add(iIndex, btnAdd);
                TextView optionContent = new TextView(Survey_view.this);
                RelativeLayout.LayoutParams optionParam = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                optionContent.setLayoutParams(optionParam);
                optionContent.setBackgroundColor(Color.argb(255, 255, 255, 255));
                optionContent.setGravity(Gravity.LEFT);
                optionContent.setPadding(20, 0, 0, 0);
                optionContent.setTextSize(14);
                optionContent.setHint("Edit your question's option here.");
                optionContent.setText(this.survey.questions.get(i+1).getOptionContent(j));

                optionContent.setId(j);
                rlOption.addView(optionContent);



                layout.addView(rlOption);
            }


            ContentView.addView(layout, i);
        }
    }

    public void toSend(View v){
        Intent intent = new Intent(Survey_view.this, SendPage.class);
        intent.putExtra("user",user);
        intent.putExtra("survey",survey);
        startActivity(intent);
    }
    public void Back_SvP(View v){
        Intent intent = new Intent(Survey_view.this, Home_page.class);
        intent.putExtra("user",user);
        startActivity(intent);
        finish();

    }
    public void Add_new_question(View v) {
        Intent intent = new Intent(Survey_view.this, Question_type.class);
        int questionId=this.survey.questions.size()+1;
        intent.putExtra("survey",survey);
        intent.putExtra("questionId",questionId);
        intent.putExtra("user",user);
        startActivity(intent);
        finish();
    }

}
