package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.servicenovigrad.data.Class.Employee;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class WelcomePageEmployee extends AppCompatActivity {

    private TextView WelcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page_employee);

        WelcomeText = findViewById(R.id.welcomeText);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("currentEmployee");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String currentUserName = dataSnapshot.child("username").getValue(String.class);
                WelcomeText.setText("Welcome "+ currentUserName);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });



    }

    public void ManageBranch(View view) {

        //Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), employee_manage_branch.class);
        startActivityForResult (intent,0);
    }

    public void logOutBtn(View view) {

        //Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult (intent,0);
    }

    public void ManageRequests(View view) {

        //Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), request_view.class);
        startActivityForResult (intent,0);
    }
}