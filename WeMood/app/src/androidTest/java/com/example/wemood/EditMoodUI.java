package com.example.wemood;

import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;
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

public class EditMoodUI {
    private Solo solo;

    @Rule
    public ActivityTestRule<LogSignInActivity> rule =
            new ActivityTestRule<>(LogSignInActivity.class, true, true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
        solo.assertCurrentActivity("Wrong Activity", LogSignInActivity.class);
        solo.enterText((EditText) solo.getView(R.id.add_user_name), "yeye@qq.com");
        solo.enterText((EditText) solo.getView(R.id.add_user_password), "yeziyi123");
        solo.clickOnButton("Sign in");
        solo.waitForActivity(MainActivity.class, 5000);
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }

    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }

    @Test
    public void testEdit() throws Exception {
        RadioButton profileButton = (RadioButton) solo.getView(R.id.profile_tab);
        solo.clickOnView(profileButton);
        //go to history
        Button historyButton = (Button) solo.getView(R.id.history);
        solo.clickOnView(historyButton);
        solo.waitForActivity(MoodHistory.class, 5000);
        solo.assertCurrentActivity("Not in MoodHistory", MoodHistory.class);
        assertTrue(solo.waitForText("My Moods History", 1, 2000));
        // Check happy mood
        Button happyButton = (Button) solo.getView(R.id.happy);
        solo.clickOnView(happyButton);
        solo.waitForActivity(HappyMood.class, 5000);
        solo.assertCurrentActivity("Not in HappyMood", HappyMood.class);
        assertTrue(solo.waitForText("My Happy Moods", 1, 2000));
        // Check editMood
        ListView mood = (ListView) solo.getView(R.id.moodList);
        solo.clickOnView(mood);
        solo.assertCurrentActivity("Not in EditMood", EditMood.class);
        // Click Edit
        Button edit = (Button) solo.getView(R.id.add);
        solo.clickOnView(edit);
        solo.assertCurrentActivity("Not in EditMood", HappyMood.class);
    }

    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}
