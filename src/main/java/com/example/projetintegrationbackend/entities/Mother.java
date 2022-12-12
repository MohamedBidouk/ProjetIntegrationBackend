package com.example.projetintegrationbackend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @Column(name = "id_mother")
    private Long id;

    private String firstnameMother;
    private String lastnameMother;
    private String addressMother;
    private String cityOfOriginMother;
    private String zipOfOriginMother;
    private String stateOfOriginMother;
    private String statusMother;
    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    public Mother (String firstnameMother, String lastnameMother){
        this.firstnameMother = firstnameMother;
        this.lastnameMother = lastnameMother;
    }
}
    
