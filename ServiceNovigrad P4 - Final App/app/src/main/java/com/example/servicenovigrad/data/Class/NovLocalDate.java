package com.example.servicenovigrad.data.Class;

public class NovLocalDate {

    int date;
    int year;
    int month;

    public NovLocalDate(){
    }

    public NovLocalDate(int year, int date, int month) {
        this.date = date;
        this.year = year;
        this.month = month;
    }

    public NovLocalDate(NovLocalDate doB) {
        this.date = doB.getDate();
        this.year = doB.getYear();
        this.month = doB.getMonth();
    }


    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
