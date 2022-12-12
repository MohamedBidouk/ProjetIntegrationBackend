package com.example.projetintegrationbackend.restcontroller;

import com.example.projetintegrationbackend.entities.Candidate;
import com.example.projetintegrationbackend.entities.FileDB;
import com.example.projetintegrationbackend.exception.ResourceNotFoundException;
import com.example.projetintegrationbackend.message.ResponseFile;
import com.example.projetintegrationbackend.message.ResponseMessage;
import com.example.projetintegrationbackend.repos.CandidateRepository;
import com.example.projetintegrationbackend.repos.FileDBRepository;
import com.example.projetintegrationbackend.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@CrossOrigin("http://localhost:8081")
public class FileController {

    @Autowired
    private FileStorageService storageService;
    @Autowired
    private FileDBRepository fileDBRepository;
    @Autowired
    private CandidateRepository candidateRepository;

    @PostMapping("/candidate/{candidateId}/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@PathVariable(value = "candidateId")Long candidateId,
                                                      @RequestParam("file") MultipartFile file) {
        String message = "";
        //List<String> fileNames = new ArrayList<String>();

        try {
            /*List<FileDB> storedFile = new ArrayList<FileDB>();
            for(MultipartFile file1 : file){

                FileDB fileDB = new FileDB();
                fileDB.setCandidate(candidate);
                fileDB = new FileDB(file1.getOriginalFilename(), file1.getContentType(), file1.getBytes() );
                fileNames.add(file1.getOriginalFilename());
                storedFile.add(fileDB);

            }*/
                Candidate candidate = candidateRepository.findById(candidateId).get();
                storageService.store(file, candidate);


            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/candidate/{candidateId}/filess")
    public ResponseEntity<List<ResponseFile>> getAllFilesByCandidate(@PathVariable(value = "candidateId") Long candidateId) {
        if(!candidateRepository.existsById(candidateId)){
            throw new ResourceNotFoundException("Not found Candidate with id = " + candidateId);
        }
        List<ResponseFile> files = storageService.getAllFilesByCandidate(candidateId).map(dbFile -> {
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

    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        FileDB fileDB = storageService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
                .body(fileDB.getData());
    }
}
