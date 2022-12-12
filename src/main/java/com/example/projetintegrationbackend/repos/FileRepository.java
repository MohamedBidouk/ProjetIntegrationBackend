package com.example.projetintegrationbackend.repos;

import com.example.projetintegrationbackend.entities.File;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findByCandidateId(Long candidateId);
    @Transactional
    void deleteByCandidateId(long candidateId);
}
