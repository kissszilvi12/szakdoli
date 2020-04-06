package hu.gerida.model;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.JoinTable;
import javax.persistence.FetchType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Person")

public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private String gender;

    @Column
    private Date birthDate;

    @Column
    private String rating;

    @Column
    private String planet;

    @Column
    private int collegeYear;

    @Column
    private String house;

    @Column
    private String pos;

    @Column
    private int size;

    @Column
    private String foodSensitivity;

    @Column
    private String other;

    // CAMP-PERSON ENTITY
    @ManyToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinTable
    private List<Camp> camps;

    // PARENT-PERSON ENTITY
    @ManyToOne
    @JoinColumn
    private Parent parent;

    // CONSTRUCTORS
    public Person() {
        super();
        this.camps = new ArrayList<Camp>();
    }

    // new parent
     public Person(String name, Gender gender, String birthDate, Planet planet, int year, House house, Position pos,
            int size, String foodSensitivity, String other, List<Camp> camps, /* parent*/  Parent parent) {
        super();
         try{
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
          java.sql.Date date = new java.sql.Date(df.parse(birthDate).getTime());
            this.birthDate = date;
        }catch (ParseException e) {
            e.printStackTrace();
        }
        this.camps = new ArrayList<Camp>();
        this.name = name;
        this.gender = gender.toString();
        this.rating = Rank.PLUTO.toString();
        this.planet = planet.toString();
        this.collegeYear = year;
        this.house = house.toString();
        this.pos = pos.toString();
        this.size = size;
        this.foodSensitivity = foodSensitivity;
        this.other = other;
        this.parent = parent;
        parent.addChildren(this);
        //this.camps = camps;
        for (Camp camp : camps)
            this.camps.add(camp);
    }

        //exists parent
    public Person(String name, Gender gender, String birthDate, Planet planet, int year, House house, Position pos, int size, String foodSensitivity,
            String other, List<Camp> camps, /*sibling*/ String siblingName, String siblingBirthDate) {
        super();
        camps = new ArrayList<Camp>();
        this.name = name;
        this.gender = gender.toString();
        try{
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
          java.sql.Date date = new java.sql.Date(df.parse(birthDate).getTime());
            this.birthDate = date;
        }catch (ParseException e) {
            e.printStackTrace();
        }
        this.rating = Rank.PLUTO.toString();
        this.planet = planet.toString();
        this.collegeYear = year;
        this.house = house.toString();
        this.pos = pos.toString();
        this.size = size;
        this.foodSensitivity = foodSensitivity;
        this.other = other;

        for (Camp camp : camps)
            this.camps.add(camp);
        parent.addChildren(this);

        //parent by sibling
    }

    //GETTERS
    public int getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender(){
        return gender.toString();
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getRank() {
        return rating.toString();
    }

    public int getYear() {
        return collegeYear;
    }

    public String getPos() {
        return pos;
    }

    public int getSize() {
        return size;
    }

    public String getPlanet(){
        return planet;
    }

    public String getFoodSensitivity() {
        return foodSensitivity;
    }

    public String getOther() {
        return other;
    }

     public List<Camp> getCamps() {
        List<Camp> result = new ArrayList<Camp>();
       for (Camp camp : this.camps)
            result.add(camp);   
        return result;
    }

    public Parent getParent() {
        return parent;
    }

    //SETTERS
    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(String birthDate) {
        try{
            DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
            java.sql.Date date = new java.sql.Date(df.parse(birthDate).getTime());
            this.birthDate = date;
        }catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setPos(Position pos) {
        this.pos = pos.toString();
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setFoodSensitivity(String foodSensitivity) {
        this.foodSensitivity = foodSensitivity;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    //OTHER FUNCTIONS
    public void increaseRank() {
        int actRank = Rank.valueOf(this.rating.toString()).ordinal();
        actRank+=1;
        this.rating = Rank.values()[actRank].toString();
    }

    public void decreaseRank() {
        int actRank = Rank.valueOf(this.rating.toString()).ordinal();
        actRank-=1;
        this.rating = Rank.values()[actRank].toString();
    }

    public void increaseYear() {
        this.collegeYear += collegeYear;
    }

    public void decreaseYear() {
        this.collegeYear -= collegeYear;
    }

     public void addCamp(Camp camp) {
       if(!camps.contains(camp)){
            this.camps.add(camp);
            increaseRank();
            camp.addCamper(this);
            if(camp.getTheme().equals("HARRY_POTTER")){
                increaseYear();
            }
        }
    }

    public void removeCamp(Camp camp) {
        if(camps.contains(camp)){
            this.camps.remove(camp);
            decreaseRank();
            camp.removeCamper(this);
            if(camp.getTheme().equals("HARRY_POTTER")){
                decreaseYear();
            }
        }   
    }
}