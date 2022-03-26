package com.example.servicenovigrad;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.servicenovigrad.data.Class.Branch;
import com.example.servicenovigrad.data.Class.BranchList;
import com.example.servicenovigrad.data.Class.NovApplication;
import com.example.servicenovigrad.data.Class.NovApplicationList;
import com.example.servicenovigrad.data.Class.NovService;
import com.example.servicenovigrad.data.Class.UserHelperClass;
import com.example.servicenovigrad.data.Class.UserList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class request_view extends AppCompatActivity {

    ListView listViewUsers;

    List<NovApplication> usersList;
    DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_view);

        listViewUsers = (ListView) findViewById(R.id.listViewRequests);

        usersList = new ArrayList<>();

        listViewUsers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                NovApplication NovApp = usersList.get(i);
                showUpdateDeleteDialog(NovApp.getUsersid(), NovApp.getServiceid(), NovApp.getApplicationID());
                return true;
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference tempIDPath = FirebaseDatabase.getInstance().getReference("currentEmployee/username");

        tempIDPath.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String currentService = dataSnapshot.getValue(String.class);

                databaseUsers = FirebaseDatabase.getInstance().getReference("applications/pending/" + currentService);

                databaseUsers.addValueEventListener(new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot2){
                        usersList.clear();

                        for (DataSnapshot dataSnapshotxyz: dataSnapshot2.getChildren()){
                            NovApplication Applica = dataSnapshotxyz.getValue(NovApplication.class);
                            usersList.add(Applica);
                        }

                        NovApplicationList usersAdaptor = new NovApplicationList(request_view.this, usersList);
                        listViewUsers.setAdapter(usersAdaptor);

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

    private void showUpdateDeleteDialog(final String Usersid, final String serviceId, final String ApplicationID) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_employee_reviewing_dialog, null);
        dialogBuilder.setView(dialogView);

        final Button buttonApprove = (Button) dialogView.findViewById(R.id.btnServiceApprove);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.btnServiceReject);


        dialogBuilder.setTitle(Usersid);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                approveUsers(Usersid ,serviceId, ApplicationID);
                b.dismiss();

            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rejectUsers(Usersid, serviceId, ApplicationID);
                b.dismiss();
            }
        });
    }

    private boolean approveUsers(final String Usersid,final String serviceID, final String ApplicationID) {

        DatabaseReference tempIDPath = FirebaseDatabase.getInstance().getReference("currentEmployee/username");

        tempIDPath.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                final String currentEmployee = dataSnapshot.getValue(String.class);

                final DatabaseReference dbRefPending = FirebaseDatabase.getInstance().getReference("applications/pending/"
                        + currentEmployee + "/" + ApplicationID);

                dbRefPending.addValueEventListener(new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot2){

                        NovApplication currentApplication = dataSnapshot2.getValue(NovApplication.class);

                        DatabaseReference dbRefApprove = FirebaseDatabase.getInstance().getReference("applications/approved/" + currentEmployee);
                        String approveid = dbRefApprove.push().getKey();
                        dbRefApprove = dbRefApprove.child(approveid);

                        dbRefApprove.setValue(currentApplication);

                        try {
                            TimeUnit.SECONDS.sleep(4);
                            dbRefPending.removeValue();

                            DatabaseReference servicePath = FirebaseDatabase.getInstance().getReference("services/"+serviceID+"/serviceName");
                            servicePath.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot3) {
                                    String serviceName = dataSnapshot3.getValue(String.class);

                                    String Message = "Your "+ serviceName +" application is being approved!";

                                    DatabaseReference dbRefNotifcation = FirebaseDatabase.getInstance().getReference("notifcation/" + Usersid + "/" +
                                            ApplicationID);

                                    dbRefNotifcation.setValue(Message);

                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError){

                                }
                            });

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

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


        Toast.makeText(getApplicationContext(), "Application Approved", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean rejectUsers(final String Usersid, final String serviceID, final String ApplicationID) {

        DatabaseReference tempIDPath = FirebaseDatabase.getInstance().getReference("currentEmployee/username");

        tempIDPath.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                final String currentEmployee = dataSnapshot.getValue(String.class);

                final DatabaseReference dbRefPending = FirebaseDatabase.getInstance().getReference("applications/pending/"
                        + currentEmployee + "/" + ApplicationID);

                dbRefPending.addValueEventListener(new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot2){
                        NovApplication currentApplication = dataSnapshot2.getValue(NovApplication.class);

                        DatabaseReference dbRefApprove = FirebaseDatabase.getInstance().getReference("applications/rejected/" + currentEmployee);
                        String approveid = dbRefApprove.push().getKey();
                        dbRefApprove = dbRefApprove.child(approveid);

                        dbRefApprove.setValue(currentApplication);

                        DatabaseReference servicePath = FirebaseDatabase.getInstance().getReference("services/"+serviceID+"/serviceName");
                        servicePath.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot3) {
                                String serviceName = dataSnapshot3.getValue(String.class);

                                String Message = "Your "+ serviceName +" application is being rejected!";

                                DatabaseReference dbRefNotifcation = FirebaseDatabase.getInstance().getReference("notifcation/" + Usersid + "/" +
                                        ApplicationID);

                                dbRefNotifcation.setValue(Message);
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError){

                            }
                        });


                        try {
                            TimeUnit.SECONDS.sleep(4);
                            dbRefPending.removeValue();

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


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

        Toast.makeText(getApplicationContext(), "Application Rejected", Toast.LENGTH_LONG).show();
        return true;
    }


    public void BackRequestBtn(View view) {

        //Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), WelcomePageEmployee.class);
        startActivityForResult (intent,0);
    }


}