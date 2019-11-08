package com.example.wemood;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import static junit.framework.TestCase.assertTrue;


/**
 * Unit test class for LogSignInActivity. All the unit tests are written here.*/
public class LogSignInActivityUnitTest {

    private CollectionReference collectionReference;
    private FirebaseFirestore db;
    LogSignInActivity activity = new LogSignInActivity();


    /**To test correct format of both username and password*/
    public void TestValidateForm(){
        String RightEmail = "123@qq.com";
        String RightPassword = "5879380321";
        assertTrue(activity.validateForm(RightEmail,RightPassword));
    }

}