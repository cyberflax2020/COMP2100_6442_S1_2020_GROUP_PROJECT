package com.example.myapplication111;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Users implements Serializable {       //create a class for all registered users
    String username;
    ArrayList<Surveys> publishedSurvey;
    ArrayList<Surveys> engagedSurvey;

    public Users (String username){
        this.username=username;
        this.publishedSurvey=new ArrayList<>();
        this.engagedSurvey=new ArrayList<>();

    }



    //publish a survey to the service

    public Boolean publishNewSurvey(Surveys newSurvey) throws ExecutionException, InterruptedException {

        HttpPost PublishPost = new HttpPost();
        PublishPost.execute("Publish Survey", this.username, null);
        String sid = PublishPost.get();
        newSurvey.SurveyID = Integer.parseInt(sid);
        this.publishedSurvey.add(newSurvey);

        Gson gson = new GsonBuilder().create();
        String toSave = gson.toJson(this);

        HttpPost SavePost = new HttpPost();
        SavePost.execute("Save User Class", username, toSave);
        String saveResult = SavePost.get();

        return saveResult.equals("saving succeeded");
    }

    //delete a survey and push it to the service
    public Boolean deleteSurvey(int surveyId) throws ExecutionException, InterruptedException {

        Surveys survey;
        String result = "";
        for (int i = 0; i < this.publishedSurvey.size(); i++) {
            survey = this.publishedSurvey.get(i);
            if (survey.SurveyID == surveyId) {
                this.publishedSurvey.remove(i);
                HttpPost DeletePost = new HttpPost();
                DeletePost.execute("Delete Survey", Integer.toString(surveyId));
                result = DeletePost.get();
                break;
            }
        }

        return result.equals("deleting succeeded");
    }


    //update a survey and push it to the service
    public Boolean updateSurvey(int surveyId) throws ExecutionException, InterruptedException {

        HttpPost EngagePost = new HttpPost();
        EngagePost.execute("Engage Survey", this.username, Integer.toString(surveyId));
        String engageResult = EngagePost.get();
        if (engageResult.equals("engaging succeeded") == false) {
            return false;
        }

        Gson gson = new GsonBuilder().create();
        String toSave = gson.toJson(this);

        HttpPost SavePost = new HttpPost();
        SavePost.execute("Save User Class", username, toSave);
        String saveResult = SavePost.get();

        return saveResult.equals("saving succeeded");
    }
}
