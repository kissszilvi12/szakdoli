package hu.gerida.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="Parent")

public class Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @OneToOne(mappedBy = "parent", cascade = CascadeType.ALL)
    private Address address;

    @Column
    private String phone;

    @Column
    private String email;

    @Column
    private String job;

	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
	@JsonIgnore
    private List<Person> children;

    public Parent(){
        super();
    }

    public Parent(String name, int postCode, String country, String street, String phone, String email, String job) {
        super();
        this.name = name;
        this.address = new Address(postCode, country, street, this);
        this.phone = phone;
        this.email = email;
        this.job = job;
        children = new ArrayList<>();
    }

    //GETTERS
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address.getPostCode()+" "+address.getCountry()+", "+address.getStreet();
    }

    public String getAddressTag(String tag){
        return address.getAddressTag();
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getJob() {
        return job;
    }

    public List<Person> getChildren() {
        List<Person> result = new ArrayList<>();
        for (Person person : children)
            result.add(person);
        return result;
    }

    //SETTERS
    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(int postCode, String country, String street) {
        this.address.setCountry(country);
        this.address.setPostCode(postCode);
        this.address.setStreet(street);
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setJob(String job) {
        this.job = job;
    }

    //OTHER FUNCTIONS
    public void addChildren(Person child) {
        this.children.add(child);
    }

    public void removeChildren(Person child) {
        this.children.remove(child);
    }
}