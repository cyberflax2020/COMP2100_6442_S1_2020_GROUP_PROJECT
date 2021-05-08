package com.example.myapplication111;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
public class Data_structure_test {
    Surveys survey=new Surveys("b");
    Questions questions=new Questions("c",1);
    Questions.Option option=new Questions.Option('1',"d");

    @Test
    public void addquestion_test(){
        questions.questionId=20;
        survey.addQuestion(questions);
        questions.addOption(option);
        assertTrue(survey.questions.get(20)==questions);
        assertTrue(survey.questions.get(20).optionIdMap.get(1)==option.optionContent);
    }

}
