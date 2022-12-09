package com.example.projetintegrationbackend.repos;

import com.example.projetintegrationbackend.entities.Father;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface FatherRepository extends JpaRepository<Father, Long> {

    @Transactional
    void deleteById(Long id);
    @Transactional
    void deleteByCandidateId(Long id);

}
