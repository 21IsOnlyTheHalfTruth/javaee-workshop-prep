package com.dedalus.service;

import com.dedalus.config.NinjaApiConfig;
import com.dedalus.dto.AnimalDTO;
import io.quarkus.cache.CacheResult;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class NinjaApiController {
    @Inject
    @RestClient
    NinjaApiRestClient ninjaApiRestClient;

    @Inject
    NinjaApiConfig ninjaApiConfig;

    @CacheResult(cacheName = "animal-cache")
    public List<NinjaAnimalDTO> getAnimalsByName(String name){
        if(ninjaApiConfig.getNinjaApiKey() == null){
            throw new WebApplicationException("Environmentvariable API Key not configured", Response.Status.INTERNAL_SERVER_ERROR);
        }
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return ninjaApiRestClient.getAnimalList(name, ninjaApiConfig.getNinjaApiKey());
    }


    public AnimalDTO populateAdditionalInformation(AnimalDTO animalDTO){
        //We mutate it instead of returning a new copy
        List<NinjaAnimalDTO> ninjaAnimalDTOList = getAnimalsByName(animalDTO.type.name());
        Optional<NinjaAnimalDTO> firstAnimal = ninjaAnimalDTOList.stream().findFirst();
        if(firstAnimal.isPresent()){
            return AnimalDTO.fromNinjaAnimalDTO(firstAnimal.get(), animalDTO);
        }
        return animalDTO;
    }
}
