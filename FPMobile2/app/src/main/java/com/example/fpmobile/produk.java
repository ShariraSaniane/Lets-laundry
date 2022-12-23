package com.example.fpmobile;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class produk extends AppCompatActivity {
    private ImageView buttonBack;
    private Button Pesanlaundry;
    private EditText NumberBaju;
    private EditText NumberCelana;
    private EditText NumberSprei;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk);

        NumberBaju = findViewById(R.id.NumberBaju);
        NumberCelana = findViewById(R.id.NumberCelana);
        NumberSprei = findViewById(R.id.NumberSprei);
        buttonBack = (ImageView)findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(produk.this, MainActivity.class
                ));
            }
        });
//        public produk(ImageView buttonBack) {
//                this.buttonBack = buttonBack;
//        }
//
//
//        public produk(Button Pesanlaundry){
//                this.Pesanlaundry = Pesanlaundry;
//        }

        View buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(produk.this, "Berhasil back", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(produk.this, MainActivity.class));
                finish();
            }
        });

        Pesanlaundry = findViewById(R.id.Pesanlaundry);
        Pesanlaundry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String CountBaju = NumberBaju.getText().toString();
                String CountCelana = NumberCelana.getText().toString();
                String CountSprei = NumberSprei.getText().toString();

                if (TextUtils.isEmpty(CountBaju) || TextUtils.isEmpty(CountCelana) || TextUtils.isEmpty(CountSprei)) {
                    Toast.makeText(produk.this, "Pesanan Gagal", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(produk.this, "Pesanan Berhasil", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(produk.this, face_validation.class));
                    finish();
                }
            }
        });

    }


}