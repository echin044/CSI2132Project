package com.example.servicenovigrad.data.Class;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.servicenovigrad.Notifcation;
import com.example.servicenovigrad.R;

import java.util.List;

public class NotifcationList extends ArrayAdapter<String> {
    private Activity context;
    List<String> users;

    public NotifcationList(Activity context, List<String> users) {
        super(context, R.layout.activity_layout_notifcation_list, users);
        this.context = context;
        this.users = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_layout_notifcation_list, null, true);

        TextView TextViewUserName = (TextView) listViewItem.findViewById(R.id.textViewNotifcation);

        String currentNote = users.get(position);
        TextViewUserName.setText(String.valueOf(currentNote));

        return listViewItem;
    }

}
