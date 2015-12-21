package vn.webapp.modules.researchmanagement.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vn.webapp.dao.BaseDao;
import vn.webapp.modules.researchdeclarationmanagement.model.mTopics;
import vn.webapp.modules.researchmanagement.model.mThreads;

@Repository("nProjectDAO")
@SuppressWarnings({ "unchecked" })
public class nProjectDAOImpl extends BaseDao implements nProjectDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * Get topic list by user
	 * 
	 * @param null
	 * @return List
	 */
	@Override
	public List<mThreads> loadThreadsListByStaff(String userRole, String userCode) {
		try {
			begin();
			Criteria criteria = getSession().createCriteria(mThreads.class, "threads");
			if (!userRole.equals("ROLE_ADMIN")) {
				criteria.add(Restrictions.eq("threads.PROJ_User_Code", userCode));
			}
			criteria.addOrder(Order.desc("threads.PROJ_ID"));
			List<mThreads> threads = criteria.list();
			commit();
			return threads;
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
	 * Get topic list by user and year
	 * 
	 * @param null
	 * @return List
	 */
	@Override
	public List<mTopics> loadTopicListByYear(String userRole, String userCode,
			String reportingrYear) {
		try {
			begin();
			Criteria criteria = getSession().createCriteria(mTopics.class,"topics");
			if (!userRole.equals("SUPER_ADMIN")) {
				criteria.add(Restrictions.eq("topics.PROJDECL_User_Code",userCode));
			}
			criteria.add(Restrictions.eq("topics.PROJDECL_ReportingAcademicDate", reportingrYear));
			criteria.addOrder(Order.asc("topics.PROJDECL_Name"));
			List<mTopics> topics = criteria.list();
			commit();
			return topics;
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
	 * Get topic list by year
	 * 
	 * @param null
	 * @return List
	 */
	@Override
	public List<mTopics> loadTopicSummaryListByYear(String reportingrYear) {
		try {
			begin();
			Criteria criteria = getSession().createCriteria(mTopics.class,"topics");
			criteria.add(Restrictions.eq("topics.PROJDECL_ReportingAcademicDate", reportingrYear));
			criteria.addOrder(Order.asc("topics.PROJDECL_Name"));
			List<mTopics> topics = criteria.list();
			commit();
			return topics;
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
	public List<mThreads> filerThreadsList(String userRole, String userCode, Integer iStartItem, Integer iNumberOfItems, 
			String sThreadStatus, String sThreadCategory, String sThreadYear, String sThreadFaculty, 
			String sThreadDepartment, String sThreadStaff)
	{
		System.out.println("ThreadDAOImpl::filterThreadsList, userRole = " + userRole + ", userCode = " + userCode + 
				", iStartItem = " + iStartItem + ", iNumberOfItems = " + iNumberOfItems + ", sThreadStatus = " + sThreadStatus + 
				", sThreadCategory = " + sThreadCategory + ", sThreadYear = " + sThreadYear + ", sThreadFaculty = " + sThreadFaculty + 
				", sThreadDepartment = " + sThreadDepartment + ", sThreadStaff = " + sThreadStaff);
		try {
			begin();
			Criteria criteria = getSession().createCriteria(mThreads.class);
			criteria.setFirstResult(0);
			List<mThreads> threads = criteria.list();
			commit();
			return threads;
			/*
			begin();
			Criteria criteria = getSession().createCriteria(Threads.class, "threads").createAlias("staffs", "staffs", JoinType.LEFT_OUTER_JOIN).
					createAlias("staffs.department", "department", JoinType.LEFT_OUTER_JOIN).createAlias("department.faculty", "faculty", JoinType.LEFT_OUTER_JOIN);
			if (!userRole.equals("ROLE_ADMIN")) {
				criteria.add(Restrictions.eq("threads.PROJ_User_Code", userCode));
			}
			if(null != sThreadStatus && !sThreadStatus.equals("")){
				criteria.add(Restrictions.eq("threads.PROJ_Status_Code", sThreadStatus));
			}
			if(null != sThreadCategory && !sThreadCategory.equals("")){
				criteria.add(Restrictions.eq("threads.PROJ_ProjCat_Code", sThreadCategory));
			}
			if(null != sThreadYear && !sThreadYear.equals("")){
				criteria.add(Restrictions.like("threads.PROJ_AcaYear_Code", "%"+sThreadYear));
				//criteria.add(Restrictions.or(Restrictions.like("threads.PROJ_EndDate", "%"+sThreadYear)));
			}
			if(null != sThreadFaculty && !sThreadFaculty.equals("")){
				criteria.add(Restrictions.like("faculty.Faculty_Code", "%"+sThreadFaculty));
			}
			if(null != sThreadDepartment && !sThreadDepartment.equals("")){
				criteria.add(Restrictions.like("department.Department_Code", "%"+sThreadDepartment));
			}
			if(null != sThreadStaff && !sThreadStaff.equals("")){
				criteria.add(Restrictions.like("staffs.Staff_Code", "%"+sThreadStaff));
			}
			criteria.setFirstResult(iStartItem);
			criteria.setMaxResults(iNumberOfItems);
			criteria.addOrder(Order.desc("threads.PROJ_ID"));
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			List<Threads> threads = criteria.list();
			commit();
			return threads;
			*/
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
	@Override
	public List<mThreads> filerThreadsList(String userRole, String userCode, 
			String sThreadStatus, String sThreadCategory, String sThreadYear, String sThreadFaculty, 
			String sThreadDepartment, String sThreadStaff)
	{
		System.out.println("ThreadDAOImpl::filterThreadsList, userRole = " + userRole + ", userCode = " + userCode + 
				", sThreadStatus = " + sThreadStatus + 
				", sThreadCategory = " + sThreadCategory + ", sThreadYear = " + sThreadYear + ", sThreadFaculty = " + sThreadFaculty + 
				", sThreadDepartment = " + sThreadDepartment + ", sThreadStaff = " + sThreadStaff);
		try {
			begin();
			Criteria criteria = getSession().createCriteria(mThreads.class);
			criteria.setFirstResult(0);
			List<mThreads> threads = criteria.list();
			commit();
			return threads;
			/*
			begin();
			Criteria criteria = getSession().createCriteria(Threads.class, "threads").createAlias("staffs", "staffs", JoinType.LEFT_OUTER_JOIN).
					createAlias("staffs.department", "department", JoinType.LEFT_OUTER_JOIN).createAlias("department.faculty", "faculty", JoinType.LEFT_OUTER_JOIN);
			if (!userRole.equals("ROLE_ADMIN")) {
				criteria.add(Restrictions.eq("threads.PROJ_User_Code", userCode));
			}
			if(null != sThreadStatus && !sThreadStatus.equals("")){
				criteria.add(Restrictions.eq("threads.PROJ_Status_Code", sThreadStatus));
			}
			if(null != sThreadCategory && !sThreadCategory.equals("")){
				criteria.add(Restrictions.eq("threads.PROJ_ProjCat_Code", sThreadCategory));
			}
			if(null != sThreadYear && !sThreadYear.equals("")){
				criteria.add(Restrictions.like("threads.PROJ_AcaYear_Code", "%"+sThreadYear));
				//criteria.add(Restrictions.or(Restrictions.like("threads.PROJ_EndDate", "%"+sThreadYear)));
			}
			if(null != sThreadFaculty && !sThreadFaculty.equals("")){
				criteria.add(Restrictions.like("faculty.Faculty_Code", "%"+sThreadFaculty));
			}
			if(null != sThreadDepartment && !sThreadDepartment.equals("")){
				criteria.add(Restrictions.like("department.Department_Code", "%"+sThreadDepartment));
			}
			if(null != sThreadStaff && !sThreadStaff.equals("")){
				criteria.add(Restrictions.like("staffs.Staff_Code", "%"+sThreadStaff));
			}
			criteria.setFirstResult(iStartItem);
			criteria.setMaxResults(iNumberOfItems);
			criteria.addOrder(Order.desc("threads.PROJ_ID"));
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			List<Threads> threads = criteria.list();
			commit();
			return threads;
			*/
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
	 * Count Items
	 */
	@Override
	public int countItems(String userRole, String userCode, String sThreadStatus, String sThreadCategory, String sThreadYear, String sThreadFaculty, String sThreadDepartment, String sThreadStaff){
		try {
			begin();
			Criteria criteria = getSession().createCriteria(mThreads.class, "threads");
			if (!userRole.equals("ROLE_ADMIN")) {
				criteria.add(Restrictions.eq("threads.PROJ_User_Code", userCode));
			}
			if(null != sThreadStatus && !sThreadStatus.equals("")){
				criteria.add(Restrictions.eq("threads.PROJ_Status_Code", sThreadStatus));
			}
			if(null != sThreadCategory && !sThreadCategory.equals("")){
				criteria.add(Restrictions.eq("threads.PROJ_ProjCat_Code", sThreadCategory));
			}
			if(null != sThreadYear && !sThreadYear.equals("")){
				criteria.add(Restrictions.like("threads.PROJ_StartDate", "%"+sThreadYear));
				//criteria.add(Restrictions.or(Restrictions.like("threads.PROJ_EndDate", "%"+sThreadYear)));
			}
			criteria.addOrder(Order.desc("threads.PROJ_ID"));
			List<mThreads> threads = criteria.list();
			commit();
			return threads.size();
		} catch (HibernateException e) {
			e.printStackTrace();
			rollback();
			close();
			return 0;
		} finally {
			flush();
			close();
		}
	}
	
	/**
	 * Save a topic
	 * 
	 * @param object
	 * @return int
	 */
	@Override
	public int saveAThread(mThreads thread) {
		try {
			begin();
			int id = 0;
			id = (int) getSession().save(thread);
			commit();
			return id;
		} catch (HibernateException e) {
			e.printStackTrace();
			rollback();
			close();
			return 0;
		} finally {
			flush();
			close();
		}
	}

	/**
	 * Load A Thread by id and User code
	 * 
	 * @param object
	 * @return int
	 */
	@Override
	public mThreads loadAThreadByIdAndUserCode(String userRole, String userCode, int threadId) {
		try {
			begin();
			Criteria criteria = getSession().createCriteria(mThreads.class);
			criteria.add(Restrictions.eq("PROJ_ID", threadId));
			if (!userRole.equals("ROLE_ADMIN")) {
				criteria.add(Restrictions.eq("PROJ_User_Code", userCode));
			}
			mThreads thread = (mThreads) criteria.uniqueResult();
			commit();
			return thread;
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
	 * Edit a thread
	 * 
	 * @param object
	 * @return int
	 */
	@Override
	public void editAThread(mThreads thread) {
		try {
			begin();
			getSession().update(thread);
			commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			rollback();
			close();
		} finally {
			flush();
			close();
		}
	}

	/**
	 * Remove a paper
	 * 
	 * @param paperId
	 * @return
	 */
	@Override
	public int removeAThread(int threadId) {
		mThreads thread = new mThreads();
		thread.setPROJ_ID(threadId);
		System.out.println("aa = " + threadId);
		try {
			begin();
			getSession().delete(thread);
			commit();
			return 1;
		} catch (HibernateException e) {
			e.printStackTrace();
			rollback();
			close();
			return 0;
		} finally {
			flush();
			close();
		}
	}
	
	/**
	 * Get topic list by user
	 * 
	 * @param null
	 * @return List
	 */
	@Override
	public List<mThreads> loadThreadsListForReporting(String threadCategory, String threadStatus, String threadStaff, String yearForGenerating){
		try {
			begin();
			Criteria criteria = getSession().createCriteria(mThreads.class, "threads");
			if(null != yearForGenerating && !yearForGenerating.equals("")){
				criteria.add(Restrictions.eq("threads.PROJ_AcaYear_Code", yearForGenerating));
			}
			if(null != threadCategory && !threadCategory.equals("")){
				criteria.add(Restrictions.eq("threads.PROJ_ProjCat_Code", threadCategory));
			}
			if(null != threadStatus && !threadStatus.equals("")){
				criteria.add(Restrictions.eq("threads.PROJ_Status_Code", threadStatus));
			}
			if(null != threadStaff && !threadStaff.equals("")){
				criteria.add(Restrictions.eq("threads.PROJ_User_Code", threadStaff));
			}
			criteria.addOrder(Order.desc("threads.PROJ_ID"));
			List<mThreads> threads = criteria.list();
			commit();
			return threads;
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
