package com.dedalus.service;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.List;

@RegisterRestClient(baseUri = "https://api.api-ninjas.com/v1")
public interface NinjaApiRestClient {

    @GET
    @Path("/animals")
    List<NinjaAnimalDTO> getAnimalList(@QueryParam("name") String name, @HeaderParam("X-Api-Key") String apiKey);
}
