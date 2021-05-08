package com.example.myapplication111;

import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;


public class Survey_result extends AppCompatActivity {

    // LinearLayout container
    private LinearLayout ContentView;
    private Surveys survey = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.survey = (Surveys)getIntent().getSerializableExtra("survey");
        setContentView(R.layout.activity_survey_result);

        ContentView = (LinearLayout) this.findViewById(R.id.content_view);
        for (int i = 1; i <= survey.questions.size(); i++) {
        //for (int i = 1; i <= 3; i++) {
            LinearLayout layout = new LinearLayout(Survey_result.this);
            LinearLayout.LayoutParams layoutParamsNext = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            //set layout margin
            layoutParamsNext.setMargins(0, 50, 0, 0);
            layout.setLayoutParams(layoutParamsNext);
            layout.setBackgroundColor(Color.argb(255, 28, 134, 238));
            layout.setPadding(20, 20,20, 20);
            layout.setOrientation(LinearLayout.VERTICAL);

            //create question textview
            TextView questionContent = new TextView(Survey_result.this);
            LinearLayout.LayoutParams questionParam = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            questionParam.setMargins(0, 0, 0, 20);
            questionContent.setLayoutParams(questionParam);
            questionContent.setBackgroundColor(Color.argb(255, 255, 255, 255));
            questionContent.setGravity(Gravity.LEFT);
            questionContent.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            questionContent.setPadding(20, 0, 0, 0);
            questionContent.setTextSize(16);
            questionContent.setText(survey.questions.get(i).questionContent);
            //questionContent.setText("question no." + Integer.toString(i));
            layout.addView(questionContent);

            for (int j = 1; j <= survey.questions.get(i).optionNum(); j++) {
            //for (int j = 1; j <= 2; j++) {
                TextView answerContent = new TextView(Survey_result.this);

                //create answer textview
                answerContent.setBackgroundColor(Color.argb(255, 255, 255, 255));
                answerContent.setPadding(20, 20, 20 ,20);
                answerContent.setText(survey.questions.get(i).getOptionContent(j));
                //answerContent.setText("answer no." + Integer.toString(j));
                layout.addView(answerContent);
            }

            //set bar chart
            BarChart barChart = new BarChart(Survey_result.this);
            barChart.setMinimumHeight(400);

            HashMap<String, Integer> questionResult = survey.questions.get(i).getQuestionResultMap();
            System.out.println(questionResult);

            ArrayList<BarEntry> barEntries = new ArrayList<>();
            for (int x = 1; x <= survey.questions.get(i).optionNum(); x++) {
            //for(int x = 1; x <= 5; x++) {
                //the y part should be how many times an answer been choosed
                barEntries.add(new BarEntry(x, questionResult.get(survey.questions.get(i).getOptionContent(x))));
            }
            BarDataSet barDataSet = new BarDataSet(barEntries, "Choose Rate");
            barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
            barDataSet.setValueTextColor(Color.BLACK);
            barDataSet.setValueTextSize(14f);

            BarData barData = new BarData(barDataSet);

            barChart.setFitBars(true);
            barChart.setData(barData);
            barChart.getDescription().setText("Question 1");
            barChart.animateY(2000);

            layout.addView(barChart);

            ContentView.addView(layout, i - 1);
        }
    }
}