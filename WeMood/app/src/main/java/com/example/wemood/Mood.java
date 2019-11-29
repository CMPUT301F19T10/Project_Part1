package com.example.wemood;

import java.io.Serializable;
import java.util.Date;

/**
 * Class name: Mood
 *
 * version 1.0
 *
 * Date: November 1, 2019
 *
 * Copyright [2019] [Team10, Fall CMPUT301, University of Alberta]
 */

/**
 * create a mood
 */
public class Mood implements Comparable<Mood>, Serializable {
    private Date datetime;
    private String emotionalState;
    private String explanation;
    private String comment;
    private String socialSituation;
    private String location;
    private String username;
    private double longitude;
    private double latitude;
    private String uri;

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public Mood() {
    }

    /**
     * construct a mood
     * @param datetime
     * @param emotionalState
     * @param comment
     * @param socialSituation
     * @param title
     */
    public Mood(Date datetime, String emotionalState, String comment, String socialSituation, String title, double longitude, double latitude, String location, String uri) {
        this.datetime = datetime;
        this.emotionalState = emotionalState;
        this.comment = comment;
        this.socialSituation = socialSituation;
        this.explanation = title;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * construct a mood
     * @param datetime
     * @param emotionalState
     * @param explanation
     * @param comment
     * @param socialSituation
     * @param location
     * @param username
     */
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
    // used to sort moods in moodList by date
    @Override
    public int compareTo(Mood mood){
        return getDatetime().compareTo(mood.getDatetime());
    }

}