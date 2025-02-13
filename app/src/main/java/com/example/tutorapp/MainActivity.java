package com.example.tutorapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.tutorapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EdgeToEdge.enable(this); // This can stay after setting the content view

        dbHandler = new DBHandler(this);

        binding.loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.email.getText().toString();
                String password = binding.password.getText().toString();

                if (email.isEmpty() || password.isEmpty())
                    Toast.makeText(MainActivity.this, "Please fill all the inputs.", Toast.LENGTH_SHORT).show();
                else {
                    Boolean checkCredentials = dbHandler.checkEmailPassword(email, password);

                    if (checkCredentials) {
                        Toast.makeText(MainActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                        Log.d("LoginRedirect", "Button clicked");
                        String userType = dbHandler.checkUserType(email, password);

                        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("TUTOR_EMAIL", email); // Save tutor's email
                        editor.apply();

                        Intent intent = "Tutor".equals(userType) ?
                                new Intent(MainActivity.this, Tutor.class) :
                                new Intent(MainActivity.this, Student.class);

                        intent.putExtra("userEmail", email);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Password incorrect!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        binding.redirect1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LoginRedirect", "Button clicked");
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });
    }
}