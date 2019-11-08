package com.example.wemood;


import android.app.Activity;
import android.widget.EditText;


import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test class for Log in Activity. All the UI tests are written here.
 * Robotium test framework is used*/


public class LogSignInActivityTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<LogSignInActivity> rule =
            new ActivityTestRule<>(LogSignInActivity.class, true, true);

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
     * Check if noting is entered, jump to MainActivity or not*/


    @Test
    public  void checkEmptyLogin(){
        solo.assertCurrentActivity("Wrong Activity", LogSignInActivity.class);

        solo.clickOnButton("Sign up");

        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);
    }

    /**
     * Using correct password and email address to
     * log in, result should be jumping to MainActivity
     * */

    @Test
    public void checkRightLogin(){
        //Assert current activity is LogInSignIn Activity otherwise show "Wrong Activity"
        solo.assertCurrentActivity("Wrong Activity", LogSignInActivity.class);



        solo.enterText((EditText) solo.getView(R.id.add_user_name), "123@qq.com");
        solo.enterText((EditText) solo.getView(R.id.add_user_password), "abcd1234");

        solo.clickOnButton("Sign in");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

    }

    /**
     * Using Wrong user eamil and password to check if
     * app log in successfully*/

    @Test
    public void checkWrongLogin(){
        solo.assertCurrentActivity("Wrong Activity", LogSignInActivity.class);

        solo.enterText((EditText) solo.getView(R.id.add_user_name), "1123@qq.com");
        solo.enterText((EditText) solo.getView(R.id.add_user_password), "abc45d1234");

        solo.clickOnButton("Sign in");
        solo.assertCurrentActivity("Wrong Activity", LogSignInActivity.class);
    }

}
