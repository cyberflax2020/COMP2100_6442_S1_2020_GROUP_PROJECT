package com.example.myapplication111;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class Data_modify_test {

    Surveys survey=new Surveys("b");
    Questions questions=new Questions("c",1);
    Questions.Option option=new Questions.Option('1',"d");


    @Test
    public void Data_modify_test_option(){

        option.optionContent="test";
        questions.addOption(option);
        assertTrue(questions.optionIdMap.values().contains("test"));

        questions.deleteOption(1);
        assertTrue(!questions.optionIdMap.values().contains("test"));

    }

    @Test
    public void Data_modify_test_question(){
        questions.addOption(new Questions.Option(1,"a"));
        questions.addOption(new Questions.Option(2,"b"));
        questions.makeNewChoice(1);
        questions.makeNewChoice(1);
        questions.makeNewChoice(2);
        assertTrue(questions.optionCountMap.get(1)==2);
        assertTrue(questions.optionCountMap.get(2)==1);
        questions.cancelChoice(1);
        assertTrue(questions.optionCountMap.get(1)==1);
    }


    @Test
    public void Data_modify_test_survey(){
        survey.addQuestion(questions);
        assertTrue(survey.questions.keySet().size()==1);
    }

}
