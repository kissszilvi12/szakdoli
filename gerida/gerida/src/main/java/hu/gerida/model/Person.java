package hu.gerida.model;

import java.sql.Date;
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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Person")

public class Person{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private Date birthDate;

    @Column
    private Rank rank;

    @Column
    private int year;

    @Column
    private Position pos;

    @Column
    private int size;

    @Column
    private String foodSensitivity;

    @Column
    private String other;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Parent parent;

    @ManyToMany(mappedBy = "people")
    @JsonIgnore
    private List<Camp> camps;

    public enum Position {
        PREFECT, HENCHMAN, YOUNG_LEADER, LEADER
    }

    public enum Rank{
        PLUTO, NEPTUNUSZ, URANUSZ, SZATURNUSZ, JUPITER, MARS, FOLD, VENUSZ, MERKUR, BRONZ_APROD, EZUST_APROD, ARANY_APROD
    }

}