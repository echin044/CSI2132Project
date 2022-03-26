package com.example.servicenovigrad;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.servicenovigrad.data.Class.Branch;
import com.example.servicenovigrad.data.Class.BranchList;
import com.example.servicenovigrad.data.Class.NovServiceList;
import com.example.servicenovigrad.data.Class.NovService;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class ServiceFromSearch extends AppCompatActivity {

    ListView listViewServices;

    List<NovService> serviceList;
    DatabaseReference databaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_from_search);
        listViewServices = (ListView) findViewById(R.id.listViewServices);

        serviceList = new ArrayList<>();

        databaseService = FirebaseDatabase.getInstance().getReference("services");

        listViewServices.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                NovService NovServices = serviceList.get(i);
                final String serviceId = NovServices.getId();

                DatabaseReference dAdd = FirebaseDatabase.getInstance().getReference("currentServiceId");

                dAdd.setValue(serviceId);
                Toast.makeText(ServiceFromSearch.this, NovServices.getServiceName() +" Selected",
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), Service_Application.class);
                startActivityForResult (intent,0);
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference tempIDPath = FirebaseDatabase.getInstance().getReference("currentBranchName");

        tempIDPath.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String currentBranch = dataSnapshot.getValue(String.class);

                databaseService = FirebaseDatabase.getInstance().getReference("Branch/"+ currentBranch +"/Service");

                databaseService.addValueEventListener(new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot){
                        serviceList.clear();

                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                            NovService Serviceabc = postSnapshot.getValue(NovService.class);
                            serviceList.add(Serviceabc);
                        }

                        NovServiceList usersAdaptor = new NovServiceList(ServiceFromSearch.this, serviceList);
                        listViewServices.setAdapter(usersAdaptor);

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError){

                    }
                });

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }





    public void backApplyBtn(View view) {

        //Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), searchForBranch.class);
        startActivityForResult (intent,0);
    }

}