package com.dedalus.validation;

import com.dedalus.model.AnimalValidationErrors;

import javax.ws.rs.core.Response;

public class AnimalValidationException extends RuntimeException {

    public AnimalValidationErrors animalValidationErrors;

    //Optional
    public Response.Status statusCode = Response.Status.BAD_REQUEST;

    public AnimalValidationException(AnimalValidationErrors animalValidationErrors, Response.Status statusCode) {
        this.animalValidationErrors = animalValidationErrors;
        this.statusCode = statusCode;
    }

    public AnimalValidationException(AnimalValidationErrors animalValidationErrors) {
        this.animalValidationErrors = animalValidationErrors;
    }
}
