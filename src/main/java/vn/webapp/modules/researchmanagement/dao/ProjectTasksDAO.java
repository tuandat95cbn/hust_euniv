package vn.webapp.modules.researchmanagement.dao;

import java.util.List;

import vn.webapp.modules.researchmanagement.model.ProjectTasks;

public interface ProjectTasksDAO {
	/**
	 * 
	 * @return
	 */
	public List<ProjectTasks> getList();
    
	/**
	 * 
	 * @param projectStatusCode
	 * @return
	 */
    public ProjectTasks loadAProjectTaskByCode(String sCode);
}
