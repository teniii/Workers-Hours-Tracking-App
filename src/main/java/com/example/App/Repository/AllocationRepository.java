package com.example.App.Repository;

import com.example.App.Entity.Project;
import com.example.App.compositeId.AllocationId;
import com.example.App.Entity.Allocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface AllocationRepository extends JpaRepository<Allocation, AllocationId> {
    //List <Allocation> findAllByComments(String text);
    Allocation findByAllocationIdUserIdAndAllocationId_ProjectId(int user, int project);
    List <Allocation> findByAllocationIdUserId(int user);
    List<Allocation> findDistinctByAllocationId_ProjectIn(List<Project> projects);
    Allocation findByAllocationId_ProjectIdAndAllocationId_Date(int project, Date date);
    Allocation findByAllocationId_ProjectIdAndAllocationId_DateAndAllocationId_UserId(int project, Date date, int user);

    void deleteByAllocationId_DateAndAllocationId_ProjectIdAndAllocationId_UserId(Date date, int project, int user);



    //@Query(value = "SELECT SUM(total_days) FROM MyEntity", nativeQuery = true)
    //@Query("SELECT SUM(m.totalDays) FROM MyEntity m")
    //

}
