package com.example.myapplication111;

// a ID generator that creates unique IDs for options of every single question, and creates question id.

import java.io.Serializable;

public class Id_generator implements Serializable {
    int currentNum=0;

    public int generateNewId(){
        currentNum++;
        return currentNum;

    }
}
