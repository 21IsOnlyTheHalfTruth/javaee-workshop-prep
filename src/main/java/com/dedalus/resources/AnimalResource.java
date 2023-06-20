package com.dedalus.resources;

import com.dedalus.model.AnimalEntity;
import com.dedalus.persistence.AnimalRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/resources/animal")
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class AnimalResource {
    @Inject
    AnimalRepository repository;

    @POST

    public AnimalEntity postAnimal(AnimalEntity animal) {
       /* AnimalEntity greetingEntity = new AnimalEntity();
        greetingEntity.setPhrase("Hello World!");*/
        return animal /*repository.save(greetingEntity)*/;
    }
}