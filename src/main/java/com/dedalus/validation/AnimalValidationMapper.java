package com.dedalus.validation;

import com.dedalus.dto.ValidationResponseDTO;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AnimalValidationMapper implements ExceptionMapper<AnimalValidationException> {

    @Override
    public Response toResponse(AnimalValidationException exception) {
        return Response.status(exception.statusCode.getStatusCode()).entity(ValidationResponseDTO.fromAnimalValidationException(exception)).build();
    }
}
