package com.example.servicenovigrad;

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

public class searchForBranchByHours extends AppCompatActivity implements SearchView.OnQueryTextListener {
    ListView listViewBranches;
    DatabaseReference databaseBranch;
    List<Branch> branchesOffered;
    SearchView searchView;
    List<Branch> tempBranches;
    String searchKey;
    BranchList branchAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_branch);
        listViewBranches = (ListView) findViewById(R.id.listViewBranches);
        searchView = (SearchView) findViewById(R.id.searchView);
        branchesOffered = new ArrayList<>();
        tempBranches = new ArrayList<>();
        databaseBranch = FirebaseDatabase.getInstance().getReference("Branch");
        searchView.setOnQueryTextListener(this);
        listViewBranches.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Branch branch = branchesOffered.get(i);
                final String branchName = branch.getId();

                DatabaseReference dAdd = FirebaseDatabase.getInstance().getReference("currentBranchName");

                dAdd.setValue(branchName);
                Toast.makeText(searchForBranchByHours.this, "Branch "+ branch.getName() +" Selected",
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), ServiceFromSearch.class);
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

                branchAdaptor = new BranchList(searchForBranchByHours.this, branchesOffered);
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
        branchAdaptor.hoursSearchHelp(searchKey);
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