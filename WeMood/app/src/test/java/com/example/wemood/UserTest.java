package com.example.wemood;

/**
 * @author Boyuan Dong
 *
 * @version 2.0
 */

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Class name: MainActivityFriendPageTest
 *
 * Version 2.0
 *
 * Date: November 28, 2019
 *
 * Copyright [2019] [Team10, Fall CMPUT301, University of Alberta]
 */

/**
 * Will test the methods of Class User
 */
public class UserTest {

    /**
     * test if the getUserName returns the username of the user
     */
    @Test
    public void testGetUserName(){
        User testUser = new User("testA@gmail.com", "testA", "111-222-1122", "TestA",null);
        assertEquals("testA", testUser.getUserName());
    }

    /**
     * test if the setUserName will set the userName of the user
     */
    @Test
    public void testSetUserName(){
        User testUser = new User("testA@gmail.com", "testA", "111-222-1122", "TestA",null);
        testUser.setUserName("TestAA");
        assertEquals("TestAA", testUser.getUserName());
    }

    /**
     * test if the GetUserID will return the userID of the user
     */
    @Test
    public void testGetUserId(){
        User testUser = new User("testA@gmail.com", "testA", "111-222-1122", "TestA",null);
        assertEquals("TestA", testUser.getUserId());
    }

    /**
     * test if the setUserId will set the UserID of the user
     */
    @Test
    public void testSetUserId(){
        User testUser = new User("testA@gmail.com", "testA", "111-222-1122", "TestA",null);
        testUser.setUserId("TestAAA");
        assertEquals("TestAAA", testUser.getUserId());
    }

    /**
     * test if the getEmail will return the Email of the user
     */
    @Test
    public void testGetEmail(){
        User testUser = new User("testA@gmail.com", "testA", "111-222-1122", "TestA",null);
        assertEquals("testA@gmail.com", testUser.getEmail());
    }

    /**
     * test if the setEmail will set the Email of the user
     */
    @Test
    public void testSetEmail(){
        User testUser = new User("testA@gmail.com", "testA", "111-222-1122", "TestA",null);
        testUser.setEmail("testAAA@gmail.com");
        assertEquals("testAAA@gmail.com", testUser.getEmail());
    }

    /**
     * test if the getPhone will return the phone of the user
     */
    @Test
    public void testGetPhone(){
        User testUser = new User("testA@gmail.com", "testA", "111-222-1122", "TestA",null);
        assertEquals("111-222-1122", testUser.getPhone());
    }

    /**
     * test if the setPhone will set the phone of the user
     */
    @Test
    public void testSetPhone(){
        User testUser = new User("testA@gmail.com", "testA", "111-222-1122", "TestA",null);
        testUser.setPhone("111-111-2222");
        assertEquals("111-111-2222", testUser.getPhone());
    }


    /**
     * test if the getFriendList will return the friendlist of the user
     */
    @Test
    public void testGetFriendList(){
        User testUser = new User("testA@gmail.com", "testA", "111-222-1122", "TestA",null);
        assertTrue(testUser.getFriendList().isEmpty());
    }

    /**
     * test if the setFriendList will set the friendlist of the user
     */
    @Test
    public void testSetFriendList(){
        User testUser = new User("testA@gmail.com", "testA", "111-222-1122", "TestA",null);
        ArrayList<String> testFriendList = new ArrayList<>();
        testFriendList.add("testFriend1");
        testUser.setFriendList(testFriendList);
        assertEquals("testFriend1", testUser.getFriendList().get(0));
    }


    /**
     * test if the getwaitFriendList will return the waitfriendlist of the user
     */
    @Test
    public void testGetwaitFriendList(){
        User testUser = new User("testA@gmail.com", "testA", "111-222-1122", "TestA",null);
        assertTrue(testUser.getWaitFriendList().isEmpty());
    }

    /**
     * test if the setwaitFriendList will set the waitfriendlist of the user
     */
    @Test
    public void testSetwaitFriendList(){
        User testUser = new User("testA@gmail.com", "testA", "111-222-1122", "TestA",null);
        ArrayList<String> testwaitFriendList = new ArrayList<>();
        testwaitFriendList.add("testWaitFriend1");
        testUser.setWaitFriendList(testwaitFriendList);
        assertEquals("testWaitFriend1", testUser.getWaitFriendList().get(0));
    }


}
