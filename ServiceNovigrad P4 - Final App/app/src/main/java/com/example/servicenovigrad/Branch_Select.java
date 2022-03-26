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

public class Branch_Select extends AppCompatActivity {

    ListView listViewBranches;
    List<Branch> branchesOffered;

    DatabaseReference databaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch__select);

        listViewBranches = (ListView) findViewById(R.id.listViewBranches);

        branchesOffered = new ArrayList<>();

        listViewBranches.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Branch branch = branchesOffered.get(i);

                DatabaseReference dbbb = FirebaseDatabase.getInstance().getReference("currentBranchName");

                dbbb.setValue(branch.getId());

                Toast.makeText(Branch_Select.this, "Branch "+ branch.getName() +" Selected",
                        Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), Service_Application.class);
                startActivityForResult(intent, 0);

                return true;
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference tempIDPath = FirebaseDatabase.getInstance().getReference("currentServiceId");

        tempIDPath.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String currentService = dataSnapshot.getValue(String.class);

                databaseService = FirebaseDatabase.getInstance().getReference("services/"+ currentService +"/Branch");

                databaseService.addValueEventListener(new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot){
                        branchesOffered.clear();

                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                            Branch Branchabc = postSnapshot.getValue(Branch.class);
                            branchesOffered.add(Branchabc);
                        }

                        BranchList usersAdaptor = new BranchList(Branch_Select.this, branchesOffered);
                        listViewBranches.setAdapter(usersAdaptor);

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


    public void backUserApplyBtn(View view) {

        //Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), Apply_User.class);
        startActivityForResult (intent,0);
    }
}