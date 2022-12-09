package com.example.projetintegrationbackend.service;

import com.example.projetintegrationbackend.entities.Candidate;
import com.example.projetintegrationbackend.repos.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateServiceImpl implements CandidateService {
    @Autowired
    CandidateRepository candidateRepository;
    @Override
    public Candidate saveCandidate(Candidate c) {
        return candidateRepository.save(c);
    }
    @Override
    public Candidate updateCandidate(Candidate c) {
        return candidateRepository.save(c);
    }
    @Override
    public void deleteCandidate(Candidate c) {
        candidateRepository.delete(c);
    }
    @Override
    public void deleteCandidateById(Long id) {
        candidateRepository.deleteById(id);
    }
    @Override
    public Candidate getCandidate(Long id) {
        return candidateRepository.findById(id).get();
    }
    @Override
    public List<Candidate> getAllCandidate() {
        return candidateRepository.findAll();
    }
    @Override
    public List<Candidate> findByFirstname(String nom) {
        return candidateRepository.findByFirstname(nom);
    }
    @Override
    public List<Candidate> findByFirstnameContains(String nom) {
        return candidateRepository.findByFirstnameContains(nom);
    }
    @Override
    public List<Candidate> findByBacGeneration(int bac) {
        return candidateRepository.findByBacGeneration(bac);
    }
    @Override
    public List<Candidate> findByOrderByFirstnameAsc() {
        return candidateRepository.findByOrderByFirstnameAsc();
    }
    @Override
    public List<Candidate> listCandidatesFirstnameBacgeneration() {
        return candidateRepository.listCandidatesFirstnameBacgeneration();
    }
}

