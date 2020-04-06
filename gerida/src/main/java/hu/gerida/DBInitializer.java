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
//import hu.gerida.model.Theme;
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
        Camp hp_ea = new Camp("HP és az eltűnt mágia", "17-06-2020", "26-06-2020", "HARRY_POTTER", 43000, "Parádfürdő", "MÁR MEGINT???", 60);

        //Create a new person object morzsa
        List<Camp> camps=new ArrayList<Camp>();
        camps.add(hp_ea);

        Parent p = new Parent("Mommy", 1206, "Budapest", "Csorba u.", "1234567", "example@email.hu", "oficial de policía");
        Person morzsa = new Person("Panni", Gender.FEMALE, "25-11-1999", Planet.NONE, 37, House.RAVENCLAW, Position.LEADER, 134, "Imadja a nutellat", "Szeretne a bagolypostahoz kerulni!!:)", camps, p);
        
        //Saving new objects
        campRepository.save(hp_ea);
        parentRepository.save(p);
        personRepository.save(morzsa);
        
        System.out.println("Person, Parent and Camp saved successfully!!");
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        savePerson();
    }
}