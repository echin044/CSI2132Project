package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class rating_system extends AppCompatActivity {
    RatingBar ratingBar;
    Button btnSumbit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_system);

        ratingBar = findViewById(R.id.ratingBar);
        btnSumbit = findViewById(R.id.btnRate);

        btnSumbit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final double rating = ratingBar.getRating();

                final DatabaseReference refcurrentService = FirebaseDatabase.getInstance().getReference("currentServiceId");

                refcurrentService.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String ServiceId = dataSnapshot.getValue(String.class);

                        final DatabaseReference refCurrentBranch = FirebaseDatabase.getInstance().getReference("currentBranchName");
                        refCurrentBranch.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot2) {
                                final String BranchID = dataSnapshot2.getValue(String.class);

                                final DatabaseReference refCurrentUsers = FirebaseDatabase.getInstance().getReference("currentCustomer/username");
                                refCurrentUsers.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot3) {
                                        final String userName = dataSnapshot3.getValue(String.class);

                                        final DatabaseReference refAverageRating = FirebaseDatabase.getInstance().getReference("Branch/" + BranchID + "/AverageRating");
                                        refAverageRating.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot4) {

                                                DatabaseReference refRating = FirebaseDatabase.getInstance().getReference("Branch/" + BranchID + "/Rating/" + userName + "/rating");

                                                if(dataSnapshot4.exists()) {

                                                    refRating.setValue(rating);

                                                    DatabaseReference refallRating = FirebaseDatabase.getInstance().getReference("Branch/" + BranchID + "/Rating");

                                                    try {
                                                        TimeUnit.SECONDS.sleep(4);

                                                        refallRating.addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot5) {
                                                                double total = 0.0;
                                                                double count = 0.0;
                                                                double average;

                                                                for(DataSnapshot ds: dataSnapshot5.getChildren()) {
                                                                    double DBrating = ds.child("rating").getValue(double.class);
                                                                    total = total + DBrating;
                                                                    count = count + 1;
                                                                }
                                                                average = total / count;
                                                                refAverageRating.setValue(average);
                                                            }
                                                            @Override
                                                            public void onCancelled(DatabaseError databaseError) {
                                                            }
                                                        });


                                                    } catch (InterruptedException e) {
                                                        e.printStackTrace();
                                                    }

                                                    Intent intent = new Intent(getApplicationContext(), WelcomePageUsers.class);
                                                    startActivityForResult (intent,0);

                                                    Toast.makeText(getApplicationContext(), "Thank you for your rating!", Toast.LENGTH_SHORT).show();

                                                }
                                                else{
                                                    refRating.setValue(rating);
                                                    refAverageRating.setValue(rating);

                                                    Toast.makeText(getApplicationContext(), "Thank you for your rating!", Toast.LENGTH_SHORT).show();

                                                    Intent intent = new Intent(getApplicationContext(), WelcomePageUsers.class);
                                                    startActivityForResult (intent,0);
                                                }
                                            };
                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                            }
                                        });
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });


                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });


    }

    public void BacktoMain(View view) {

        //Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), WelcomePageUsers.class);
        startActivityForResult (intent,0);
    }
}