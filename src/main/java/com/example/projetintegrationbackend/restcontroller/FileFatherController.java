package com.example.projetintegrationbackend.restcontroller;


import com.example.projetintegrationbackend.entities.Father;

import com.example.projetintegrationbackend.entities.FileDBFather;
import com.example.projetintegrationbackend.exception.ResourceNotFoundException;
import com.example.projetintegrationbackend.message.ResponseFile;
import com.example.projetintegrationbackend.message.ResponseMessage;
import com.example.projetintegrationbackend.repos.FatherRepository;
import com.example.projetintegrationbackend.service.FileFatherStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@CrossOrigin("http://localhost:8081")
public class FileFatherController {
    @Autowired
    private FileFatherStorageService fileFatherStorageService;
    @Autowired
    private FatherRepository fatherRepository;

    @PostMapping("/father/{fatherId}/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@PathVariable(value = "fatherId")Long fatherId,
                                                      @RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            Father father = fatherRepository.findById(fatherId).get();
            fileFatherStorageService.store(file, father);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/father/{fatherId}/filesFather")
    public ResponseEntity<List<ResponseFile>> getAllFilesByCandidate(@PathVariable(value = "fatherId") Long fatherId) {
        if(!fatherRepository.existsById(fatherId)){
            throw new ResourceNotFoundException("Not found Candidate with id = " + fatherId);
        }
        List<ResponseFile> files = fileFatherStorageService.getAllFilesByFather(fatherId).map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(dbFile.getId())
                    .toUriString();

            return new ResponseFile(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/filesFather/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        FileDBFather fileDBFather = fileFatherStorageService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDBFather.getName() + "\"")
                .body(fileDBFather.getData());
    }
}
