package com.raulrh.flotaespacial.gui;

import com.raulrh.flotaespacial.entities.*;
import com.raulrh.flotaespacial.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDate;
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

    public void addMision(String descripcion, String estado, NaveEspacial nave) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Mision mision = new Mision();
        mision.setDescripcion(descripcion);
        mision.setEstado(estado);
        mision.setIdNave(nave);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(mision);
        transaction.commit();
        session.close();
    }

    public void modifyMision(int id, String descripcion, String estado, NaveEspacial nave) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Mision mision = session.get(Mision.class, id);
        mision.setDescripcion(descripcion);
        mision.setEstado(estado);
        mision.setIdNave(nave);
        Transaction transaction = session.beginTransaction();
        session.merge(mision);
        transaction.commit();
        session.close();
    }

    /**
     * Deletes a television from the database.
     *
     * @param id the ID of the television to delete.
     */
    public void deleteTelevision(int id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Television television = session.get(Television.class, id);
        session.remove(television);
        transaction.commit();
        session.close();
    }

    /**
     * Checks if a television exists in the database.
     *
     * @param model the model of the television.
     * @param brand the brand of the television.
     * @param id    the ID of the television.
     * @return true if the television exists, false otherwise.
     */
    public boolean checkTelevisionExists(String model, String brand, int id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Television television = session.createQuery("from Television where model = :model and brand = :brand and id != :id", Television.class)
                .setParameter("model", model)
                .setParameter("brand", brand)
                .setParameter("id", id)
                .uniqueResult();

        session.close();
        return television != null;
    }

    /**
     * Adds a new customer to the database.
     *
     * @param name      Customer's first name.
     * @param surname   Customer's last name.
     * @param mail      Customer's email.
     * @param phone     Customer's phone number.
     * @param birthDate Customer's birth date.
     * @param type      Customer's type identifier.
     */
    public void addCustomer(String name, String surname, String mail, String phone, LocalDate birthDate, short type) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Customer customer = new Customer();
        customer.setFirstName(name);
        customer.setLastName(surname);
        customer.setEmail(mail);
        customer.setPhone(phone);
        customer.setRegistrationDate(birthDate);
        customer.setType(type);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(customer);
        transaction.commit();
        session.close();
    }

    /**
     * Modifies an existing customer's details in the database.
     *
     * @param id        Customer's unique identifier.
     * @param name      Customer's updated first name.
     * @param surname   Customer's updated last name.
     * @param mail      Customer's updated email.
     * @param phone     Customer's updated phone number.
     * @param birthDate Customer's updated birth date.
     * @param type      Customer's updated type identifier.
     */
    public void modifyCustomer(int id, String name, String surname, String mail, String phone, LocalDate birthDate, int type) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Customer customer = session.get(Customer.class, id);
        customer.setFirstName(name);
        customer.setLastName(surname);
        customer.setEmail(mail);
        customer.setPhone(phone);
        customer.setRegistrationDate(birthDate);
        customer.setType((short) type);
        session.merge(customer);
        transaction.commit();
        session.close();
    }

    /**
     * Deletes a customer from the database.
     *
     * @param id Customer's unique identifier.
     */
    public void deleteCustomer(int id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Customer customer = session.get(Customer.class, id);
        session.remove(customer);
        transaction.commit();
        session.close();
    }

    /**
     * Checks if a customer exists in the database based on their email and ID.
     *
     * @param email Customer's email to check.
     * @param id    Customer's unique identifier.
     * @return True if the customer exists, false otherwise.
     */
    public boolean checkCustomerExists(String email, int id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Customer customer = session.createQuery("from Customer where email = :email and id != :id", Customer.class)
                .setParameter("email", email)
                .setParameter("id", id)
                .uniqueResult();

        session.close();
        return customer != null;
    }

    /**
     * Adds a new sale to the database.
     *
     * @param customerId   the ID of the customer making the purchase
     * @param televisionId the ID of the television being purchased
     * @param saleDate     the date of the sale
     * @param quantity     the quantity of televisions purchased
     * @param totalPrice   the total price of the sale
     */
    public void addSale(int customerId, int televisionId, LocalDate saleDate, int quantity, double totalPrice) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Sale sale = new Sale();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Customer customer = session.get(Customer.class, customerId);
        Television television = session.get(Television.class, televisionId);
        sale.setCustomer(customer);
        sale.setTelevision(television);
        sale.setSaleDate(saleDate);
        sale.setQuantity(quantity);
        sale.setTotal(totalPrice);
        session.persist(sale);
        transaction.commit();
        session.close();
    }

    /**
     * Updates an existing sale in the database.
     *
     * @param id           the ID of the sale to update
     * @param customerId   the ID of the customer
     * @param televisionId the ID of the television
     * @param saleDate     the date of the sale
     * @param quantity     the quantity of televisions purchased
     * @param totalPrice   the total price of the sale
     */
    public void modifySale(int id, int customerId, int televisionId, LocalDate saleDate, int quantity, double totalPrice) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Sale sale = session.get(Sale.class, id);
        Customer customer = session.get(Customer.class, customerId);
        Television television = session.get(Television.class, televisionId);
        sale.setCustomer(customer);
        sale.setTelevision(television);
        sale.setSaleDate(saleDate);
        sale.setQuantity(quantity);
        sale.setTotal(totalPrice);
        session.merge(sale);
        transaction.commit();
        session.close();
    }

    /**
     * Deletes a sale from the database.
     *
     * @param id the ID of the sale to delete
     */
    public void deleteSale(int id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Sale sale = session.get(Sale.class, id);
        session.remove(sale);
        transaction.commit();
        session.close();
    }

    /**
     * Adds a new supplier to the database.
     *
     * @param name    the name of the supplier
     * @param phone   the phone number of the supplier
     * @param address the address of the supplier
     * @param mail    the email of the supplier
     */
    public void addSupplier(String name, String phone, String address, String mail) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Supplier supplier = new Supplier();
        supplier.setName(name);
        supplier.setPhone(phone);
        supplier.setAddress(address);
        supplier.setEmail(mail);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(supplier);
        transaction.commit();
        session.close();
    }

    /**
     * Updates an existing supplier in the database.
     *
     * @param id      the ID of the supplier
     * @param name    the new name of the supplier
     * @param phone   the new phone number of the supplier
     * @param address the new address of the supplier
     * @param mail    the new email of the supplier
     */
    public void modifySupplier(int id, String name, String phone, String address, String mail) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Supplier supplier = session.get(Supplier.class, id);
        supplier.setName(name);
        supplier.setPhone(phone);
        supplier.setAddress(address);
        supplier.setEmail(mail);
        Transaction transaction = session.beginTransaction();
        session.merge(supplier);
        transaction.commit();
        session.close();
    }

    /**
     * Deletes a supplier from the database.
     *
     * @param id the ID of the supplier to delete
     */
    public void deleteSupplier(int id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Supplier supplier = session.get(Supplier.class, id);
        session.remove(supplier);
        transaction.commit();
        session.close();
    }

    /**
     * Checks if a supplier exists in the database based on email and ID.
     *
     * @param email the email of the supplier
     * @param id    the ID of the supplier
     * @return true if the supplier exists, false otherwise
     */
    public boolean checkSupplierExists(String email, int id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Supplier supplier = session.createQuery("from Supplier where email = :email and id != :id", Supplier.class)
                .setParameter("email", email)
                .setParameter("id", id)
                .uniqueResult();
        session.close();
        return supplier != null;
    }
}
