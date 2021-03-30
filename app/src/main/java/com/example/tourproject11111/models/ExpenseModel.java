package com.example.tourproject11111.models;

public class ExpenseModel {

    public String expId;
    public String tourId;
    public String title;
    public double amount;
    private long timeStamp;
    private String time,date;

    public ExpenseModel() {

    }

    public ExpenseModel(String expId, String tourId, String title, double amount, long timeStamp, String time, String date) {
        this.expId = expId;
        this.tourId = tourId;
        this.title = title;
        this.amount = amount;
        this.timeStamp = timeStamp;
        this.time = time;
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExpId() {
        return expId;
    }

    public void setExpId(String expId) {
        this.expId = expId;
    }

    public String getTourId() {
        return tourId;
    }

    public void setTourId(String tourId) {
        this.tourId = tourId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
