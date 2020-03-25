package hu.gerida.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import hu.gerida.model.Camp;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface CampRepository extends CrudRepository<Camp, Integer> {

    @Override
    public List<Camp> findAll();
    public List<Camp> findByNameIgnoreCaseStartingWithOrderByNameAscFromAscTillAsc(String name);  //find by camp name
    public List<Camp> findByThemeIgnoreCaseStartingWithOrderByNameAscFromAscTillAsc(String theme);  //filter by theme
}