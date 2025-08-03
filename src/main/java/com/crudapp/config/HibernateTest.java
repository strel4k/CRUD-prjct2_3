package com.crudapp.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateTest {
    public static void main(String[] args) {
        try (SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory()){
            System.out.println("Hibernate is working");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}