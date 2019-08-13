package com.example.eveorg.Model;

import java.io.Serializable;

public class Events implements Serializable {
    private String image;
    private String name;
    //private String detail;


    public Events(String image, String name) {
        this.image = image;
        this.name = name;
        //this.detail = detail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getDetail() {
//        return detail;
//    }
//
//    public void setDetail(String detail) {
//        this.detail = detail;
//    }
}
