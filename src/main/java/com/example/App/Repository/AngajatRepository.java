package com.example.App.Repository;

import com.example.App.Entity.Angajat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface AngajatRepository extends JpaRepository<Angajat, Integer> {

    Angajat findByCNP(String CNP);
    Angajat findById(int id);

    @Query(value = "select a.id_project idProj, a.s maxim\n" +
            "from (\n" +
            "     select id_project,sum(hours) s\n" +
            "    from allocation\n" +
            "    where id_user = :idUser\n" +
            "    group by id_project\n" +
            "         ) a\n" +
            "ORDER by a.s DESC LIMIT 1", nativeQuery = true)
    List<List<Integer>> findProjectWithTheMostHours(@Param("idUser") Integer id);

    @Query(value = "select a.w, a.s 'maxim'\n" +
            "from(\n" +
            "select sum(hours) s, week(date,7) w\n" +
            "from allocation\n" +
            "where id_user = :idUser\n" +
            "group by week(date,7)) a\n" +
            "ORDER by a.s DESC LIMIT 1", nativeQuery = true)
    List<List<Integer>> findTheMostProductiveWeek(@Param("idUser") Integer id);

    //@Query("SELECT SUM(m.totalDays) FROM MyEntity m")
}
