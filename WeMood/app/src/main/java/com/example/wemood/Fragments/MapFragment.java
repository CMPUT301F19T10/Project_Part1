package com.example.wemood.Fragments;

/**
 * Class name: MapFragment
 *
 * version 3.0
 *
 * Date: November 25, 2019
 *
 * Copyright [2019] [Team10, Fall CMPUT301, University of Alberta]
 */

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.wemood.Mood;
import com.example.wemood.R;
import com.example.wemood.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * @author ChengZhang Dong
 *
 * @version 3.0
 */
public class MapFragment extends Fragment implements View.OnClickListener {

    private SupportMapFragment mapFragment;
    private Button myMap;
    private Button friendsMap;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private CollectionReference collectionReference;
    private String userName;
    private LocationManager lm;
    private double longitude;
    private double latitude;
    private int statusNumber;

    /**
     * Required empty public constructor
     */
    public MapFragment() {
    }

    /**
     * constructor
     *
     * @return map fragment
     */
    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    /**
     * initialize the location manager and update the location
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.statusNumber = 0;
        // Initialize FireBase Auth
        mAuth = FirebaseAuth.getInstance();
        // Initialize Database
        db = FirebaseFirestore.getInstance();
        user = mAuth.getCurrentUser();
        userName = user.getDisplayName();
        user = mAuth.getCurrentUser();
        lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationUpdate();
    }

    /**
     * Inflate the layout for this fragment
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return the view of this fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        myMap = rootView.findViewById(R.id.myMap);
        friendsMap = rootView.findViewById(R.id.friendsMap);

        // set 2 buttons
        myMap.setOnClickListener(this);
        friendsMap.setOnClickListener(this);

        // create the map
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                try {
                    googleMap.setMyLocationEnabled(true);
                } catch (Exception e) {

                }

                googleMap.clear();

                // set the camera to the current location
                CameraPosition camera = CameraPosition.builder()
                        .target(new LatLng(getLatitude(), getLongitude()))
                        .zoom(10)
                        .bearing(0)
                        .tilt(45)
                        .build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera), null);
                setMoodMarker(googleMap,userName);
            }
        });
        return rootView;
    }

    /**
     * set the feature of 2 buttons
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // tap this button will show all moods of the current user on the map
            case R.id.myMap:
                this.statusNumber = 1;
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        googleMap.clear();
                        setMoodMarker(googleMap,userName);
                    }
                });
                break;
            // tap this button will show all friends' moods of the current user on the map
            case R.id.friendsMap:
                this.statusNumber = 2;
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        googleMap.clear();
                        setFriendsMapMarker(googleMap);
                    }
                });
                break;
        }
    }

    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            // update location if it is changed
            setLongitude(location.getLongitude());
            setLatitude(location.getLatitude());
        }

        // need override this method
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        // update location if GPS is allowed
        @Override
        public void onProviderEnabled(String provider) {
            if (getActivity().checkCallingOrSelfPermission(ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Need GPS Permission!", Toast.LENGTH_SHORT).show();
                return;
            }
            setLongitude(lm.getLastKnownLocation(provider).getLongitude());
            setLatitude(lm.getLastKnownLocation(provider).getLatitude());
        }

        // need override this method
        @Override
        public void onProviderDisabled(String provider) {
        }
    };

    /**
     * update location and markers depending on the statusNumber
     */
    @Override
    public void onResume() {
        super.onResume();
        locationUpdate();
        switch (this.statusNumber){
            // My mood markers
            case 0:
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        googleMap.clear();
                        setMoodMarker(googleMap,userName);
                    }
                });
                // My mood markers
            case 1:
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        googleMap.clear();
                        setMoodMarker(googleMap,userName);
                    }
                });
                // Friend mood markers
            case 2:
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        googleMap.clear();
                        setFriendsMapMarker(googleMap);
                    }
                });
        }
    }

    /**
     * stop updating location
     */
    @Override
    public void onPause() {
        super.onPause();
        lm.removeUpdates(mLocationListener);
    }

    /**
     * update location
     */
    public void locationUpdate() {

        // if no permission
        if (getActivity().checkCallingOrSelfPermission(ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getActivity(), "Need GPS Permission!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(location == null){
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,mLocationListener);
            }
            setLongitude(location.getLongitude());
            setLatitude(location.getLatitude());
            // get the location every 2 seconds
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 8, mLocationListener);
        }catch (Exception e){
        }

    }

    /**
     * get current longitude
     *
     * @return current longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * set current longitude
     *
     * @param longitude current location information
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * get current latitude
     *
     * @return current latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * set current latitude
     *
     * @param latitude current location information
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * set mood markers depending on mood types
     * @param googleMap
     * @param userName
     */
    public void setMoodMarker(final GoogleMap googleMap,final String userName) {
        collectionReference = db.collection("Users")
                .document(userName)
                .collection("MoodList");
        collectionReference
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Mood mood = document.toObject(Mood.class);
                                if (mood.getLatitude() != 0 && mood.getLongitude() != 0){
                                    switch (mood.getEmotionalState()) {
                                        case "angry":
                                            googleMap.addMarker(new MarkerOptions()
                                                    .position(new LatLng(mood.getLatitude(),mood.getLongitude()))
                                                    .title(mood.getDatetime().toString())
                                                    .snippet(userName + ": " + mood.getComment())
                                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.angry_marker)));
                                            break;
                                        case "sad":
                                            googleMap.addMarker(new MarkerOptions()
                                                    .position(new LatLng(mood.getLatitude(),mood.getLongitude()))
                                                    .title(mood.getDatetime().toString())
                                                    .snippet(userName + ": " + mood.getComment())
                                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.sad_marker)));
                                            break;
                                        case "happy":
                                            googleMap.addMarker(new MarkerOptions()
                                                    .position(new LatLng(mood.getLatitude(),mood.getLongitude()))
                                                    .title(mood.getDatetime().toString())
                                                    .snippet(userName + ": " + mood.getComment())
                                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.happy_marker)));
                                            break;
                                        case "tired":
                                            googleMap.addMarker(new MarkerOptions()
                                                    .position(new LatLng(mood.getLatitude(),mood.getLongitude()))
                                                    .title(mood.getDatetime().toString())
                                                    .snippet(userName + ": " + mood.getComment())
                                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.tired_marker)));
                                            break;
                                        case "lonely":
                                            googleMap.addMarker(new MarkerOptions()
                                                    .position(new LatLng(mood.getLatitude(),mood.getLongitude()))
                                                    .title(mood.getDatetime().toString())
                                                    .snippet(userName + ": " + mood.getComment())
                                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.lonely_marker)));
                                            break;
                                    }
                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    /**
     * set friends' mood markers
     * @param googleMap
     */
    public void setFriendsMapMarker(final GoogleMap googleMap) {
        collectionReference = db.collection("Users");
        collectionReference.document(userName).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class);
                        ArrayList<String> friendList = user.getFriendList();
                        if (!friendList.isEmpty()){
                            for (String friend:friendList){
                                collectionReference.document(friend).get()
                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                User friend = documentSnapshot.toObject(User.class);
                                                String friendName = friend.getUserName();
                                                setMoodMarker(googleMap,friendName);
                                            }
                                        });
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
                Log.d(ContentValues.TAG, e.toString());
            }
        });
    }
}
