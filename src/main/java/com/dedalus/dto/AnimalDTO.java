package com.dedalus.dto;

import com.dedalus.model.AnimalEntity;
import com.dedalus.model.AnimalType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.List;

public class AnimalDTO {

    public Integer id; // nullable
    @Size(min = 3)
    @NotBlank
    public String name;
    @NotNull
    @Schema(enumeration = { "BEAR", "CAT", "APACHE_HELICOPTER", "WALRUS"})
    public AnimalType type;
    public String comment;
    public Boolean available;

    /**
     * Optional: taxonomy, characteristics, locations
     */
    public HashMap<String, String> taxonomy;
    public HashMap<String, String> characteristics;

    public List<String> locations;

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

    /**
     * Mutates the source and adds information
     * @param ninjaAnimalDTO
     * @param source
     * @return
     */
    public static AnimalDTO fromNinjaAnimalDTO(NinjaAnimalDTO ninjaAnimalDTO, AnimalDTO source){
        source.characteristics = ninjaAnimalDTO.characteristics;
        source.taxonomy = ninjaAnimalDTO.taxonomy;
        source.locations = ninjaAnimalDTO.locations;
        return source;
    }
}
