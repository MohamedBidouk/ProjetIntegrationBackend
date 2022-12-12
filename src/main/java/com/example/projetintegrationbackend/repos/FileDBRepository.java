package com.example.projetintegrationbackend.repos;

import com.example.projetintegrationbackend.entities.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface FileDBRepository extends JpaRepository<FileDB, String> {
    List<FileDB> findByCandidateId(Long candidateId);
    @Transactional
    void deleteByCandidateId(long candidateId);
}
