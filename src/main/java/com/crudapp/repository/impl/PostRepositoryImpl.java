package com.crudapp.repository.impl;

import com.crudapp.model.Post;
import com.crudapp.repository.PostRepository;
import com.crudapp.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class PostRepositoryImpl implements PostRepository {

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public Post save(Post post) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(post);
            transaction.commit();
            return post;
        }
    }

    @Override
    public Post update(Post post) {
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            session.merge(post);
            transaction.commit();
            return post;
        }

    }

    @Override
    public Optional<Post> getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Post post = session.get(Post.class, id);
            return Optional.ofNullable(post);
        }
    }

    @Override
    public List<Post> getAll() {
        try (Session session = sessionFactory.openSession()){
            return session.createQuery("FROM Post", Post.class).list();
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            Post post = session.get(Post.class, id);
            if (post != null) {
                session.remove(post);
            }
            transaction.commit();
        }
    }
}
