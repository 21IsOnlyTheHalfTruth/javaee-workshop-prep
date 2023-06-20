package com.dedalus.resources;

import com.dedalus.model.AnimalEntity;
import com.dedalus.model.AnimalType;
import com.dedalus.persistence.AnimalRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/resources/animal")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

@Transactional
public class GreetingResource {
    @Inject
    AnimalRepository repository;

    @POST
    public AnimalEntity postAnimal(AnimalEntity animal) {
       /* AnimalEntity greetingEntity = new AnimalEntity();
        greetingEntity.setPhrase("Hello World!");*/
        return animal /*repository.save(greetingEntity)*/;
    }
    @GET
    public AnimalEntity postAnimal() {
        return new AnimalEntity("myname", AnimalType.HELICOPTER, "myComment", false);
    }
}