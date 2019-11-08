package com.example.wemood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FriendRequestList extends ArrayAdapter<String> {

    private Context context;
    private List<String> messages;

    // Constructor to get the context and list of friend request messages
    public FriendRequestList(Context context, ArrayList<String> messages) {
        super(context,0,messages);
        this.messages = messages;
        this.context = context;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.friend_request_content, parent, false);
        }

        // for each message get the textview and find by id's
        String message = messages.get(position);
        TextView MessageView = view.findViewById(R.id.friend_request_message); //get the messageview
        // set the message view
        MessageView.setText(message);

        return view;
    }


    }
