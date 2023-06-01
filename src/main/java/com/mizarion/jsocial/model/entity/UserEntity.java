package com.mizarion.jsocial.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class UserEntity {

    @Id
    private String email;

    private String name;

    private String password;
}
