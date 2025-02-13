package com.example.tutorapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tutorapp.databinding.ActivityRatingBinding;

public class Rating extends AppCompatActivity {

    private ActivityRatingBinding binding;
    private RatingBar ratingBar;
    private Button submitButton;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRatingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); // Use binding.getRoot() here

        String ratedUserEmail = getIntent().getStringExtra("RATED_USER_EMAIL");
        dbHandler = new DBHandler(this);

        Log.d("RatingActivity", "Received RATED_USER: " + ratedUserEmail);
        if (ratedUserEmail == null || ratedUserEmail.isEmpty()) {
            Toast.makeText(this, "No user email provided.", Toast.LENGTH_SHORT).show();
        }

        binding.submitButton.setOnClickListener(v -> {
            float rating = binding.ratingBar.getRating();

            if (rating > 0) {
                Log.d("RatingActivity", "Rated User Email: " + ratedUserEmail);
                boolean success = dbHandler.updateRating(ratedUserEmail, rating);

                if (success) {
                    Toast.makeText(this, "Rating updated successfully!", Toast.LENGTH_SHORT).show();
                    finish(); // Close activity after success
                } else {
                    Toast.makeText(this, "Failed to update rating.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please select a rating.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}