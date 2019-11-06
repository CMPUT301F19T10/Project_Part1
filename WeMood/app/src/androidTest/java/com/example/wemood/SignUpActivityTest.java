package com.example.wemood;


import android.app.Activity;
import android.widget.EditText;


import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;


/**
 * Test class for Sign up Activity. All the UI tests are written here.
 * Robotium test framework is used*/


public class SignUpActivityTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<SignUpActivity> rule =
            new ActivityTestRule<>(SignUpActivity.class, true, true);

    /**
     * Run before all tests and creates solo instance
     * @throws Exception
     * */

    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }

    /**
     * Gets the Activity
     * @throws Exception
     * */

    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }

    /**
     * Using already exist email address and wrong format of password abd email,
     * to test if sign-up successfully jump to home page*/

    @Test
    public void checkList(){
        //Assert current activity is SignUpActivity otherwise show "Wrong Activity"
        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);


        solo.enterText((EditText) solo.getView(R.id.email),"123@qq.com");
        solo.enterText((EditText) solo.getView(R.id.user_password),"abcd1234");
        solo.enterText((EditText) solo.getView(R.id.username),"Willy");
        solo.enterText((EditText) solo.getView(R.id.phone),"1234567890");
        assertTrue(solo.waitForText("123@qq.com",1,2000));
        assertTrue(solo.waitForText("abcd1234",1,2000));
        assertTrue(solo.waitForText("Willy",1,2000));
        assertTrue(solo.waitForText("1234567890",1,2000));

        solo.clickOnButton("Confirm");
        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);


        solo.enterText((EditText) solo.getView(R.id.email),"123qq.com");
        solo.enterText((EditText) solo.getView(R.id.user_password),"1234");
        solo.enterText((EditText) solo.getView(R.id.username),"Willy");
        solo.enterText((EditText) solo.getView(R.id.phone),"123456790");


        solo.clickOnButton("Confirm");
        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);

    }

    /**Close Activity after each test
     * @throws Exception*/
    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }


}
