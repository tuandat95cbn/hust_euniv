package vn.webapp.modules.researchmanagement.service;

import java.util.List;

import vn.webapp.modules.researchdeclarationmanagement.model.mTopics;
import vn.webapp.modules.researchmanagement.model.Projects;
import vn.webapp.modules.researchmanagement.model.mThreads;

public interface nProjectService {
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
	 * @return
	 */
	public List<Projects> loadProjectsListByStaff(String userRole, String userCode);

	/**
	 * 
	 * @param userRole
	 * @param userCode
	 * @param reportingrYear
	 * @return
	 */
	public List<mTopics> loadTopicListByYear(String userRole, String userCode,
			String reportingrYear);

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
	 * @param sThreadStatus
	 * @param sThreadCategory
	 * @param sThreadYear
	 * @param sThreadFaculty
	 * @param sThreadDepartment
	 * @param sThreadStaff
	 * @return
	 */
	public List<mThreads> filerThreadsListNoPagination(String userRole, String userCode,
			String sThreadStatus,
			String sThreadCategory, String sThreadYear, String sThreadFaculty,
			String sThreadDepartment, String sThreadStaff);
	
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
	public int countItems(String userRole, String userCode, String sThreadStatus, String sThreadCategory, String sThreadYear, String sThreadFaculty, String sThreadDepartment, String sThreadStaff);

	/**
	 * 
	 * @param userCode
	 * @param threadName
	 * @param threadCategory
	 * @param threadContent
	 * @param threadStartDate
	 * @param threadEndDate
	 * @param threadMotivation
	 * @param threadReportingDate
	 * @param threadResult
	 * @param threadStatus
	 * @param threadBudget
	 * @param threadSourceUploadFileSrc
	 * @param threadCode
	 * @param listStaffs
	 * @param listStaffRoles
	 * @return
	 */
	public int saveAThread(String userCode, String threadName, String threadCategory, String threadContent, String threadStartDate, 
							String threadEndDate, String threadMotivation, String threadReportingDate, String threadResult, String threadStatus, 
							int threadBudget, String threadSourceUploadFileSrc, String threadCode, List<String> listStaffs, List<String> listStaffRoles);

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
	 * @param userRole
	 * @param userCode
	 * @param threadId
	 * @return
	 */
	public Projects loadAProjectByIdAndUserCode(String userRole, String userCode, int projectId);

	/**
	 * 
	 * @param userRole
	 * @param userCode
	 * @param threadName
	 * @param threadCategory
	 * @param threadContent
	 * @param threadStartDate
	 * @param threadEndDate
	 * @param threadMotivation
	 * @param threadReportingDate
	 * @param threadResult
	 * @param threadStatus
	 * @param threadBudget
	 * @param threadSourceUploadFileSrc
	 * @param threadCode
	 * @param threadId
	 * @param listStaffs
	 * @param listStaffRoles
	 */
	public void editAThread(String userRole, String userCode, String threadName, String threadCategory, String threadContent, 
							String threadStartDate, String threadEndDate, String threadMotivation, String threadReportingDate, String threadResult, 
							String threadStatus, int threadBudget, String threadSourceUploadFileSrc, String threadCode, int threadId, List<String> listStaffs, List<String> listStaffRoles);

	/**
	 * 
	 * @param userRole
	 * @param userCode
	 * @param threadId
	 * @param threadStatus
	 */
	public void editStatusThread(String userRole, String userCode, int threadId, String threadStatus);
	
	/**
	 * 
	 * @param threadId
	 * @return
	 */
	public int removeAThread(int threadId);
	
	/**
	 * 
	 * @param projectId
	 * @return
	 */
	public int removeAProject(int projectId);
	
	/**
	 * 
	 * @param threadCategory
	 * @param threadStatus
	 * @param threadFaculty
	 * @param threadDepartment
	 * @param threadStaff
	 * @param yearForGenerating
	 * @return
	 */
	public List<mThreads> loadThreadsListForReporting(String threadCategory, String threadStatus, String threadFaculty, String threadDepartment, String threadStaff, String yearForGenerating);
	
	/**
	 * 
	 * @param facultyCode
	 * @return
	 */
	public String getFacultyName(String facultyCode);
	
	/**
	 * 
	 * @param departmentCode
	 * @return
	 */
	public String getDepartmentName(String departmentCode);
}
