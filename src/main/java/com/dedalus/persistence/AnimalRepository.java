package com.dedalus.persistence;

import com.dedalus.model.AnimalEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
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

    public AnimalEntity put(AnimalEntity entity) {
        Optional<AnimalEntity> entityFromDBOptional = Optional.ofNullable(em.getReference(entity.getClass(), entity.id));
        // set attributes
        if(entityFromDBOptional.isEmpty()) {
            System.out.println("Was not able to find the id:"+entity.id); // in a real world this would be a logger
            throw new WebApplicationException("Was not able to find the id:  " +entity.id, Response.Status.NOT_FOUND);
        }
        var entityFromDb = entityFromDBOptional.get();
        updateEntity(entity,entityFromDb );
        em.merge(entityFromDb);
        return entityFromDb;
    }

    private void updateEntity(AnimalEntity source, AnimalEntity target) {
        source.name = target.name;
        source.type = target.type;
        source.comment = target.comment;
        source.available = target.available;
    }

    public List<AnimalEntity> getAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AnimalEntity> cq = cb.createQuery(AnimalEntity.class);
        Root<AnimalEntity> rootEntry = cq.from(AnimalEntity.class);
        CriteriaQuery<AnimalEntity> all = cq.select(rootEntry);

        TypedQuery<AnimalEntity> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }
}
/*
    public AnimalEntity getById(Long id){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AnimalEntity> cq = cb.createQuery("select * from Artist where id="; AnimalEntity.class);

        Root<AnimalEntity> rootEntry = cq.from(AnimalEntity.class);
        CriteriaQuery<AnimalEntity> all = cq.select(rootEntry);

        TypedQuery<AnimalEntity> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }

}
*/
