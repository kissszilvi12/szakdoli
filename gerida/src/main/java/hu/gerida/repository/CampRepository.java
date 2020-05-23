package hu.gerida.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import hu.gerida.model.Camp;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface CampRepository extends CrudRepository<Camp, Integer> {

    @Override
    public List<Camp> findAll();

    @Query("select c from Camp c where id=:id")
    public Camp findById(int id);

    @Query("select c from Camp c where isActive=1")
    public List<Camp> getActiveCampList();

    @Query("select c from Camp c where isActive=0")
    public List<Camp> getInactiveCampList();

//    @Query("select c from Camp c where fromDate=:from")
//    public Camp getCampByFrom(String from);

    @Query("select c from Camp c where name=:name")
    public List<Camp> getCampByName(String name);

    @Query("select c from Camp c where theme=:theme")
    public List<Camp> getCampByTheme(String theme);

    @Query("select c from Camp c where YEAR(fromDate)=:y")
    public List<Camp> getCampByYear(int y);

    @Query("select YEAR(from_date) from Camp c")
    public List<Integer> getCampYears();   
    
}