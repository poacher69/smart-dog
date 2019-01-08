package com.wc.ottoapp;

public class getInformation {
    String date;
    float temperature;
    float humidity;
    float distance;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public getInformation(String date, float temperature, float humidity, float distance) {

        this.date = date;
        this.temperature = temperature;
        this.humidity = humidity;
        this.distance = distance;
    }

    public getInformation(){}
}
