package com.example.App.util.moreUtils;

public class HoursPerProject {
    private String projectName;
    private int hours;

    public HoursPerProject() {
    }

    public HoursPerProject(String projectName, int hours) {
        this.projectName = projectName;
        this.hours = hours;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }
}
