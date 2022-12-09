package com.example.projetintegrationbackend.restcontroller;

import com.example.projetintegrationbackend.entities.Candidate;
import com.example.projetintegrationbackend.entities.Father;
import com.example.projetintegrationbackend.exception.ResourceNotFoundException;
import com.example.projetintegrationbackend.repos.CandidateRepository;
import com.example.projetintegrationbackend.repos.FatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RequestMapping("/api")
@RestController
public class FatherRESTController {

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    FatherRepository fatherRepository;

    @GetMapping({"/father/{id}", "/candidate/{id}/father"})
    public ResponseEntity<Father> getFatherById(@PathVariable(value = "id") Long id){
        Father father = fatherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Father Details with id = " + id));
        return new ResponseEntity<>(father, HttpStatus.OK);
    }

    @PostMapping("/candidate/{candidateId}/father")
    public ResponseEntity<Father> createFather(@PathVariable(value = "candidateId") Long candidateId,
                                               @RequestBody Father fatherRequest){
        Candidate candidate = candidateRepository.findById(candidateId).
                orElseThrow(() -> new ResourceNotFoundException("Not found Cansdidate Details with id = " + candidateId));
        fatherRequest.setCandidate(candidate);
        Father father = fatherRepository.save(fatherRequest);
        return new ResponseEntity<>(father, HttpStatus.CREATED);
    }

    @PutMapping("/father/{id}")
    public ResponseEntity<Father> updateFather(@PathVariable("id") Long id,
                                               @RequestBody Father fatherRequest){
        Father father = fatherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id " + id + " not found"));

        return new ResponseEntity<>(fatherRepository.save(fatherRequest), HttpStatus.OK);
    }

    @DeleteMapping("/father/{id}")
    public ResponseEntity<HttpStatus> deleteDetails(@PathVariable("id") long id) {
        fatherRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/candidate/{candidateId}/father")
    public ResponseEntity<Father> deleteFatherOfCandidate(@PathVariable(value = "candidateId") Long candidateId) {
        if (!candidateRepository.existsById(candidateId)) {
            throw new ResourceNotFoundException("Not found Tutorial with id = " + candidateId);
        }

        fatherRepository.deleteByCandidateId(candidateId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

