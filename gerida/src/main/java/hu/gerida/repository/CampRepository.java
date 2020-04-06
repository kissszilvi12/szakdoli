package hu.gerida.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import hu.gerida.model.Camp;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface CampRepository extends CrudRepository<Camp, Integer> {

    @Override
    public List<Camp> findAll();

    @Query("select c from Camp c where is_active=1")
    public List<Camp> getActiveCampList();

    @Query("select c from Camp c where from_date=:from")
    public Optional<Camp> getCampByFrom(Date from);

    @Query("select c from Camp c where name=:name")
    public List<Camp> getCampByName(String name);

    @Query("select c from Camp c where theme=:theme")
    public List<Camp> getCampByTheme(String theme);

    @Query("select c from Camp c where YEAR(from_date)=:y")
    public List<Camp> getCampByYear(int y);
    
}