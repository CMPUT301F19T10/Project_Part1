package com.example.wemood;

/**
 * @author Zuhao Yang
 *
 * @version 2.0
 */

import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
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
 * Class name: ProfileFragmentTest
 *
 * Version 2.0
 *
 * Date: November 26, 2019
 *
 * Copyright [2019] [Team10, Fall CMPUT301, University of Alberta]
 */

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
        solo.enterText((EditText) solo.getView(R.id.add_user_name), "yzhtest@qq.com");
        solo.enterText((EditText) solo.getView(R.id.add_user_password), "yzh123");
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
        // Enter the ProfileFragment Fragment
        RadioButton profileButton = (RadioButton) solo.getView(R.id.profile_tab);
        solo.clickOnView(profileButton);
        solo.waitForFragmentById(R.id.profileFragment, 5000);

        // Check profile figure
        ImageView figureView = (ImageView) solo.getView(R.id.figure);
        solo.waitForView(figureView, 1, 2000);

        // Check text show
        // Check number of moods
        assertTrue(solo.waitForText("1", 1, 2000));

        // Check number of following
        assertTrue(solo.waitForText("1", 1, 2000));

        // Check UserName
        assertTrue(solo.waitForText("yzh123", 1, 2000));

        // Check UserID, Email, Phone Number
        assertTrue(solo.waitForText("RhB9589Gw4apQWR50qFamY28avn1", 1, 2000));
        assertTrue(solo.waitForText("yzhtest@qq.com", 1, 2000));
        assertTrue(solo.waitForText("8888888888888", 1, 2000));
    }

    /**
     * Check whether MoodHistory Activity works properly
     */
    @Test
    public void checkMoodHistory() {
        // Enter the ProfileFragment Fragment
        RadioButton profileButton = (RadioButton) solo.getView(R.id.profile_tab);
        solo.clickOnView(profileButton);
        solo.waitForFragmentById(R.id.profileFragment, 5000);

        // Check history button
        Button historyButton = (Button) solo.getView(R.id.history);
        solo.clickOnView(historyButton);
        solo.waitForActivity(MoodHistory.class, 5000);
        solo.assertCurrentActivity("Not in MoodHistory", MoodHistory.class);
        // Check title for MoodHistory
        assertTrue(solo.waitForText("My Moods History", 1, 2000));

        // Check happy mood
        Button happyButton = (Button) solo.getView(R.id.happy);
        solo.clickOnView(happyButton);
        solo.waitForActivity(HappyMood.class, 5000);
        solo.assertCurrentActivity("Not in HappyMood", HappyMood.class);
        // Check title for HappyMood
        assertTrue(solo.waitForText("My Happy Moods", 1, 2000));
        // Check ListView for HappyMood
        ListView happyMoodList = (ListView) solo.getView(R.id.moodList);
        solo.waitForView(happyMoodList, 1, 2000);
        // Check return to MoodHistory
        ImageButton happyBackButton = (ImageButton) solo.getView(R.id.back);
        solo.clickOnView(happyBackButton);
        solo.waitForActivity(MoodHistory.class, 5000);
        solo.assertCurrentActivity("Not in MoodHistory", MoodHistory.class);

        // Check angry mood
        Button angryButton = (Button) solo.getView(R.id.angry);
        solo.clickOnView(angryButton);
        solo.waitForActivity(AngryMood.class, 5000);
        solo.assertCurrentActivity("Not in AngryMood", AngryMood.class);
        // Check title for AngryMood
        assertTrue(solo.waitForText("My Angry Moods", 1, 2000));
        // Check ListView for AngryMood
        ListView angryMoodList = (ListView) solo.getView(R.id.moodList);
        solo.waitForView(angryMoodList, 1, 2000);
        // Check return to MoodHistory
        ImageButton angryBackButton = (ImageButton) solo.getView(R.id.back);
        solo.clickOnView(angryBackButton);
        solo.waitForActivity(MoodHistory.class, 5000);
        solo.assertCurrentActivity("Not in MoodHistory", MoodHistory.class);

        // Check sad mood
        Button sadButton = (Button) solo.getView(R.id.sad);
        solo.clickOnView(sadButton);
        solo.waitForActivity(SadMood.class, 5000);
        solo.assertCurrentActivity("Not in SadMood", SadMood.class);
        // Check title for SadMood
        assertTrue(solo.waitForText("My Sad Moods", 1, 2000));
        // Check ListView for SadMood
        ListView sadMoodList = (ListView) solo.getView(R.id.moodList);
        solo.waitForView(sadMoodList, 1, 2000);
        // Check return to MoodHistory
        ImageButton sadBackButton = (ImageButton) solo.getView(R.id.back);
        solo.clickOnView(sadBackButton);
        solo.waitForActivity(MoodHistory.class, 5000);
        solo.assertCurrentActivity("Not in MoodHistory", MoodHistory.class);

        // Check tired mood
        Button tiredButton = (Button) solo.getView(R.id.tired);
        solo.clickOnView(tiredButton);
        solo.waitForActivity(TiredMood.class, 5000);
        solo.assertCurrentActivity("Not in TiredMood", TiredMood.class);
        // Check title for TiredMood
        assertTrue(solo.waitForText("My Tired Moods", 1, 2000));
        // Check ListView for TiredMood
        ListView tiredMoodList = (ListView) solo.getView(R.id.moodList);
        solo.waitForView(tiredMoodList, 1, 2000);
        // Check return to MoodHistory
        ImageButton tiredBackButton = (ImageButton) solo.getView(R.id.back);
        solo.clickOnView(tiredBackButton);
        solo.waitForActivity(MoodHistory.class, 5000);
        solo.assertCurrentActivity("Not in MoodHistory", MoodHistory.class);

        // Check lonely mood
        Button lonelyButton = (Button) solo.getView(R.id.lonely);
        solo.clickOnView(lonelyButton);
        solo.waitForActivity(LonelyMood.class, 5000);
        solo.assertCurrentActivity("Not in HappyMood", LonelyMood.class);
        // Check title for LonelyMood
        assertTrue(solo.waitForText("My Lonely Moods", 1, 2000));
        // Check ListView for LonelyMood
        ListView lonelyMoodList = (ListView) solo.getView(R.id.moodList);
        solo.waitForView(lonelyMoodList, 1, 2000);
        // Check return to MoodHistory
        ImageButton lonelyBackButton = (ImageButton) solo.getView(R.id.back);
        solo.clickOnView(lonelyBackButton);
        solo.waitForActivity(MoodHistory.class, 5000);
        solo.assertCurrentActivity("Not in MoodHistory", MoodHistory.class);

        // Check all mood
        Button allButton = (Button) solo.getView(R.id.all);
        solo.clickOnView(allButton);
        solo.waitForActivity(AllMood.class, 5000);
        solo.assertCurrentActivity("Not in AllMood", AllMood.class);
        // Check title for HappyMood
        assertTrue(solo.waitForText("My All Moods", 1, 2000));
        // Check ListView for HappyMood
        ListView allMoodList = (ListView) solo.getView(R.id.moodList);
        solo.waitForView(allMoodList, 1, 2000);
        // Check return to MoodHistory
        ImageButton allBackButton = (ImageButton) solo.getView(R.id.back);
        solo.clickOnView(allBackButton);
        solo.waitForActivity(MoodHistory.class, 5000);
        solo.assertCurrentActivity("Not in MoodHistory", MoodHistory.class);

        // Check return to ProfileFragment
        ImageButton historyBackButton = (ImageButton) solo.getView(R.id.back);
        solo.clickOnView(historyBackButton);
        solo.waitForFragmentById(R.id.profileFragment, 5000);
    }

    @Test
    public void checkLogOut() {
        // Enter the ProfileFragment Fragment
        RadioButton profileButton = (RadioButton) solo.getView(R.id.profile_tab);
        solo.clickOnView(profileButton);
        solo.waitForFragmentById(R.id.profileFragment, 5000);

        // Check LogOut button
        RadioButton logOutButton = (RadioButton) solo.getView(R.id.logout);
        solo.clickOnView(logOutButton);
        solo.waitForActivity(LogSignInActivity.class, 5000);
        solo.assertCurrentActivity("Not in LogSignInActivity", LogSignInActivity.class);
    }

    /**
     * Closes the activity after each test
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

}
