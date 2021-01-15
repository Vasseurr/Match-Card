package com.example.matchcard;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

public class Card extends AppCompatButton {

    boolean isOpen = false;     //open->face, close->back sides of card
    boolean isSpin = true;
    static boolean isMix = true;

    int backSideOfCardID;
    int faceSideOfCardID;
    int card_number;
    Drawable face;
    Drawable back;

    static int[] icons_animal = new int[]{R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e,R.drawable.f};

    public Card(@NonNull Context context, int id, int number_of_Cards) {
        super(context);

        setId(id);      //button id

        int index;      //for cards --> If number of cards are 12, index (mode number of cards / 2)
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Mix();
            }
        });     //thread is used to shred high works to threads

        thread.start();

        try {
            thread.join();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        card_number = number_of_Cards/2;        //unique card number

        index = (id - 1) % card_number;
        faceSideOfCardID = icons_animal[index];
        backSideOfCardID = R.drawable.backside;

        back = ContextCompat.getDrawable(context,backSideOfCardID);
        face = ContextCompat.getDrawable(context,faceSideOfCardID);
        setBackground(back);

    }

    public void Spin() {
        if(!isSpin)       //eşleşmeden sonra kullanıcı tekrar döndürmesin kartları
            return;

        if(!isOpen) {   //arkası cevriliyse
            setBackground(face);
            isOpen=true;
        }
        else {
            setBackground(back);
            isOpen=false;
        }
    }

    public static void Mix() {
        if (isMix) {
            for (int j = 0; j < icons_animal.length; j++) {
                int random = (int) (Math.random() * icons_animal.length);
                int k = icons_animal[random];
                icons_animal[random] = icons_animal[j];
                icons_animal[j] = k;
            }
        }
    }
}
