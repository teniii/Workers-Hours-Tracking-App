package com.example.App.util.moreUtils;

public class HoursPerAngajat {
    private String angajatName;
    private int hours;

    public HoursPerAngajat() {
    }

    public HoursPerAngajat(String angajatName, int hours) {
        this.angajatName = angajatName;
        this.hours = hours;
    }

    public String getAngajatName() {
        return angajatName;
    }

    public void setAngajatName(String angajatName) {
        this.angajatName = angajatName;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }
}
