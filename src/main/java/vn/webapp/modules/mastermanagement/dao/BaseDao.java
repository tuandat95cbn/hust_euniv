/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.webapp.modules.mastermanagement.dao;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * 
 */
@SuppressWarnings("unchecked")
public class BaseDao{
    
    @Autowired
    private SessionFactory sessionFactory;
    private static final ThreadLocal THREAD = new ThreadLocal();
 
    protected BaseDao(){
    }
    /**
     * 
     * @return 
     */
    public Session getSession() {
        Session session = (Session) BaseDao.THREAD.get();
        if (session == null) {
            session = sessionFactory.openSession();
            BaseDao.THREAD.set(session);
            getSession().setFlushMode(FlushMode.COMMIT);
        }
        return session;
    }
    /**
     * 
     */
    protected void begin() {
        getSession().beginTransaction();
    }
 
    protected void commit() {
        getSession().getTransaction().commit();
    }
    /**
     * 
     */
    protected void rollback() {
        getSession().getTransaction().rollback();
        getSession().close();
        BaseDao.THREAD.set(null);
    }
    /**
     * 
     */
    protected void flush() {
        getSession().flush();
    }
    /**
     * 
     */
    protected void close() {
        getSession().close();
        BaseDao.THREAD.set(null);
    }
}
