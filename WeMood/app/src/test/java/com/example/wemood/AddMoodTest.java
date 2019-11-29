package com.example.wemood;

/**
 * @author Ziyi Ye
 *
 * @version 2.0
 */

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Class name: AddMoodTest
 *
 * Version 2.0
 *
 * Date: November 28, 2019
 *
 * Copyright [2019] [Team10, Fall CMPUT301, University of Alberta]
 */

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
