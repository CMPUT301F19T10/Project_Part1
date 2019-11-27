package com.example.wemood;
/**
 * @author Boyuan Dong
 *
 * @version 2.0
 */

import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

import com.robotium.solo.Solo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Class name: MainActivityFriendPageTest
 *
 * Version 2.0
 *
 * Date: November 26, 2019
 *
 * Copyright [2019] [Team10, Fall CMPUT301, University of Alberta]
 */

/**
 * Will Test the MainActivityHomePage.
 */
public class MainActivityHomePageTest {
    private Solo solo;

    /**
     * The First Activity in the app would be LogSignInActivity.
     */
    @Rule
    public ActivityTestRule<LogSignInActivity> rule = new ActivityTestRule<>(LogSignInActivity.class, true, true);

    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */

    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
        //initialize the ShowActivity environment before testing
        solo.assertCurrentActivity("Not in LogSignInActivity", LogSignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.add_user_name), "winsordby1@gmail.com");
        solo.enterText((EditText) solo.getView(R.id.add_user_password),"dby123");
        solo.clickOnView(solo.getView(R.id.sign_in_button));
        solo.waitForActivity(MainActivity.class,2000);

    }

    /**
     * Check whether the MainActivity opens by clicking on the Sign in Button.
     */

    @Test
    public void checkoutMainActivity() {
        solo.assertCurrentActivity("Not in MainActivity", MainActivity.class);
    }

    /**
     * Check whether the HomeFragment opens by clicking on the HomeButton RadioButton.
     */

    @Test
    public void checkHomeFragmentChange() {
        RadioButton HomeButton = (RadioButton) solo.getView(R.id.home_tab);
        solo.clickOnView(HomeButton);
        solo.waitForFragmentById(R.id.home_fragment);
    }


    /**
     * Check whether the MoodDetailClicked Activity opens by clicking on the item in the list.
     */

    @Test
    public void checkMoodDetailClickedActivityOpen() {
        RadioButton HomeButton = (RadioButton) solo.getView(R.id.home_tab);
        solo.clickOnView(HomeButton);
        solo.waitForFragmentById(R.id.home_fragment,2000);
        solo.clickInList(0);
        solo.waitForActivity(MoodDetailClicked.class,2000);
        solo.assertCurrentActivity("Not in MoodDetailClicked",MoodDetailClicked.class);
    }

    /**
     * Check whether the FriendRequestMessageFragment opens by clicking on the bell RadioButton.
     */

    @Test
    public void checkFriendRequestMessageFragmentChange() {
        RadioButton HomeButton = (RadioButton) solo.getView(R.id.home_tab);
        solo.clickOnView(HomeButton);
        solo.waitForFragmentById(R.id.home_fragment,2000);
        RadioButton bellButton = (RadioButton) solo.getView(R.id.friend_request_bell);
        solo.clickOnView(bellButton);
        solo.waitForActivity(FriendRequestMessageActivity.class,2000);
        solo.assertCurrentActivity("Not in FriendRequestMessage",FriendRequestMessageActivity.class);
    }


    /**
     * Check whether the FriendRequestFragmentDialog opens by clicking on one message.
     */
    @Test
    public void checkFriendRequestFragmentDialog() {
        RadioButton HomeButton = (RadioButton) solo.getView(R.id.home_tab);
        solo.clickOnView(HomeButton);
        solo.waitForFragmentById(R.id.home_fragment);
        RadioButton bellButton = (RadioButton) solo.getView(R.id.friend_request_bell);
        solo.clickOnView(bellButton);
        solo.waitForActivity(FriendRequestMessageActivity.class,2000);
        solo.clickInList(1);
        solo.waitForFragmentById(R.id.request_message_dialog,2000);
    }

}
