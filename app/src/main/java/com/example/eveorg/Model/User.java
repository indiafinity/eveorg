package com.example.eveorg.Model;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class User implements Serializable {
    private String SapID;
    private String Password;
    private String Name;
    private String Email;
    private String Mobile;

    public User(String sapID, String password, String name, String email, String mobile) {
        this.SapID = sapID;
        this.Password = password;
        this.Name = name;
        this.Email = email;
        this.Mobile = mobile;
    }

    public User(){
    }

    public String getSapID() {
        return SapID;
    }

    public void setSapID(String sapID) {
        SapID = sapID;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }
}
