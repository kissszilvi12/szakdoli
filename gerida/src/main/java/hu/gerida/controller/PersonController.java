package hu.gerida.controller;

import java.io.StringReader;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import hu.gerida.model.Camp;
import hu.gerida.model.Gender;
import hu.gerida.model.House;
import hu.gerida.model.Parent;
import hu.gerida.model.Person;
import hu.gerida.model.Planet;
import hu.gerida.model.Position;

import hu.gerida.repository.CampRepository;
import hu.gerida.repository.ParentRepository;
import hu.gerida.repository.PersonRepository;

@RestController
public class PersonController {
	@Autowired
	public PersonRepository personRepository;
	@Autowired
	CampRepository campRepository;
	@Autowired
	ParentRepository parentRepository;

	@CrossOrigin
	@GetMapping("/campers")
	public List<Person> getAllChildren() {
		List<Person> personList = new ArrayList<>();
		Iterable<Person> person = personRepository.findAll();

		for (Person p : person) {
			personList.add(p);
		}

		return personList;
	}

	@CrossOrigin
	@GetMapping("/camper/byName/{name}")
	public List<Person> getChildListByName(@PathVariable("name") String name) {
		List<Person> personList = new ArrayList<>();
		Iterable<Person> person = personRepository.getChildListByName(name);
		for (Person p : person) {
			personList.add(p);
		}
		return personList;
	}

	@CrossOrigin
	@GetMapping("/camper/byId/{id}")
	public Person getChildById(@PathVariable("id") int id) {
		Person person = personRepository.getChildById(id);
		return person;
	}

	@CrossOrigin
	@GetMapping("/camper/byPos/{position}")
	public List<Person> getChildListByPosition(@PathVariable("position") String position) {
		List<Person> personList = new ArrayList<>();
		Iterable<Person> person = personRepository.getChildListByPosition(position);
		for (Person p : person) {
			personList.add(p);
		}
		return personList;
	}

	@CrossOrigin
	@GetMapping("/camper/byHouse/{house}")
	public List<Person> getChildListByHouse(@PathVariable("house") String house) {
		List<Person> personList = new ArrayList<>();
		Iterable<Person> person = personRepository.getChildListByHouse(house);
		for (Person p : person) {
			personList.add(p);
		}
		return personList;
	}

	@CrossOrigin
	@GetMapping("/camper/byPlanet/{planet}")
	public List<Person> getChildListByPlanet(@PathVariable("planet") String planet) {
		List<Person> personList = new ArrayList<>();
		Iterable<Person> person = personRepository.getChildListByPlanet(planet);
		for (Person p : person) {
			personList.add(p);
		}
		return personList;
	}

	@CrossOrigin
	@PostMapping("/addcamper")
	public List<Person> createCamper(@RequestBody String request, String name, String gender, String birthDate,
			String planet, String house, String size, String pos, String foodSensitivity, String other, String camps,
			/* parent */ String parentName, String postCode, String country, String street, String phone, String email,
			String job) {
		Parent parent = new Parent(parentName, Integer.parseInt(postCode), country, street, phone, email, job);
		Gender g = Gender.valueOf(gender);
		House h = House.valueOf(house);
		Position p = Position.valueOf(pos);
		Planet pl = Planet.valueOf(planet);

		List<Camp> cList = new ArrayList<>();
		int id;
		for (String n : camps.split(";")) {
			id = Integer.parseInt(n);
			cList.add(campRepository.findById(id));
		}

		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date textToDate = new Date(df.parse(birthDate).getTime());
			Person person = new Person(name, g, textToDate, pl, h, p, Integer.parseInt(size), foodSensitivity, other,
					cList, parent);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Helytelen dátum formátum!");
		}

		parentRepository.save(parent);

		return personRepository.findAll();
	}

	@CrossOrigin
	@PostMapping("/addcreatedcamper")
	public List<Person> createCamper(@RequestBody String request){
		System.out.println(request);
		JsonReader jsonReader;
		Map<String, JsonObject> objects = new HashMap<>();	//for the json objects (object name, and datas)

		//create a json object from request string
		jsonReader = Json.createReader(new StringReader(request));
		JsonObject requestObj = jsonReader.readObject();		
		jsonReader.close();

		//create more json object from the new object
		for(String k : requestObj.keySet()){
			String object = requestObj.get(k).toString();	//Jsonvalue to string
			jsonReader = Json.createReader(new StringReader(object));	//String to jsonReader
			objects.put(k, jsonReader.readObject()); //add new json object and its name to list
		}

		//create new parent
		JsonObject parentObject = objects.get("parent");
		String pc = cut(parentObject.get("postCode").toString());
		int postCode = Integer.parseInt(pc);
		String parentName= cut(parentObject.get("name").toString());
		String country = cut(parentObject.get("country").toString());
		String street = cut(parentObject.get("street").toString());
		String phone= cut(parentObject.get("phone").toString());
		String email= cut(parentObject.get("email").toString());
		String job = cut(parentObject.get("job").toString());

		Parent parent = new Parent(parentName, postCode, country, street, phone, email, job);

		//create new person
		JsonObject personObject = objects.get("person");
		String s = cut(personObject.get("size").toString());
		int size = Integer.parseInt(s);		
		String name = cut(personObject.get("name").toString());
		String foodSensitivity = personObject.get("foodSensitivity") == null ? "" : cut(personObject.get("foodSensitivity").toString());
		String other = cut(personObject.get("other").toString());
		String g = cut(personObject.get("gender").toString().toUpperCase(), "\"");
		Gender gender = Gender.valueOf(g);
		Position pos = Position.valueOf("GYEREK");
		House house = House.valueOf("NINCS");
		Planet planet = Planet.valueOf("NINCS");

		String camps = personObject.get("camps").toString();
		List<Camp> cList = new ArrayList<>();
		int id;
		for (String n : camps.split(",")) {
			n = cut(n, "[");
			n = cut(n, "]");
			id = Integer.parseInt(n);
			cList.add(campRepository.findById(id));
		}

		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date textToDate = new Date(df.parse(cut(personObject.get("birthDate").toString())).getTime());
			Person person = new Person(name, gender, textToDate, planet, house, pos, size, foodSensitivity, other, cList, parent);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Helytelen dátum formátum!");
		}

		parentRepository.save(parent);
		jsonReader.close();

		

		return personRepository.findAll();
	}

	@CrossOrigin
	@PutMapping("/camper/{id}")
	public List<Person> setCamper(@PathVariable("id") int id){
		personRepository.updateById(id);
		return personRepository.findAll();
	}
    
    @CrossOrigin
	@DeleteMapping("/camper/{id}")
	public List<Person> deleteCamper(@PathVariable("id") int id){
		personRepository.deleteById(id);
		return personRepository.findAll();
	}

	private String cut(String content, String ch){
		content = content.replace(ch, " ");
		return content.trim();
	}

	private String cut(String content){
		content = content.replace("\"", " ");
		return content.trim();
	}
}