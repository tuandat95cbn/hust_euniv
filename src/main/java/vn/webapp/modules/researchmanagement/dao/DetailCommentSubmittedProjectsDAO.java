package vn.webapp.modules.researchmanagement.dao;

import java.util.List;

import vn.webapp.modules.researchmanagement.model.DetailCommentSubmittedProjects;
import vn.webapp.modules.researchmanagement.model.mCommentsOfSubmittedProjects;

public interface DetailCommentSubmittedProjectsDAO{

	/**
	 * 
	 * @param commentsOfSubmittedProjects
	 * @return
	 */
	public int saveDetailsCommentsOfSubmittedProjects(DetailCommentSubmittedProjects commentsOfSubmittedProjects); 

	/**
	 * 
	 * @param commentsOfSubmittedProjects
	 */
	public void editDetailsCommentsOfSubmittedProjects(DetailCommentSubmittedProjects commentsOfSubmittedProjects);
	
	/**
	 * 
	 * @param iCommentsOfSubmittedProjectsId
	 * @return
	 */
	public DetailCommentSubmittedProjects loadDetailsCommentsOfSubmittedProjectsByProjectCode(String sProjectCode);

}
