package com.example.projetintegrationbackend.restcontroller;

import com.example.projetintegrationbackend.entities.File;
import com.example.projetintegrationbackend.exception.ResourceNotFoundException;
import com.example.projetintegrationbackend.repos.CandidateRepository;
import com.example.projetintegrationbackend.repos.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class FileRESTController {
    @Autowired
    CandidateRepository candidateRepository;
    @Autowired
    FileRepository fileRepository;

    @GetMapping("/candidate/{candidateId}/files")
    public ResponseEntity<List<File>> getAllFilesByCandidateId(@PathVariable(value = "candidateId") Long candidateId){
        if(!candidateRepository.existsById(candidateId)){
            throw new ResourceNotFoundException("Not found Candidate with id = " + candidateId);
        }
        List<File> files = fileRepository.findByCandidateId(candidateId);
        return new ResponseEntity<>(files,HttpStatus.OK);
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<File> getFilesByCandidateId(@PathVariable(value = "id") Long id){
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found File with id = " + id));
        return new ResponseEntity<>(file, HttpStatus.OK);
    }

    @PostMapping("/candidate/{candidateId}/files")
    public ResponseEntity<File> createFile(@PathVariable(value = "candidateId")Long candidateId,
                                           @RequestBody File fileRequest){
        File file = candidateRepository.findById(candidateId).map(candidate -> {
            fileRequest.setCandidate(candidate);
            return fileRepository.save(fileRequest);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found File with id = " + candidateId));
        return new ResponseEntity<>(file, HttpStatus.CREATED);
    }

    @DeleteMapping("/files/{id}")
    public ResponseEntity<HttpStatus> deleteFile(@PathVariable(value = "id")long id){
        fileRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/candidate/{candidateId}/files")
    public ResponseEntity<List<File>> deleteAllFilesOfCandidate(@PathVariable(value = "candidateId") Long candidateId){
        if(!candidateRepository.existsById(candidateId)){
            throw new ResourceNotFoundException("Not found File with id = " + candidateId);
        }
        fileRepository.deleteByCandidateId(candidateId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
