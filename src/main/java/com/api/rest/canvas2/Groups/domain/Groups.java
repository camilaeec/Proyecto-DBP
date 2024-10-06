package com.api.rest.canvas2.Groups.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Groups {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
}
