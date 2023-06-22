package com.dedalus.dto;

import com.dedalus.validation.AnimalValidationException;

public class ValidationResponseDTO {

    public ValidationResponseDTO(String messageCode, String message) {
        this.message = message;
        this.messageCode = messageCode;
    }

    public static ValidationResponseDTO fromAnimalValidationException(AnimalValidationException exception){
        return new ValidationResponseDTO(exception.animalValidationErrors.name(), exception.animalValidationErrors.toString());
    }

    public String message;
    public String messageCode;

}
