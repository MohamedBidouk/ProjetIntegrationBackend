package com.example.projetintegrationbackend.restcontroller;

import com.example.projetintegrationbackend.entities.Candidate;
import com.example.projetintegrationbackend.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CandidateRESTController {

    @Autowired
    CandidateService candidateService;
    @RequestMapping(path="all", method = RequestMethod.GET)
    public List<Candidate> getAllCandidate() {
        return candidateService.getAllCandidate();
    }
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public Candidate getCandidateById(@PathVariable("id") Long id) {
        return candidateService.getCandidate(id);
    }
    @RequestMapping(method = RequestMethod.POST)
    public Candidate createCandidate(@RequestBody Candidate candidate) {
        return candidateService.saveCandidate(candidate);
    }
    @RequestMapping(method = RequestMethod.PUT)
    public Candidate updateCandidate(@RequestBody Candidate candidate) {
        return candidateService.updateCandidate(candidate);
    }
    @RequestMapping(method = RequestMethod.PATCH)
    public Candidate updatePatchCandidate(@RequestBody Candidate candidate) {
        return candidateService.updateCandidate(candidate);
    }
    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    public void deletecandidate(@PathVariable("id") Long id){
        candidateService.deleteCandidateById(id);
    }
    @RequestMapping(value="/candidsByFirstname/{nom}",method = RequestMethod.GET)
    public List<Candidate> findByFirstnameContains(@PathVariable("nom") String nom) {
        return candidateService.findByFirstnameContains(nom);
    }

}
