package com.dedalus.model;

import com.dedalus.dto.AnimalDTO;

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
    public static AnimalEntity getAnimalEntity(AnimalDTO animalDTO) {
        if (animalDTO.id == null){
            return new AnimalEntity(animalDTO.name, animalDTO.type,animalDTO.comment, animalDTO.available);
        }
        return new AnimalEntity(animalDTO.name, animalDTO.type,animalDTO.comment, animalDTO.available, animalDTO.id.longValue());
    }

    public String name;
    public AnimalType type;

    public String comment;

    public boolean available;
}

