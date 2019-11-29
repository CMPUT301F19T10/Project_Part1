package com.example.wemood;

/**
 * @author Zuhao Yang
 *
 * @version 1.0
 */

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class name: AngryMoodUnitTest
 *
 * Version 1.0
 *
 * Date: November 28, 2019
 *
 * Copyright [2019] [Team10, Fall CMPUT301, University of Alberta]
 */

/**
 * This class is designed to conduct the unit test for AngryMood Activity
 */
public class AngryMoodUnitTest {
    /**
     * Construct a mock moodDataList
     * @return the mock moodDataList
     */
    private ArrayList<Mood> mockMoodDataList() {
        ArrayList<Mood> moodDataList = new ArrayList<>();
        moodDataList.add(mockMood());

        return moodDataList;
    }

    /**
     * Construct a mock mood
     * @return the mock mood
     */
    private Mood mockMood() {

        return new Mood(new Date(18,5,7,9,30),"","","","",1.,1.,"","");
    }

    /**
     * Test whether AngryMood DataList works properly
     */
    @Test
    void testAngryMood() {
        ArrayList<Mood> moodDataList = mockMoodDataList();
        assertEquals(1, moodDataList.size());

        Mood newMood = mockMood();
        moodDataList.add(newMood);
        assertEquals(2, moodDataList.size());
        moodDataList.remove(newMood);
        assertEquals(1, moodDataList.size());
    }

}
