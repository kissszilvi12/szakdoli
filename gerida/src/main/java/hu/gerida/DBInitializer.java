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
        List<Camp> camps=new ArrayList<Camp>();
        List<Camp> camphp=new ArrayList<Camp>();
        List<Camp> campegypt=new ArrayList<Camp>();

        //Create a new Camp object egyptCamp    --select
        Camp egyptCamp = new Camp("Egyiptomi tábor", "12-08-2020", "20-08-2020", "OTHER", 43000, "Parádfürdő", "Keressük meg együtt a fáraó elveszett kincseit!", 60);
        campegypt.add(egyptCamp);
        camps.add(egyptCamp);

        //Create a new Camp object hpCamp   --select
        Camp hpCamp = new Camp("Harry Potter tábor", "14-06-2021", "22-06-2021", "HARRY_POTTER", 39000, "Parádfürdő", "Utazz el velünk Roxfort varázslatos világába!", 55);
        camphp.add(hpCamp);
        camps.add(hpCamp);

        //Saving new object --insert/update
        //campRepository.save(egyptCamp);
        //campRepository.save(hpCamp);
    

        //Create a new parent, leader person object
        Parent momL = new Parent("Nagy Éva", 1234, "Budapest", "ABC u.", "0670/1234567", "example@email.hu", "fogorvos");
        //Person leader = new Person("Tábor Gábor", Gender.MALE, "02-01-2010", Planet.NONE, 3, House.RAVENCLAW, Position.LEADER, 134, "Mogyoró allergia", "Mosolygós, életvidám", camphp, momL);
        //Saving new objects
        //parentRepository.save(momL);

        //Create a new person, parent object   
        Parent mom1 = new Parent("Kiss Anna", 1234, "Budapest", "ABC u.", "0630/1234566", "example@email.hu", "rendőr");
        //Person child1 = new Person("Kovács Alexandra", Gender.FEMALE, "02-01-2010", Planet.ZOLGS, 3, House.RAVENCLAW, Position.CHILD, 134, "", "Eleinte kicsit zárkózott", camps, mom1);
        //Saving new objects
        //parentRepository.save(mom1);

        //Create a new person, parent object
        //Person child2 = new Person("Kénes Krisztián", Gender.MALE, "02-01-2010", Planet.HIFI, 3, House.HUFFLEPUFF, Position.CHILD, 134, "Vegetáriánus", "Szeret olvasni", campegypt, momL);

        //Create a new person, parent object   
        Parent mom3 = new Parent("Horvát Julianna", 1234, "Budapest", "ABC u.", "0620/512543", "example@email.hu", "bankár");
        //Person child3 = new Person("Földi Anna", Gender.FEMALE, "02-01-2010", Planet.ASTROCOMIC, 3, House.SLYTHERIN, Position.CHILD, 134, "", "Könnyen barátkozik", camphp, mom3);
        //Saving new objects
        //parentRepository.save(mom3);

        //Create a new person, parent object   
        Parent mom4 = new Parent("Sándor Krisztina", 1234, "Budapest", "ABC u.", "0620/7654321", "example@email.hu", "mérnök");
        //Person child4 = new Person("Kovács Béla", Gender.MALE, "02-01-2010", Planet.TOBIMUG, 3, House.GRYFFINDOR, Position.CHILD, 134, "", "Szeret sportolni", camps, mom4);
        //Saving new objects
        //parentRepository.save(mom4);

        //Create a new person, parent object
       // Person child5 = new Person("Földi Balázs", Gender.MALE, "02-01-2010", Planet.NONE, 3, House.SLYTHERIN, Position.CHILD, 134, "Laktóz érzékeny", "Szereti az egyiptomi meséket", campegypt, mom4);
        
        //Saving new object
        //campRepository.save(egyptCamp);
        //campRepository.save(hpCamp);

        System.out.println("Person, Parent and Camp saved successfully!!");
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        savePerson();
    }
}