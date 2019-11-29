package com.example.wemood;

/**
 * @author Boyuan Dong
 *
 * @version 2.0
 */

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
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
 * Will Test the methods in Mood Class
 */

public class MoodTest {
    /**
     * test if the getDatetime returns the date and time of the mood
     */
    @Test
    public void testGetDateTime(){
        Mood testMood = new Mood(new Date(2019,11,9,11,30),"happy","I am Happy","Reading Week is comming soon","With a Crowed","University","boyuan");
        assertEquals(new Date(2019,11,9,11,30), testMood.getDatetime());
    }

    /**
     * test if the setDatetime will set the date and time of the user
     */
    @Test
    public void testSetDateTime(){
        Mood testMood = new Mood(new Date(2019,11,9,11,30),"happy","I am Happy","Reading Week is comming soon","With a Crowed","University","boyuan");
        testMood.setDatetime(new Date(2019,10,10,14,30));
        assertEquals(new Date(2019,10,10,14,30), testMood.getDatetime());
    }

    /**
     * test if the getEmotionalState will return the EmotionalState of the mood
     */
    @Test
    public void testGetEmotionalState(){
        Mood testMood = new Mood(new Date(2019,11,9,11,30),"happy","I am Happy","Reading Week is comming soon","With a Crowed","University","boyuan");
        assertEquals("happy", testMood.getEmotionalState());
    }

    /**
     * test if the setEmotionalState will set the EmotionalState of the mood
     */
    @Test
    public void testSetEmotionalState(){
        Mood testMood = new Mood(new Date(2019,11,9,11,30),"happy","I am Happy","Reading Week is comming soon","With a Crowed","University","boyuan");
        testMood.setEmotionalState("tired");
        assertEquals("tired", testMood.getEmotionalState());
    }

    /**
     * test if the getComment will return the comment of the Mood
     */
    @Test
    public void testGetComment(){
        Mood testMood = new Mood(new Date(2019,11,9,11,30),"happy","I am Happy","Reading Week is comming soon","With a Crowed","University","boyuan");
        assertEquals("Reading Week is comming soon", testMood.getComment());
    }

    /**
     * test if the SetComment will set the comment of the Mood
     */
    @Test
    public void testSetComment(){
        Mood testMood = new Mood(new Date(2019,11,9,11,30),"happy","I am Happy","Reading Week is comming soon","With a Crowed","University","boyuan");
        testMood.setComment("Today is a really happy day");
        assertEquals("Today is a really happy day", testMood.getComment());
    }

    /**
     * test if the getSocialSituation will return the SocialSituation of the mood
     */
    @Test
    public void testGetSocialSituation(){
        Mood testMood = new Mood(new Date(2019,11,9,11,30),"happy","I am Happy","Reading Week is comming soon","With a Crowed","University","boyuan");
        assertEquals("With a Crowed", testMood.getSocialSituation());
    }

    /**
     * test if the setSocialSituation will set the SocialSituation of the mood
     */
    @Test
    public void testSetSocialSituation(){
        Mood testMood = new Mood(new Date(2019,11,9,11,30),"happy","I am Happy","Reading Week is comming soon","With a Crowed","University","boyuan");
        testMood.setSocialSituation("Alone");
        assertEquals("Alone", testMood.getSocialSituation());
    }


    /**
     * test if the getExplanation will return the Explanation of the mood
     */
    @Test
    public void testGetExplanation(){
        Mood testMood = new Mood(new Date(2019,11,9,11,30),"happy","I am Happy","Reading Week is comming soon","With a Crowed","University","boyuan");
        assertEquals("I am Happy", testMood.getExplanation());
    }

    /**
     * test if the setExplanation will set the Explanation of the mood
     */
    @Test
    public void testSetExplanation(){
        Mood testMood = new Mood(new Date(2019,11,9,11,30),"happy","I am Happy","Reading Week is comming soon","With a Crowed","University","boyuan");
        testMood.setExplanation("Reading Week");
        assertEquals("Reading Week", testMood.getExplanation());
    }

    /**
     * test if the getLocation will return the Location of the mood
     */
    @Test
    public void testGetLocation(){
        Mood testMood = new Mood(new Date(2019,11,9,11,30),"happy","I am Happy","Reading Week is comming soon","With a Crowed","University","boyuan");
        assertEquals("University", testMood.getLocation());
    }

    /**
     * test if the setLocation will set the Location of the mood
     */
    @Test
    public void testSetLocation(){
        Mood testMood = new Mood(new Date(2019,11,9,11,30),"happy","I am Happy","Reading Week is comming soon","With a Crowed","University","boyuan");
        testMood.setLocation("U OF A");
        assertEquals("U OF A", testMood.getLocation());
    }


    /**
     * test if the getUsername will return the Username of the mood
     */
    @Test
    public void testGetUsername(){
        Mood testMood = new Mood(new Date(2019,11,9,11,30),"happy","I am Happy","Reading Week is comming soon","With a Crowed","University","boyuan");
        assertEquals("boyuan", testMood.getUsername());
    }

    /**
     * test if the getUsername will set the Username of the mood
     */
    @Test
    public void testSetUsername(){
        Mood testMood = new Mood(new Date(2019,11,9,11,30),"happy","I am Happy","Reading Week is comming soon","With a Crowed","University","boyuan");
        testMood.setUsername("Anna");
        assertEquals("Anna", testMood.getUsername());
    }



}
