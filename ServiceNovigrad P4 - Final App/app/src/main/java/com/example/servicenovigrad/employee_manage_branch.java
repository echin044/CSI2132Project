package com.example.servicenovigrad;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.servicenovigrad.data.Class.Address;
import com.example.servicenovigrad.data.Class.Branch;
import com.example.servicenovigrad.data.Class.NovService;
import com.example.servicenovigrad.data.Class.NovServiceList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class employee_manage_branch extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_manage_branch);
    }



    public void ManageService(View view) {

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("currentEmployee");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String currentUserName = dataSnapshot.child("username").getValue(String.class);
                final DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("users");
                final DatabaseReference usersdb = ref2.child(currentUserName);

                usersdb.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot2) {
                        if (dataSnapshot2.hasChild("branch")) {
                            //Application Context and Activity
                            Intent intent = new Intent(getApplicationContext(), employee_manage_service.class);
                            startActivityForResult (intent,0);

                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Branch doesn't exist", Toast.LENGTH_LONG).show();
                        }
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

    public void CreateBranch(View view) {

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("currentEmployee");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String currentUserName = dataSnapshot.child("username").getValue(String.class);
                final DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("users");
                final DatabaseReference usersdb = ref2.child(currentUserName);

                usersdb.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot2) {
                        if (dataSnapshot2.hasChild("branch")) {
                            Toast.makeText(getApplicationContext(), "Branch already exist", Toast.LENGTH_LONG).show();
                        }
                        else{
                            //Application Context and Activity
                            Intent intent = new Intent(getApplicationContext(), create_branch.class);
                            startActivityForResult (intent,0);
                        }
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

    public void EditBranch(View view) {

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("currentEmployee");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String currentUserName = dataSnapshot.child("username").getValue(String.class);
                final DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("users");
                final DatabaseReference usersdb = ref2.child(currentUserName);

                usersdb.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot2) {
                        if (dataSnapshot2.hasChild("branch")) {
                            //Application Context and Activity
                            Intent intent = new Intent(getApplicationContext(), edit_branch.class);
                            startActivityForResult (intent,0);

                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Branch doesn't exist", Toast.LENGTH_LONG).show();
                        }
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

    public void backEmployeeBtn(View view) {

        //Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), WelcomePageEmployee.class);
        startActivityForResult (intent,0);
    }

}