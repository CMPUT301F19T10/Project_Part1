package com.example.wemood;

import java.util.Date;

public class Mood implements Comparable<Mood>{
    private Date datetime;
    private String emotionalState;
    private String explanation;
    private String comment;
    private String socialSituation;
    private String location;
    private String username;


    // Add mood original constructor
    public Mood(Date datetime, String emotionalState, String comment, String socialSituation, String title) {
        this.datetime = datetime;
        this.emotionalState = emotionalState;
        this.comment = comment;
        this.socialSituation = socialSituation;
        this.explanation = title;

    }


    // Used in Home Page Constructor
    public Mood(Date datetime, String emotionalState, String explanation, String comment, String socialSituation, String location, String username) {
        this.datetime = datetime;
        this.emotionalState = emotionalState;
        this.comment = comment;
        this.socialSituation = socialSituation;
        this.explanation = explanation;
        this.location = location;
        this.username = username;

    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getEmotionalState() {
        return emotionalState;
    }

    public void setEmotionalState(String emotionalState) {
        this.emotionalState = emotionalState;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSocialSituation() {
        return socialSituation;
    }

    public void setSocialSituation(String socialSituation) {
        this.socialSituation = socialSituation;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Compare to the other mood's date
    // used to sort moods in moodlist by date
    @Override
    public int compareTo(Mood mood){
        return getDatetime().compareTo(mood.getDatetime());
    }

}