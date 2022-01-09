package de.leanix.persistance.repository;

import de.leanix.persistance.entity.TaskEntity;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.inject.Singleton;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Singleton
public class TaskRepository extends AbstractDAO<TaskEntity> {
    private final SessionFactory sessionFactory;

    public TaskRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    public List<TaskEntity> findAll() {
        CriteriaBuilder criteriaBuilder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<TaskEntity> criteria = criteriaBuilder.createQuery(TaskEntity.class);
        Root<TaskEntity> root = criteria.from(TaskEntity.class);
        criteria.select(root);

        Query<TaskEntity> query = sessionFactory.getCurrentSession().createQuery(criteria);

        return query.getResultList();
    }
}
