package com.example.myapplication111;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

public class SendPage extends AppCompatActivity {

    Button setDateButton;
    EditText date;
    Button setTimeButton;
    EditText time;
    Button confirmTimeButton;
    int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;

    Surveys survey=null;
    Users user=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_page);
        setDateButton = (Button) findViewById(R.id.setDateButton);
        date = (EditText) findViewById(R.id.date);
        setTimeButton = (Button) findViewById(R.id.setTimeButton);
        time = (EditText) findViewById(R.id.time);
        confirmTimeButton = (Button) findViewById(R.id.confirmTimeButton);
        this.survey = (Surveys) getIntent().getSerializableExtra("survey"); //load survey
        this.user = (Users) getIntent().getSerializableExtra("user");   //load user
    }

    public void setDate(View view) {
        final Calendar c = Calendar.getInstance();
        yearFinal = c.get(Calendar.YEAR);
        monthFinal = c.get(Calendar.MONTH);
        dayFinal = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
            }
        }, yearFinal, monthFinal, dayFinal);
        datePickerDialog.show();
    }

    public void setTime(View view) {
        final Calendar c = Calendar.getInstance();
        hourFinal = c.get(Calendar.HOUR_OF_DAY);
        minuteFinal = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                time.setText(hourOfDay + ":" + minute);
            }
        }, hourFinal, minuteFinal, true);
        timePickerDialog.show();
    }

    public void confirmTime(View view) throws ParseException, ExecutionException, InterruptedException {
        if (date.getText().toString().trim().length() == 0) {
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Please enter expiry date!")
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //do nothing - it will close on its own
                        }
                    }).show();
            return;
        } else if (time.getText().toString().trim().length() == 0) {
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Please enter expiry time!")
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //do nothing - it will close on its own
                        }
                    }).show();
            return;
        } else {
            System.out.println(time.getText().toString() + " " + date.getText().toString());
            String time0 = time.getText().toString();
            String data0 = date.getText().toString();
            String hour = time0.split(":")[0];
            String minute = time0.split(":")[1];
            String year = data0.split("-")[2];
            String month = data0.split("-")[1];
            String day = data0.split("-")[0];

            if(hour.length() == 1){
                hour = "0"+hour;
            }
            if(minute.length() == 1){
                minute = "0"+minute;
            }
            if(month.length() == 1){
                month = "0" + month;
            }
            if (day.length()==1){
                day = "0" + day;
            }


            this.survey.setEndDate( year,month,
                    day,hour,minute,"00"  );


            
            System.out.println(this.user.hashCode()+"user");
            System.out.println(this.survey.hashCode()+"survey");

            Boolean pushResult = this.user.publishNewSurvey(this.survey);
            pushResult = true;

            if(pushResult){
                Toast.makeText(this, "Push succeed!", Toast.LENGTH_SHORT).show();
                Intent intent_create_title = new Intent(this, Home_page.class);
                intent_create_title.putExtra("user", user);
                startActivity(intent_create_title);
                
            }
            else {
                Toast.makeText(this, "Push failed!", Toast.LENGTH_SHORT).show();
            }






        }
    }
}
