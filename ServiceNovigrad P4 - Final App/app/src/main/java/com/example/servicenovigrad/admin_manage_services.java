package com.example.servicenovigrad;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.servicenovigrad.data.Class.Address;
import com.example.servicenovigrad.data.Class.Branch;
import com.example.servicenovigrad.data.Class.NovService;
import com.example.servicenovigrad.data.Class.NovServiceList;
import com.example.servicenovigrad.data.Class.UserHelperClass;
import com.example.servicenovigrad.data.Class.UserList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class admin_manage_services extends AppCompatActivity {

    ListView listViewService;

    List<NovService> serviceList;
    DatabaseReference databaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_services);

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

                NovServiceList usersAdaptor = new NovServiceList(admin_manage_services.this, serviceList);
                listViewService.setAdapter(usersAdaptor);

            }
            @Override
            public void onCancelled(DatabaseError databaseError){

            }
        });
    }

    private void showUpdateDeleteDialog(final String serviceId, String ServiceName) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_services_update_dialog, null);
        dialogBuilder.setView(dialogView);

        final Button buttonDelete = (Button) dialogView.findViewById(R.id.btnDeleteService);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.btnEditService);
        final EditText editTextServiceNameInput = (EditText) dialogView.findViewById(R.id.editTextServiceNameInput);
        final Switch switchExpiration = (Switch) dialogView.findViewById(R.id.switchExpiration);
        final EditText editTextConstraint = (EditText) dialogView.findViewById(R.id.editTextLicense);

        dialogBuilder.setTitle(ServiceName);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextServiceNameInput.getText().toString().trim();

                if (!TextUtils.isEmpty(name)) {

                    List<Branch> branchList = null;

                    boolean expiration = (switchExpiration.isChecked() == true);

                    if (editTextConstraint.getText().toString().isEmpty()) {
                        updateService(name, branchList, expiration, serviceId);
                        b.dismiss();
                    } else {
                        String LisenseString = editTextConstraint.getText().toString();

                        String[] convertedArray = LisenseString.split(",");
                        List<String> convertedList = new ArrayList<String>();
                        for (String eachString : convertedArray) {
                            convertedList.add(eachString.trim());
                        }
                        updateServiceWithConstriant(name, branchList, expiration, serviceId, convertedList);
                        b.dismiss();
                    }
                }
                else {
                    editTextServiceNameInput.setError("Input a service name");
            }
        }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteService(serviceId);
                b.dismiss();
            }
        });
    }

    private void updateService(String serviceName, List<Branch> branchList, boolean expiration, String id) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("services").child(id);

        NovService currentservice = new NovService(serviceName, branchList, expiration, id);
        dR.setValue(currentservice);

        Toast.makeText(getApplicationContext(), "Service Updated", Toast.LENGTH_LONG).show();

    }

    private void updateServiceWithConstriant(String serviceName, List<Branch> branchList, boolean expiration, String id, List<String> convertedList) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("services").child(id);

        NovService currentservice = new NovService(serviceName, branchList, expiration, id, convertedList);
        dR.setValue(currentservice);

        Toast.makeText(getApplicationContext(), "Service Updated", Toast.LENGTH_LONG).show();

    }

    private boolean deleteService(String serviceId) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("services").child(serviceId);

        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Service Deleted", Toast.LENGTH_LONG).show();
        return true;
    }


    public void createNewServiceBtn(View view) {

        //Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), create_new_service.class);
        startActivityForResult (intent,0);
    }

    public void backAdminBtn(View view) {

        //Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), WelcomePageAdmin.class);
        startActivityForResult (intent,0);
    }
}