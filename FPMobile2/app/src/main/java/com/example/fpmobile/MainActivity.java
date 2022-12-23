package com.example.fpmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private ImageView imgprofile;
    private ImageView btncucibasah;
    private ImageView btndryclean;
    private ImageView btnpremium;
    private ImageView btnsetrika;
    private Button btnlaundry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgprofile = findViewById(R.id.imgprofile);
        btncucibasah = findViewById(R.id.btncucibasah);
        btndryclean = findViewById(R.id.btndryclean);
        btnpremium = findViewById(R.id.btnpremium);
        btnsetrika = findViewById(R.id.btnsetrika);
        btnlaundry = findViewById(R.id.btnlaundry);

//        View slide_profile = findViewById(R.id.imgprofile);
        imgprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Berhasil profile", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, profile.class));
                finish();
            }
        });
        btncucibasah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent produk = new Intent(MainActivity.this, produk.class);
                Toast.makeText(MainActivity.this, "Berhasil Membuka Daftar Cucian", Toast.LENGTH_SHORT).show();
                startActivity(produk);
            }
        });
        btndryclean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent produk = new Intent(MainActivity.this, produk.class);
                Toast.makeText(MainActivity.this, "Berhasil Membuka Daftar Cucian", Toast.LENGTH_SHORT).show();
                startActivity(produk);
            }
        });
        btnpremium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent produk = new Intent(MainActivity.this, produk.class);
                Toast.makeText(MainActivity.this, "Berhasil Membuka Daftar Cucian", Toast.LENGTH_SHORT).show();
                startActivity(produk);
            }
        });
        btnsetrika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent produk = new Intent(MainActivity.this, produk.class);
                Toast.makeText(MainActivity.this, "Berhasil Membuka Daftar Cucian", Toast.LENGTH_SHORT).show();
                startActivity(produk);
            }
        });
        btnlaundry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MapsActivity = new Intent(MainActivity.this, MapsActivity.class);
                Toast.makeText(MainActivity.this, "Berhasil Membuka Lokasi Laundry", Toast.LENGTH_SHORT).show();
                startActivity(MapsActivity);
            }
        });
    }
}