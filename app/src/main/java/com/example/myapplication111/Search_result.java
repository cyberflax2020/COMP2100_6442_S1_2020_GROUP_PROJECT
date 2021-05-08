package com.example.myapplication111;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;
import java.util.concurrent.ExecutionException;

public class Search_result extends AppCompatActivity {

    TextView surveyTitleTextView;
    Button engageButton;
    Button checkResultButton;
    Surveys survey;
    String publisher;
    Users user;
    int survey_index;
    Users engager_class;
    String engager;
    Date now = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_title_page);

        this.publisher = getIntent().getStringExtra("publisher");
        this.survey_index = getIntent().getIntExtra("index", 0);
        this.engager = getIntent().getStringExtra("engager");

        HttpPost GetPost1 = new HttpPost();
        GetPost1.execute("Get User Class", publisher);
        String userclass = "";
        try {
            userclass = GetPost1.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Gson gson = new GsonBuilder().create();
        user = gson.fromJson(userclass, Users.class);

        if (engager.equals("guest:guest") == false) {
            if (engager.equals(publisher) == true) {
                engager_class = user;
            }
            else {
                HttpPost GetPost2 = new HttpPost();
                GetPost2.execute("Get User Class", engager);
                try {
                    userclass = GetPost2.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                engager_class = gson.fromJson(userclass, Users.class);
            }
        }

        survey = user.publishedSurvey.get(survey_index);

        surveyTitleTextView = (TextView) findViewById(R.id.surveyTitleTextView);
        engageButton = (Button) findViewById(R.id.engageButton);
        checkResultButton = (Button) findViewById(R.id.checkResultButton);

        surveyTitleTextView.setText(survey.surveyName);
    }

    public void engage(View view) {
        if (engager.equals("guest:guest") == true) {
            new AlertDialog.Builder(this)
                    .setTitle("Error!")
                    .setMessage("You cannot engage a survey as guest")
                    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //do nothing - it will close on its own
                        }
                    })
                    .show();
        } else if(!survey.beforeEndDate(now, survey.endDate)) {
            new AlertDialog.Builder(this)
                    .setTitle("Error!")
                    .setMessage("This survey has reached the expiry date")
                    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //do nothing - it will close on its own
                        }
                    })
                    .show();
        }
        else {
            for (int i = 0; i < engager_class.engagedSurvey.size(); i++) {
                if (survey.SurveyID == engager_class.engagedSurvey.get(i).SurveyID) {
                    new AlertDialog.Builder(this)
                            .setTitle("Error!")
                            .setMessage("You have engaged this survey")
                            .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //do nothing - it will close on its own
                                }
                            })
                            .show();
                    return;
                }
            }

            Intent engage = new Intent(Search_result.this, Survey_engage.class);
            engage.putExtra("survey", survey);
            startActivityForResult(engage, 0);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            survey = (Surveys)data.getSerializableExtra("survey");
        } catch (NullPointerException n) {
            n.printStackTrace();
            finish();
        }
        Gson gson = new GsonBuilder().create();
        user.publishedSurvey.set(survey_index, survey);
        Gson gson_tojson = new GsonBuilder().create();
        String toSave = gson_tojson.toJson(user);
        HttpPost SavePost = new HttpPost();
        SavePost.execute("Save User Class", user.username, toSave);

        engager_class.engagedSurvey.add(survey);
        toSave = gson_tojson.toJson(engager_class);
        HttpPost SavePost2 = new HttpPost();
        SavePost2.execute("Save User Class", engager_class.username, toSave);

        finish();
    }

    public void checkResult(View view) {
        Intent checkResult = new Intent(Search_result.this, Survey_result.class);
        checkResult.putExtra("survey", survey);
        startActivity(checkResult);
    }
}
