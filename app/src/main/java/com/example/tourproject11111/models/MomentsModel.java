package com.example.tourproject11111.models;

public class MomentsModel {

    public String momentId;
    public String tourId;
    public String imagedownload;

    public MomentsModel() {
    }

    public MomentsModel(String momentId, String tourId, String imagedownload) {
        this.momentId = momentId;
        this.tourId = tourId;
        this.imagedownload = imagedownload;
    }

    public String getMomentId() {
        return momentId;
    }

    public void setMomentId(String momentId) {
        this.momentId = momentId;
    }

    public String getTourId() {
        return tourId;
    }

    public void setTourId(String tourId) {
        this.tourId = tourId;
    }

    public String getImagedownload() {
        return imagedownload;
    }

    public void setImagedownload(String imagedownload) {
        this.imagedownload = imagedownload;
    }

    @Override
    public String toString() {
        return "MomentsModel{" +
                "momentId='" + momentId + '\'' +
                ", tourId='" + tourId + '\'' +
                ", imagedownload='" + imagedownload + '\'' +
                '}';
    }
}
