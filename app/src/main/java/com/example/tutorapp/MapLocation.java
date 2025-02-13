package com.example.tutorapp;

import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapLocation extends AppCompatActivity implements OnMapReadyCallback  {

    DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_map_location);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync( this);
        }
        dbHandler = new DBHandler(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        int classId = getIntent().getIntExtra("CLASS_ID", -1);
        Log.d("MapLocation", "Received CLASS_ID: " + classId);

        if (classId == -1) {
            Toast.makeText(this, "Invalid Class ID", Toast.LENGTH_SHORT).show();
            return;
        }


        Cursor cursor = dbHandler.getClassById(classId);
        if (cursor != null && cursor.moveToFirst()) {
            int addressIndex = cursor.getColumnIndex("address");
            if (addressIndex == -1) {
                Toast.makeText(this, "Address column not found", Toast.LENGTH_SHORT).show();
                return;
            }

            String address = cursor.getString(addressIndex);

            if (address != null && !address.isEmpty()) {
                LatLng location = getLatLngFromAddress(address);

                if (location != null) {
                    googleMap.addMarker(new MarkerOptions().position(location).title("Class Location"));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
                } else {
                    Toast.makeText(this, "Unable to find location for address", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Address not available", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Class not found", Toast.LENGTH_SHORT).show();
        }

    }
    private LatLng getLatLngFromAddress(String address) {
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> addresses = geocoder.getFromLocationName(address, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address location = addresses.get(0);
                return new LatLng(location.getLatitude(), location.getLongitude());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
