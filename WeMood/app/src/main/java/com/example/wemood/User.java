package com.example.wemood;


import java.util.ArrayList;

/**
 * Class name: User
 *
 * version 1.0
 *
 * Date: November 1, 2019
 *
 * Copyright [2019] [Team10, Fall CMPUT301, University of Alberta]
 */
public class  User {
    private String email;
    private String userName;
    private String phone;
    private String userId;
    private ArrayList<String> friendList;

    /**
     * required empty constructor
     */
    public User() {}

    /**
     *
     * @param email
     * user's email
     * @param userName
     * user's unique username
     * @param phone
     * user's phone
     * @param userId
     * user's unique id
     */
    public User(String email, String userName, String phone, String userId) {
        this.email = email;
        this.userName = userName;
        this.phone = phone;
        this.userId = userId;
        this.friendList = new ArrayList<>();
        this.friendList.add("JoJo");
        this.friendList.add("Naruto");
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ArrayList<String> getFriendList() {
        return friendList;
    }

    public void setFriendList(ArrayList<String> friendList) {
        this.friendList = friendList;
    }
}