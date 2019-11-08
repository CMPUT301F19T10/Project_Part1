package com.example.wemood.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.wemood.FriendRequestList;
import com.example.wemood.R;

import java.util.ArrayList;

public class FriendRequestMessageFragment extends Fragment {

    // Declare the variables so that you will be able to reference it later.
    ListView messageList;
    ArrayAdapter<String> messageAdapter;
    ArrayList<String> dataMessageList;


    public FriendRequestMessageFragment(){
        // Empty constructor
    }

    public static FriendRequestMessageFragment newInstance() {
        FriendRequestMessageFragment fragment = new FriendRequestMessageFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friend_request_message, container, false);

        messageList = view.findViewById(R.id.friend_request_message_list);
        // click massage item to jump a Friend Request Dialog
        messageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String requestmessage = dataMessageList.get(i);
                new RequestFragmentDialog(view).show(getFragmentManager(), "Friend Request");
            }
        });
        dataMessageList = new ArrayList<>();
        // insert test data to datalist
        String message1 = "Ken wants to be your friend";
        String message2 = "Wong wants to be your friend";
        String message3 = "Lee wants to be your friend";
        dataMessageList.add(message1);
        dataMessageList.add(message2);
        dataMessageList.add(message3);
        messageAdapter = new FriendRequestList(getContext(), dataMessageList);
        messageList.setAdapter(messageAdapter);
        return view;
    }



//    @Override
//    // Decline the friend request
//    public void DeclineRequest(String message){ messageAdapter.remove(message);};
//
//
//    @Override
//    // Accept the friend request
//    public void AcceceptRequest(String message){ messageAdapter.remove(message); };
}