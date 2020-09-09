package com.example.App.util.moreUtils;

public class HoursPerProjectPerAngajat {
    private String projectName;
    private String angajatName;
    private int hours;


    public HoursPerProjectPerAngajat() {
    }

    public HoursPerProjectPerAngajat(String projectName, String angajatName, int hours) {
        this.angajatName = angajatName;
        this.hours = hours;
        this.projectName = projectName;
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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

}
