package com.example.tutorapp;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tutorapp.databinding.ActivityMainBinding;


public class LandscapeFragment extends Fragment {
    public LandscapeFragment() {
        // Required empty public constructor
    }

    ActivityMainBinding binding;
    DBHandler dbHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(inflater, container, false);

        dbHandler = new DBHandler(requireContext());

        binding.loginbutton.setOnClickListener(v -> {
            String email = binding.email.getText().toString();
            String password = binding.password.getText().toString();

            SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserSession", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("DRIVER_EMAIL", email);
            editor.apply();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getActivity(), "Please fill out everything.", Toast.LENGTH_SHORT).show();
            } else {
                boolean checkCredentials = dbHandler.checkEmailPassword(email, password);

                if (checkCredentials) {
                    Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT).show();
                    String userType = dbHandler.checkUserType(email, password);

                    Intent intent = "driver".equals(userType) ?
                            new Intent(getActivity(), Tutor.class) :
                            new Intent(getActivity(), Student.class);

                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "Password incorrect!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.redirect1.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Register.class);
            startActivity(intent);
        });

        return binding.getRoot();
    }
}