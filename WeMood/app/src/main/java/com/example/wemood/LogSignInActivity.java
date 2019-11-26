package com.example.wemood;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.opencensus.tags.Tag;


/**
 *   This is a activity that allow user to sign in or jump to
 *   sign-up activity to sign up. In this activity we check whether
 *   user has enter the correct email address and password and check
 *   it in our app database by pressing sign in button, which is
 *   firebase and also in order to protect user privacy we set up
 *   a hiding password function. If user-input email address and
 *   password are match we switch to main activity w    hich is homepage
 *   of this app.If sign-up button is pressed then jump to sign-up activity.
 *
 */

public class LogSignInActivity extends AppCompatActivity implements
        View.OnClickListener{


    final String TAG = "LogSignInActivity";
    private EditText addEmail;
    private EditText addPassWord;
    private CheckBox checkbox;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_sign_in);

        // Views
        addEmail = findViewById(R.id.add_email);
        addPassWord = findViewById(R.id.add_user_password);

        // Buttons
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_up_button).setOnClickListener(this);

        checkbox = findViewById(R.id.checkbox);


        addPassWord.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        /**
         * This method is to set up a listener to see if check box
         * has been pressed. If checkbox is pressed then we hide password
         * and change its text from "hide password" to "show password"
         * and then if checkbox is pressed again then we show the password
         * in EditText*/

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked) {
                    addPassWord.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    checkbox.setText("Hide Password");
                    addPassWord.setSelection(addPassWord.getText().length());
                } else {
                    addPassWord.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    //addPassWord.setInputType(129);
                    //checkbox.setText("Show Password");
                    addPassWord.setSelection(addPassWord.getText().length());
                }
            }
        });

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
    }


    /**
     * In this method, we check which button is pressed
     * by looking at its ID. if pressed button is sign in
     * then we take user input both email and password
     * to check in our firestore. If sign-up button is detect
     * we switch to sign-up activity
     * @param  v*/


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.sign_in_button) {
            signIn(addEmail.getText().toString(), addPassWord.getText().toString());

        } else if (i == R.id.sign_up_button) {
            Intent intent = new Intent(LogSignInActivity.this,SignUpActivity.class);
            startActivity(intent);
        }
    }


    /**
     * What this method do is to take two argument: password and email
     * then judge its validation if input is valid, then we log in with
     * our database. Succeed then log in and show "Success" ,else we show
     * toast message : "Login failed"
     * @param email
     * @param password*/


    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm(addEmail.getText().toString(),addPassWord.getText().toString())) {
            return;
        }

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(LogSignInActivity.this, "Success!",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LogSignInActivity.this,MainActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LogSignInActivity.this, "Login failed:(",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // [END sign_in_with_email]
    }

    /**
     * The purpose of this method is to see if email and password
     * EditText are empty, if yes show error message on right
     * hand side accordingly
     * @return Return boolean value*/

    public boolean validateForm(String email,String password) {
        boolean valid = true;

        //String email = addEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            addEmail.setError("Required.");
            valid = false;
        } else {
            addEmail.setError(null);
        }

        //String password = addPassWord.getText().toString();
        if (TextUtils.isEmpty(password)) {
            addPassWord.setError("Required.");
            valid = false;
        } else {
            addPassWord.setError(null);
        }

        return valid;
    }

}
