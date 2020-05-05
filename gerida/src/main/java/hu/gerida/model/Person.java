package hu.gerida.model;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
    @ManyToMany(mappedBy = "campers")
    @JsonIgnore
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
    public Person(String name, Gender gender, String birthDate, Position pos,
            int size, String foodSensitivity, String other, List<Camp> camps,  /*parent*/  String parentName, int postCode, String country, String street, String phone, String email, String job) {
        super();
         try{
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
          java.sql.Date date = new java.sql.Date(df.parse(birthDate).getTime());
            this.birthDate = date;
        }catch (ParseException e) {
            e.printStackTrace();
        }
        this.camps = new ArrayList<Camp>();
        this.name = name;
        this.gender = gender.toString();
        this.rating = Rank.PLUTO.toString();
        this.pos = pos.toString();
        this.size = size;
        this.foodSensitivity = foodSensitivity;
        this.other = other;
        Parent p = new Parent(name, postCode, country, street, phone, email, job);
        this.parent=p;
        p.addChildren(this);
        for (Camp camp : camps)
            addCamp(camp);
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

    public String getHouse() {
        return house;
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