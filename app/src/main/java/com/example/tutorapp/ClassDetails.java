package com.example.tutorapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tutorapp.databinding.ActivityClassDetailsBinding;

import java.util.Locale;

public class ClassDetails extends AppCompatActivity {

    DBHandler dbHandler;
    ActivityClassDetailsBinding binding;
    private Integer classID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_class_details);

        binding = ActivityClassDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String tutorEmail = getIntent().getStringExtra("RATED_USER_EMAIL");
        if (tutorEmail == null || tutorEmail.isEmpty()) {
            Toast.makeText(this, "Tutor email not found.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        dbHandler = new DBHandler(this);
        Cursor cursor = dbHandler.getData(tutorEmail);

        if (cursor != null && cursor.moveToFirst()) {
            String subject = cursor.getString(0);
            Integer price = cursor.getInt(1);
            String time = cursor.getString(2);
            String date = cursor.getString(3);
            String details = cursor.getString(4);
            classID = cursor.getInt(5);


            TextView classSubject = findViewById(R.id.classSubject);
            TextView classPrice = findViewById(R.id.classPrice);
            TextView classTime = findViewById(R.id.classTime);
            TextView classDate = findViewById(R.id.classDate);
            TextView classDetails = findViewById(R.id.classDetails);


            classSubject.setText(subject);
            classPrice.setText(String.valueOf(price));
            classTime.setText(time);
            classDate.setText(date);
            classDetails.setText(details);


            cursor.close();
        } else {
            Toast.makeText(this, "Class not found", Toast.LENGTH_SHORT).show();
        }


        binding.rateTutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassDetails.this,Rating.class);
                intent.putExtra("RATED_USER", tutorEmail);
                startActivity(intent);
            }
        });

//        String finalEmail = email;
        binding.Enroll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(ClassDetails.this, "Success!", Toast.LENGTH_SHORT).show();
            }
        });

        binding.seeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (classID != null) {
                    Intent intent = new Intent(ClassDetails.this, MapLocation.class);
                    Log.d("ClassDetails", "CLASS_ID: " + classID);
                    intent.putExtra("CLASS_ID", classID); // Pass class ID
                    startActivity(intent);
                } else {
                    Toast.makeText(ClassDetails.this, "Class ID not available", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}