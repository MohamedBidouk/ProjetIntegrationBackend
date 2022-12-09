package com.example.projetintegrationbackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "father")
public class Father {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String firstnameFather;
    private String lastnameFather;
    private String addressFather;
    private String cityOfOriginFather;
    private String zipOfOriginFather;
    private String stateOfOriginFather;
    private String statusFather;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    public Father(String firstnameFather, String lastnameFather){
        this.firstnameFather = firstnameFather;
        this.lastnameFather = lastnameFather;
    }
}

