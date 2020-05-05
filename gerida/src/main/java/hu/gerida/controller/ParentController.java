package hu.gerida.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import hu.gerida.model.Camp;
import hu.gerida.model.Parent;
import hu.gerida.repository.ParentRepository;

@RestController
public class ParentController
{
	@Autowired
	public ParentRepository parentRepository;

	@CrossOrigin
	@GetMapping("/parents/{camp}")
	public List<Parent> getParentEmailListByCamp(Camp camp){
		List<Parent> personList = new ArrayList<>();
		Iterable<Parent> person = parentRepository.getParentListByCamp(camp);
		for (Parent p : person){
			personList.add(p);
		}
		return personList;
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

	@CrossOrigin
	@PostMapping("/addParent")
	public List<Parent> createParent(@RequestBody Parent parent){
        parentRepository.save(parent);
        return parentRepository.findAll();
	}

        
	@DeleteMapping("/parent/{id}")
	public List<Parent> deleteParent(@PathVariable("id") int id)
	{
		parentRepository.deleteById(id);

		return getAllParents();
	}
}