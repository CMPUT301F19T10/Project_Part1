package com.example.wemood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class FriendNameList extends ArrayAdapter<User> {
    private ArrayList<User> friends;
    private Context context;


    public FriendNameList(Context context, ArrayList<User> friends) {
        super(context, 0,friends);
        this.friends = friends;
        this.context = context;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.friend_list, parent, false);
        }

        User friendname = friends.get(position);

        TextView FriendName = view.findViewById(R.id.friend_view);

        FriendName.setText(friendname.getUserName());

        return view;
    }
}