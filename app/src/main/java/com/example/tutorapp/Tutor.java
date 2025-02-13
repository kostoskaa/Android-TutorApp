package com.example.tutorapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tutorapp.databinding.ActivityTutorBinding;

import java.util.ArrayList;

public class Tutor extends AppCompatActivity {

    ActivityTutorBinding binding;
    RecyclerView mRecyclerView;
    private ArrayList<String> email,name;


    private ArrayList<Float> rating;
    MyAdapter1 mAdapter;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tutor);
        dbHandler = new DBHandler(this);
        binding = ActivityTutorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.AddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(Tutor.this, AddClass.class);
                startActivity(intent);
            }
        });

        email = new ArrayList<>();
        name = new ArrayList<>();
        rating = new ArrayList<>();

        //сетирање на RecyclerView контејнерот
        mRecyclerView = findViewById(R.id.list1);

        // сетирање на кориснички дефиниран адаптер myAdapter (посебна класа)
        mAdapter = new MyAdapter1(this, email,name,rating);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //прикачување на адаптерот на RecyclerView
        mRecyclerView.setAdapter(mAdapter);

        // и default animator (без анимации)
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        displayData();

    }
    private void displayData() {
        Cursor cursor = dbHandler.getData1();
        if(cursor.getCount()==0){
            Toast.makeText(Tutor.this,"There are no students to display.",Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                email.add(cursor.getString(0));
                name.add(cursor.getString(1));
                Float currentRating = cursor.isNull(2) ? 0.0f : cursor.getFloat(2);
                rating.add(currentRating);

            }
        }
    }
}