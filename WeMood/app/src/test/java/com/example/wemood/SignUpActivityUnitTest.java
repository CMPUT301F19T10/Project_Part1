package com.example.wemood;

/**
 * @author Ruochen Lin
 *
 * @version 2.0
 */

import org.junit.Test;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * Class name: SignUpActivityUnitTest
 *
 * Version 2.0
 *
 * Date: November 28, 2019
 *
 * Copyright [2019] [Team10, Fall CMPUT301, University of Alberta]
 */

/**
 * Unit test class for Sign up Activity. All the unit tests are written here.*/
public class SignUpActivityUnitTest {

    /**To get activity object so that we can access method from SignUpActivity*/
    private CollectionReference collectionReference;
    private FirebaseFirestore db;
    SignUpActivity activity = new SignUpActivity();

    /**To test correct format of phone*/

    @Test
    public void TestAddRightPhone(){
        String RightPhone = "5879380321";
        assertTrue(activity.isPhoneValid(RightPhone));
    }

    /**To test wrong format of phone*/
    @Test
    public  void TestAddWrongPhone(){
        String WrongPhone = "134dsad";
        assertFalse(activity.isPhoneValid(WrongPhone));
    }

    /**To test wrong format of password*/
    @Test
    public  void TestAddWrongPassword(){
        String RightPassword = "2313";
        assertFalse(activity.isPasswordValid(RightPassword));
    }

    /**To test Empty password*/

    @Test
    public void TestAddEmptyPassword(){
        String EmptyPassword = "";
        assertFalse(activity.isPasswordValid(EmptyPassword));
    }

}
