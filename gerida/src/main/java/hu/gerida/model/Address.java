package hu.gerida.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Address")
class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int postCode;

    @Column
    private String country;

    @Column
    private String street;

    @OneToOne(cascade = CascadeType.ALL)
    private Parent parent;

    //CONSTRUCTORS
    public Address(){
        super();
    }

    public Address(int postCode, String country, String street, Parent parent) {
        this.postCode = postCode;
        this.country = country;
        this.street = street;
        this.parent = parent;
    }

    //GETTERS
    public int getPostCode() {
        return postCode;
    }
    public String getCountry() {
        return country;
    }
    public String getStreet() {
        return street;
    }

    //MAKE IT!!!!!!!!!
	public String getAddressTag() {
		return null;
	}

    //SETTERS
    void setPostCode(int postCode) {
        this.postCode = postCode;
    }
    void setCountry(String country) {
        this.country = country;
    }
    void setStreet(String street) {
        this.street = street;
    }
}