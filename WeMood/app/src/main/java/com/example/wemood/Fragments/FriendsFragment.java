package com.example.wemood.Fragments;
/**
 * @author Alpha Hou
 *
 * @version 1.0
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.wemood.FriendNameList;
import com.example.wemood.R;
import com.example.wemood.User;

import java.util.ArrayList;

/**
 * Class name: FriendsFragment
 *
 * Version 1.0
 *
 * Date: November 7, 2019
 *
 * Copyright [2019] [Team10, Fall CMPUT301, University of Alberta]
 */

/**
 * Will connect to the firebase later
 */
public class FriendsFragment extends Fragment {

    ListView friendList;
    ArrayAdapter<User> friendAdapter;
    ArrayList<User> frienddataList;

    /**
     * Constructor
     */
    public FriendsFragment() {
        // Required empty public constructor
    }

    /**
     * Constructor
     * @return
     */
    public static FriendsFragment newInstance() {
        FriendsFragment fragment = new FriendsFragment();
        return fragment;
    }

    /**
     * Initialize the FriendsFragment
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Create the view of the FriendsFragment. Will display a list of friends' username and will be
     * able to search a user by his/her username and click the "Search" button. Will be able to see the
     * friend's information by clicking the username on the list.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_friends, container, false);
//        TextView contentTv = rootView.findViewById(R.id.content_tv);
//        contentTv.setText(mContentText);

        friendList = (ListView) rootView.findViewById(R.id.friend_list_content);
        frienddataList = new ArrayList<>();

        frienddataList.add(new User("Alpha@ualberta.ca","Alpha","123-222-2222","AlphaUserID"));
        frienddataList.add(new User("Zoey@ualberta.ca","Zoey","123-111-1111","ZoeyUserID"));
        frienddataList.add(new User("Boyuan@gmail.com","Boyuan","122-111-1122","BoyuanUserID"));
        frienddataList.add(new User("Anna@gmail.com","Anna","222-222-1234","AnnaUserID"));

        friendAdapter = new FriendNameList(getActivity(), frienddataList);
        friendList.setAdapter(friendAdapter);

        final Button searchButton = rootView.findViewById(R.id.friend_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FriendsNotExistFragment friendsNotExitsFragment = new FriendsNotExistFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, friendsNotExitsFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        friendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FriendsExistFragment friendsExitsFragment = new FriendsExistFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, friendsExitsFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return rootView;
    }
}