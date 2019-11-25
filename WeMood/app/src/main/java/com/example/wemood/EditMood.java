package com.example.wemood;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Date;

public class EditMood extends AppCompatActivity {
    int index;
    Uri imageUri;
    private FirebaseFirestore db;
    Mood mood;
    private StorageReference Folder;
    ImageView imageView;
    private CollectionReference collectionReference;
    private FirebaseAuth mAuth;
    String situationString, emotionString;
    Date date;
    private static final int PICK_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mood);
        setSituationSpinner();
        setEmotionSpinner();



        Intent intent = getIntent();
        index = intent.getIntExtra("index", 0);
        db = FirebaseFirestore.getInstance();
        mood = (Mood) intent.getSerializableExtra("mood");
        date = mood.getDatetime();

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();

        final String userName = user.getDisplayName();

        //open imageview
        Folder = FirebaseStorage.getInstance().getReference().child("ImageFolder").child(userName);
        imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });

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
                                mood = document.toObject(Mood.class);
                                final Date d = mood.getDatetime();
                                if (d.equals(date)) {
                                    //get text
                                    EditText reason = findViewById(R.id.reason);
                                    reason.setText(mood.getComment());
                                    EditText title = findViewById(R.id.title);
                                    title.setText(mood.getExplanation());

                                    //get image if it exists
                                    FirebaseStorage storage = FirebaseStorage.getInstance();
                                    final StorageReference image = storage.getReference().child("ImageFolder/" + userName + "/" + mood.getDatetime().toString());
                                    if (image != null){
                                        image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(final Uri uri) {
                                                Picasso.get().load(uri).into(imageView);
                                            }
                                        });
                                    }


                                    Button Edit = findViewById(R.id.add);
                                    Edit.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            //set
                                            if (imageUri != null){
                                                StorageReference Image = Folder.child(mood.getDatetime().toString());
                                                Image.putFile(imageUri);

                                            }

                                            EditText r= findViewById(R.id.reason);
                                            String newReason = r.getText().toString();
                                            EditText t= findViewById(R.id.title);
                                            String newTitle = t.getText().toString();
                                            if (containsSpace(newTitle)){
                                                Toast.makeText(EditMood.this, "title has no more than 3 words", Toast.LENGTH_SHORT).show();
                                            } else {
                                                final DocumentReference docRef = db.collection("Users").document(userName);
                                                mood.setComment(newReason);
                                                mood.setExplanation(newTitle);
                                                mood.setEmotionalState(emotionString);
                                                mood.setSocialSituation(situationString);
                                                db.collection("MoodList").document(mood.getDatetime().toString())
                                                        .delete()
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                System.out.println("delete success");
                                                                docRef.collection("MoodList").document(d.toString()).set(mood);
                                                            }
                                                        });

                                                finish();
                                            }

                                        }
                                    });

                                }
                            }
                        }
                    }});
    }

    private void setSituationSpinner() {
        Spinner situation = findViewById(R.id.situations);
        ArrayAdapter<CharSequence> sitAdapter = ArrayAdapter.createFromResource(this, R.array.situations, android.R.layout.simple_spinner_item);
        sitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        situation.setAdapter(sitAdapter);
        situation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("choose a situation")){
                    situationString = "";
                }else{
                    situationString = parent.getItemAtPosition(position).toString();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    /**
     * check if title has more than 3 words
     * @param comment
     * @return whether comment has more than 3 words
     */
    public boolean containsSpace(String comment){
        String Comment = comment.trim();
        int numSpace = 0;
        for(int i =0;i< Comment.length(); i++){
            if (Character.isWhitespace(Comment.charAt(i))){
                numSpace++;
            }
        }
        if (numSpace > 2){
            return true;
        }else{
            return false;
        }
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
                if (parent.getItemAtPosition(position).equals("choose an emotion")){
                    emotionString = "";
                }
                else{
                    emotionString = parent.getItemAtPosition(position).toString();
                    switch (emotionString){
                        case "happy":
                            view.setBackgroundColor(Color.rgb(253,91,91));
                            view.getBackground().setAlpha(200);
                            //Bitmap bMap = BitmapFactory.decodeResource(view.getResources(), R.drawable.happy_marker);
                            //Bitmap bMapScaled = Bitmap.createScaledBitmap(bMap, 100, 100, true);
                            //FriendMoodState.setImageBitmap(bMapScaled);
                            break;
                        case "sad":
                            view.setBackgroundColor(Color.rgb(106,106,240));
                            view.getBackground().setAlpha(200);
                            //Bitmap bMap1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.sad_marker);
                            //Bitmap bMapScaled1 = Bitmap.createScaledBitmap(bMap1, 100, 100, true);
                            //FriendMoodState.setImageBitmap(bMapScaled1);
                            break;
                        case "tired":
                            view.setBackgroundColor(Color.rgb(121,121,121));
                            view.getBackground().setAlpha(200);
                            //Bitmap bMap2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.tired_marker);
                            //Bitmap bMapScaled2 = Bitmap.createScaledBitmap(bMap2, 100, 100, true);
                            //FriendMoodState.setImageBitmap(bMapScaled2);
                            break;
                        case "angry":
                            view.setBackgroundColor(Color.rgb(250,233,90));
                            view.getBackground().setAlpha(200);
                            //Bitmap bMap3 = BitmapFactory.decodeResource(view.getResources(), R.drawable.angry_marker);
                            //Bitmap bMapScaled3 = Bitmap.createScaledBitmap(bMap3, 100, 100, true);
                            //FriendMoodState.setImageBitmap(bMapScaled3);
                            break;
                        case "lonely":
                            view.setBackgroundColor(Color.rgb(255,152,0));
                            view.getBackground().setAlpha(200);
                            //Bitmap bMap4 = BitmapFactory.decodeResource(view.getResources(), R.drawable.loney_marker);
                            //Bitmap bMapScaled4 = Bitmap.createScaledBitmap(bMap4, 100, 100, true);
                            //FriendMoodState.setImageBitmap(bMapScaled4);
                            break;
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == PICK_IMAGE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                imageUri = data.getData();
                imageView.setImageURI(imageUri);

            }
        }
    }
}
