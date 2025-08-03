package com.crudapp.repository.impl;

import com.crudapp.model.Label;
import com.crudapp.repository.LabelRepository;
import com.crudapp.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class LabelRepositoryImpl implements LabelRepository {

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public Label save(Label label) {
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            session.persist(label);
            transaction.commit();
            return label;
        }
    }

    @Override
    public Label update(Label label) {
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            session.merge(label);
            transaction.commit();
            return label;
        }
    }

    @Override
    public Label findById(Long id) {
        try (Session session = sessionFactory.openSession()){
            return session.get(Label.class, id);
        }
    }

    @Override
    public List<Label> findAll() {
        try (Session session = sessionFactory.openSession()){
            return session.createQuery("FROM Label", Label.class).list();
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            Label label = session.get(Label.class, id);
            if (label != null) {
                session.remove(label);
            }
            transaction.commit();
        }
    }
}
