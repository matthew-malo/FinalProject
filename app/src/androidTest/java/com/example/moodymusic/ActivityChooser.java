package com.example.moody;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class ActivityChooser extends AppCompatActivity implements View.OnClickListener {

    Button workingOut;
    Button studying;
    Button chores;
    Button relaxing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        workingOut = (Button) findViewById(R.id.workingOut);
        workingOut.setOnClickListener(this);

        studying = (Button) findViewById(R.id.studying);
        studying.setOnClickListener(this);

        chores = (Button) findViewById(R.id.chores);
        chores.setOnClickListener(this);

        relaxing = (Button) findViewById(R.id.relaxing);
        relaxing.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.workingOut:

                break;
        }

        switch (v.getId()) {
            case R.id.studying:

                break;
        }

        switch (v.getId()) {
            case R.id.chores:

                break;
        }

        switch (v.getId()) {
            case R.id.relaxing:

                break;
        }
    }
}
