package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.servicenovigrad.data.Class.Address;
import com.example.servicenovigrad.data.Class.Branch;
import com.example.servicenovigrad.data.Class.NovLocalTime;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class edit_branch extends AppCompatActivity {

    private Button EditBranch;
    private EditText editTextStreet, editTextStreetNumber, editTexTTown, editTextZipCode,
            editTextStartTime, editTextEndTime, editTextBranchName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_branch);

        EditBranch = findViewById(R.id.btnCreateBranchC);
        editTextStreet = findViewById(R.id.editTextStreet);
        editTextBranchName = findViewById(R.id.editTextBranchName);
        editTextStreetNumber = findViewById(R.id.editTextStreetNumber);
        editTexTTown = findViewById(R.id.editTexTTown);
        editTextZipCode = findViewById(R.id.editTextZipCode);
        editTextStartTime = findViewById(R.id.editTextTime);
        editTextEndTime = findViewById(R.id.editTextTime2);

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("currentEmployee");

        EditBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String currentUserName = dataSnapshot.child("username").getValue(String.class);
                        final DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("users");
                        final DatabaseReference usersdb = ref2.child(currentUserName);

                        usersdb.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot2) {
                                // functions of creating branch
                                if (editTextBranchName.getText().toString().isEmpty()){
                                    editTextBranchName.setError("Please Enter Valid Branch Name");
                                }
                                else if (editTextStreet.getText().toString().isEmpty()){
                                    editTextStreet.setError("Please Enter Valid Street Value");
                                }
                                else if (editTextStreetNumber.getText().toString().isEmpty()){
                                    editTextStreetNumber.setError("Please Enter Valid Street Number");
                                }
                                else if (editTexTTown.getText().toString().isEmpty()){
                                    editTexTTown.setError("Please Enter Valid Town");
                                }
                                else if (editTextZipCode.getText().toString().isEmpty()){
                                    editTextZipCode.setError("Please Enter Valid Zip Code");
                                }
                                else if (editTextStartTime.getText().toString().isEmpty() ||
                                        !(editTextStartTime.getText().toString().contains(":"))) {
                                    editTextStartTime.setError("Please Enter Valid Start Time");
                                }
                                else if (editTextEndTime.getText().toString().isEmpty()||
                                        !(editTextEndTime.getText().toString().contains(":"))) {
                                    editTextEndTime.setError("Please Enter Valid End Time");
                                }
                                else{
                                    String StartTime = editTextStartTime.getText().toString();

                                    String[] convertedArray = StartTime.split(":");
                                    List<Integer> startTimeList = new ArrayList<Integer>();
                                    for (String number : convertedArray) {
                                        startTimeList.add(Integer.parseInt(number.trim()));
                                    }

                                    String EndTime = editTextEndTime.getText().toString();

                                    String[] convertedArray2 = EndTime.split(":");
                                    List<Integer> endTimeList = new ArrayList<Integer>();
                                    for (String number : convertedArray2) {
                                        endTimeList.add(Integer.parseInt(number.trim()));
                                    }

                                    if (startTimeList.get(0) > 24 || startTimeList.get(1) > 60 || startTimeList.size() > 2){
                                        editTextStartTime.setError("Please Enter Valid Start Time xx:xx");
                                    }
                                    else if (endTimeList.get(0) > 24 || endTimeList.get(1) > 60 || endTimeList.size() > 2){
                                        editTextEndTime.setError("Please Enter Valid Start Time xx:xx");
                                    }
                                    else {
                                        String branchName = editTextBranchName.getText().toString();
                                        String Street = editTextStreet.getText().toString();
                                        String StreetNum = editTextStreetNumber.getText().toString();
                                        String Town = editTexTTown.getText().toString();
                                        String ZipCode = editTextZipCode.getText().toString();
                                        NovLocalTime startTime = new NovLocalTime(startTimeList.get(0), startTimeList.get(1));
                                        NovLocalTime endTime = new NovLocalTime(endTimeList.get(0), endTimeList.get(1));

                                        Address branchAddress = new Address(Street, StreetNum, Town, ZipCode);
                                        Branch branchData = new Branch(branchName, branchAddress, startTime, endTime, currentUserName);

                                        usersdb.child("branch").setValue(branchData);

                                        DatabaseReference dbRefBranch = FirebaseDatabase.getInstance().getReference("Branch/" + currentUserName);
                                        dbRefBranch.setValue(branchData);

                                        Toast.makeText(edit_branch.this, "Branch successfully edited",
                                                Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
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
        });
    }
}