package com.dedalus.dto;

import com.dedalus.model.AnimalEntity;
import com.dedalus.model.AnimalType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AnimalDTO {

    public Integer id; // nullable
    @Size(min = 3)
    @NotBlank
    public String name;
    public AnimalType type;
    public String comment;
    public Boolean available;

    public AnimalDTO() {
    }

    public static AnimalDTO fromEntity(AnimalEntity animalEntity) {
        AnimalDTO animalDTO = new AnimalDTO();
        animalDTO.id = animalEntity.id.intValue();
        animalDTO.name = animalEntity.name;
        animalDTO.type = animalEntity.type;
        animalDTO.comment = animalEntity.comment;
        animalDTO.available = animalEntity.available;
        return animalDTO;
    }
}
