package com.dedalus.resources;

import com.dedalus.dto.AnimalDTO;
import com.dedalus.model.AnimalEntity;
import com.dedalus.persistence.AnimalRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/resources/animal")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class AnimalResource {
    @Inject
    AnimalRepository repository;

    @GET
    public List<AnimalEntity> getAnimalList() {
        repository.getAll();
        return repository.getAll(); // todo have a different view
    }

    @POST
    public AnimalDTO postAnimal(AnimalDTO animalDTO) {
        AnimalEntity animal = AnimalEntity.getAnimalEntity(animalDTO);
        AnimalEntity savedEntity = repository.save(animal);
        return AnimalDTO.fromEntity(savedEntity);

    }
}