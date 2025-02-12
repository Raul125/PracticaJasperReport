package com.raulrh.flotaespacial.gui;

import com.raulrh.flotaespacial.entities.Mision;
import com.raulrh.flotaespacial.entities.NaveEspacial;
import com.raulrh.flotaespacial.entities.Tripulante;
import com.raulrh.flotaespacial.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class Model {
    public Model() {

    }

    public List<Mision> getMisiones() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        return session.createQuery("from Mision", Mision.class).list();
    }

    public List<Tripulante> getTripulantes() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        return session.createQuery("from Tripulante ", Tripulante.class).list();
    }

    public List<NaveEspacial> getNaves() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        return session.createQuery("from NaveEspacial", NaveEspacial.class).list();
    }

    public void addMision(String descripcion, String estado, int nave) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Mision mision = new Mision();
        mision.setDescripcion(descripcion);
        mision.setEstado(estado);
        Session session = sessionFactory.openSession();
        NaveEspacial naveEspacial = session.get(NaveEspacial.class, nave);
        mision.setIdNave(naveEspacial);
        Transaction transaction = session.beginTransaction();
        session.persist(mision);
        transaction.commit();
        session.close();
    }

    public void modifyMision(int id, String descripcion, String estado, int nave) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        NaveEspacial naveEspacial = session.get(NaveEspacial.class, nave);
        Mision mision = session.get(Mision.class, id);
        mision.setDescripcion(descripcion);
        mision.setEstado(estado);
        mision.setIdNave(naveEspacial);
        Transaction transaction = session.beginTransaction();
        session.merge(mision);
        transaction.commit();
        session.close();
    }

    public void deleteMision(int id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Mision mision = session.get(Mision.class, id);
        session.remove(mision);
        transaction.commit();
        session.close();
    }

    public void addNave(String nombre, String clase, int tripulantes) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        NaveEspacial nave = new NaveEspacial();
        nave.setNombreNave(nombre);
        nave.setClase(clase);
        nave.setCapacidadTripulacion(tripulantes);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(nave);
        transaction.commit();
        session.close();
    }

    public void modifyNave(int id, String nombre, String clase, int tripulantes) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        NaveEspacial nave = session.get(NaveEspacial.class, id);
        nave.setNombreNave(nombre);
        nave.setClase(clase);
        nave.setCapacidadTripulacion(tripulantes);
        session.merge(nave);
        transaction.commit();
        session.close();
    }

    public void deleteNave(int id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        NaveEspacial customer = session.get(NaveEspacial.class, id);
        session.remove(customer);
        transaction.commit();
        session.close();
    }

    public boolean checkNaveExists(String nombre, int id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        NaveEspacial nave = session.createQuery("from NaveEspacial where nombreNave = :nombre and id != :id", NaveEspacial.class)
                .setParameter("nombre", nombre)
                .setParameter("id", id)
                .uniqueResult();

        session.close();
        return nave != null;
    }

    public void addTripulante(int nave, String nombre, String rango) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Tripulante tripulante = new Tripulante();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        NaveEspacial naveEspacial = session.get(NaveEspacial.class, nave);
        tripulante.setIdNave(naveEspacial);
        tripulante.setNombreTripulante(nombre);
        tripulante.setRango(rango);
        session.persist(tripulante);
        transaction.commit();
        session.close();
    }

    public void modifyTripulante(int id, int nave, String nombre, String rango) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        NaveEspacial naveEspacial = session.get(NaveEspacial.class, nave);
        Tripulante tripulante = session.get(Tripulante.class, id);
        tripulante.setIdNave(naveEspacial);
        tripulante.setNombreTripulante(nombre);
        tripulante.setRango(rango);
        session.merge(tripulante);
        transaction.commit();
        session.close();
    }

    public void deleteTripulante(int id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Tripulante tripulante = session.get(Tripulante.class, id);
        session.remove(tripulante);
        transaction.commit();
        session.close();
    }

    public boolean checkTripulanteExists(String nombre, int id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Tripulante tripulante = session.createQuery("from Tripulante where nombreTripulante = :nombre and id != :id", Tripulante.class)
                .setParameter("nombre", nombre)
                .setParameter("id", id)
                .uniqueResult();
        session.close();
        return tripulante != null;
    }
}
