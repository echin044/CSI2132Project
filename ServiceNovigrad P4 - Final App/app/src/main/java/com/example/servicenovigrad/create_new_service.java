package com.example.servicenovigrad;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.servicenovigrad.data.Class.Branch;
import com.example.servicenovigrad.data.Class.NovService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class create_new_service extends AppCompatActivity {

    private EditText editTextServiceName, licenseType;
    private Switch expirationSwitch;
    private Button btnCreateService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_service);

        btnCreateService = findViewById(R.id.btnCreateService);
        editTextServiceName = findViewById(R.id.editTextServiceName);
        expirationSwitch = findViewById(R.id.ExpirationSwitch);
        licenseType = findViewById(R.id.editTextLicense);

        btnCreateService.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("services");
                List<Branch> branchList = null;
                String id = ref.push().getKey();
                boolean expiration = (expirationSwitch.isChecked() == true);

                if (editTextServiceName.getText().toString().isEmpty()) {
                    editTextServiceName.setError("Please Enter a Service Name");
                }
                else {
                    if (licenseType.getText().toString().isEmpty()) {
                        final NovService service = new NovService(editTextServiceName.getText().toString(), branchList, expiration, id);

                        DatabaseReference serviceData = ref.child(id);

                        ValueEventListener eventListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (!dataSnapshot.exists()) {
                                    ref.child(service.getId()).setValue(service);
                                    Toast.makeText(create_new_service.this, "Service successfully created",
                                            Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else {
                                    Toast.makeText(create_new_service.this, "Service is created already",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        };

                        serviceData.addListenerForSingleValueEvent(eventListener);

                    }
                    else{
                        String LisenseString = licenseType.getText().toString();

                        String[] convertedArray = LisenseString.split(",");
                        List<String> convertedList = new ArrayList<String>();
                        for (String eachString : convertedArray) {
                            convertedList.add(eachString.trim());
                        }


                        final NovService service = new NovService(editTextServiceName.getText().toString(), branchList, expiration, id, convertedList);

                        DatabaseReference serviceData = ref.child(id);

                        ValueEventListener eventListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (!dataSnapshot.exists()) {
                                    ref.child(service.getId()).setValue(service);

                                    Toast.makeText(create_new_service.this, "Service successfully created",
                                            Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else {
                                    Toast.makeText(create_new_service.this, "Service is created already",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        };

                        serviceData.addListenerForSingleValueEvent(eventListener);

                    }



                }



            };



    });
    }
}