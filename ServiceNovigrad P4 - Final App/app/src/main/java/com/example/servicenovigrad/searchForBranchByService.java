package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import com.example.servicenovigrad.data.Class.UserHelperClass;
import com.example.servicenovigrad.data.Class.UserList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;
import java.util.ArrayList;
import java.util.List;

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
import android.widget.SearchView;

public class searchForBranchByService extends AppCompatActivity implements SearchView.OnQueryTextListener {
    ListView listViewBranches;
    DatabaseReference databaseBranch;
    List<Branch> branchesOffered;
    SearchView searchView;
    List<Branch> tempBranches;
    String searchKey;
    BranchList branchAdaptor;
    List<NovService> serviceList;
    DatabaseReference databaseService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_branch);
        listViewBranches = (ListView) findViewById(R.id.listViewBranches);
        searchView = (SearchView) findViewById(R.id.searchView);
        branchesOffered = new ArrayList<>();
        tempBranches = new ArrayList<>();
        serviceList = new ArrayList<>();
        databaseBranch = FirebaseDatabase.getInstance().getReference("Branch");
        searchView.setOnQueryTextListener(this);




        listViewBranches.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                NovService NovServices = serviceList.get(i);
                final String serviceId = NovServices.getId();

                DatabaseReference sAdd = FirebaseDatabase.getInstance().getReference("currentServiceId");

                sAdd.setValue(serviceId);
                Branch branch = branchesOffered.get(i);
                final String branchName = branch.getId();

                DatabaseReference dAdd = FirebaseDatabase.getInstance().getReference("currentBranchName");

                dAdd.setValue(branchName);
                Toast.makeText(searchForBranchByService.this, "Branch "+ branch.getName() +" Selected",
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), Service_Application.class);
                startActivityForResult(intent, 0);
                return true;
            }
        });
        databaseBranch.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Branch branch = postSnapshot.getValue(Branch.class);
                    branchesOffered.add(branch);
                }

                branchAdaptor = new BranchList(searchForBranchByService.this, branchesOffered);
                listViewBranches.setAdapter(branchAdaptor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String search) {
        searchKey=search;

        databaseService = FirebaseDatabase.getInstance().getReference("services");

        databaseService.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                serviceList.clear();

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    NovService novServices = postSnapshot.getValue(NovService.class);
                    serviceList.add(novServices);

                }
                NovService service;
                service = new NovService();
                for(int i = 0; i<serviceList.size();i++){
                    if(searchKey.equals(serviceList.get(i).getServiceName())){
                        service = new NovService(serviceList.get(i).getServiceName(),serviceList.get(i).getId());
                        i=serviceList.size();
                    }
                }
                String servID = service.getId();
                System.out.println(servID);
                DatabaseReference refService = FirebaseDatabase.getInstance().getReference("services/" + servID + "/Branch");
                refService.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot3) {
                        for(DataSnapshot ds: dataSnapshot3.getChildren()) {
                            Branch branchName = ds.getValue(Branch.class);
                            tempBranches.add(branchName);
                            branchAdaptor.serviceSearchHelp(tempBranches);

                        }


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


        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    public void backUserApplyBtn(View view) {

        //Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), WelcomePageUsers.class);
        startActivityForResult (intent,0);
    }
    public void refreshSearchBtn(View view){
        Intent intent = new Intent(getApplicationContext(), searchForBranchByService.class);
        startActivityForResult (intent,0);
    }

}