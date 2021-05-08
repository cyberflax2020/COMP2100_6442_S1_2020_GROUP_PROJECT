package com.example.myapplication111;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.ExecutionException;

public class Home_page extends AppCompatActivity implements View.OnClickListener {

    // Contents of searching.
    private ImageView idIvSearch;
    // New Survey
    private AppCompatButton idBtnSurvey;
    // Published
    private AppCompatButton idBtnPublish;
    // Engaged
    private AppCompatButton idBtnEngaged;
    private AppCompatButton Home;
    private AppCompatButton User_center;

    private Surveys survey_found = null;
    private Users survey_publisher = null;
    private int survey_index = 0;

    Users user=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.user = (Users)getIntent().getSerializableExtra("user");

        setContentView(R.layout.activity_page2);
        idIvSearch = (ImageView) findViewById(R.id.idIvSearch);
        idBtnSurvey = (AppCompatButton) findViewById(R.id.idBtnSurvey);
        idBtnPublish = (AppCompatButton) findViewById(R.id.idBtnPublish);
        idBtnEngaged = (AppCompatButton) findViewById(R.id.idBtnEngaged);
        User_center = (AppCompatButton) findViewById(R.id.User_center);
        Home = (AppCompatButton) findViewById(R.id.Home);
        idIvSearch.setOnClickListener(this);
        idBtnSurvey.setOnClickListener(this);
        idBtnPublish.setOnClickListener(this);
        idBtnEngaged.setOnClickListener(this);
        Home.setOnClickListener(this);
        User_center.setOnClickListener(this);



    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.idIvSearch:
                Toast.makeText(this, "Start searching", Toast.LENGTH_SHORT).show();
                AutoCompleteTextView search_text_view = findViewById(R.id.idActvSearch);
                String sid = search_text_view.getText().toString();

                if (sid.equals("") == true) {
                    new AlertDialog.Builder(this)
                            .setTitle("Error!")
                            .setMessage("Please enter a survey ID")
                            .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //do nothing - it will close on its own
                                }
                            })
                            .show();
                    return;
                }

                HttpPost SearchPost = new HttpPost();
                SearchPost.execute("Search Survey", sid);
                String search_result = "";
                try {
                    search_result = SearchPost.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (search_result.equals("no such survey") == true) {
                    new AlertDialog.Builder(this)
                            .setTitle("Error!")
                            .setMessage("Survey not found")
                            .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //do nothing - it will close on its own
                                }
                            })
                            .show();
                }
                else {
                    survey_publisher = new Users("default");
                    Gson gson = new GsonBuilder().create();
                    survey_publisher = gson.fromJson(search_result, Users.class);
                    for (int i = 0; i < survey_publisher.publishedSurvey.size(); i++) {
                        survey_found = survey_publisher.publishedSurvey.get(i);
                        if ((survey_found.SurveyID == Integer.parseInt(sid))) {
                            survey_index = i;
                            Intent engage = new Intent(Home_page.this, Search_result.class);
                            //engage.putExtra("survey", survey_found);
                            engage.putExtra("publisher", survey_publisher.username);
                            engage.putExtra("index", survey_index);
                            engage.putExtra("engager", this.user.username);
                            startActivityForResult(engage, 0);
                            break;
                        }
                    }
                }
                break;
            case R.id.idBtnSurvey:
                Intent intent_create_title = new Intent(Home_page.this, Creating_title.class);
                intent_create_title.putExtra("user", user);
                startActivity(intent_create_title);
//                finish();
                break;
            case R.id.idBtnPublish:
                Intent intent_toPublish = new Intent(Home_page.this, Surveys_published.class);
                intent_toPublish.putExtra("user", user.username);
                intent_toPublish.putExtra("entry", "publish");
                startActivity(intent_toPublish);
                finish();
                break;
            case R.id.idBtnEngaged:
                Intent intent_toEngaged = new Intent(Home_page.this, Surveys_published.class);
                intent_toEngaged.putExtra("user", user.username);
                intent_toEngaged.putExtra("entry", "engage");
                startActivity(intent_toEngaged);
                finish();
                break;
            case R.id.Home:
                break;
            case R.id.User_center:
                Intent intent = new Intent(Home_page.this, Logout.class);
                intent.putExtra("user", user);
                startActivity(intent);
                finish();
                break;

        }
    }
}

