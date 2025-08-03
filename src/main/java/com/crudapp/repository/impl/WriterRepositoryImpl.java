package com.crudapp.repository.impl;

import com.crudapp.model.Writer;
import com.crudapp.repository.WriterRepository;
import com.crudapp.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;


public class WriterRepositoryImpl implements WriterRepository {

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public Writer save(Writer writer) {
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            session.persist(writer);
            transaction.commit();
            return writer;
        }
    }

    @Override
    public Writer update(Writer writer) {
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            session.merge(writer);
            transaction.commit();
        }
        return writer;
    }

    @Override
    public Writer findById(Long id) {
        try (Session session = sessionFactory.openSession()){
            return session.get(Writer.class, id);
        }
    }

    @Override
    public List<Writer> findAll() {
        try (Session session = sessionFactory.openSession()){
            return session.createQuery("FROM Writer", Writer.class).list();
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            Writer writer = session.get(Writer.class, id);
            if (writer != null) {
                session.remove(writer);
            }
            transaction.commit();
        }
    }
}
