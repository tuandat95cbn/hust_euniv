/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.webapp.modules.mastermanagement.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vn.webapp.modules.mastermanagement.model.mmUniversity;


@Repository("mmuniversityDAO")
@SuppressWarnings({"unchecked", "rawtypes"})

public class mmUniversityDAOImpl extends BaseDao implements mmUniversityDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Get Faculty list
     * @param null
     * @return List
     */
    @Override
    public List<mmUniversity> loadUniversityList() {
        try {
            begin();
            Criteria criteria = getSession().createCriteria(mmUniversity.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            List<mmUniversity> university = criteria.list();
            commit();
            return university;
        } catch (HibernateException e) {
            e.printStackTrace();
            rollback();
            close();
            return null;
        } finally {
            flush();
            close();
        }
    }
    
    /**
     * Get a Faculty by falcuty code
     * @param null
     * @return object
     */
    @Override
    public mmUniversity loadAUniversityByCodes(String universityCode){
    	try {
            begin();
            Criteria criteria = getSession().createCriteria(mmUniversity.class, "University");
            criteria.add(Restrictions.eq("University.University_Code", universityCode));
            mmUniversity university = (mmUniversity) criteria.uniqueResult();
            commit();
            return university;
        } catch (HibernateException e) {
            e.printStackTrace();
            rollback();
            close();
            return null;
        } finally {
            flush();
            close();
        }
    }
    
    
}
