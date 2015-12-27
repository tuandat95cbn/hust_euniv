/**
 * @author : HaTN 
 * @address : HUST K51
 * @modified : October 13th, 2015
 */
package vn.webapp.modules.usermanagement.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
/**
 * @author : HaTN 
 * @address : HUST K51
 * @modified : December 27th, 2015
 */
import vn.webapp.dao.BaseDao;
import vn.webapp.modules.usermanagement.model.mDepartment;
import vn.webapp.modules.usermanagement.model.mFuncsPermission;

@Repository("mmFuncsPermissionDAO")
@SuppressWarnings({"unchecked", "rawtypes"})
public class mFuncsPermissionDAOImpl extends BaseDao implements mFuncsPermissionDAO{
	@Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Get permissions by user code
     * @param null
     * @return List
     */
    @Override
    public List<mFuncsPermission> loadFunctionsPermissionByUserList(String sUserCode){
        try {
            begin();
            Criteria criteria = getSession().createCriteria(mFuncsPermission.class);
            criteria.add(Restrictions.eq("USERFUNC_USERCODE", sUserCode));
            criteria.addOrder(Order.asc("USERFUNC_ID"));
            List<mFuncsPermission> funcsPermission = criteria.list();
            commit();
            return funcsPermission;
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
     * Get department list
     * @param null
     * @return List
     */
    @Override
    public List<mDepartment> loadDepartmentListByFaculty(String facultyCode){
    	try {
            begin();
            Criteria criteria = getSession().createCriteria(mDepartment.class);
            criteria.add(Restrictions.eq("Department_Faculty_Code", facultyCode));
            criteria.addOrder(Order.asc("Department_Name"));
            List<mDepartment> department = criteria.list();
            commit();
            return department;
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
     * Get a department by its code and falcuty code
     * @param null
     * @return object
     */
    @Override
    public mDepartment loadADepartmentByCodes(String departmentCode, String falcutyCode){
    	try {
            begin();
            Criteria criteria = getSession().createCriteria(mDepartment.class, "Department");
            criteria.add(Restrictions.eq("Department.Department_Code", departmentCode));
            criteria.add(Restrictions.eq("Department.Department_Faculty_Code", falcutyCode));
            mDepartment department = (mDepartment) criteria.uniqueResult();
            commit();
            return department;
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
     * Get a department by falcuty
     * @param null
     * @return object
     */
    @Override
    public List<mDepartment> loadADepartmentByFaculty(String falcutyCode){
    	try {
            begin();
            Criteria criteria = getSession().createCriteria(mDepartment.class, "Department");
            criteria.add(Restrictions.eq("Department.Department_Faculty_Code", falcutyCode));
            criteria.addOrder(Order.asc("Department.Department_Name"));
            List<mDepartment> department = criteria.list();
            commit();
            return department;
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
