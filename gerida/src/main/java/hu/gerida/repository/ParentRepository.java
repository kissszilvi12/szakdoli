package hu.gerida.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import hu.gerida.model.Parent;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface ParentRepository extends CrudRepository<Parent, Integer> {

    @Override
    public List<Parent> findAll();
    public List<Parent> findByNameIgnoreCaseStartingWithOrderByNameAsc(String name);  //find by name
}