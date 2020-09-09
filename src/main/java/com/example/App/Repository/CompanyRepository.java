package com.example.App.Repository;

import com.example.App.Entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    public Company findByCUI(String CUI);

    Company findById(int id);

    @Query(value = "select id_user, sum(hours)\n" +
            "from allocation a join project  p on (p.id = a.id_project)\n" +
            "where (p.company_id = :idComp)\n" +
            "group by  id_user", nativeQuery = true)
    List<List<Integer>> findHoursPerUser(@Param("idComp") Integer id);

    @Query(value = "select id_project, sum(hours)\n" +
            "from allocation a join project  p on (p.id = a.id_project)\n" +
            "where (p.company_id = :idComp)\n" +
            "group by  id_project;", nativeQuery = true)
    List<List<Integer>> findHoursPerProject(@Param("idComp") Integer id);

    @Query(value = "select id_project,id_user, sum(hours)\n" +
            "from allocation a join project  p on (p.id = a.id_project)\n" +
            "where (p.company_id = :idComp)\n" +
            "group by  id_project,id_user", nativeQuery = true)
    List<List<Integer>> findHoursPerProjectPerUser(@Param("idComp") Integer id);
}
