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
import java.util.Optional;
import java.util.UUID;

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

    public Optional<TaskEntity> findById(UUID uuid) throws PersistenceException {
        TaskEntity taskEntity = sessionFactory.getCurrentSession().get(TaskEntity.class, uuid);
        return taskEntity != null ? Optional.of(taskEntity) : Optional.empty();
    }

    public TaskEntity createTask(TaskEntity taskEntity) throws PersistenceException {
        Session session = sessionFactory.getCurrentSession();

        session.save(taskEntity);

        return findById(taskEntity.getId()).get();
    }

    public Optional<TaskEntity> updateTask(UUID id, TaskEntity taskEntity) throws PersistenceException {
        Session session = sessionFactory.getCurrentSession();

        Optional<TaskEntity> savedTaskEntity = findById(id);
        TaskEntity entity;

        if(savedTaskEntity.isPresent()) {
            entity = savedTaskEntity.get();
            entity.setName(taskEntity.getName());
            entity.setDescription(taskEntity.getDescription());
            entity.setSubTasks(taskEntity.getSubTasks());
            session.update(entity);
            return Optional.of(entity);
        }

        return Optional.empty();
    }

    public Boolean deleteTask(UUID id) throws PersistenceException {
        Session session = sessionFactory.getCurrentSession();

        Optional<TaskEntity> taskEntity = findById(id);

        if(taskEntity.isPresent()) {
            session.delete(taskEntity.get());
            return true;
        }

        return false;
    }

    private CriteriaQuery<TaskEntity> getCriteriaQuery(){
        CriteriaBuilder criteriaBuilder = sessionFactory.getCurrentSession().getCriteriaBuilder();

        return criteriaBuilder.createQuery(TaskEntity.class);
    }
}
