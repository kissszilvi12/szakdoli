package hu.gerida.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import hu.gerida.model.Person;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {

    @Override
    public List<Person> findAll();
    public List<Person> findByNameIgnoreCaseStartingWithOrderByNameAscRankAscPlanetAscHouseAsc(String name);  //find by name
    public List<Person> findByHouseIgnoreCaseStartingWithOrderByNameAscRankAscPlanetAscHouseAsc(String house);  //filter by house
    public List<Person> findByPlanetIgnoreCaseStartingWithOrderByNameAscRankAscPlanetAscHouseAsc(String planet);  //filter by planet
    public List<Person> findByPositionIgnoreCaseStartingWithOrderByNameAscRankAscPlanetAscHouseAsc(String house);  //filter by house
}