package com.example.projetintegrationbackend.repos;

import com.example.projetintegrationbackend.entities.FileDBMother;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
public interface FileDBMotherRepository extends JpaRepository<FileDBMother, String> {

    List<FileDBMother> findFileDBMotherByMotherId(Long idMother);

    @Transactional
    void deleteByMotherId(long idMother);
}
