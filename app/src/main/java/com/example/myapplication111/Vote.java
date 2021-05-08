package com.example.myapplication111;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Vote extends AppCompatActivity {


    // LinearLayout container
    private LinearLayout ContentView;
    private Surveys surveys;
    private ArrayList<Questions> questions_list = new ArrayList<>();
    private Surveys survey = null;
    private ArrayList<Integer> choiceNum = new ArrayList<Integer>();
    private ArrayList<ImageButton> listbtnEdit;
    private ArrayList<CheckBox> checkBoxes_list = new ArrayList<>();
    private ArrayList<ArrayList<CheckBox>> Question_checkbox = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting_page);

        this.survey = (Surveys) getIntent().getSerializableExtra("survey");   //load survey

        int btnIDIndex = 0;
        listbtnEdit = new ArrayList<ImageButton>();

        ContentView = (LinearLayout) this.findViewById(R.id.content_view);
        //for (int i = 0; i < survey.questions.size(); i++) {
        for (int i = 0; i < 3; i++) {
            btnIDIndex = i;
            choiceNum.add(0);
            LinearLayout layout = new LinearLayout(Vote.this);
            LinearLayout.LayoutParams LayoutlayoutParams_next = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            // set margin
            LayoutlayoutParams_next.setMargins(0, 50, 0, 0);
            layout.setLayoutParams(LayoutlayoutParams_next);
            // set background color
            layout.setBackgroundColor(Color.argb(255, 28, 134, 238));
            layout.setPadding(20, 20, 20, 20);
            layout.setOrientation(LinearLayout.VERTICAL);

            // Create text view
            TextView questionContent = new TextView(Vote.this);
            LinearLayout.LayoutParams questionParam = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            questionContent.setLayoutParams(questionParam);
            questionContent.setBackgroundColor(Color.argb(255, 255, 255, 255));
            questionContent.setGravity(Gravity.LEFT);
            questionContent.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            questionContent.setPadding(20, 0, 0, 0);
            questionContent.setTextSize(16);
            //questionContent.setText(survey.questions.get(i).questionContent);
            if (choiceNum.get(i) == 1) {
                questionContent.setText("question no." + i + "(This question is a Single Choice Question.");
                layout.addView(questionContent);
            } else {
                questionContent.setText("question no." + i + "(This question is a Multiple Choice Question.");
                layout.addView(questionContent);
            }


            //for (int j = 1; j <= survey.questions.get(i).optionNum(); j++) {
            for (int j = 1; j <= 2; j++) {
                RelativeLayout rlOption = new RelativeLayout(Vote.this);
                RelativeLayout.LayoutParams rlParam = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                rlParam.setMargins(0, 20, 0, 0);
                rlOption.setPadding(20, 20, 20, 20);
                rlOption.setLayoutParams(rlParam);

                //listbtnAdd.add(iIndex, btnAdd);
                TextView optionContent = new TextView(Vote.this);
                RelativeLayout.LayoutParams optionParam = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                optionContent.setLayoutParams(optionParam);
                optionContent.setBackgroundColor(Color.argb(255, 255, 255, 255));
                optionContent.setGravity(Gravity.LEFT);
                optionContent.setPadding(20, 0, 0, 0);
                optionContent.setTextSize(16);
                //optionContent.setText(survey.questions.get(i).getOptionContent(j));
                optionContent.setText("option no." + Integer.toString(j));
                optionContent.setId(j);
                rlOption.addView(optionContent);

                CheckBox checkbox = new CheckBox(Vote.this);
                RelativeLayout.LayoutParams checkboxParam = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                checkboxParam.addRule(RelativeLayout.BELOW, j);
                checkboxParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                checkbox.setLayoutParams(checkboxParam);
                checkbox.setGravity(Gravity.RIGHT);
                checkbox.setId(i << 16);
                checkBoxes_list.add(checkbox);
                if (j == 2) {
                    ImageButton btnEdit = new ImageButton(Vote.this);
                    RelativeLayout.LayoutParams btnAddParam = new RelativeLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    btnAddParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    btnEdit.setLayoutParams(btnAddParam);
                    checkbox.setGravity(Gravity.RIGHT);
                    btnEdit.setId(btnIDIndex);
                    btnEdit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Vote.this, Edit_questions.class);
                            startActivity(intent);
                            //transfer data this questions data to edit_question page
                        }
                    });
                    // put“edit”button into RelativeLayout
                    rlOption.addView(btnEdit);
                }
                //add OnCheckedChange function
                // Single choice question
                checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if (isChecked) {
                            //if (choiceNum.get((compoundButton.getId()) >> 16) >= survey.questions.get((compoundButton.getId()) >> 16).allowedChoiceNum) {
                            if (choiceNum.get((compoundButton.getId()) >> 16) >= 1) {
                                new AlertDialog.Builder(Vote.this)
                                        .setTitle("Error!")
                                        .setMessage("Maximum choice number has been reached")
                                        .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //do nothing - it will close on its own
                                            }
                                        })
                                        .show();
                                compoundButton.setChecked(false);
                            } else {
                                choiceNum.set((compoundButton.getId()) >> 16, choiceNum.get((compoundButton.getId()) >> 16) + 1);
                            }
                        } else {
                            choiceNum.set((compoundButton.getId()) >> 16, choiceNum.get((compoundButton.getId()) >> 16) - 1);
                        }
                    }
                });

                rlOption.addView(checkbox);
                layout.addView(rlOption);
            }

            Question_checkbox.add(checkBoxes_list);
            checkBoxes_list.clear();
            ContentView.addView(layout, i);
        }
    }
}
