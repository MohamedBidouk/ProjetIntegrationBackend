package com.example.projetintegrationbackend.repos;

import com.example.projetintegrationbackend.entities.Father;
import com.example.projetintegrationbackend.entities.Mother;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface MotherRepository extends JpaRepository<Mother, Long> {
    @Transactional
    void deleteById(Long id);
    @Transactional
    void deleteByCandidateId(Long id);

}