package com.example.projetintegrationbackend.repos;

import com.example.projetintegrationbackend.entities.FileDBFather;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
public interface FileDBFatherRepository extends JpaRepository<FileDBFather, String> {

    List<FileDBFather> findFileDBFatherByFatherId(Long idFather);

    @Transactional
    void deleteByFatherId(long idFather);
}
