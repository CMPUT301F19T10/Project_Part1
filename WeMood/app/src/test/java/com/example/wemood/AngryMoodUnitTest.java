package com.example.wemood;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

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
    }

}
