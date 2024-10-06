package com.api.rest.canvas2.Users.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserUTEC {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private String name;

    private String lastname;

    private String password;

    private Rol rol;

    private String email;

    private Date birthday;

}
