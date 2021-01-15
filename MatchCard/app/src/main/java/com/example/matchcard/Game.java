package com.example.matchcard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;

public class Game extends AppCompatActivity {
    int last_card = 0;   //flip last card
    int score= 0;
    int mistake = 0;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("score",score);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null)      //when user has left the app, app saves score
            score = savedInstanceState.getInt("score");
        else
            score = 0;
        setContentView(R.layout.activity_game);

        Card.isMix = true;                                  //static value

        //final String s = i.getStringExtra("name");        //we can use getTypeExtra when we send value to this activity

        final GridLayout gl = (GridLayout) findViewById(R.id.cards);        //because of final is used in inner functions
        final int numberofCard = 4 * 3;
        final int max_skor = numberofCard / 2;
        final Card cards[] = new Card[numberofCard];

        for(int j = 1;j <= numberofCard;j++) {
            cards[j-1]=new Card(this, j, numberofCard);     //create a new object(card)

            Card.isMix = false;

            cards[j-1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Card k = (Card) view;
                    k.Spin();
                    if (last_card > 0) {  //If one card is spin
                        final Card k2 = (Card) findViewById(last_card);    //We assign first card to an object

                        if (k2.faceSideOfCardID == k.faceSideOfCardID && k2.getId() != k.getId()) {    //face side must be same but these buttons are not same
                            //They are matched
                            k2.isSpin = false;
                            k.isSpin = false;
                            score++;
                            if (score == max_skor) {        //game is finished
                                Intent i = new Intent(Game.this, ScoreActivity.class);
                                i.putExtra("mistake", mistake);
                                i.putExtra("numberOfCard", numberofCard);
                                startActivity(i);
                            }
                            last_card = 0;
                        }

                        else if(k2.faceSideOfCardID != k.faceSideOfCardID && k.isSpin) {
                            //they are not match, flip 2 cards
                            Handler h = new Handler();
                            h.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    k.Spin();
                                    k2.Spin();
                                }
                            },500);
                            mistake++;
                            last_card = 0;
                        }
                        else if(k2.faceSideOfCardID == k.faceSideOfCardID && k2.getId() == k.getId()) {     //flip cards, user click same card
                            Handler h = new Handler();
                            h.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    k.Spin();
                                    k2.Spin();
                                }
                            },500);
                            last_card = 0;
                        }
                    }
                    else {  //first card
                        last_card = k.getId();
                    }
                }
            });
        }


        Thread thread2 = new Thread(new Runnable() {        //Mix cards
            @Override
            public void run() {
                for(int j = 0;j < numberofCard;j++) {
                    int random = (int)(Math.random()*numberofCard);
                    Card k = cards[random];
                    cards[random] = cards[j];
                    cards[j] = k;
                }
            }
        });
        thread2.start();

        try {
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int j = 0;j < numberofCard;j++) {
                    gl.setRowCount(4);
                    gl.setColumnCount(3);       //set row and column count to grid
                    gl.addView(cards[j]);       //add cards to grid
                }
            }
        });
        thread3.start();

        try{
            thread3.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}