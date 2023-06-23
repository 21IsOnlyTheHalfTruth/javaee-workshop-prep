package com.dedalus.control;

import com.dedalus.config.NinjaApiConfig;
import com.dedalus.dto.AnimalDTO;
import com.dedalus.dto.NinjaAnimalDTO;
import com.dedalus.model.ExternalSystemError;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Fallback;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;


@ApplicationScoped
public class NinjaApiController {

    private static final Logger log = LoggerFactory.getLogger(NinjaApiController.class);

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
    // @CacheResult(cacheName = "animal-cache") Do not cache here, does not work super good with the fallback
    @Retry(maxRetries = 2, maxDuration = 2000, delay = 1000)
    @Fallback(fallbackMethod = "fallbackSingleNinjaAnimal")
    public List<NinjaAnimalDTO> getAnimalsByName(String name) {
        if (ninjaApiConfig.getNinjaApiKey() == null) {
            throw new WebApplicationException("NinjaApiController#getAnimalsByName(): " + ExternalSystemError.API_KEY_NOT_CONFIGURED.toString(), Response.Status.INTERNAL_SERVER_ERROR);
        }
        List<NinjaAnimalDTO> response = null;
        try{
            response =  ninjaApiRestClient.getAnimalList(name, ninjaApiConfig.getNinjaApiKey());
        }catch(WebApplicationException e){
            if(e.getResponse().getStatus() == 400){ //Could be the whole 400 range
                log.info(ExternalSystemError.EXTERNAL_BAD_REQUEST + ": NinjaApiController#getAnimalsByName()");
            }
            throw e;
        }
        return response;
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

    public List<NinjaAnimalDTO> fallbackSingleNinjaAnimal(String name) {
        log.info(ExternalSystemError.FALLBACK_METHOD_ACTIVATED + ": NinjaApiController#fallbackSingleNinjaAnimal()");
        return null;
    }
}
