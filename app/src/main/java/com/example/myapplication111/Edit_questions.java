package com.example.myapplication111;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.LinkedList;


public class Edit_questions extends AppCompatActivity {

    // LinearLayout container
    private LinearLayout ContentView;
    private EditText etContent1;
    private ArrayList<ImageButton> listbtnAdd;

    private ArrayList<Surveys> surveys;
    private ArrayList<Questions.Option> options;
    private ArrayList<EditText> et = new ArrayList<>();
    private int btnIDIndex = 1000;
    // “-” List
    private LinkedList<ImageButton> listbtnDlt;

    private int iETContentHeight = 0;
    private float num_edit_text = 1.0f;


    ArrayList<Integer> ids=new ArrayList<>();
    private int questionId=0;
    Users user=null;
    Surveys survey=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_questions);
        ContentView = (LinearLayout) this.findViewById(R.id.content_view);
        etContent1 = (EditText) this.findViewById(R.id.et_content1);
        listbtnAdd = new ArrayList<ImageButton>();
        listbtnDlt = new LinkedList<ImageButton>();
        // first “+” button

        this.survey = (Surveys) getIntent().getSerializableExtra("survey");   //load survey
        this.questionId=getIntent().getIntExtra("questionId",0);
        this.user=(Users)getIntent().getSerializableExtra("user");
        etContent1.setHint("Type in your question here, and add new question by clicking the 'plus' button below.");
        ImageButton btnAdd1 = (ImageButton) this.findViewById(R.id.ibn_add1);
        btnAdd1.setBackgroundResource(R.mipmap.add);
        btnAdd1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                iETContentHeight = etContent1.getHeight();
                num_edit_text = iETContentHeight / 70 * (listbtnDlt.size() + 1);
                addContent(v);

            }
        });

        listbtnAdd.add(btnAdd1);
        listbtnDlt.add(null);
