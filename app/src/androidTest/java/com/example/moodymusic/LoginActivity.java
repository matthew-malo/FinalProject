package com.example.moody;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        configureMainLogin();
        configureMainRegister();
    }

    private void configureMainLogin() {
        Button mainLogin = findViewById(R.id.mainLogin);
        mainLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, com.example.moody.Login.class));
            }
        });
    }

    private void configureMainRegister() {
        Button mainRegister = findViewById(R.id.mainRegister);
        mainRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, Register.class));
            }
        });
    }

}