package vn.webapp.modules.researchmanagement.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import vn.webapp.dao.BaseDao;
import vn.webapp.modules.researchmanagement.model.ProjectTasks;

@Repository("ProjectTasksDAO")
@SuppressWarnings({"unchecked", "rawtypes"})
public class ProjectTasksDAOImpl extends BaseDao implements ProjectTasksDAO {
	
	/**
     * Get all list Staff Category
     * @param null
     * @return List
     */
    public List<ProjectTasks> getList(){
    	try {
            begin();
            Criteria criteria = getSession().createCriteria(ProjectTasks.class);
            criteria.setFirstResult(0);
            List<ProjectTasks> projectTasks = criteria.list();
            commit();
            return projectTasks;
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
     * 
     */
	@Override
	public ProjectTasks loadAProjectTaskByCode(String sCode) {
		try {
			begin();
			Criteria criteria = getSession().createCriteria(ProjectTasks.class);
			criteria.add(Restrictions.eq("PRJTSK_Code", sCode));
			ProjectTasks projectTask = (ProjectTasks) criteria.uniqueResult();
			commit();
			return projectTask;
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
