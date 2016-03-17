package vn.webapp.modules.researchmanagement.dao;

import java.util.List;
import vn.webapp.modules.researchmanagement.model.mStaffJuryOfSubmittedProject;


public interface mStaffJuryOfSubmittedProjectDAO {
	
	/**
	 * Loading staff juries of all submitted projects (getting all records from table : tbljurysubmittedprojectroles) 
	 * @return : List of objects of JuryOfAnnouncedProjectCall
	 */
	public List<mStaffJuryOfSubmittedProject> loadAllStaffJuryOfSubmittedProject();
	
	/**
	 * Loading staff juries of all submitted projects by staff code (getting all records from table : tbljurysubmittedprojectroles)
	 * @return
	 */
	public List<mStaffJuryOfSubmittedProject> loadListStaffJuryOfSubmittedProjectByStaffCode(String STFJUPRJ_STAFFJURCODE);
	
	/**
	 * Loading a staff jury of submitted project  by its id (getting a record from table : tbljurysubmittedprojectroles)
	 * @param STFJUPRJ_ID
	 * @return
	 */
	public mStaffJuryOfSubmittedProject loadAStaffJuryOfSubmittedProjectById(int STFJUPRJ_ID);
	
	/**
	 * Saving a staff jury of submitted project  (saving a record in table: tbljurysubmittedprojectroles )
	 * @param juryOfAnnouncedProjectCall
	 * @return
	 */
	public int saveStaffJuryOfSubmittedProject(mStaffJuryOfSubmittedProject staffJuryOfSubmittedProject);
	
	/**
	 * Deleting a staff jury of submitted project  (deleting a record from table : tbljurysubmittedprojectroles) 
	 * @param juryOfAnnouncedProjectCall
	 * @return
	 */
	public int deleteStaffJuryOfSubmittedProject(mStaffJuryOfSubmittedProject staffJuryOfSubmittedProject);
	
	/**
	 * Editing a staff jury of submitted project (updating a record from table :tbljurysubmittedprojectroles)
	 * @param juryOfAnnouncedProjectCall
	 */
	public void editStaffJuryOfSubmittedProject(mStaffJuryOfSubmittedProject staffJuryOfSubmittedProject);
}