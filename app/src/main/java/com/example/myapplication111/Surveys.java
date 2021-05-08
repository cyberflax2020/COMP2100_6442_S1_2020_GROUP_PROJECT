package com.example.myapplication111;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class Surveys implements Serializable {
    public int SurveyID;
    String publisherId;
    String surveyName;
    Date endDate;
    HashMap<Integer,Questions> questions;

    public Surveys(String surveyName) {
        this.surveyName = surveyName;
        this.questions=new HashMap<>();
        this.endDate=new Date();
    }


    public void setEndDate(String year, String month, String day, String hour, String minute, String second) throws ParseException {
        String DateString = year + "/" + month + "/" + day + " " + hour + ":" + minute + ":" + second;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        this.endDate = sdf.parse(DateString);
    }

    public Boolean beforeEndDate(Date date, Date endDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        String now = formatter.format(date);
        String nowReplace = now.replace("/", " ");
        String nowReplace1 = nowReplace.replace(":", " ");
        ArrayList<String> nowSingle = new ArrayList<>(Arrays.asList(nowReplace1.split(" ")));

        String endDateString = formatter.format(endDate);
        String endDateStringReplace = endDateString.replace("/", " ");
        String endDateStringReplace1 = endDateStringReplace.replace(":", " ");
        ArrayList<String> endDateStringSingle = new ArrayList<>(Arrays.asList(endDateStringReplace1.split(" ")));

        for (int i = 0; i < 5; i++) {
            if(Integer.parseInt(nowSingle.get(i)) < Integer.parseInt(endDateStringSingle.get(i))) {
                return true;
            }
        }
        return false;
    }


    public void addQuestion(Questions newQuestion) {
        this.questions.put(newQuestion.questionId,newQuestion);

    }




}
