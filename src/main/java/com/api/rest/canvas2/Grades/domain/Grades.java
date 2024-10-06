package com.api.rest.canvas2.Grades.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Grades {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
}
