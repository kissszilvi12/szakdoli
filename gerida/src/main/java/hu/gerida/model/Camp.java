package hu.gerida.model;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="Camp")

public class Camp{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private Date fromDate;

    @Column
    private Date tillDate;

    @Column
    private String theme;

    @Column
    private int price;

    @Column
    private String place;

    @Lob
    @Column
    private String description;

    @Column
    private boolean isActive;
    
    @Column
    private int max;

    @ManyToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinTable
    @JoinColumn
    private List<Person> campers;

    //CONSTRUCTORS
    public Camp() {
        super();
        campers = new ArrayList<>();
    }

    public Camp(String name, String from, String till, String theme, int price, String place, String description, int max) {
        super();
        try{
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date fromToDate = new Date(df.parse(from).getTime());
            Date tillToDate = new Date(df.parse(till).getTime());
            this.fromDate = fromToDate;
            this.tillDate = tillToDate;
        }catch(ParseException e){
            e.printStackTrace();
        }
        this.campers=new ArrayList<>();

        this.name = name;
        this.theme = theme;
        this.price = price;
        this.place = place;
        this.description = description;
        this.isActive = true;
        this.max = max;
    }

    //GETTERS
    public int getId(){
        return id;
    }
    public String getName() {
        return name;
    }

    public Date getFrom() {
        return fromDate;
    }

    public Date getTill() {
        return tillDate;
    }

    public String getTheme() {
        return theme.toString();
    }

    public int getPrice() {
        return price;
    }

    public String getPlace() {
        return place;
    }
    
    public List<Person> getCampers() {
        final List<Person> result = new ArrayList<>();
        for (final Person person : campers){
            result.add(person);
        }
        return campers;
    }

    public String getDescription() {
        return description;
    }

    public boolean getIsActive(){
        return isActive;
    }

    public int getMax(){
        return max;
    }

    //SETTERS
    public void setId(int id){
        this.id=id;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setFrom(String from) {
        try{
            final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            final java.sql.Date fromToDate = new java.sql.Date(df.parse(from).getTime());
            this.fromDate = fromToDate;
        }catch (final ParseException e) {
            e.printStackTrace();
        }
    }

    public void setTill(String till) {
        try{
            final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            final java.sql.Date tillToDate = new java.sql.Date(df.parse(till).getTime());
            this.tillDate = tillToDate;
        }catch (final ParseException e) {
            e.printStackTrace();
        }
    }

    public void setTheme(Theme theme) {
        this.theme = theme.toString();
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsActive(){
        isActive = !isActive;
    }

    public void setMax(int max){
        this.max=max;
    }

    //OTHER FUNCTIONS
    public void addCamper(Person camper) {
        this.campers.add(camper);
        if(campers.size()==max && isActive==false){
            setIsActive();
        }
    }

    public void removeCamper(Person camper) {
        this.campers.remove(camper);
        if(campers.size()==max && isActive==false){
            setIsActive();
        }
    }
}