package com.example.wemood;



public class  User {
    private String email;
    private String userName;
    private String phone;
    private String userId;

    public User() {}

    public User(String email, String userName, String phone, String userId) {
        this.email = email;
        this.userName = userName;
        this.phone = phone;
        this.userId = userId;
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

}