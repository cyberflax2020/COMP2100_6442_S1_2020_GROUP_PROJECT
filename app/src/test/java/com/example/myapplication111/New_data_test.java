package com.example.myapplication111;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class New_data_test {

    Users user=new Users("a");
    Surveys survey=new Surveys("b");
    Questions questions=new Questions("c",1);

    @Test
    public void New_User(){
        assertTrue(this.user!=null);
        assertTrue(this.user.username=="a");

    }

    @Test
    public void New_survey(){
        assertTrue(this.survey!=null);
        assertTrue(this.survey.surveyName=="b");

    }

    @Test
    public void New_question(){
        assertTrue(this.questions!=null);
        assertTrue(this.questions.questionContent=="c");
    }

    @Test
    public void New_option(){
        this.questions.addOption(new Questions.Option(0,"d"));
        assertTrue(this.questions.getOptionContent(1)=="d");
    }

}
