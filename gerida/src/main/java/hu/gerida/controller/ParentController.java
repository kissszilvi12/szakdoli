package hu.gerida.controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import hu.gerida.model.Parent;
import hu.gerida.repository.ParentRepository;

@RestController
public class ParentController
{
	@Autowired
	public ParentRepository parentRepository;

	@GetMapping("/parent/{id}")
	public Parent getPersonById(@PathVariable("id") int id)
	{
		Parent parent = parentRepository.findById(id).get();

		return parent;
	}

	@GetMapping("/parents")
	public List<Parent> getAllParents()
	{
		List<Parent> parentList = new ArrayList<>();
		Iterable<Parent> parent = parentRepository.findAll();

		for (Parent p : parent)
		{
			parentList.add(p);
		}

		return parentList;
	}
        
    /*
	@DeleteMapping("/student/{id}")
	public List<Student> deletePassport(@PathVariable("id") int id)
	{
		studentRepository.deleteById(id);

		return getAllStudents();
	}*/
}