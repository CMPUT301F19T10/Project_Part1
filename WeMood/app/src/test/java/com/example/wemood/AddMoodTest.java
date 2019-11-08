package com.example.wemood;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class AddMoodTest {
    @Test
       public void testContainsSpace(){
        String word = "h e l l o";
        AddMoodActivity activity = new AddMoodActivity();
        assertTrue(activity.containsSpace(word));
    }


}