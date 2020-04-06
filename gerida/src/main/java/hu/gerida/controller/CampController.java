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

import hu.gerida.repository.CampRepository;

@RestController
public class CampController {
	@Autowired
	public CampRepository campRepository;

	@CrossOrigin
	@GetMapping("/camps")
	public List<Camp> getAllCamps(){
		List<Camp> personList = new ArrayList<>();
		Iterable<Camp> camp = campRepository.findAll();
		for (Camp p : camp){
			personList.add(p);
        }
        
		return personList;
	}

	@CrossOrigin
	@GetMapping("/activeCamps")
	public List<Camp> getActiveCampList(){
		List<Camp> campList = new ArrayList<>();
		Iterable<Camp> camps = campRepository.getActiveCampList();
		for (Camp c : camps)
			campList.add(c);
		return campList;
	}

	@CrossOrigin
	@GetMapping("/campByName/{name}")
	public List<Camp> getCampByName(@PathVariable("name") String name){
		List<Camp> campList = new ArrayList<>();
		Iterable<Camp> camps = campRepository.getCampByName(name);
		for (Camp c : camps){
			campList.add(c);
		}
		return campList;
	}

	@CrossOrigin
	@GetMapping("/campByTheme/{theme}")
	public List<Camp> getCampByTheme(@PathVariable("theme") String theme){
		List<Camp> campList = new ArrayList<>();
		Iterable<Camp> camps = campRepository.getCampByTheme(theme);
		for (Camp c : camps){
			campList.add(c);
		}
		return campList;
	}

	@CrossOrigin
	@GetMapping("/campByYear/{year}")
	public List<Camp> getCampByYear(@PathVariable("year") int year){
		List<Camp> campList = new ArrayList<>();
		Iterable<Camp> camps = campRepository.getCampByYear(year);
		for (Camp p : camps){
			campList.add(p);
		}
		return campList;
	}
	
	@CrossOrigin
	@PostMapping("/addcamp")
	public <Optional>Camp createCamper(@RequestBody Camp camp){
        campRepository.save(camp);
        return campRepository.getCampByFrom(camp.getFrom()).get();
	}
    
    @CrossOrigin
	@DeleteMapping("/camp/{id}")
	public List<Camp> deleteCamper(@PathVariable("id") int id)
	{
		campRepository.deleteById(id);

		return campRepository.findAll();
	}
}