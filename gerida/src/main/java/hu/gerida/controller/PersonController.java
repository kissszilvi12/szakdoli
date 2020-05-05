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

import hu.gerida.model.Person;

import hu.gerida.repository.PersonRepository;

@RestController
public class PersonController
{
	@Autowired
	public PersonRepository personRepository;

	@CrossOrigin
	@GetMapping("/campers")
	public List<Person> getAllChildren(){
		List<Person> personList = new ArrayList<>();
		Iterable<Person> person = personRepository.findAll();

		for (Person p : person)
		{
			personList.add(p);
		}

		return personList;
	}

	@CrossOrigin
	@GetMapping("/camper/byName/{name}")
	public List<Person> getChildListByName(@PathVariable("name") String name){
		List<Person> personList = new ArrayList<>();
		Iterable<Person> person = personRepository.getChildListByName(name);
		for (Person p : person){
			personList.add(p);
		}
		return personList;
	}

	@CrossOrigin
	@GetMapping("/camper/byId/{id}")
	public Person getChildById(@PathVariable("id") int id){
		Person person = personRepository.getChildById(id);
		return person;
	}

	@CrossOrigin
	@GetMapping("/camper/byPos/{position}")
	public List<Person> getChildListByPosition(@PathVariable("position") String position){
		List<Person> personList = new ArrayList<>();
		Iterable<Person> person = personRepository.getChildListByPosition(position);
		for (Person p : person){
			personList.add(p);
		}
		return personList;
	}

	@CrossOrigin
	@GetMapping("/camper/byHouse/{house}")
	public List<Person> getChildListByHouse(@PathVariable("house") String house){
		List<Person> personList = new ArrayList<>();
		Iterable<Person> person = personRepository.getChildListByHouse(house);
		for (Person p : person){
			personList.add(p);
		}
		return personList;
	}

	@CrossOrigin
	@GetMapping("/camper/byPlanet/{planet}")
	public List<Person> getChildListByPlanet(@PathVariable("planet") String planet){
		List<Person> personList = new ArrayList<>();
		Iterable<Person> person = personRepository.getChildListByPlanet(planet);
		for (Person p : person){
			personList.add(p);
		}
		return personList;
	}
	
	@CrossOrigin
	@PostMapping("/addcamper")
	public <Optional>Person createCamper(@RequestBody Person person){
        personRepository.save(person);
        return personRepository.findById(person.getId()).get();
	}
    
    @CrossOrigin
	@DeleteMapping("/camper/{id}")
	public List<Person> deleteCamper(@PathVariable("id") int id)
	{
		personRepository.deleteById(id);

		return personRepository.findAll();
	}
}