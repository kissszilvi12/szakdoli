package hu.gerida.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import hu.gerida.model.Camp;
import hu.gerida.model.Parent;
import hu.gerida.model.Person;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {

    @Override
    public List<Person> findAll();

    @Query("select p from Person p where name= :name")
    public List<Person> getChildListByName(String name);

    @Query("select p from Person p where pos= :pos")
    public List<Person> getChildListByPosition(String pos);

    @Query("select p from Person p where house= :house")
    public List<Person> getChildListByHouse(String house);

    @Query("select p from Person p where planet= :planet")
    public List<Person> getChildListByPlanet(String planet);

    @Query("select p from Person p where camp= :camp")
    public List<Parent> getParentEmailListByCamp(Camp camp);
    
    @Query("select p from Person p")
    public Parent getParentBySibling(String siblingName, String siblingBirthDate);
    
}