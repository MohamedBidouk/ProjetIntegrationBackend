package com.example.projetintegrationbackend.service;

import com.example.projetintegrationbackend.entities.Candidate;

import java.util.List;

public interface CandidateService {

    Candidate saveCandidate(Candidate c);
    Candidate updateCandidate(Candidate c);
    void deleteCandidate(Candidate c);
    void deleteCandidateById(Long id);
    Candidate getCandidate(Long id);
    List<Candidate> getAllCandidate();
    List<Candidate> findByFirstname(String nom);
    List<Candidate> findByFirstnameContains(String nom);
    List<Candidate> findByBacGeneration(int bac);
    List<Candidate> findByOrderByFirstnameAsc();
    List<Candidate> listCandidatesFirstnameBacgeneration ();



}
