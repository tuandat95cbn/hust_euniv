/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.webapp.modules.mastermanagement.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vn.webapp.modules.mastermanagement.model.mmListMasterThesis;


@Repository("mmlistMasterThesisDAO")
@SuppressWarnings({"unchecked", "rawtypes"})

public class mmListMasterThesisDAOImpl extends BaseDao implements mmListMasterThesisDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Get staff list
     * @param String
     * @return object
     */
    @Override
    public List<mmListMasterThesis> getListMasterThesis(){
    	try {
    		begin();
            Criteria criteria = getSession().createCriteria(mmListMasterThesis.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            criteria.setFirstResult(0);
            List<mmListMasterThesis> masterThesis = criteria.list();
            commit();
            return masterThesis;
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
