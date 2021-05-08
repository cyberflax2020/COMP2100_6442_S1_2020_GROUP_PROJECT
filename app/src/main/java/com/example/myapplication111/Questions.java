package com.example.myapplication111;


import java.io.Serializable;
import java.util.HashMap;

public class Questions implements Serializable {
    int questionId;
    static Id_generator optionIdGenerator;
    String questionContent;
    int allowedChoiceNum;    //how many choices are allowed to be chosen, 1 for single-choice question
    int participantNum;      //how many participants have answered
    HashMap<Integer, String> optionIdMap; //Map single option to its Id
    HashMap<Integer, Integer> optionCountMap;  //the first Integer is option Id, the second Integer is the times that the option has been chosen


    public static class Option {            //create option inner class

        int OptionID;
        String optionContent;

        public Option(int OptionID, String optionContent) {
            this.OptionID = OptionID;
            this.optionContent = optionContent;

        }
    }


    public Questions(String questionContent, int allowedChoiceNum) {
        optionIdGenerator = new Id_generator();
        this.questionContent = questionContent;
        this.allowedChoiceNum = allowedChoiceNum;
        this.participantNum = 0;
        this.optionCountMap = new HashMap<>();
        this.optionIdMap = new HashMap<>();

    }


    public void addOption(Option newOption) {
        newOption.OptionID=optionIdGenerator.generateNewId();
        this.optionIdMap.put(newOption.OptionID, newOption.optionContent);
        this.optionCountMap.put(newOption.OptionID, 0);

    }

    public void setOptionContent(int optionId, String content) {
        this.optionIdMap.put(optionId, content);
    }

    public void deleteOption(int optionId) {
        optionIdMap.remove(optionId);
        optionCountMap.remove(optionId);
    }


    public void makeNewChoice(int optionId) {
        int newValue = optionCountMap.get(optionId) + 1;
        optionCountMap.put(optionId, newValue);
    }

    public void cancelChoice(int optionId) {
        int newValue = optionCountMap.get(optionId) - 1;
        optionCountMap.put(optionId, newValue);
    }

    public int optionNum() {
        return optionIdMap.size();
    }

    public String getOptionContent(int optionId) {
        return this.optionIdMap.get(optionId);
    }

    public HashMap<String, Integer> getQuestionResultMap() {     //output a map with option contents and chosen times
        HashMap questionResultMap = new HashMap<>();
        for (Integer id : optionIdMap.keySet()) {
            String key = optionIdMap.get(id);
            Integer value = optionCountMap.get(id);
            questionResultMap.put(key, value);

        }
        return questionResultMap;
    }

}
