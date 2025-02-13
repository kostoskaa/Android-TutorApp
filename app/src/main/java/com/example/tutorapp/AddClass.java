package com.example.tutorapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tutorapp.databinding.ActivityAddClassBinding;

public class AddClass extends AppCompatActivity {

    DBHandler dbHandler;
    ActivityAddClassBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_class);
        dbHandler = new DBHandler(this);
        binding = ActivityAddClassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.submitclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                String driverEmail = sharedPreferences.getString("TUTOR_EMAIL", null);

                // Validate tutor email
                if (driverEmail == null || driverEmail.isEmpty()) {
                    Toast.makeText(AddClass.this, "Tutor email not found. Please log in again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String subject = binding.subject.getText().toString();
                String details = binding.details.getText().toString();
                String pricestr = binding.price.getText().toString();
                String address = binding.address.getText().toString();
                Integer price = Integer.parseInt(pricestr);
                String time = binding.time.getText().toString();
                String date = binding.date.getText().toString();
                if (subject.isEmpty() || details.isEmpty() || pricestr.isEmpty() || time.isEmpty() || date.isEmpty() || details.isEmpty() || address.isEmpty()){
                    Toast.makeText(AddClass.this, "Please fill everything!", Toast.LENGTH_SHORT).show();
                }
                else{
                    boolean insert = dbHandler.insertClass(subject, driverEmail,null,price, time, date, details, address);
                    if (insert){
                        Toast.makeText(AddClass.this, "Success", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(AddClass.this, "Fail", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
}