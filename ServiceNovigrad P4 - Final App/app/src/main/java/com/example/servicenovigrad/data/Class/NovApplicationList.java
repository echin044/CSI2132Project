package com.example.servicenovigrad.data.Class;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.servicenovigrad.Branch_Select;
import com.example.servicenovigrad.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class NovApplicationList extends ArrayAdapter<NovApplication> {
        private Activity context;
        List<NovApplication> users;

        public NovApplicationList(Activity context, List<NovApplication> users) {
                super(context, R.layout.activity_layout_employee_reviewing, users);
                this.context = context;
                this.users = users;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = context.getLayoutInflater();
                View listViewItem = inflater.inflate(R.layout.activity_layout_employee_reviewing, null, true);

                TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewFName);
                TextView textViewAddress = (TextView) listViewItem.findViewById(R.id.textViewAddressAppl);
                TextView textViewDoB = (TextView) listViewItem.findViewById(R.id.textViewdob);
                final TextView textViewServiceName = (TextView) listViewItem.findViewById(R.id.TextViewServiceApp);
                TextView textViewLiscence = (TextView) listViewItem.findViewById(R.id.textViewLiscence);

                NovApplication novApp = users.get(position);

                textViewName.setText(novApp.getFirstName() + " " + novApp.getLastName());

                Address currentAddress = novApp.getAddress();
                String combineAddress = currentAddress.getNumber() + " " + currentAddress.getStreet()+ " " + currentAddress.getTown()+ " " + currentAddress.getZipCode();

                textViewAddress.setText(combineAddress);
                textViewLiscence.setText(novApp.getLicense());

                NovLocalDate dateOfBirth = new NovLocalDate(novApp.getDoB());
                String stringDOB = dateOfBirth.getYear() + "/" + dateOfBirth.getDate() + "/" + dateOfBirth.getMonth();

                textViewDoB.setText(stringDOB);

                final String currentService = novApp.getServiceid();

                DatabaseReference tempIDPath = FirebaseDatabase.getInstance().getReference("services/"+currentService);

                tempIDPath.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                                String serviceName = dataSnapshot.child("serviceName").getValue(String.class);

                                textViewServiceName.setText(serviceName);
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError){

                        }
                });


                return listViewItem;
        }
}
