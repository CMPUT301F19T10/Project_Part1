package com.example.wemood;
/**
 * @author Ziyi Ye
 *
 * @version 1.0
 */

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * Class name: AddMoodActivity
 *
 * Version 1.0
 *
 * Date: November 4, 2019
 *
 * Copyright [2019] [Team10, Fall CMPUT301, University of Alberta]
 */


public class AddMoodActivity extends AppCompatActivity{
    Mood mood;
    private StorageReference Folder;
    ImageView imageView;
    Uri imageUri;
    String downloadUri;
    private static final int PICK_IMAGE = 100;
    String situationString, emotionString;
    private FirebaseFirestore db;
    String name;
    Date currentTime = Calendar.getInstance().getTime();
    private Switch locationSwitch;
    private TextView locationMessage;
    private LocationManager lm;
    private double longitude;
    private double latitude;
    private LocationListener mLocationListener;
    private Context context;

    /**
     * Initialize
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mood);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        name = user.getDisplayName();

        db = FirebaseFirestore.getInstance();
        Folder = FirebaseStorage.getInstance().getReference().child("ImageFolder").child(name);

        final CollectionReference collectionReference = db.collection("Users");

        imageView = findViewById(R.id.imageView);

        locationSwitch = findViewById(R.id.gpsSwitch);
        locationMessage = findViewById(R.id.locationMessage);
        lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });

        setSituationSpinner();
        setEmotionSpinner();

        /**
         * press the Add Mood button
         * put the mood to fireBase
         */
        Button Add = findViewById(R.id.add);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText exp = findViewById(R.id.reason);
                final String explanation = exp.getText().toString();
                EditText titl = findViewById(R.id.title);
                final String title = titl.getText().toString();

