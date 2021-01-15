package com.example.matchcard;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        TextView tv = (TextView) findViewById(R.id.textView);
        Intent i = getIntent();
        int mistake = i.getIntExtra("mistake",0);
        int number_of_card = i.getIntExtra("numberOfCard",1);
        tv.setTextSize(30);     //set font size

        int score = (number_of_card*100) - mistake;
        int bonus = 100;

        tv.setText("\tCard" + ":  " + number_of_card + "\n\t" + "Mistake: "  + mistake + "\n\t" + "Score: " + score
                + "\n\tBONUS:  " + bonus + "\n\t" + "Total: " + (bonus + score));

        Button tryb = findViewById(R.id.trybtn);
        Button menu = findViewById(R.id.menubtn);

        tryb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ScoreActivity.this, Game.class);
                startActivity(i);
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ScoreActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

    }
}