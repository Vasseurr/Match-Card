package com.example.matchcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.play_button);     //set start button id and this id comes activity_main.xml
        start.setOnClickListener(new View.OnClickListener() {       //when user push this button, we want the user to go to the other activity
            @Override
            public void onClick(View view) {
                    Intent i = new Intent(MainActivity.this, Game.class);        //intent helps to load other activity
                                                                                                //first parameter is this activity, second parameter is other activity that user will go
                    //i.pushExtra("name", "string_name");                                      //when we want to send variables to other activity, we can use pushExtra
                                                                                                //first parameter is key, second parameter is value
                    startActivity(i);                                                               //we are starting other activity
            }
        });

    }
}