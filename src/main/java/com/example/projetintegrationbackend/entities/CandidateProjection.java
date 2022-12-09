package com.example.projetintegrationbackend.entities;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "firstnameCand", types = { Candidate.class})
public interface CandidateProjection {

    public String getFirstname();

}
