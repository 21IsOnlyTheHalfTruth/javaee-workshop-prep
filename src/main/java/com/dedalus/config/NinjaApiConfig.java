package com.dedalus.config;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class NinjaApiConfig {
    @ConfigProperty(name="NINJA_API_KEY")
    String ninjaApiKey;

    public String getNinjaApiKey() {
        return ninjaApiKey;
    }
}
