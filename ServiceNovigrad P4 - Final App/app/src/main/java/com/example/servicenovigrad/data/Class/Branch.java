package com.example.servicenovigrad.data.Class;

import android.os.Build;


import java.time.LocalTime;

public class Branch {

    String name;
    String id;
    Address address;
    NovLocalTime startTime = new NovLocalTime(9, 00);
    NovLocalTime endTime = new NovLocalTime(17, 30);
    Double averageRating;

    public Branch(String name, Address address, NovLocalTime startTime, NovLocalTime endTime, String id) {
        this.name = name;
        this.address = address;
        this.startTime = startTime;
        this.endTime = endTime;
        this.id = id;
    }

    public Branch(String name, Address address, NovLocalTime startTime, NovLocalTime endTime, String id, Double averageRating) {
        this.name = name;
        this.address = address;
        this.startTime = startTime;
        this.endTime = endTime;
        this.id = id;
        this.averageRating = averageRating;
    }

    public Branch(){
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Branch(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public NovLocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(NovLocalTime startTime) {
        this.startTime = startTime;
    }

    public NovLocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(NovLocalTime endTime) {
        this.endTime = endTime;
    }

    public String combineTime(){
        String s = startTime.toString()+"-"+endTime.toString();
        return s;
    }
}
