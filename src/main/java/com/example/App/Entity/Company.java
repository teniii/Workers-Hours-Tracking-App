package com.example.App.Entity;


import javax.persistence.*;

@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "CUI")
    private String CUI;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    /*@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "company")
    private List<Project> projects;*/

    public Company() {
    }

    public Company(String CUI, String password, String name, String email) {
        this.CUI = CUI;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public Company(int id, String CUI, String password, String name, String email) {
        this.id = id;
        this.CUI = CUI;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", CUI='" + CUI + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCUI() {
        return CUI;
    }

    public void setCUI(String CUI) {
        this.CUI = CUI;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /*public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public void add(Project tempProj) {

        if (projects == null) {
            projects = new ArrayList<>();
        }

        projects.add(tempProj);

        tempProj.setCompany(this);
    }*/
}

