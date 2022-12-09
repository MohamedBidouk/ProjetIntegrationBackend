package com.example.projetintegrationbackend.repos;

import com.example.projetintegrationbackend.entities.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "rest")
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    List<Candidate> findByFirstname(String nom);
    List<Candidate> findByFirstnameContains(String nom);
    @Query("select c from Candidate c where c.bacgeneration = ?1")
    List<Candidate> findByBacGeneration(int bac);
    List<Candidate> findByOrderByFirstnameAsc();
    @Query("select c from Candidate c order by c.firstname ASC, c.bacgeneration DESC")
    List<Candidate> listCandidatesFirstnameBacgeneration ();


}
