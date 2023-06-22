package com.dedalus.service;

import com.dedalus.config.NinjaApiConfig;
import com.dedalus.dto.AnimalDTO;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Fallback;

import io.quarkus.cache.CacheResult;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;


@ApplicationScoped
public class NinjaApiController {

    @Inject
    Logger log;

    @Inject
    @RestClient
    NinjaApiRestClient ninjaApiRestClient;

    @Inject
    NinjaApiConfig ninjaApiConfig;


    /**
     * returns a list of animals that match to the query
     *
     * @return null if the call fails
     */
    @CacheResult(cacheName = "animal-cache")
    @Retry(maxRetries = 2, maxDuration = 2000)
    @Fallback(fallbackMethod = "fallbackSingleNinjaAnimal")
    private List<NinjaAnimalDTO> getAnimalsByName(String name) {
        if (ninjaApiConfig.getNinjaApiKey() == null) {
            throw new WebApplicationException("NinjaApiController#getAnimalsByName(): " + ExternalSystemErrors.API_KEY_NOT_CONFIGURED.toString(), Response.Status.INTERNAL_SERVER_ERROR);
        }
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return ninjaApiRestClient.getAnimalList(name, ninjaApiConfig.getNinjaApiKey());
    }

    public AnimalDTO populateAdditionalInformation(AnimalDTO animalDTO) {
        //We mutate it instead of returning a new copy, could be also made pure
        Optional<List<NinjaAnimalDTO>> optionalNinjaAnimalDTOList;

        optionalNinjaAnimalDTOList = Optional.ofNullable(getAnimalsByName(animalDTO.type.name()));

        Optional<NinjaAnimalDTO> firstAnimal = getFirstAnimalOptional(optionalNinjaAnimalDTOList);

        if (firstAnimal.isPresent()) {
            return AnimalDTO.fromNinjaAnimalDTO(firstAnimal.get(), animalDTO);
        }
        return animalDTO;
    }

    /**
     * Returns an Optional the first animal
     * @param ninjaAnimalDTOList Optional that can be empty, an empty list or a list with entries
     */
    private static Optional<NinjaAnimalDTO> getFirstAnimalOptional(Optional<List<NinjaAnimalDTO>> ninjaAnimalDTOList) {
        Optional<NinjaAnimalDTO> firstAnimal = ninjaAnimalDTOList
                .map(a -> a.stream()
                        .findFirst())
                .orElse(Optional.empty());
        return firstAnimal;
    }

    public NinjaAnimalDTO fallbackSingleNinjaAnimal(String name) {
        log.info("Falling back to NinjaApiController#fallbackSingleNinjaAnimal()");
        return null;
    }
}
