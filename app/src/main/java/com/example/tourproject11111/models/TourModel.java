package com.example.tourproject11111.models;

public class TourModel {
    private String id;
    private String name;
    private String destination;
    private double budget;
    private String formatterdDate;
    private long timeStamp;
    private int month;
    private int year;

    public TourModel() {
    }

    public TourModel(String id, String name, String destination, double budget, String formatterdDate, long timeStamp, int month, int year) {
        this.id = id;
        this.name = name;
        this.destination = destination;
        this.budget = budget;
        this.formatterdDate = formatterdDate;
        this.timeStamp = timeStamp;
        this.month = month;
        this.year = year;
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

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public String getFormatterdDate() {
        return formatterdDate;
    }

    public void setFormatterdDate(String formatterdDate) {
        this.formatterdDate = formatterdDate;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "TourModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", destination='" + destination + '\'' +
                ", budget=" + budget +
                ", formatterdDate='" + formatterdDate + '\'' +
                ", timeStamp=" + timeStamp +
                ", month=" + month +
                ", year=" + year +
                '}';
    }
}
