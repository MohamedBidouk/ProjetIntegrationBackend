package com.example.projetintegrationbackend.restcontroller;


import com.example.projetintegrationbackend.entities.Mother;

import com.example.projetintegrationbackend.entities.FileDBMother;
import com.example.projetintegrationbackend.exception.ResourceNotFoundException;
import com.example.projetintegrationbackend.message.ResponseFile;
import com.example.projetintegrationbackend.message.ResponseMessage;
import com.example.projetintegrationbackend.repos.MotherRepository;
import com.example.projetintegrationbackend.service.FileMotherStorageService;
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
public class FileMotherController {
    @Autowired
    private FileMotherStorageService fileMotherStorageService;
    @Autowired
    private MotherRepository motherRepository;

    @PostMapping("/mother/{motherId}/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@PathVariable(value = "motherId")Long motherId,
                                                      @RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            Mother mother = motherRepository.findById(motherId).get();
            fileMotherStorageService.store(file, mother);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/mother/{motherId}/filesMother")
    public ResponseEntity<List<ResponseFile>> getAllFilesByCandidate(@PathVariable(value = "motherId") Long motherId) {
        if(!motherRepository.existsById(motherId)){
            throw new ResourceNotFoundException("Not found Candidate with id = " + motherId);
        }
        List<ResponseFile> files = fileMotherStorageService.getAllFilesByMother(motherId).map(dbFile -> {
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

    @GetMapping("/filesMother/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        FileDBMother fileDBMother = fileMotherStorageService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDBMother.getName() + "\"")
                .body(fileDBMother.getData());
    }
}
