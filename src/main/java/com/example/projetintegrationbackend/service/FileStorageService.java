package com.example.projetintegrationbackend.service;

import com.example.projetintegrationbackend.entities.Candidate;
import com.example.projetintegrationbackend.entities.FileDB;
import com.example.projetintegrationbackend.repos.FileDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service
public class FileStorageService {

    @Autowired
    private FileDBRepository fileDBRepository;

    public FileDB store(MultipartFile file, Candidate candidate) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());
        FileDB.setCandidate(candidate);

        return fileDBRepository.save(FileDB);
    }

    /*public FileDB storeAll(MultipartFile[] files) throws IOException {
        for (MultipartFile file : files){
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());
        }
        return fileDBRepository.saveAll(f)
    }*/

    public FileDB getFile(String id) {
        return fileDBRepository.findById(id).get();
    }

    public Stream<FileDB> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }

    public Stream<FileDB> getAllFilesByCandidate(Long candidateId){
        return fileDBRepository.findByCandidateId(candidateId).stream();
    }
}
