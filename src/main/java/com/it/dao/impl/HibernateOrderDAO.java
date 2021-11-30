package com.it.dao.impl;

import com.it.dao.IOrderDAO;
import com.it.model.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateOrderDAO implements IOrderDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void persistOrder(Order order) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.save(order);
            tx.commit();
        } catch (Exception e){
            if(tx != null)
                tx.rollback();
        }finally {
            session.close();
        }
    }
}
