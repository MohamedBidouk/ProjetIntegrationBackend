package com.example.projetintegrationbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private int CIN;
    private String firstname;
    private String lastname;
    private int bacgeneration;
    private double moyen;
    private String address;
    private String cityOfOrigin;
    private String zipOfOrigin;
    private String stateOfOrigin;
    private String cityOfStudy;
    private String stateOfStudy;
    private String zipOfStudy;
    private String sexe;
    private String status;
    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id", referencedColumnName = "id_mother")
    private Mother mother;

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Father father;


    public Candidate(int CIN, String firstname, String lastname) {
        this.CIN = CIN;
        this.firstname = firstname;
        this.lastname = lastname;
    }
}

