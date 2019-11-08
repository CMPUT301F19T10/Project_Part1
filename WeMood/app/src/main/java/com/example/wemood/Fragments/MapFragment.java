package com.example.wemood.Fragments;

/**
 * Class name: MapFragment
 *
 * version 1.0
 *
 * Date: November 3, 2019
 *
 * Copyright [2019] [Team10, Fall CMPUT301, University of Alberta]
 */

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.wemood.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * @author ChengZhang Dong
 *
 * @version 1.0
 */
public class MapFragment extends Fragment implements View.OnClickListener{

    private SupportMapFragment mapFragment;
    private Button myMap;
    private Button friendsMap;

    private LocationManager lm;
    private double longitude;
    private double latitude;

    /**
     * Required empty public constructor
     */
    public MapFragment() {}

    /**
     * constructor
     * @return map fragment
     *
     */
    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    /**
     * initialize the location manager and update the location
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationUpdate();
    }

    /**
     * Inflate the layout for this fragment
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
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frg);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                try{
                    googleMap.setMyLocationEnabled(true);
                }catch (Exception e){

                }

                googleMap.clear();

                // set the camera to the current location
                CameraPosition camera = CameraPosition.builder()
                        .target(new LatLng(getLatitude(), getLongitude()))
                        .zoom(10)
                        .bearing(0)
                        .tilt(45)
                        .build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera),null);

                // add a mock mood marker
                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(37.4219999, -122.0862462))
                        .title("2019-11-3 10:44")
                        .snippet("happy : yooooo!"));
            }
        });
        return rootView;
    }

    /**
     * set the feature of 2 buttons
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // tap this button will show all moods of the current user on the map
            case R.id.myMap:
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        googleMap.clear();
                        googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(37.4219999, -122.0862462))
                                .title("2019-11-3 10:44")
                                .snippet("happy : yooooo!"));
                    }
                });
                break;
            // tap this button will show all friends' moods of the current user on the map
            case R.id.friendsMap:
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        googleMap.clear();

                        googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(37.4629101, -122.2449094))
                                .title("2019-11-3 1:22")
                                .snippet("sad : noooo!"));

                        googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(37.3092293, -122.1136845))
                                .title("2019-11-6 11:44")
                                .snippet("tired : ohhhhh!"));

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
            if ( getActivity().checkCallingOrSelfPermission(ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
     * update location
     */
    public void onResume() {
        super.onResume();
        locationUpdate();
    }

    /**
     * stop updating location
     */
    public void onPause() {
        super.onPause();
        lm.removeUpdates(mLocationListener);
    }

    /**
     * update location
     */
    public void locationUpdate() {

        // if no permission
        if ( getActivity().checkCallingOrSelfPermission(ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getActivity(), "Need GPS Permission!", Toast.LENGTH_SHORT).show();
            return;
        }

        Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        setLongitude(location.getLongitude());
        setLatitude(location.getLatitude());

        // get the location every 2 seconds
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 8,mLocationListener);
    }

    /**
     * get current longitude
     * @return current longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * set current longitude
     * @param longitude
     * current location information
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * get current latitude
     * @return current latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * set current latitude
     * @param latitude
     * current location information
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}