//        System.out.println("zzzzzzzzzzzzzzzzzzzzzzzz"+survey.questions.get(1).questionContent);
//        System.out.println(questionId);
//        System.out.println("?????????????????????????????"+survey.questions.get(questionId).questionContent);
//        if(survey.questions.get(questionId).questionContent!=""){
//            etContent1.setText(survey.questions.get(questionId).questionContent);
//        }
//        if(survey.questions.get(questionId).optionIdMap.size()>=1){
//            System.out.println("survey.questions.get(questionId).optionIdMap.size()"+ survey.questions.get(questionId).optionIdMap.size());
//            System.out.println("survey.questions.get(questionId).questionContent"+survey.questions.get(questionId).questionContent);
//            System.out.println("survey.questions.get(questionId).optionIdMap.size()"+survey.questions.get(questionId).optionIdMap.size());}
//            etContent1.setText(survey.questions.get(questionId).questionContent);
//            for(int i = 0;i<survey.questions.get(questionId).optionIdMap.size();i++){
//                System.out.println(i+"||||||||||||||||||||||||question_content");
//                addContent_havecontent(ContentView,i + 1);
//
//            }
        }



    private void addContent(View v) {
        if (v == null) {
            return;
        }

        int iIndex = -1;
        for (int i = 0; i < listbtnAdd.size(); i++) {
            if (listbtnAdd.get(i) == v) {
                iIndex = i;
                break;
            }
        }

        if (iIndex >= 0) {
            // Add edit text at next one's position
            iIndex += 1;

            LinearLayout layout = new LinearLayout(Edit_questions.this);
            LinearLayout.LayoutParams LayoutlayoutParams_next = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            // set margin
            LayoutlayoutParams_next.setMargins(0, (int) (num_edit_text * 5), 0, 0);
            layout.setLayoutParams(LayoutlayoutParams_next);
            // set background color
            layout.setBackgroundColor(Color.argb(255, 28, 134, 238));
            layout.setPadding((int) (num_edit_text * 5), (int) (num_edit_text * 5),
                    (int) (num_edit_text * 5), (int) (num_edit_text * 5));
            layout.setOrientation(LinearLayout.VERTICAL);

            // Create edit text
            EditText etContent = new EditText(Edit_questions.this);
            LinearLayout.LayoutParams etParam = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, iETContentHeight);
            etContent.setLayoutParams(etParam);
            etContent.setBackgroundColor(Color.argb(255, 30, 144, 255));
            etContent.setGravity(Gravity.LEFT);
            etContent.setSingleLine(false);
            etContent.setPadding((int) (num_edit_text * 5), 0, 0, 0);
            etContent.setTextSize(16);
            etContent.setHint("Type in your options here, and you can delete options " +
                    "by clicking the garbage button below.");
            layout.addView(etContent);

            et.add(etContent);
            RelativeLayout rlBtn = new RelativeLayout(Edit_questions.this);
            RelativeLayout.LayoutParams rlParam = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            rlBtn.setPadding(0, (int) (num_edit_text * 5), 0, 0);
            rlBtn.setLayoutParams(rlParam);


            // create "-" button
            ImageButton btnDelete = new ImageButton(Edit_questions.this);
            btnDelete.setBackgroundResource(R.mipmap.delete);
            RelativeLayout.LayoutParams btnDeleteAddParam = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            btnDeleteAddParam.setMargins(0, 0, (int) (num_edit_text * 5), 0);
            btnDeleteAddParam.addRule(RelativeLayout.RIGHT_OF, btnIDIndex);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteContent(v);
                }
            });
            rlBtn.addView(btnDelete, btnDeleteAddParam);
            listbtnDlt.add(iIndex, btnDelete);


            layout.addView(rlBtn);
            ContentView.addView(layout, iIndex);

            btnIDIndex++;
        }
    }
    // If the question and its option already have content.
    private void addContent_havecontent(View v,Integer option_index) {
        if (v == null) {
            return;
        }

        LinearLayout layout = new LinearLayout(Edit_questions.this);
        LinearLayout.LayoutParams LayoutlayoutParams_next = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        // set margin
        LayoutlayoutParams_next.setMargins(0, (int) (num_edit_text * 5), 0, 0);
        layout.setLayoutParams(LayoutlayoutParams_next);
        // set background color
        layout.setBackgroundColor(Color.argb(255, 28, 134, 238));
        layout.setPadding((int) (num_edit_text * 5), (int) (num_edit_text * 5),
                (int) (num_edit_text * 5), (int) (num_edit_text * 5));
        layout.setOrientation(LinearLayout.VERTICAL);
        System.out.println("aaaaaaaaaaaaaaaaaaa"+survey.questions.get(questionId).optionIdMap.get(option_index));



        // Create edit text
        EditText etContent = new EditText(Edit_questions.this);
        System.out.println("Hash!!!!!!!!!!!!!"+etContent.hashCode());
        LinearLayout.LayoutParams etParam = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, iETContentHeight);
        etContent.setLayoutParams(etParam);
        etContent.setBackgroundColor(Color.argb(255, 30, 144, 255));
        etContent.setGravity(Gravity.LEFT);
        etContent.setSingleLine(false);
        etContent.setPadding((int) (num_edit_text * 5), 0, 0, 0);
        etContent.setTextSize(16);
        if(survey.questions.get(questionId).optionIdMap.get(option_index)==""){
            etContent.setHint("Type in your options here, and you can delete options " +
                "by clicking the garbage button below.");}
        else if(survey.questions.get(questionId).optionIdMap.get(option_index)!=""){
            etContent.setText(survey.questions.get(questionId).optionIdMap.get((option_index)));
        }
        layout.addView(etContent);
        System.out.println( "Layout!!!!!!!!!!!!!"+layout.getChildCount());;
        System.out.println("etcontent"+ etContent.getText().toString());
        et.add(etContent);
        RelativeLayout rlBtn = new RelativeLayout(Edit_questions.this);
        RelativeLayout.LayoutParams rlParam = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rlBtn.setPadding(0, (int) (num_edit_text * 5), 0, 0);
        rlBtn.setLayoutParams(rlParam);


        // create "-" button
        ImageButton btnDelete = new ImageButton(Edit_questions.this);
        btnDelete.setBackgroundResource(R.mipmap.delete);
        RelativeLayout.LayoutParams btnDeleteAddParam = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        btnDeleteAddParam.setMargins(0, 0, (int) (num_edit_text * 5), 0);
        btnDeleteAddParam.addRule(RelativeLayout.RIGHT_OF, btnIDIndex);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteContent(v);
            }
        });
        rlBtn.addView(btnDelete, btnDeleteAddParam);
        listbtnDlt.add(option_index, btnDelete);


        layout.addView(rlBtn);
        ContentView.addView(layout, option_index);




    }
    public ArrayList<String> get_info(EditText ed){
        ArrayList<String> question_options = new ArrayList<>();
        String input_content = ed.getText().toString();
        question_options.add(input_content);
        return question_options;

    }

    private void deleteContent(View v) {
        if (v == null) {
            return;
        }

        int index = -1;
        // Judge which "-" button trigger the activity
        for (int i = 0; i <listbtnDlt.size();i++) {
            if (listbtnDlt.get(i) == v) {
                index = i-1;
                break;
            }
        }
        if (this.questionId==1){
        if (index >= 0) {
            listbtnDlt.remove(index + 1);
            ContentView.removeViewAt(index + 1);
            System.out.println(et.size());
            et.remove(index);
        }
    }
    }

    public void saveQuestion (View v){


        for(int i =0;i<et.size();i++){

            String optionContent=et.get(i).getText().toString();

            if (i == 0) {
                this.survey.questions.get(this.questionId).setOptionContent(1, optionContent);
            }
            else {
                this.survey.questions.get(this.questionId).addOption(new Questions.Option(0, optionContent));
            }

        }

        this.survey.questions.get(questionId).questionContent=etContent1.getText().toString();
        Intent intent = new Intent(Edit_questions.this, Survey_view.class);
        intent.putExtra("survey",survey);
        intent.putExtra("user",this.user);
        startActivity(intent);
        finish();
    }


    public void Onclick_Cancel(View V){
        Intent intent = new Intent(Edit_questions.this, Question_type.class);
        intent.putExtra("survey",survey);
        intent.putExtra("questionId",questionId);
        intent.putExtra("user",user);
        startActivity(intent);
        finish();

    }


}