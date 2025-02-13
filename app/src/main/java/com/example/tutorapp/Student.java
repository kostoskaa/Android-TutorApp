package com.example.tutorapp;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Student extends AppCompatActivity {

    RecyclerView mRecyclerView;
    private ArrayList<String> email,subject;
    private ArrayList<Integer> price;

    MyAdapter2 mAdapter;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student);
        dbHandler = new DBHandler(this);

        email = new ArrayList<>();
        subject = new ArrayList<>();
        price = new ArrayList<>();

        mRecyclerView = findViewById(R.id.list);

        mAdapter = new MyAdapter2(this, email,subject, price);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        displayData();

    }
    private void displayData() {
        Cursor cursor = dbHandler.getDataForClasses();
        if(cursor.getCount()==0){
            Toast.makeText(Student.this,"There are no classes to display.",Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                email.add(cursor.getString(0));
                subject.add(cursor.getString(1));
                price.add(cursor.getInt(2));
            }
        }
    }
}