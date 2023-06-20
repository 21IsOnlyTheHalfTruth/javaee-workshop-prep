package com.dedalus.persistence;

import com.dedalus.model.AnimalEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AnimalEntity> cq = cb.createQuery(AnimalEntity.class);
        Root<AnimalEntity> rootEntry = cq.from(AnimalEntity.class);
        CriteriaQuery<AnimalEntity> all = cq.select(rootEntry);

        TypedQuery<AnimalEntity> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }

}
