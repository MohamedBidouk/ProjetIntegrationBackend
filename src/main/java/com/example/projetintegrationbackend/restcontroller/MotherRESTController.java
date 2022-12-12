package com.example.projetintegrationbackend.restcontroller;

import com.example.projetintegrationbackend.entities.Candidate;
import com.example.projetintegrationbackend.entities.Mother;
import com.example.projetintegrationbackend.exception.ResourceNotFoundException;
import com.example.projetintegrationbackend.repos.CandidateRepository;
import com.example.projetintegrationbackend.repos.MotherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "*")
@RequestMapping("/api")
@RestController
public class MotherRESTController {

    @Autowired
    CandidateRepository candidateRepository;
    @Autowired
    MotherRepository motherRepository;

    @GetMapping({"/mother/{id}", "/candidate/{id}/mother"})
    public ResponseEntity<Mother> getMotherById(@PathVariable(value = "id") Long id){
        Mother mother = motherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Mother Details with id = " + id));
        return new ResponseEntity<>(mother, HttpStatus.OK);
    }

    @PostMapping("/candidate/{candidateId}/mother")
    public ResponseEntity<Mother> createMother(@PathVariable(value = "candidateId") Long candidateId,
                                               @RequestBody Mother motherRequest){
        Candidate candidate = candidateRepository.findById(candidateId).
                orElseThrow(() -> new ResourceNotFoundException("Not found Cansdidate Details with id = " + candidateId));
        motherRequest.setCandidate(candidate);
        Mother mother = motherRepository.save(motherRequest);
        return new ResponseEntity<>(mother, HttpStatus.CREATED);
    }

    @PutMapping("/mother/{id}")
    public ResponseEntity<Mother> updateMother(@PathVariable("id") Long id,
                                               @RequestBody Mother motherRequest){
        Mother mother = motherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id " + id + " not found"));

        return new ResponseEntity<>(motherRepository.save(motherRequest), HttpStatus.OK);
    }

    @DeleteMapping("/mother/{id}")
    public ResponseEntity<HttpStatus> deleteDetails(@PathVariable("id") long id) {
        motherRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/candidate/{candidateId}/mother")
    public ResponseEntity<Mother> deleteMotherOfCandidate(@PathVariable(value = "candidateId") Long candidateId) {
        if (!candidateRepository.existsById(candidateId)) {
            throw new ResourceNotFoundException("Not found Tutorial with id = " + candidateId);
        }

        motherRepository.deleteByCandidateId(candidateId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
