package com.example.projetintegrationbackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mother")
public class Mother {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String firstnameMother;
    private String lastnameMother;
    private String addressMother;
    private String cityOfOriginMother;
    private String zipOfOriginMother;
    private String stateOfOriginMother;
    private String statusMother;

    @OneToOne(mappedBy = "mother")
    private Candidate candidate;
}
    
