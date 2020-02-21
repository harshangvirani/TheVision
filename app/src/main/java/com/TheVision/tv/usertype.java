package com.TheVision.tv;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class usertype extends AppCompatActivity implements View.OnClickListener {

    Button btntotal, btnless;
    String tts;
    DBhelper db;
    TextToSpeech t1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usertype);

        tts = "choose user type click top for Total blind and down for less visual";
        ttsFirstPage();

        btnless = (Button) findViewById(R.id.btnLessVisual);
        btntotal = (Button) findViewById(R.id.btnTotal);

        btnless.setOnClickListener(this);
        btntotal.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLessVisual:
                DBhelper.typeuser = "LessVisual";
                break;

            case R.id.btnTotal:
                DBhelper.typeuser = "TotalBlind";
                break;
        }

        db = new DBhelper(this);

        if (db.insert()) {
            Toast.makeText(this, "Record inserted", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();
        }

        ArrayList<String> arrayList;
        arrayList = db.select();
        String s = arrayList.get(0);
        ArrayList<String> arrayList1;
        arrayList1 = db.selectlang();
        String s1 = arrayList1.get(0);
        SharedPreferences sharedPreferences = getSharedPreferences("UserType", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user1", s);
        editor.putString("lang", s1);
        editor.apply();
        if (arrayList.get(0).equals("TotalBlind")) {
            Intent ine = new Intent(usertype.this, TotalBlindSubject_act.class);
            ine.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            t1.shutdown();
            startActivity(ine);
        }
        if (arrayList.get(0).equals("LessVisual")) {
            Intent ine = new Intent(usertype.this, Subject.class);
            ine.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            t1.shutdown();
            startActivity(ine);
        }


    }


    public void ttsFirstPage() {
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });

        final Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {

                t1.setSpeechRate(0.8f);
                t1.setPitch(1.0f);
                t1.speak(tts, TextToSpeech.QUEUE_FLUSH, null, null);

            }
        }, 500);
    }


}
