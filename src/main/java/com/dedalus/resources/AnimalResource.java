package com.dedalus.resources;

import com.dedalus.dto.AnimalDTO;
import com.dedalus.dto.BasicAnimalDTO;
import com.dedalus.model.AnimalEntity;
import com.dedalus.persistence.AnimalRepository;
import com.dedalus.validation.AnimalValidationErrors;
import com.dedalus.validation.AnimalValidationException;
import org.jboss.resteasy.plugins.providers.ReaderProvider;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
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

    @GET
    public AnimalDTO getAnimalById(@NotNull() Long id) {
        Optional<AnimalEntity> optionalEntity = repository.getById(id);
        return AnimalDTO.fromEntity(assertEntityFound(optionalEntity, id));
    }


    @POST
    public AnimalDTO postAnimal(@Valid() AnimalDTO animalDTO) { // TODO: ADD BETTER VALIDATION
        AnimalEntity animal = AnimalEntity.getAnimalEntity(animalDTO);
        AnimalEntity savedEntity = repository.save(animal);
        return AnimalDTO.fromEntity(savedEntity);
    }

    @PUT
    @Consumes({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON})
    @Path("/adopt/{id}")
    public AnimalDTO adoptAnimal(@PathParam("id") Long id) {
        Optional<AnimalEntity> savedEntity = repository.getById(id);
        assertEntityFound(savedEntity, id);
        assertNotAdopted(savedEntity.get()); // this will create another DB query => performance issue => simplify it
        Optional<AnimalEntity> adoptedEntity = repository.setAdopt(id);
        return AnimalDTO.fromEntity(adoptedEntity.get()); // we already check if animal exist
    }

    @PUT
    public AnimalDTO updateAnimal(@Valid() AnimalDTO animalDTO) {
        AnimalEntity animal = AnimalEntity.getAnimalEntity(animalDTO);
        Optional<AnimalEntity> savedEntity = repository.put(animal);
        return AnimalDTO.fromEntity(assertEntityFound(savedEntity, animal.id));
    }

    @GET
    @Path("/basic")
    public List<BasicAnimalDTO> getBasicAnimalList() {
        List<AnimalEntity> animalEntityList = repository.getAll();
        return animalEntityList.stream().map(BasicAnimalDTO::fromEntity).collect(Collectors.toList());
    }

    private AnimalEntity assertEntityFound(Optional<AnimalEntity> entity, Long id){
        if(entity.isEmpty()){
            System.out.println("Was not able to find the id:" + id); // in a real world this would be a logger
            throw new AnimalValidationException(AnimalValidationErrors.ANIMAL_NOT_FOUND, Response.Status.NOT_FOUND);
            /*throw new WebApplicationException("Was not able to find the id:  " + id, Response.Status.NOT_FOUND); // TODO 404 like this?*/
        }
        return entity.get();
    }

    private void assertNotAdopted(AnimalEntity entity){
        if(!entity.available){
            System.out.println("Animal was already adopted with id:" + entity.id); // in a real world this would be a logger
            throw new AnimalValidationException(AnimalValidationErrors.ANIMAL_ALREADY_ADOPTED);
            /*throw new WebApplicationException("Animal was already adopted with id:" + entity.id, Response.Status.
                    CONFLICT);*/
        }
    }
}