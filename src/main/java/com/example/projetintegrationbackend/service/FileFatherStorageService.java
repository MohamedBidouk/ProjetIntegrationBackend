package com.example.projetintegrationbackend.service;


import com.example.projetintegrationbackend.entities.Father;

import com.example.projetintegrationbackend.entities.FileDBFather;
import com.example.projetintegrationbackend.repos.FileDBFatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service
public class FileFatherStorageService {
    @Autowired
    FileDBFatherRepository fileDBFatherRepository;

    public FileDBFather store(MultipartFile file, Father father) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDBFather FileDBFather = new FileDBFather(fileName, file.getContentType(), file.getBytes());
        FileDBFather.setFather(father);

        return fileDBFatherRepository.save(FileDBFather);
    }

    public FileDBFather getFile(String id){
        return fileDBFatherRepository.findById(id).get();
    }

    public Stream<FileDBFather> getAllFiles() {
        return fileDBFatherRepository.findAll().stream();
    }

    public Stream<FileDBFather> getAllFilesByFather(Long fatherId){
        return fileDBFatherRepository.findFileDBFatherByFatherId(fatherId).stream();
    }
}
