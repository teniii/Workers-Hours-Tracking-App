package com.example.App.compositeId;

import com.example.App.Entity.Project;
import com.example.App.Entity.Angajat;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

@Embeddable
public class AllocationId implements Serializable {

    @ManyToOne(fetch = FetchType.EAGER,
            cascade= {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_user")
    private Angajat user;

    @ManyToOne(fetch = FetchType.EAGER,
            cascade= {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_project")
    private Project project;

    private Date date;

    public AllocationId() {
    }

    public AllocationId(Date date) {
//        this.project = project;
//        this.user = user;
        this.date = date;
    }

    public Angajat getUser() {
        return user;
    }

    public void setUser(Angajat user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        com.example.App.compositeId.AllocationId that = (com.example.App.compositeId.AllocationId) o;
        return getUser().equals(that.getUser()) &&
                getProject().equals(that.getProject()) &&
                getDate().equals(that.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getProject(), getDate());
    }

    /*@Override
    public String toString() {
        return "AllocationId{" +
                "user=" + user +
                ", project=" + project +
                ", date=" + date +
                '}';
    }*/
}
