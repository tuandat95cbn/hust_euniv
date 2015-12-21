package vn.webapp.modules.researchmanagement.dao;

import java.util.List;
import vn.webapp.modules.researchdeclarationmanagement.model.mTopics;
import vn.webapp.modules.researchmanagement.model.mThreads;

public interface nProjectDAO {
	/**
	 * 
	 * @param userRole
	 * @param userCode
	 * @return
	 */
	public List<mThreads> loadThreadsListByStaff(String userRole, String userCode);

	/**
	 * 
	 * @param userRole
	 * @param userCode
	 * @param reportingrYear
	 * @return
	 */
	public List<mTopics> loadTopicListByYear(String userRole, String userCode, String reportingrYear);

	/**
	 * 
	 * @param reportingrYear
	 * @return
	 */
	public List<mTopics> loadTopicSummaryListByYear(String reportingrYear);
	
	/**
	 * 
	 * @param userRole
	 * @param userCode
	 * @param iStartItem
	 * @param iNumberOfItems
	 * @param sThreadStatus
	 * @param sThreadCategory
	 * @param sThreadYear
	 * @param sThreadFaculty
	 * @param sThreadDepartment
	 * @param sThreadStaff
	 * @return
	 */
	public List<mThreads> filerThreadsList(String userRole, String userCode, Integer iStartItem, Integer iNumberOfItems, String sThreadStatus, String sThreadCategory, String sThreadYear, String sThreadFaculty, String sThreadDepartment, String sThreadStaff);
	
	/**
	 * 
	 * @param userRole
	 * @param userCode
	 * @param sThreadStatus
	 * @param sThreadCategory
	 * @param sThreadYear
	 * @param sThreadFaculty
	 * @param sThreadDepartment
	 * @param sThreadStaff
	 * @return
	 */
	public List<mThreads> filerThreadsList(String userRole, String userCode, String sThreadStatus, String sThreadCategory, String sThreadYear, String sThreadFaculty, String sThreadDepartment, String sThreadStaff);
	
	/**
	 * 
	 * @param userRole
	 * @param userCode
	 * @param sThreadStatus
	 * @param sThreadCategory
	 * @param sThreadYear
	 * @param sThreadFaculty
	 * @param sThreadDepartment
	 * @param sThreadStaff
	 * @return
	 */
	public int countItems(String userRole, String userCode, String sThreadStatus, String sThreadCategory, String sThreadYear, String sThreadFaculty, String sThreadDepartment, String sThreadStaff);

	/**
	 * 
	 * @param thread
	 * @return
	 */
	public int saveAThread(mThreads thread); 

	/**
	 * 
	 * @param userRole
	 * @param userCode
	 * @param threadId
	 * @return
	 */
	public mThreads loadAThreadByIdAndUserCode(String userRole, String userCode, int threadId);

	/**
	 * 
	 * @param thread
	 */
	public void editAThread(mThreads thread);

	/**
	 * 
	 * @param threadId
	 * @return
	 */
	public int removeAThread(int threadId);
	
	/**
	 * 
	 * @param threadCategory
	 * @param threadStatus
	 * @param threadStaff
	 * @param yearForGenerating
	 * @return
	 */
	public List<mThreads> loadThreadsListForReporting(String threadCategory, String threadStatus, String threadStaff, String yearForGenerating);

}
