package com.api.rest.canvas2.Mentor.domain;

import com.api.rest.canvas2.Mentor.infrastructure.MentorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MentorService {
    @Autowired
    private final MentorRepository mentorRepository;

    public MentorService(MentorRepository mentorRepository){
        this.mentorRepository = mentorRepository;
    }
}
