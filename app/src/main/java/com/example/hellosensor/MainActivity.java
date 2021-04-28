package com.example.hellosensor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    Button toAccelloAct;
    Button toCompassAct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toAccelloAct = (Button) findViewById(R.id.toAccelloAct);
        toCompassAct = (Button) findViewById(R.id.toCompassAct);
        toCompassAct.setOnClickListener( v -> {
            Intent intent = new Intent(this, CompassActivity.class);
            startActivity(intent);
        });
        toAccelloAct.setOnClickListener(v -> {
            Intent intent = new Intent(this, AccelloActivity.class);
            startActivity(intent);
        });
    }

}