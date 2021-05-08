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

public class Survey_engage extends AppCompatActivity {

    // LinearLayout container
    private LinearLayout ContentView;
    private Surveys survey = null;
    private ArrayList<Integer> choiceNum = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.survey = (Surveys)getIntent().getSerializableExtra("survey");
        setContentView(R.layout.activity_survey_engage);

        ContentView = (LinearLayout) this.findViewById(R.id.content_view);

        ImageButton sendBtn = this.findViewById(R.id.imageButton);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 1; i <= choiceNum.size(); i++) {
                    if (choiceNum.get(i - 1) != 0) {
                        survey.questions.get(i).participantNum++;
                    }
                }

                Intent intent = getIntent();
                intent.putExtra("survey", survey);
                setResult(0, intent);
                finish();
            }
        });

        for (int i = 1; i <= survey.questions.size(); i++) {
        //for (int i = 1; i <= 3; i++) {
            choiceNum.add(0);
            LinearLayout layout = new LinearLayout(Survey_engage.this);
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
            TextView questionContent = new TextView(Survey_engage.this);
            LinearLayout.LayoutParams questionParam = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            questionContent.setLayoutParams(questionParam);
            questionContent.setBackgroundColor(Color.argb(255, 255, 255, 255));
            questionContent.setGravity(Gravity.LEFT);
            questionContent.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            questionContent.setPadding(20, 0, 0, 0);
            questionContent.setTextSize(16);
            questionContent.setText(survey.questions.get(i).questionContent);
            //questionContent.setText("question no." + Integer.toString(i));
            layout.addView(questionContent);

            for (int j = 1; j <= survey.questions.get(i).optionNum(); j++) {
            //for (int j = 1; j <= 2; j++) {
                RelativeLayout rlOption = new RelativeLayout(Survey_engage.this);
                RelativeLayout.LayoutParams rlParam = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                rlParam.setMargins(0, 20, 0,0);
                rlOption.setPadding(20, 20, 20, 20);
                rlOption.setLayoutParams(rlParam);

                TextView optionContent = new TextView(Survey_engage.this);
                RelativeLayout.LayoutParams optionParam = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                optionContent.setLayoutParams(optionParam);
                optionContent.setBackgroundColor(Color.argb(255, 255, 255, 255));
                optionContent.setGravity(Gravity.LEFT);
                optionContent.setPadding(20, 0, 0, 0);
                optionContent.setTextSize(16);
                optionContent.setText(survey.questions.get(i).getOptionContent(j));
                //optionContent.setText("option no." + Integer.toString(j));
                optionContent.setId(j);
                rlOption.addView(optionContent);

                CheckBox checkbox = new CheckBox(Survey_engage.this);
                RelativeLayout.LayoutParams checkboxParam = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                checkboxParam.addRule(RelativeLayout.BELOW, j);
                checkboxParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                checkbox.setLayoutParams(checkboxParam);
                checkbox.setGravity(Gravity.RIGHT);
                checkbox.setId((i << 16) + (j << 8));

                //add OnCheckedChange function
                checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if (isChecked) {
                            if (choiceNum.get(((compoundButton.getId()) >> 16) - 1) >= survey.questions.get((compoundButton.getId()) >> 16).allowedChoiceNum) {
                            //if (choiceNum.get(((compoundButton.getId()) >> 16) - 1) >= 1) {
                                new AlertDialog.Builder(Survey_engage.this)
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
                            }
                            else {
                                choiceNum.set(((compoundButton.getId()) >> 16) - 1, choiceNum.get(((compoundButton.getId()) >> 16) - 1) + 1);
                                survey.questions.get((compoundButton.getId()) >> 16).makeNewChoice(((compoundButton.getId()) >> 8) & 0xff);
                            }
                        }
                        else {
                            choiceNum.set(((compoundButton.getId()) >> 16) - 1, choiceNum.get(((compoundButton.getId()) >> 16) - 1) - 1);
                            survey.questions.get((compoundButton.getId()) >> 16).cancelChoice(((compoundButton.getId()) >> 8) & 0xff);
                        }
                    }
                });

                rlOption.addView(checkbox);

                layout.addView(rlOption);
            }

            ContentView.addView(layout, i - 1);
        }
    }
}