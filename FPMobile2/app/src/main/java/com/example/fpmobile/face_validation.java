package com.example.fpmobile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class face_validation extends AppCompatActivity {

    private String currPicPath;
    Button btnAmbilGambar;
    Button validateBtn;
    TextView title;
    ImageView hasilFoto;

    private static final String TAG = "FACE_DETECT_TAG";
    private static final int SCALING_FACTOR = 10;

    private FaceDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_validation);

        btnAmbilGambar = (Button) findViewById(R.id.btnAmbilGambar);
        validateBtn = (Button) findViewById(R.id.validateBtn);
        title = (TextView) findViewById(R.id.title);
        hasilFoto = (ImageView) findViewById(R.id.hasilFoto);

        FaceDetectorOptions realTimeFdo =
                new FaceDetectorOptions.Builder()
                        .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
                        .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                        .build();
        detector = FaceDetection.getClient(realTimeFdo);

        btnAmbilGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fileName = "test_foto";
                File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

                try {
                    File imageFile = File.createTempFile(fileName, ".jpg", storageDir);
                    currPicPath = imageFile.getAbsolutePath();
                    Uri imageUri = FileProvider.getUriForFile(face_validation.this, "com.example.fpmobile.fileprovider", imageFile);

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        validateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BitmapDrawable imgToPass = (BitmapDrawable) hasilFoto.getDrawable();
                Bitmap imgToPassing = imgToPass.getBitmap();

                analyzePhoto(imgToPassing);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(currPicPath);

            hasilFoto.setImageBitmap(bitmap);

            validateBtn.setVisibility(View.VISIBLE);
        }
    }

    private void analyzePhoto(Bitmap bitmap) {
        Bitmap smallerBitmap = Bitmap.createScaledBitmap(
                bitmap,
                bitmap.getWidth()/SCALING_FACTOR,
                bitmap.getHeight()/SCALING_FACTOR,
                false
        );

        InputImage inputImage = InputImage.fromBitmap(smallerBitmap, 0);

        detector.process(inputImage)
                .addOnSuccessListener(new OnSuccessListener<List<Face>>() {
                    @Override
                    public void onSuccess(List<Face> faces) {
                        if (faces.size() >= 1) {
                            Toast.makeText(face_validation.this, "Validasi foto berhasil", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(face_validation.this, sukses.class));
                            finish();
                        } else {
                            Toast.makeText(face_validation.this, "Validasi foto gagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(face_validation.this, "Gagal menganalisis gambar" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}