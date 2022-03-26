package com.example.servicenovigrad.data.Class;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.servicenovigrad.R;
import com.example.servicenovigrad.employee_manage_service;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class EServiceList extends ArrayAdapter<NovService> {
    private Activity context;
    List<NovService> NovService;

    public EServiceList(Activity context, List<NovService> NovService) {
        super(context, R.layout.activity_layout_service_list, NovService);
        this.context = context;
        this.NovService = NovService;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        final View listViewItem = inflater.inflate(R.layout.activity_service_list_e, null, true);

        final TextView textViewServiceName = (TextView) listViewItem.findViewById(R.id.textViewServiceName);
        final TextView textViewOnOff = (TextView) listViewItem.findViewById(R.id.textViewServiceOnOff);

        NovService currentService = NovService.get(position);

        textViewServiceName.setText(String.valueOf(currentService.getServiceName()));

        // the logic to see if the service is on or off
        final String abcservice = currentService.getId();

//        final List<Branch> branchL = currentService.getBranchList();

//        if(branchL!=null){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("currentEmployee");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String currentUserName = dataSnapshot.child("username").getValue(String.class);
                final DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("users");
                final DatabaseReference usersdb = ref2.child(currentUserName);

                usersdb.addListenerForSingleValueEvent(new ValueEventListener() {

                    String result = "inactive";
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot2) {
                        if (dataSnapshot2.hasChild("branch")) {
                            Branch currentBranch = dataSnapshot2.child("branch").getValue(Branch.class);
                            String currentBranchname = currentBranch.getName();

                            final DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("services");
                            final DatabaseReference usersdb3 = ref3.child(abcservice).child("Branch").child(currentUserName);

                            usersdb3.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshotbranchname) {
                                    if (dataSnapshotbranchname.exists()) {
                                        textViewOnOff.setText("Active");
                                    } else {
                                        textViewOnOff.setText("Inactive");
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError dataSnapshotbranchname) {
                                }
                            });

                        } else {

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
        
        return listViewItem;
    }
}
