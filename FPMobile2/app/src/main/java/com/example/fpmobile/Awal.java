package com.example.fpmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;

public class Awal extends AppCompatActivity {
    Button slide_register;
    TextView slide_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.awal);

        slide_register=findViewById(R.id.buttonRegister);
        slide_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regist = new Intent(Awal.this,Register.class);
                startActivity(regist);
            }
        });

        slide_login=findViewById(R.id.klik_login);
        slide_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(Awal.this,Login.class);
                startActivity(login);
            }
        });


    }

}
