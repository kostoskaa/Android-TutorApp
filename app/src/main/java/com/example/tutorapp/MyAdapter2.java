package com.example.tutorapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.ViewHolder> {

    private Context mContext;
    private ArrayList email, subject, price;

    private ArrayList rating;

    public MyAdapter2(Context mContext, ArrayList email, ArrayList subject, ArrayList price) {
        this.mContext = mContext;
        this.email = email;
        this.subject = subject;
        this.price = price;
    }

    @NonNull
    @Override
    public MyAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_my_adapter2, parent, false);
        return new MyAdapter2.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter2.ViewHolder holder, int position) {

        holder.email.setText(email.get(position) != null ? email.get(position).toString() : "No Email");
        holder.subject.setText(subject.get(position) != null ? subject.get(position).toString() : "No Subject");
        holder.price.setText(price.get(position) != null ? price.get(position).toString() : "No Price");

        holder.chooseButton.setOnClickListener(v -> {
            if (email.get(position) != null) {
                String tutorEmail = email.get(position).toString();
                Log.d("MyAdapter2", "Tutor email: " + tutorEmail);
                Intent intent = new Intent(mContext, ClassDetails.class);
                intent.putExtra("RATED_USER_EMAIL", tutorEmail);
                mContext.startActivity(intent);
            } else {
                Log.e("MyAdapter2", "Email is null for position: " + position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return email.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView email,subject,price;

        public Button chooseButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            email =  itemView.findViewById(R.id.TutorEmail);
            subject = itemView.findViewById(R.id.TutorSubject);
            price = itemView.findViewById(R.id.TutorPrice);
            chooseButton = itemView.findViewById(R.id.ChooseClass);
        }

    }
}