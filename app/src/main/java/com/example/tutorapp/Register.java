package com.example.tutorapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tutorapp.databinding.ActivityRegisterBinding;

public class Register extends AppCompatActivity {

    ActivityRegisterBinding binding;
    DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EdgeToEdge.enable(this);
        dbHandler = new DBHandler(this);

        binding.registerbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String name = binding.name1.getText().toString();
                String email = binding.email1.getText().toString();
                String password = binding.password1.getText().toString();
                String confirmPass = binding.confirmpassword.getText().toString();

                int selectedTypeId = binding.rgroup.getCheckedRadioButtonId();
                RadioButton selectedUserType = findViewById(selectedTypeId);
                String userType = (selectedUserType != null) ? selectedUserType.getText().toString() : null;


                if(name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPass.isEmpty() || userType.isEmpty())
                    Toast.makeText(Register.this,"Please fill all the fields.", Toast.LENGTH_SHORT).show();
                else{
                    if(password.equals(confirmPass)){
                        Boolean checkUserEmail = dbHandler.checkEmail(email);

                        if(!checkUserEmail){
                            Boolean insert = dbHandler.insertData(name, email, password, userType,null );

                            if(insert){
                                Toast.makeText(Register.this,"Success!",Toast.LENGTH_SHORT).show();
                                Intent intent = "Tutor".equals(userType) ?
                                        new Intent(Register.this, Tutor.class) :
                                        new Intent(Register.this, Student.class);

                                startActivity(intent);
                            } else {
                                Toast.makeText(Register.this,"  Failed!",Toast.LENGTH_SHORT).show();
                            }
                        } else{
                            Toast.makeText(Register.this,"User already exists!",Toast.LENGTH_SHORT).show();
                        }
                    }  else{
                        Toast.makeText(Register.this,"Password incorrect.",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        binding.redirect2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Register.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}