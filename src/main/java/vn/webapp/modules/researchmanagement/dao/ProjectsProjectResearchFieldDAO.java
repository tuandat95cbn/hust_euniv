package vn.webapp.modules.researchmanagement.dao;

import vn.webapp.modules.researchmanagement.model.ProjectsProjectResearchField;

public interface ProjectsProjectResearchFieldDAO {
	
	/**
     * 
     * @param projectTask
     * @return
     */
    public int saveAProjectSearchField(ProjectsProjectResearchField projectsProjectResearchField);
    
    /**
     * 
     * @param projectTask
     * @return
     */
    public int removeAProjectSearchField(ProjectsProjectResearchField projectsProjectResearchField);
    
    /**
     * 
     * @param pt
     */
    public void editAProjectSearchField(ProjectsProjectResearchField projectsProjectResearchField);
    
}
