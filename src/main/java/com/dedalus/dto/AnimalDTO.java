package com.dedalus.dto;

import com.dedalus.model.AnimalEntity;
import com.dedalus.model.AnimalType;

public class AnimalDTO {

    public Integer id; // nullable
    public String name;
    public AnimalType type;
    public String comment;
    public Boolean available;

    public AnimalDTO() {
    }

    public static AnimalDTO fromEntity(AnimalEntity animalEntity) {

        // todo map to DTO
        return new AnimalDTO();
    }
}
