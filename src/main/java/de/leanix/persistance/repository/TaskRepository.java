package de.leanix.persistance.repository;

import de.leanix.persistance.entity.TaskEntity;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.inject.Singleton;
import javax.persistence.PersistenceException;
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

        CriteriaQuery<TaskEntity> criteriaQuery = getCriteriaQuery();
        Root<TaskEntity> root = criteriaQuery.from(TaskEntity.class);
        criteriaQuery.select(root);
        Query<TaskEntity> query = sessionFactory.getCurrentSession().createQuery(criteriaQuery);

        return query.getResultList();
    }

    public TaskEntity createTask(TaskEntity taskEntity) throws PersistenceException {
        Session session = sessionFactory.getCurrentSession();

        session.save(taskEntity);

        return sessionFactory.getCurrentSession().get(TaskEntity.class, taskEntity.getId());
    }

    private CriteriaQuery<TaskEntity> getCriteriaQuery(){
        CriteriaBuilder criteriaBuilder = sessionFactory.getCurrentSession().getCriteriaBuilder();

        return criteriaBuilder.createQuery(TaskEntity.class);
    }
}
