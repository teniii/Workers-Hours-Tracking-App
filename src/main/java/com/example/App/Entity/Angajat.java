package com.example.App.Entity;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class Angajat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

      /*@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "allocationId.user")
    private List<Allocation> allocations;
*/

    @Column(name = "CNP")
    private String CNP;

    @Column(name = "password")
    private String password;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "email")
    private String email;

//    String idString;
//
//    public String getIdString() {
//        return idString;
//    }

//    public void setIdString(String idString) {
//        this.idString = idString;
//    }

    public Angajat() {
    }

    public Angajat(String CNP, String password, String firstname, String lastname, String email) {
        this.CNP = CNP;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public Angajat(int id, String CNP, String password, String firstname, String lastname, String email) {
        this.id = id;
        this.CNP = CNP;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //    public List<Allocation> getAllocations() {
//        return allocations;
//    }

//    public void setAllocations(List<Allocation> allocations) {
//        this.allocations = allocations;
//    }

    /*public void add(Allocation tempAlloc) {

        if (allocations == null) {
            allocations = new ArrayList<>();
        }

        allocations.add(tempAlloc);

        //tempAlloc.setUser(this);
    }*/
}
