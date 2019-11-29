package com.example.wemood;

/**
 * @author Ruochen Lin
 *
 * @version 2.0
 */

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import static junit.framework.TestCase.assertTrue;

/**
 * Class name: LogSignInActivityUnitTest
 *
 * Version 2.0
 *
 * Date: November 28, 2019
 *
 * Copyright [2019] [Team10, Fall CMPUT301, University of Alberta]
 */

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