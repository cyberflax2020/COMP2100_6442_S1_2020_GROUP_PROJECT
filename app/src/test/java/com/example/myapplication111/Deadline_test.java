package com.example.myapplication111;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertTrue;

public class Deadline_test {
    Surveys surveys = new Surveys("b");
    Date date = new Date();
    Calendar newDate = Calendar.getInstance();

    @Test
    public void TestDeadlineByYear() {
        newDate.setTime(date);
        newDate.add(Calendar.YEAR, 1);
        Date newDateFinal = newDate.getTime();
        assertTrue(surveys.beforeEndDate(date, newDateFinal));

    }

    @Test
    public void TestDeadlineByMonth() {
        newDate.setTime(date);
        newDate.add(Calendar.MONTH, 1);
        Date newDateFinal = newDate.getTime();
        assertTrue(surveys.beforeEndDate(date, newDateFinal));

    }

    @Test
    public void TestDeadlineByDay() {
        newDate.setTime(date);
        newDate.add(Calendar.DATE, 1);
        Date newDateFinal = newDate.getTime();
        assertTrue(surveys.beforeEndDate(date, newDateFinal));

    }

    @Test
    public void TestDeadlineByHour() {
        newDate.setTime(date);
        newDate.add(Calendar.HOUR_OF_DAY, 1);
        Date newDateFinal = newDate.getTime();
        assertTrue(surveys.beforeEndDate(date, newDateFinal));
    }

    @Test
    public void TestDeadlineByMinute() {
        newDate.setTime(date);
        newDate.add(Calendar.MINUTE, 1);
        Date newDateFinal = newDate.getTime();
        assertTrue(surveys.beforeEndDate(date, newDateFinal));
    }

    @Test
    public void TestDeadlineBySecond() {
        newDate.setTime(date);
        newDate.add(Calendar.SECOND, 10);
        Date newDateFinal = newDate.getTime();
        assertTrue(surveys.beforeEndDate(date, newDateFinal));
    }
}
