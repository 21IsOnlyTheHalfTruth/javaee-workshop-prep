package com.dedalus.service;

public enum ExternalSystemErrors {

    API_KEY_NOT_CONFIGURED("Environment variable API Key not configured");
    private final String message;

    ExternalSystemErrors(String s) {
        message = s;
    }

    @Override
    public String toString() {
        return String.format("%s: %s", this.name(), message);
    }
}
