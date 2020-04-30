package hu.gerida.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import hu.gerida.model.Parent;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface ParentRepository extends CrudRepository<Parent, Integer> {

    @Override
    public List<Parent> findAll();
    public List<Parent> findByNameIgnoreCaseStartingWithOrderByNameAsc(String name);  //find by name
    
    @Query("SELECT p.email FROM Parent p")
    public List<String> getAllEmails();

    @Query("SELECT p.email FROM Parent p WHERE p.id in (SELECT q.id FROM Person q WHERE q.rating='PLUTO' and q.id in (SELECT r.id FROM Camp c JOIN c.campers r WHERE c.fromDate in (SELECT d.fromDate FROM Camp d WHERE d.isActive=1)))")
    public List<String> getFirstCampEmails();

    @Query("SELECT p.email FROM Parent p WHERE p.id in (SELECT q.id FROM Person q WHERE q.id in (SELECT r.id FROM Camp c JOIN c.campers r WHERE c.fromDate=:from))")
    public List<String> getEmailsByCamp(String from);
}