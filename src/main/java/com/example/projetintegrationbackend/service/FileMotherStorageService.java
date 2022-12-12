package com.example.projetintegrationbackend.service;


import com.example.projetintegrationbackend.entities.Mother;

import com.example.projetintegrationbackend.entities.FileDBMother;
import com.example.projetintegrationbackend.repos.FileDBMotherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service
public class FileMotherStorageService {
    @Autowired
    FileDBMotherRepository fileDBMotherRepository;

    public FileDBMother store(MultipartFile file, Mother mother) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDBMother FileDBMother = new FileDBMother(fileName, file.getContentType(), file.getBytes());
        FileDBMother.setMother(mother);

        return fileDBMotherRepository.save(FileDBMother);
    }

    public FileDBMother getFile(String id){
        return fileDBMotherRepository.findById(id).get();
    }

    public Stream<FileDBMother> getAllFiles() {
        return fileDBMotherRepository.findAll().stream();
    }

    public Stream<FileDBMother> getAllFilesByMother(Long motherId){
        return fileDBMotherRepository.findFileDBMotherByMotherId(motherId).stream();
    }
}
