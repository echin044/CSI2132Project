package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.servicenovigrad.data.Class.Address;
import com.example.servicenovigrad.data.Class.Branch;
import com.example.servicenovigrad.data.Class.NovApplication;
import com.example.servicenovigrad.data.Class.NovLocalDate;
import com.example.servicenovigrad.data.Class.NovLocalTime;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Service_Application extends AppCompatActivity {
    private Button SubmitForm;
    private EditText editTextFirstName, editTextLastName, editTextDoB, editTextStreet,
            editTextStreetNumber, editTexTTown, editTextZipCode, editTextLicense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service__application);

        SubmitForm = findViewById(R.id.btnSubmitForm);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextDoB = findViewById(R.id.editTextDoB);
        editTextStreet = findViewById(R.id.editTextStreet);
        editTextStreetNumber = findViewById(R.id.editTextStreetNumber);
        editTexTTown = findViewById(R.id.editTexTTown);
        editTextZipCode = findViewById(R.id.editTextZipCode);
        editTextLicense = findViewById(R.id.editTextLicense);

        SubmitForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("currentCustomer");

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String currentUserName = dataSnapshot.child("username").getValue(String.class);

                        final DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("currentServiceId");

                        ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                final String ServiceId = dataSnapshot.getValue(String.class);

                                final DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("currentBranchName");

                                ref3.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        final String BranchId = dataSnapshot.getValue(String.class);


                                        // functions of creating branch
                                        if (editTextFirstName.getText().toString().isEmpty()) {
                                            editTextFirstName.setError("Please Enter Valid First Name");
                                        } else if (editTextLastName.getText().toString().isEmpty()) {
                                            editTextLastName.setError("Please Enter Valid Last Name");
                                        } else if (editTextDoB.getText().toString().isEmpty() ||
                                                !(editTextDoB.getText().toString().contains("/"))) {
                                            editTextDoB.setError("Please Enter Date of Birth YYYY/DD/MM");
                                        } else if (editTextStreet.getText().toString().isEmpty()) {
                                            editTextStreet.setError("Please Enter Valid Street");
                                        } else if (editTextStreetNumber.getText().toString().isEmpty()) {
                                            editTextStreetNumber.setError("Please Enter Valid Street Number");
                                        } else if (editTexTTown.getText().toString().isEmpty()) {
                                            editTexTTown.setError("Please Enter Valid Town");
                                        } else if (editTextZipCode.getText().toString().isEmpty()) {
                                            editTextZipCode.setError("Please Enter Valid Zip Code");
                                        }
//                                        else if (editTextLicense.getText().toString().isEmpty()) {
//                                            editTextLicense.setError("Please Enter Valid License Format");
//                                        }
                                        else {
                                            String datOfBirth = editTextDoB.getText().toString();

                                            String[] convertedArray = datOfBirth.split("/");
                                            List<Integer> dobList = new ArrayList<Integer>();
                                            for (String number : convertedArray) {
                                                dobList.add(Integer.parseInt(number.trim()));
                                            }

                                            if (dobList.get(0) > 2050 || dobList.get(1) > 31 || dobList.get(2) > 12 || dobList.size() != 3) {
                                                editTextDoB.setError("Please enter Valid Date YYYY/DD/MM");
                                            } else {

                                                String firstName = editTextFirstName.getText().toString();
                                                String lastName = editTextLastName.getText().toString();
                                                String streetName = editTextStreet.getText().toString();
                                                String streetNumber = editTextStreetNumber.getText().toString();
                                                String townName = editTexTTown.getText().toString();
                                                String zipCode = editTextZipCode.getText().toString();
                                                String license = editTextLicense.getText().toString();
                                                NovLocalDate DoB = new NovLocalDate(dobList.get(0), dobList.get(1), dobList.get(2));

                                                Address currentAddress = new Address(streetName, streetNumber, townName, zipCode);

                                                DatabaseReference dAdd = FirebaseDatabase.getInstance().getReference("applications/pending/"+BranchId);
                                                String Applicationid = dAdd.push().getKey();

                                                if (editTextLicense.getText().toString().isEmpty()){
                                                    NovApplication currentApplication = new NovApplication(firstName, lastName, currentAddress, DoB, Applicationid ,ServiceId, "No License Type",currentUserName);
                                                    DatabaseReference dFinalAdd = dAdd.child(Applicationid);

                                                    dFinalAdd.setValue(currentApplication);

                                                    Toast.makeText(Service_Application.this, "Sucessfully Apply",
                                                            Toast.LENGTH_SHORT).show();

                                                    Intent intent = new Intent(getApplicationContext(), rating_system.class);
                                                    startActivityForResult (intent,0);
                                                }
                                                else{
                                                    NovApplication currentApplication = new NovApplication(firstName, lastName, currentAddress, DoB, Applicationid ,ServiceId, license, currentUserName);
                                                    DatabaseReference dFinalAdd = dAdd.child(Applicationid);

                                                    dFinalAdd.setValue(currentApplication);

                                                    Toast.makeText(Service_Application.this, "Sucessfully Apply",
                                                            Toast.LENGTH_SHORT).show();

                                                    Intent intent = new Intent(getApplicationContext(), rating_system.class);
                                                    startActivityForResult (intent,0);
                                                }

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


                    @Override
                    public void onCancelled(DatabaseError databaseError){
                    }

                });
            }


        });
    }
}