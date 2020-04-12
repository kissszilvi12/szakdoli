package hu.gerida;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

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

@Component
public class DBInitializer implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private CampRepository campRepository;

    public void savePerson()
    {
               
        //Create a new Camp object hp_ea
        Camp hpCamp = new Camp("Harry Potter tábor", "17-06-2021", "26-06-2021", "HARRY_POTTER", 43000, "Parádfürdő", "Roxfort varázslatos világa!", 60);
        List<Camp> camps=new ArrayList<Camp>();
        camps.add(hpCamp);

        //Create a new person, parent object        
        Parent mom = new Parent("Kovács Mária", 1234, "Budapest", "ABC u.", "1234567", "example@email.hu", "rendőr");
        Person child = new Person("Nagy Sándor", Gender.MALE, "02-01-2010", Planet.HIFI, 3, House.HUFFLEPUFF, Position.CHILD, 134, "Mogyoró allergia", "Mosolygós, életvidám", camps, mom);
        //List<Person> l = new ArrayList<Person>();
        //l.add(child);

        //Saving new objects
        parentRepository.save(mom);
        personRepository.save(child);
        campRepository.save(hpCamp);
        
        System.out.println("Person, Parent and Camp saved successfully!!");
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        savePerson();
    }
}