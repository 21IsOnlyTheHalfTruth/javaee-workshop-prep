package com.dedalus.persistence;

import com.dedalus.model.AnimalEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class AnimalRepository {
    @Inject
    EntityManager em;

    public AnimalEntity save(AnimalEntity entity) {
        em.persist(entity);
        return entity;
    }
    public List<AnimalEntity> getAll(){
        // todo
        return null;
    }

}
