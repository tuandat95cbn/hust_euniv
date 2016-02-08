package vn.webapp.modules.researchmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.webapp.modules.researchmanagement.dao.ProjectTasksDAO;
import vn.webapp.modules.researchmanagement.model.ProjectTasks;

@Service("ProjectTasksService")
public class ProjectTasksServiceImpl implements ProjectTasksService {
	
	@Autowired
    private ProjectTasksDAO projectTasksDAO;

    /**
     * Get all list of project statuses
     * @param null
     * @return List
     */
    @Override
    public List<ProjectTasks> getList(){
    	return projectTasksDAO.getList();
    }

    /**
     * 
     */
    @Override
    public ProjectTasks loadAProjectTaskByCode(String sCode){
    	try {
			if (sCode != null) {
				return projectTasksDAO.loadAProjectTaskByCode(sCode);
			}
			return null;
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return null;
		}
    }
    
    /**
     * 
     */
    @Override
    public List<ProjectTasks> loadAProjectTaskByProjectCode(String sProjectCode)
    {
    	try {
			if (sProjectCode != null) {
				return projectTasksDAO.loadAProjectTaskByProjectCode(sProjectCode);
			}
			return null;
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return null;
		}
    }
}
