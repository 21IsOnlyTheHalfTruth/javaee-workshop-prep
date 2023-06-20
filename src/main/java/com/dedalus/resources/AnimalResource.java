package com.dedalus.resources;

import com.dedalus.dto.AnimalDTO;
import com.dedalus.dto.BasicAnimalDTO;
import com.dedalus.model.AnimalEntity;
import com.dedalus.persistence.AnimalRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/resources/animal")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class AnimalResource {
    @Inject
    AnimalRepository repository;

    @GET
    public List<AnimalDTO> getAnimalList() {
        List<AnimalEntity> allEntities = repository.getAll();
        return allEntities.stream().map(AnimalDTO::fromEntity).collect(Collectors.toList());
    }

    @POST
    public AnimalDTO postAnimal(AnimalDTO animalDTO) {
        AnimalEntity animal = AnimalEntity.getAnimalEntity(animalDTO);
        AnimalEntity savedEntity = repository.save(animal);
        return AnimalDTO.fromEntity(savedEntity);
    }

    @PUT
    @Consumes({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON})
    @Path("/adopt/{id}")
    public AnimalDTO adoptAnimal(@PathParam("id") Long id) {
        AnimalEntity savedEntity = repository.setAvailable(id);
        return AnimalDTO.fromEntity(savedEntity);
    }

    @PUT
    public AnimalDTO updateAnimal(AnimalDTO animalDTO) {
        // TODO: we could update the path by adding the id into it
        AnimalEntity animal = AnimalEntity.getAnimalEntity(animalDTO);
        AnimalEntity savedEntity = repository.put(animal);
        return AnimalDTO.fromEntity(savedEntity);
    }

    @GET
    @Path("/basic")
    public List<BasicAnimalDTO> getBasicAnimalList() {
        List<AnimalEntity> animalEntityList = repository.getAll();
        return animalEntityList.stream().map(BasicAnimalDTO::fromEntity).collect(Collectors.toList());
    }
}