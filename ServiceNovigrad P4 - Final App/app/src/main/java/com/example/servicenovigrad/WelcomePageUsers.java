package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WelcomePageUsers extends AppCompatActivity {

    private TextView WelcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page_users);

        WelcomeText = findViewById(R.id.welcomeTextC);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("currentCustomer");

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
    public void UserServices(View view){
        Intent intent = new Intent(getApplicationContext(), Apply_User.class);
        startActivityForResult (intent,0);
    }

    public void Notifcation(View view){
        Intent intent = new Intent(getApplicationContext(), Notifcation.class);
        startActivityForResult (intent,0);
    }

    public void logOutBtn(View view) {

        //Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult (intent,0);
    }

    public void searchForBranchBtn(View view){
        Intent intent = new Intent (getApplicationContext(),searchForBranch.class);
        startActivityForResult(intent,0);
    }

    public void searchForHr(View view){
        Intent intent = new Intent (getApplicationContext(),searchForBranchByHours.class);
        startActivityForResult(intent,0);
    }

    public void searchForService(View view){
        Intent intent = new Intent (getApplicationContext(),searchForBranchByService.class);
        startActivityForResult(intent,0);
    }
}