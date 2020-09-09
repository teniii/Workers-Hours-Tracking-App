package com.example.App.Entity;

import javax.persistence.*;

@Entity
public class Project {
    @Id
    private int id;
    private String name;

    @ManyToOne(fetch = FetchType.EAGER,
                cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "company_id")
    private Company company;

    /*@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "allocationId.project")
    private List<Allocation> allocations;*/

    public Project() {    }

    public Project(String name) {
        this.name = name;
    }

    public Project(String name, Company company) {
        this.name = name;
        this.company = company;
    }

    public boolean isEmpty() {
        if(id == 0) {
            return false;
        }
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

   /* public List<Allocation> getAllocations() {
        return allocations;
    }

    public void setAllocations(List<Allocation> allocations) {
        this.allocations = allocations;
    }
*/
    /*public void add(Allocation tempAlloc) {

        if (allocations == null) {
            allocations = new ArrayList<>();
        }

        allocations.add(tempAlloc);

        //tempAlloc.setProject(this);
    }*/
}
