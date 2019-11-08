package com.example.wemood;
/**
 * @author Boyuan Dong
 *
 * @version 1.0
 */

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
/**
 * Class name: FriendRequestList
 *
 * Version 1.0
 *
 * Date: November 7, 2019
 *
 * Copyright [2019] [Team10, Fall CMPUT301, University of Alberta]
 */
public class FriendRequestList extends ArrayAdapter<String> {

    private Context context;
    private List<String> messages;

    /**
     * Constructor to get the context and list of friend request messages.
     * @param context
     * @param messages
     */
    public FriendRequestList(Context context, ArrayList<String> messages) {
        super(context,0,messages);
        this.messages = messages;
        this.context = context;
    }

    /**
     * Will get the view of the FriendRequestList. Will display a list of messages of friend requests need user
     * to decline or accept.
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
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
