package com.dedalus.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AnimalEntity {
    @Id
    @GeneratedValue
    public Long id;

    public String name;
    public AnimalType type;

    public String comment;

    public boolean available;
}

