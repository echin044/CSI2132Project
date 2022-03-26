package com.example.servicenovigrad;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.servicenovigrad.data.Class.Branch;
import com.example.servicenovigrad.data.Class.EServiceList;
import com.example.servicenovigrad.data.Class.NovService;
import com.example.servicenovigrad.data.Class.NovServiceList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class employee_manage_service extends AppCompatActivity {

    ListView listViewService;

    List<NovService> serviceList;
    DatabaseReference databaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_manage_service);

        listViewService = (ListView) findViewById(R.id.listViewService);

        serviceList = new ArrayList<>();

        databaseService = FirebaseDatabase.getInstance().getReference("services");

        listViewService.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                NovService NovServices = serviceList.get(i);
                showUpdateDeleteDialog(NovServices.getId(), NovServices.getServiceName());
                return true;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseService.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                serviceList.clear();

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    NovService novServices = postSnapshot.getValue(NovService.class);
                    serviceList.add(novServices);
                }

                EServiceList usersAdaptor = new EServiceList(employee_manage_service.this, serviceList);
                listViewService.setAdapter(usersAdaptor);

            }
            @Override
            public void onCancelled(DatabaseError databaseError){

            }
        });
    }

    private void showUpdateDeleteDialog(final String serviceId, final String ServiceName) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_employee_services_update_dialog, null);
        dialogBuilder.setView(dialogView);

        final Button buttonDelete = (Button) dialogView.findViewById(R.id.btnServiceInactive);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.btnServiceActive);

        dialogBuilder.setTitle(ServiceName);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateService(serviceId, ServiceName);
                b.dismiss();

            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteService(serviceId, ServiceName);
                b.dismiss();
            }
        });
    }

    private void updateService(final String serviceId, final String ServiceName) {

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("currentEmployee");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String currentUserName = dataSnapshot.child("username").getValue(String.class);
                final Branch currentBranch = dataSnapshot.child("branch").getValue(Branch.class);

                final DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("users");
                final DatabaseReference usersdb = ref2.child(currentUserName);

                usersdb.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot2) {
                        Branch currentBranch = dataSnapshot2.child("branch").getValue(Branch.class);
                        String branchName = currentBranch.getName();

                        DatabaseReference dAdd = FirebaseDatabase.getInstance().getReference("services").child(serviceId).child("Branch").child(currentUserName);

                        dAdd.setValue(currentBranch);

                        DatabaseReference dBranch = FirebaseDatabase.getInstance().getReference("Branch").child(currentUserName).child("Service").child(serviceId);

                        NovService currentService = new NovService(ServiceName, serviceId);

                        dBranch.setValue(currentService);

                        Toast.makeText(getApplicationContext(), "Service Active now", Toast.LENGTH_LONG).show();

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

    private boolean deleteService(final String serviceId, final String ServiceName) {

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
                        Branch currentBranch = dataSnapshot2.child("branch").getValue(Branch.class);
                        String branchName = currentBranch.getName();

                        DatabaseReference dRemove = FirebaseDatabase.getInstance().getReference("services").child(serviceId).child("Branch").child(currentUserName);

                        dRemove.removeValue();

                        DatabaseReference dBranch = FirebaseDatabase.getInstance().getReference("Branch").child(currentUserName).child("Service").child(serviceId);

                        dBranch.removeValue();


                        Toast.makeText(getApplicationContext(), "Service Inactive now", Toast.LENGTH_LONG).show();

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

        return true;

    }

    public void backBtn(View view) {

        //Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), employee_manage_branch.class);
        startActivityForResult (intent,0);
    }


}