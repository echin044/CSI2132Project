package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.servicenovigrad.data.Class.NotifcationList;
import com.example.servicenovigrad.data.Class.UserHelperClass;
import com.example.servicenovigrad.data.Class.UserList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Notifcation extends AppCompatActivity {

    ListView listViewUsers;

    List<String> notifcationList;
    DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifcation);

        listViewUsers = (ListView) findViewById(R.id.listViewNotifcation);

        notifcationList = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference tempIDPath = FirebaseDatabase.getInstance().getReference("currentCustomer/username");

        tempIDPath.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                final String currentUsers = dataSnapshot.getValue(String.class);

                databaseUsers = FirebaseDatabase.getInstance().getReference("notifcation/" + currentUsers);
                databaseUsers.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        notifcationList.clear();

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            String note = postSnapshot.getValue(String.class);
                            notifcationList.add(note);
                        }

                        NotifcationList usersAdaptor = new NotifcationList(Notifcation.this, notifcationList);
                        listViewUsers.setAdapter(usersAdaptor);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError){

            }

        });


    }

    public void backUser(View view) {

        //Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), WelcomePageUsers.class);
        startActivityForResult (intent,0);
    }
}