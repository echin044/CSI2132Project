package com.example.servicenovigrad.data.Class;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.servicenovigrad.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class BranchList extends ArrayAdapter<Branch> {
    private Activity context;
    List<Branch> users;

    public BranchList(Activity context, List<Branch> users) {
        super(context, R.layout.activity_layout_user_service_avail_list, users);
        this.context = context;
        this.users = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_layout_user_service_avail_list, null, true);

        TextView textViewbranchName = (TextView) listViewItem.findViewById(R.id.textViewBranchNameUs);
        TextView textViewbranchaddress = (TextView) listViewItem.findViewById(R.id.TextViewAddressUser);
        TextView textViewbranchStartTime = (TextView) listViewItem.findViewById(R.id.TextViewUStarttime);
        TextView textViewbranchEndTime = (TextView) listViewItem.findViewById(R.id.textViewBEndTime);
        final TextView textViewRating = (TextView) listViewItem.findViewById(R.id.textViewRating);


        Branch currentUser = users.get(position);

        textViewbranchName.setText(String.valueOf(currentUser.getName()));

        Address currentAddress = currentUser.getAddress();
        String combineAddresss = currentAddress.getNumber() + " " + currentAddress.getStreet()+ " " + currentAddress.getTown()+ " " + currentAddress.getZipCode();

        textViewbranchaddress.setText(combineAddresss);

        NovLocalTime startTime = currentUser.getStartTime();
        String combineStartTime = Integer.toString(startTime.getHour()) + ":" + Integer.toString(startTime.getMinutes());

        NovLocalTime endTime = currentUser.getEndTime();
        String combineEndTime = Integer.toString(endTime.getHour()) + ":" + Integer.toString(endTime.getMinutes());

        String currentid = currentUser.getId();

        textViewbranchStartTime.setText("Start time: "+ combineStartTime);
        textViewbranchEndTime.setText("End time: "+ combineEndTime);

        DatabaseReference tempIDPath = FirebaseDatabase.getInstance().getReference("Branch/" + currentid + "/AverageRating");

        if (currentUser.getAverageRating() == null){

            tempIDPath.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        double rating = dataSnapshot.getValue(double.class);
                        String ratingintex = Double.toString(rating);
                        textViewRating.setText("Rating: " + ratingintex);
                    }
                    else{
                        textViewRating.setText("No Rating Yet");
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError){
                }
            });

        }
        else{
            textViewRating.setText("Rating: " + currentUser.getAverageRating());
        }



        return listViewItem;
    }
    public void addressSearchHelp(String search){
        boolean exists = false;
        for(int j=0; j< users.size();j++){
            if(users.get(j).address.toString().equals(search)){
                exists = true;
            }
        }
        if(exists==false){
            return;
        }

        for(int i=0;i< users.size(); i++){
            if(!users.get(i).address.toString().equals(search)){
                users.remove(i);
            }
        }


        notifyDataSetChanged();

    }
    public void hoursSearchHelp(String search){
        boolean exists = false;
        String[] time = search.split(":");
        String hour = time[0];
        String minutes = time[1];
        int hourInt = Integer.parseInt(hour);
        int minuteInt = Integer.parseInt(minutes);
        System.out.println(hourInt);
        System.out.println(minuteInt);
        for(int j =0; j<users.size();j++){
            if(!(users.get(j).getStartTime().getHour()<=hourInt && users.get(j).getStartTime().getMinutes()<=minuteInt && users.get(j).getEndTime().getHour()>=hourInt && users.get(j).getEndTime().getMinutes()>=minuteInt )){
                users.remove(j);
            }

        }

        notifyDataSetChanged();
    }
    public void serviceSearchHelp(List<Branch> branchesOffering){

        boolean exists;
        users.clear();
        for(int i =0;i<branchesOffering.size();i++){
            users.add(branchesOffering.get(i));
        }
        notifyDataSetChanged();

    }
}
