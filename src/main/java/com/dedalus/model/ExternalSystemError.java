package com.dedalus.model;

public enum ExternalSystemError {

    API_KEY_NOT_CONFIGURED("Environment variable API Key not configured"),
    FALLBACK_METHOD_ACTIVATED("Used fallback method, because the API was not reachable"),
    EXTERNAL_BAD_REQUEST("Received bad request");
    private final String message;

    ExternalSystemError(String s) {
        message = s;
    }

    @Override
    public String toString() {
        return String.format("%s: %s", this.name(), message);
    }
}
