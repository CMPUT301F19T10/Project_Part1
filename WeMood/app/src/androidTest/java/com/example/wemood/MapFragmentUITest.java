package com.example.wemood;

/**
 * @author Chengzhang Dong
 *
 * @version 2.0
 */

import android.widget.EditText;
import android.widget.RadioButton;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Class name: MapFragmentUITest
 *
 * Version 2.0
 *
 * Date: November 26, 2019
 *
 * Copyright [2019] [Team10, Fall CMPUT301, University of Alberta]
 */

/**
 * Test MapFragment
 */
public class MapFragmentUITest {
    private Solo solo;

    @Rule
    public ActivityTestRule<LogSignInActivity> rule =
            new ActivityTestRule<>(LogSignInActivity.class, true, true);

    /**
     * sign in before testing
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
        solo.assertCurrentActivity("Wrong Activity", LogSignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.add_user_name), "314601593@qq.com");
        solo.enterText((EditText) solo.getView(R.id.add_user_password), "abcd1234");
        solo.clickOnButton("Sign in");
        solo.waitForActivity(MainActivity.class,10000);
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }

    /**
     * check if 2 buttons work well
     * @throws Exception
     */
    @Test
    public void checkMap() throws Exception {
        RadioButton MapButton = (RadioButton) solo.getView(R.id.map_tab);
        solo.clickOnView(MapButton);
        solo.waitForFragmentById(R.id.mapFragment);
        solo.clickOnButton("You");
        //solo.wait(3000);
        solo.clickOnButton("Friends");
        //solo.wait(3000);
        solo.clickOnButton("You");
    }

    /**
     * end test
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }

}
