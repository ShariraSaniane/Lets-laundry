package com.example.fpmobile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.Manifest;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class profile extends AppCompatActivity {

    private Button logOutButton;
    ImageView b1;
    ImageView iv;
    ImageView backbutton;
    private static final int kodekamera= 222;
    private static final int MY_PERMISSIONS_REQUEST_WRITE=223;
    String nmFile;
    File f;
    String photoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        logOutButton = findViewById(R.id.logOutButton);
        b1 = (ImageView) findViewById(R.id.imageView3);
        iv = (ImageView) findViewById(R.id.profile);
        backbutton = (ImageView) findViewById(R.id.buttonBack);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(it,kodekamera);
            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(profile.this, MainActivity.class);
                startActivity(it);
            }
        });

        logOutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(profile.this, "Berhasil Log Out!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(profile.this, Login.class));
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode, data);
        if (resultCode== Activity.RESULT_OK) {
            switch (requestCode) {
                case (kodekamera):
                    try {
                        prosesKamera(data);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    private void prosesKamera(Intent datanya) throws IOException {
        Bitmap bm;
        bm = (Bitmap) datanya.getExtras().get("data");
        iv.setImageBitmap(bm); // Set imageview to image that was
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray(); // convert camera photo to byte array
        // save it in your external storage.
        File dir=  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File output=new File(dir, "profile.png");
        FileOutputStream fo = new FileOutputStream(output);
        fo.write(byteArray);
        fo.flush();
        fo.close();

        Toast.makeText(this,"Update Foto Berhasil!" , Toast.LENGTH_SHORT).show();
    }
}
