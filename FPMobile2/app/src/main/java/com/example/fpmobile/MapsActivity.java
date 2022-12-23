package com.example.fpmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fpmobile.databinding.ActivityMapsBinding;
import com.example.fpmobile.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    EditText cariTempat;
    private Button lokasiBtn, cariBtn;
    private FusedLocationProviderClient locationProviderClient;
    private GoogleMap mMap;
    private EditText textInput;
    private Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        textInput=findViewById(R.id.textInput);
        search=findViewById(R.id.search);

        locationProviderClient = LocationServices.getFusedLocationProviderClient(MapsActivity.this);
        getLocation();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void goCari(Double dbllat, Double dbllong) {
        OkHttpClient client = new OkHttpClient();
//        StringBuilder stringBuilder =new StringBuilder("https://serpapi.com/search.json?");
//        stringBuilder.append("location=" + )
        String searchString = textInput.getText().toString();

        String url = "https://serpapi.com/search.json?" +
                "engine=google_maps&" +
                "q=" + searchString +
                "&ll=@-7.2785823,112.7989005,14z&type=search&" +
                "key=" + "11d391dd12e9591be7a925558bf53919a333f3c02817cea78559a766be4af991&hl=id";
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.isSuccessful()){
                    String myResponse = response.body().string();
                    MapsActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            testing(myResponse);
                        }
                    });
                }
            }
        });
    }
    private void testing(String myResponse) {
//        textResult.setText(myResponse);
//        StringBuilder output = new StringBuilder();
        String output = "";
        try {
            JSONObject jsonObject = new JSONObject(myResponse);
            JSONArray data = jsonObject.getJSONArray("local_results");
            mMap.clear();
            for (int i = 0; i < 10; i++) {
                JSONObject row = data.getJSONObject(i);
                JSONObject coordinates = row.getJSONObject("gps_coordinates");
                String title = row.getString("title");
                String lat = coordinates.getString("latitude");
                String lng = coordinates.getString("longitude");
                output = "Nama warkop: " +title + " ,latitude: " + lat + " ,longitude: " + lng;
                gotoPeta(Double.parseDouble(lat),Double.parseDouble(lng),14.0f,title);
            }

            Toast.makeText(MapsActivity.this, "OK", Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(MapsActivity.this, "NO", Toast.LENGTH_SHORT).show();

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 10){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getApplicationContext(),"Izin Lokasi Tidak Aktif !",Toast.LENGTH_SHORT).show();
            } else {
                getLocation();
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Get Permission
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},10);
            return;
        } else {
            locationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                // Get location
                @Override
                public void onSuccess(Location location) {
                    if(location == null) {
                        Toast.makeText(getApplicationContext(),"Lokasi Tidak Aktif !",Toast.LENGTH_SHORT).show();
                    } else {
                        Double dbllat = location.getLatitude();
                        Double dbllong = location.getLongitude();


//                        gotoPeta(dbllat, dbllong, 16.0f, "my location");

                        search.setOnClickListener(view -> {
                            goCari(dbllat, dbllong);
                        });
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void gotoPeta(Double lat, Double lng, float dblzoom, String title) {
        LatLng Lokasibaru = new LatLng(lat,lng);
        mMap.addMarker(new MarkerOptions(). position(Lokasibaru).title("" +title));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Lokasibaru, dblzoom));
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-7.276513, 112.791692);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in ITS Surabaya"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,20));
    }


}