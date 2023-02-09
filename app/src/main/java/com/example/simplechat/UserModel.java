package com.example.simplechat;

/**
 This is a model class that represents a user in the system.
 It has private fields for user ID, user name, email, and password.
 It also provides getters and setters for each of these fields.
 This class is used to store and manage information about a user in a Java application."
 */

public class UserModel {
    private String userId;
    private String userName;
    private String userEmail;
    private String userPassword;

    public UserModel() {
    }

    public UserModel(String userId, String userName, String userEmail, String userPassword) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
