package hu.gerida.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import hu.gerida.model.Camp;
import hu.gerida.model.Parent;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface ParentRepository extends CrudRepository<Parent, Integer> {

    @Override
    public List<Parent> findAll();
    public List<Parent> findByNameIgnoreCaseStartingWithOrderByNameAsc(String name);  //find by name
    
    @Query("SELECT p FROM Parent p")
    public List<Parent> getAllParents();

    @Query("SELECT distinct p FROM Parent p WHERE p.id in (SELECT q.id FROM Person q WHERE q.rating='PLUTO' and q.id in (SELECT r.id FROM Camp c JOIN c.campers r WHERE c.fromDate in (SELECT d.fromDate FROM Camp d WHERE d.isActive=1)))")
    public List<Parent> getFirstCampParents();

    @Query("SELECT distinct p FROM Parent p WHERE p.id in (SELECT q.id FROM Person q WHERE q.id in (SELECT r.id FROM Camp c JOIN c.campers r WHERE  c.id=:id))")
    public List<Parent> getParentsByCamp(long id);

    @Query("select distinct p from Person p where camp= :camp")
    public List<Parent> getParentListByCamp(Camp camp);
}