                if (containsSpace(title)) {
                    Toast.makeText(AddMoodActivity.this, "Title has no more than 3 words", Toast.LENGTH_SHORT).show();
                } else if (emotionString.equals("")) {
                    Toast.makeText(AddMoodActivity.this, "You must select an emotion!", Toast.LENGTH_SHORT).show();
                } else if (title.equals("")) {
                    Toast.makeText(AddMoodActivity.this, "You must enter a title!", Toast.LENGTH_SHORT).show();
                }
                else {
                    final DocumentReference docRef = db.collection("Users").document(name);
                    db = FirebaseFirestore.getInstance();

                    //add imageUri to the mood if it is not null
                    if (imageUri != null){
                        //add image to storage
                        final StorageReference Image = Folder.child(currentTime.toString());
                        Image.putFile(imageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                if (!task.isSuccessful()) {
                                    throw task.getException();
                                }
                                return Image.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                    downloadUri = task.getResult().toString();
                                    addMood(currentTime, emotionString, explanation, situationString, title, docRef, downloadUri);

                                }
                            }
                        });
                    }else{
                        //if image is null then no need to add uri attribute
                        addMood(currentTime, emotionString, explanation, situationString, title, docRef, downloadUri);
                    }
                    finish();
                }
            }
        });

        locationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if (isChecked){
                    mLocationListener = new LocationListener() {
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
                            if (checkCallingOrSelfPermission(ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                Toast.makeText(AddMoodActivity.this, "Need GPS Permission!", Toast.LENGTH_SHORT).show();
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
                    locationUpdate();
                    try {
                        Geocoder geocoder = new Geocoder(getApplicationContext(),Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(getLatitude(), getLongitude(), 1);
                        String city = addresses.get(0).getLocality();
                        String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
                        if (knownName != null) {
                            locationMessage.setText(city + " - " + knownName);
                        } else {
                            locationMessage.setText(city);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.w("Current location", "Cannot get Address!");
                        System.out.println(getLatitude());
                    }
                } else {
                    locationMessage.setText("None");
                }
            }
        });
    }

    /**
     * add a mood to fireBase
     * @param currentTime
     * @param emotionString
     * @param explanation
     * @param situationString
     * @param title
     * @param docRef
     */
    public void addMood(Date currentTime, String emotionString, String explanation, String situationString, String title, DocumentReference docRef, String downloadUri){
        mood = new Mood(currentTime, emotionString, explanation, situationString, title, longitude,latitude,locationMessage.getText().toString(), downloadUri);
        //put the mood to fireBase
        docRef.collection("MoodList").document(currentTime.toString()).set(mood);

    }
    /**
     * check if title has more than 3 words
     * @param comment
     * @return whether comment has more than 3 words
     */
    public boolean containsSpace(String comment) {
        String Comment = comment.trim();
        int numSpace = 0;
        for (int i =0;i< Comment.length(); i++) {
            if (Character.isWhitespace(Comment.charAt(i))) {
                numSpace++;
            }
        }
        if (numSpace > 2) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * initialize the situation spinner
     * use situation spinner to select an emotion
     */
    private void setSituationSpinner() {
        Spinner situation = findViewById(R.id.situations);
        ArrayAdapter<CharSequence> sitAdapter = ArrayAdapter.createFromResource(this, R.array.situations, android.R.layout.simple_spinner_item);
        sitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        situation.setAdapter(sitAdapter);
        situation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("choose a situation")) {
                    situationString = "";
                } else {
                    situationString = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }


    /**
     * initialize the emotion spinner
     * use emotion spinner to select an emotion
     */
    private void setEmotionSpinner() {
        Spinner emotion = findViewById(R.id.emotionals);
        ArrayAdapter<CharSequence> emoAdapter = ArrayAdapter.createFromResource(this, R.array.emotionals, android.R.layout.simple_spinner_item);
        emoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        emotion.setAdapter(emoAdapter);
        emotion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("choose an emotion")) {
                    emotionString = "";
                }
                else{
                    emotionString = parent.getItemAtPosition(position).toString();
                    // Classify the moods by different mood states
                    // set the background color by different mood states
                    View v = findViewById(R.id.addBackground);
                    ImageView FriendMoodState = v.findViewById(R.id.friendMoodState);
                    switch (emotionString) {
                        case "happy":
                            v.setBackgroundColor(Color.rgb(253,91,91));
                            v.getBackground().setAlpha(200);
                            Bitmap bMap = BitmapFactory.decodeResource(view.getResources(), R.drawable.happy_marker);
                            Bitmap bMapScaled = Bitmap.createScaledBitmap(bMap, 100, 100, true);
                            FriendMoodState.setImageBitmap(bMapScaled);
                            break;
                        case "sad":
                            v.setBackgroundColor(Color.rgb(106,106,240));
                            v.getBackground().setAlpha(200);
                            Bitmap bMap1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.sad_marker);
                            Bitmap bMapScaled1 = Bitmap.createScaledBitmap(bMap1, 100, 100, true);
                            FriendMoodState.setImageBitmap(bMapScaled1);
                            break;
                        case "tired":
                            v.setBackgroundColor(Color.rgb(121,121,121));
                            v.getBackground().setAlpha(200);
                            Bitmap bMap2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.tired_marker);
                            Bitmap bMapScaled2 = Bitmap.createScaledBitmap(bMap2, 100, 100, true);
                            FriendMoodState.setImageBitmap(bMapScaled2);
                            break;
                        case "angry":
                            v.setBackgroundColor(Color.rgb(250,233,90));
                            v.getBackground().setAlpha(200);
                            Bitmap bMap3 = BitmapFactory.decodeResource(view.getResources(), R.drawable.angry_marker);
                            Bitmap bMapScaled3 = Bitmap.createScaledBitmap(bMap3, 100, 100, true);
                            FriendMoodState.setImageBitmap(bMapScaled3);
                            break;
                        case "lonely":
                            v.setBackgroundColor(Color.rgb(255,152,0));
                            v.getBackground().setAlpha(200);
                            Bitmap bMap4 = BitmapFactory.decodeResource(view.getResources(), R.drawable.loney_marker);
                            Bitmap bMapScaled4 = Bitmap.createScaledBitmap(bMap4, 100, 100, true);
                            FriendMoodState.setImageBitmap(bMapScaled4);
                            break;
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    /**
     * get and show the selected image in the imageView
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                imageUri = data.getData();
                imageView.setImageURI(imageUri);

            }
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
     * update location
     */
    public void locationUpdate() {

        // if no permission
        if (checkCallingOrSelfPermission(ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(AddMoodActivity.this, "Need GPS Permission!", Toast.LENGTH_SHORT).show();
            return;
        }

        Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        setLongitude(location.getLongitude());
        setLatitude(location.getLatitude());

        // get the location every 2 seconds
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 8, mLocationListener);
    }

}
