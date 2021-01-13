package com.example.eventcalendarapp;

public class EventHelperClass {

    String name, date;
    Integer hour, minute;

    public EventHelperClass() {}

    public EventHelperClass(String name, String date, Integer hour, Integer minute) {
        this.name = name;
        this.date = date;
        this.hour = hour;
        this.minute = minute;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }
}
