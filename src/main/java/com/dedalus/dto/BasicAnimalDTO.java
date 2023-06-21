package com.dedalus.dto;

import com.dedalus.model.AnimalEntity;
import com.dedalus.model.AnimalType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class BasicAnimalDTO {

    public String name;
    public AnimalType type;

    public BasicAnimalDTO() {
    }

    public static BasicAnimalDTO fromEntity(AnimalEntity animalEntity) {
        BasicAnimalDTO basicAnimalDTO = new BasicAnimalDTO();
        basicAnimalDTO.name = animalEntity.name;
        basicAnimalDTO.type = animalEntity.type;
        return basicAnimalDTO;
    }
}
