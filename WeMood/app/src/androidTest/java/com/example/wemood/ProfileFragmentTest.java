package com.example.wemood;

import android.app.Activity;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

/**
 * This class is designed to conduct the intent test for ProfileFragment Fragment
 */
public class ProfileFragmentTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<LogSignInActivity> rule =
            new ActivityTestRule<>(LogSignInActivity.class, true, true);

    /**
     * Run before all tests
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
        // Log in using an existing account
        solo.assertCurrentActivity("Wrong Activity", LogSignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.add_user_name), "zoeye@gmail.com");
        solo.enterText((EditText) solo.getView(R.id.add_user_password), "111222333");
        solo.clickOnButton("Sign in");
        // Go directly to the MainActivity to test the profile fragment
        solo.waitForActivity(MainActivity.class, 5000);
        solo.assertCurrentActivity("Not in MainActivity", MainActivity.class);
    }

    /**
     * Start the activity
     * @throws Exception
     */
    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }

    /**
     * Check whether ProfileFragment Fragment works properly
     */
    @Test
    public void checkProfileFragment() {
        RadioButton ProfileButton = (RadioButton) solo.getView(R.id.profile_tab);
        solo.clickOnView(ProfileButton);
        solo.waitForFragmentById(R.id.profileFragment, 5000);
        // Check text show
        // Check number of moods
        assertTrue(solo.waitForText("19", 1, 2000));
        // Check followers and following
        assertTrue(solo.waitForText("0", 2, 2000));
        // Check UserID, Email, Phone Number
        assertTrue(solo.waitForText("8JFRoHQcf9ND7K9EwWK6AJHHRKI3", 1, 2000));
        assertTrue(solo.waitForText("zoeye@gmail.com", 1, 2000));
        assertTrue(solo.waitForText("23214", 1, 2000));
        // Check history button
        solo.clickOnButton("My Moods History");
        // Check LogOut button
        RadioButton LogOutButton = (RadioButton) solo.getView(R.id.logout);
        solo.clickOnView(LogOutButton);
        solo.waitForActivity(LogSignInActivity.class, 5000);
        solo.assertCurrentActivity("Not in LogSignInActivity", LogSignInActivity.class);
    }

    /**
     * Closes the activity after each test
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}
