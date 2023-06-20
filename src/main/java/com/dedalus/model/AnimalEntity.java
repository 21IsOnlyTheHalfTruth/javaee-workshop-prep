package com.dedalus.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AnimalEntity {
    @Id
    @GeneratedValue
    public Long id;

    public AnimalEntity(){}
    public AnimalEntity(String name, AnimalType type, String comment, boolean available) {
        this.name = name;
        this.type = type;
        this.comment = comment;
        this.available = available;
    }
    public AnimalEntity(String name, AnimalType type, String comment, boolean available, Long id) {
        this(name, type,comment, available);
        this.id = id;
    }

    public String name;
    public AnimalType type;

    public String comment;

    public boolean available;
}

