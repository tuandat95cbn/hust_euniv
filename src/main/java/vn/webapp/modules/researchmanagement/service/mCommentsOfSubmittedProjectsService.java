package vn.webapp.modules.researchmanagement.service;

import java.util.List;

import vn.webapp.modules.researchmanagement.model.mCommentsOfSubmittedProjects;

public interface mCommentsOfSubmittedProjectsService {
	/**
	 * 
	 */
	public List<mCommentsOfSubmittedProjects> loadAllCommentsOfSubmittedProjects();
	
	/**
	 * 
	 * @param COMPROJ_STAFFCODE
	 * @param COMPROJ_PRJCODE
	 * @return
	 */
	public mCommentsOfSubmittedProjects loadCommentsOfSubmittedProjectByStaffCodeProjectCode(String COMPROJ_STAFFCODE, String COMPROJ_PRJCODE);
	
	/**
	 * 
	 * @param COMPROJ_STAFFCODE
	 * @param COMPROJ_PRJCODE
	 * @param COMPROJ_COMMENT
	 * @param COMPROJ_UpdateDate
	 * @param COMPROJ_Lock
	 * @return
	 */
	public int saveCommentsOfSubmittedProjects(String COMPROJ_STAFFCODE, String COMPROJ_PRJCODE, String COMPROJ_COMMENT, String COMPROJ_UpdateDate, boolean COMPROJ_Lock); 

	/**
	 * 
	 * @param commentsOfSubmittedProjects
	 * @return
	 */
	public int submitCommentsOfSubmittedProjects(mCommentsOfSubmittedProjects commentsOfSubmittedProjects);
	
	/**
	 * 
	 * @param iCommentsOfSubmittedProjectsId
	 * @return
	 */
	public mCommentsOfSubmittedProjects loadCommentsOfSubmittedProjectsById(int iCommentsOfSubmittedProjectsId);
	
	/**
	 * Editing the comment of a comments of submitted projects
	 */
	public void editCommentsOfSubmittedProjects(int COMPROJ_ID, String COMPROJ_COMMENT);
	
	/**
	 * 
	 * @param COMPROJ_ID
	 * @return
	 */
	public int deleteCommentsOfSubmittedProjects(int COMPROJ_ID);
}
