package com.dedalus.validation;

public enum AnimalValidationErrors {
    ANIMAL_ALREADY_ADOPTED("That animal that you tried to adopt has already been adopted"),
    ANIMAL_NOT_FOUND("That animal that you tried to search has not been found");

    private final String message;

    AnimalValidationErrors(String s) {
        message = s;
    }

    @Override
    public String toString() {
        return message;
    }
}
