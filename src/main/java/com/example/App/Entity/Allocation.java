package com.example.App.Entity;

import com.example.App.compositeId.AllocationId;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class Allocation {

    @EmbeddedId
    private AllocationId allocationId;
    /*@Id
    @Column(name = "id_project")
    private int projectId;
    @Id
    @Column(name = "id_user")
    private int userId;
    @Id
    private String date;*/
    private int hours;
    private String comments;




    public Allocation() {    }

    public Allocation(AllocationId allocationId, int hours, String comments) {
        this.allocationId = allocationId;
        this.hours = hours;
        this.comments = comments;
    }

    public AllocationId getAllocationId() {
        return allocationId;
    }

    public void setAllocationId(AllocationId allocationId) {
        this.allocationId = allocationId;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    /*@Override
    public String toString() {
        return "Allocation{" +
                "allocationId=" + allocationId +
                ", hours=" + hours +
                ", comments='" + comments + '\'' +
                '}';
    }*/
}
