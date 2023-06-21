package com.dedalus.persistence;

import com.dedalus.model.AnimalEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class AnimalRepository {
    @Inject
    EntityManager em;

    public AnimalEntity save(AnimalEntity entity) {
        em.persist(entity);
        return entity;
    }

    public Optional<AnimalEntity> put(AnimalEntity entity) {
        Optional<AnimalEntity> entityFromDBOptional = getRefById(entity.id);
        if(entityFromDBOptional.isEmpty()) {
            return Optional.empty();
        }
        em.merge(entity);
        return Optional.of(entity);
    }
    public Optional<AnimalEntity> setAdopt(Long id) {
        Optional<AnimalEntity> entityFromDBOptional = getById(id);
        // set attributes
        if(entityFromDBOptional.isEmpty()) {
            return Optional.empty();
        }
        AnimalEntity entity = entityFromDBOptional.get();
        entity.available = false;
        em.merge(entity);
        // maybe we have to flush
        return Optional.of(entity);
    }

    public List<AnimalEntity> getAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AnimalEntity> cq = cb.createQuery(AnimalEntity.class);
        Root<AnimalEntity> rootEntry = cq.from(AnimalEntity.class);
        CriteriaQuery<AnimalEntity> all = cq.select(rootEntry);

        TypedQuery<AnimalEntity> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }


    public Optional<AnimalEntity> getRefById(@NotNull()  Long id){
        return Optional.ofNullable(em.getReference(AnimalEntity.class, id));
    }
    public Optional<AnimalEntity> getById(@NotNull() Long id){
        return Optional.ofNullable(em.find(AnimalEntity.class, id));
    }
}

