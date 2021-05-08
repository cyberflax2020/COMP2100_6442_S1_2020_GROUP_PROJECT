package com.example.myapplication111;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Surveys_published extends AppCompatActivity {
    // LinearLayout container
    private LinearLayout ContentView;
    Users user=null;
    String username;
    private Surveys survey = null;
    private ArrayList<Integer> choiceNum = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.username = getIntent().getStringExtra("user");

        HttpPost GetPost = new HttpPost();
        GetPost.execute("Get User Class", username);
        String userclass = "";
        try {
            userclass = GetPost.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Gson gson = new GsonBuilder().create();
        this.user = gson.fromJson(userclass, Users.class);

        setContentView(R.layout.activity_surveys_list);
        ContentView = (LinearLayout) this.findViewById(R.id.Surveys_title);
        String entry = getIntent().getStringExtra("entry");

        if (entry.equals("publish") == true) {
            for (int i = 0; i < user.publishedSurvey.size(); i++) {
                choiceNum.add(0);
                LinearLayout layout = new LinearLayout(Surveys_published.this);
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
                TextView Surveylist = new TextView(Surveys_published.this);
                LinearLayout.LayoutParams questionParam = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                Surveylist.setLayoutParams(questionParam);
                Surveylist.setBackgroundColor(Color.argb(255, 255, 255, 255));
                Surveylist.setGravity(Gravity.LEFT);
                Surveylist.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                Surveylist.setTextSize(16);
                Surveylist.setText("ID: " + Integer.toString(user.publishedSurvey.get(i).SurveyID) + "  " + user.publishedSurvey.get(i).surveyName);

                layout.addView(Surveylist);
                // Make it possible for Textview representing each survey can be clicked.
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(Surveylist.getText().toString());
                Pattern pattern2 = Pattern.compile(Surveylist.getText().toString());
                Matcher matcher2 = pattern2.matcher(Surveylist.getText().toString());
                final int s = i;
                while (matcher2.find()) {
                    final String group = matcher2.group();
                    final int zz = s;
                    ClickableSpan what = new ClickableSpan() {

                        @Override
                        public void onClick(View widget) {
                            Intent intent = new Intent(Surveys_published.this, Search_result.class);
                            survey = user.publishedSurvey.get(zz);
                            ///intent.putExtra("survey", survey);
                            intent.putExtra("publisher", user.username);
                            intent.putExtra("index", zz);
                            intent.putExtra("engager", user.username);
                            startActivity(intent);
                        }
                    };
                    spannableStringBuilder.setSpan(what, matcher2.start(), matcher2.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                Surveylist.setText(spannableStringBuilder);
                Surveylist.setMovementMethod(LinkMovementMethod.getInstance());
                // Make it possible for Textview representing each survey can be clicked.

                ContentView.addView(layout, i);
            }
        }
        else {
            for (int i = 0; i < user.engagedSurvey.size(); i++) {
                choiceNum.add(0);
                LinearLayout layout = new LinearLayout(Surveys_published.this);
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
                TextView Surveylist = new TextView(Surveys_published.this);
                LinearLayout.LayoutParams questionParam = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                Surveylist.setLayoutParams(questionParam);
                Surveylist.setBackgroundColor(Color.argb(255, 255, 255, 255));
                Surveylist.setGravity(Gravity.LEFT);
                Surveylist.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                Surveylist.setTextSize(16);
                Surveylist.setText("ID: " + Integer.toString(user.engagedSurvey.get(i).SurveyID) + "  " + user.engagedSurvey.get(i).surveyName);

                layout.addView(Surveylist);
                // Make it possible for Textview representing each survey can be clicked.
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(Surveylist.getText().toString());
                Pattern pattern2 = Pattern.compile(Surveylist.getText().toString());
                Matcher matcher2 = pattern2.matcher(Surveylist.getText().toString());
                final int s = i;
                while (matcher2.find()) {
                    final String group = matcher2.group();
                    final int zz = s;
                    ClickableSpan what = new ClickableSpan() {

                        @Override
                        public void onClick(View widget) {
                            survey = user.engagedSurvey.get(zz);
                            HttpPost SearchPost = new HttpPost();
                            SearchPost.execute("Search Survey", Integer.toString(survey.SurveyID));
                            String search_result = "";
                            try {
                                search_result = SearchPost.get();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            if (search_result.equals("no such survey") == true) {

                            }
                            else {
                                Users survey_publisher = null;
                                Gson gson = new GsonBuilder().create();
                                survey_publisher = gson.fromJson(search_result, Users.class);
                                int survey_index = 0;
                                for (int i = 0; i < survey_publisher.publishedSurvey.size(); i++) {
                                    Surveys survey_found = survey_publisher.publishedSurvey.get(i);
                                    if ((survey_found.SurveyID == survey.SurveyID)) {
                                        survey_index = i;
                                        Intent intent = new Intent(Surveys_published.this, Search_result.class);
                                        intent.putExtra("publisher", survey_publisher.username);
                                        intent.putExtra("index", survey_index);
                                        intent.putExtra("engager", user.username);
                                        startActivity(intent);
                                        break;
                                    }
                                }
                            }
                        }
                    };
                    spannableStringBuilder.setSpan(what, matcher2.start(), matcher2.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                Surveylist.setText(spannableStringBuilder);
                Surveylist.setMovementMethod(LinkMovementMethod.getInstance());
                // Make it possible for Textview representing each survey can be clicked.

                ContentView.addView(layout, i);
            }
        }
    }

    public void Onclick_Home(View view){
        Intent intent = new Intent(Surveys_published.this, Home_page.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

    public void User_centerOnclick(View view){
        Intent intent = new Intent(Surveys_published.this, Logout.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

}
