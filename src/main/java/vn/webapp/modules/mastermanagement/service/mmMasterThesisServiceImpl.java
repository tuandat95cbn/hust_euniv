/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.webapp.modules.mastermanagement.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.webapp.modules.mastermanagement.dao.mmDefenseSessionsDAO;
import vn.webapp.modules.mastermanagement.dao.mmListMasterThesisDAO;
import vn.webapp.modules.mastermanagement.dao.mmMasterThesisDAO;
import vn.webapp.modules.mastermanagement.dao.mmStaffSpecialKeyWordsDAO;
import vn.webapp.modules.mastermanagement.dao.mmStudentDAO;
import vn.webapp.modules.mastermanagement.model.mmDefenseSession;
import vn.webapp.modules.mastermanagement.model.mmListMasterThesis;
import vn.webapp.modules.mastermanagement.model.mmMasterThesis;
import vn.webapp.modules.mastermanagement.model.mmRawMasterThesis;
import vn.webapp.modules.mastermanagement.model.mmSpecializationKeyword;
import vn.webapp.modules.mastermanagement.model.mmStaff;
import vn.webapp.modules.mastermanagement.model.mmStaffSpecializationKeywords;
import vn.webapp.modules.mastermanagement.model.mmStudent;


@Service("mmmasterThesisService")
public class mmMasterThesisServiceImpl implements mmMasterThesisService {

    @Autowired
    private mmMasterThesisDAO mmmasterThesisDAO;
    
    @Autowired
    private mmListMasterThesisDAO mmlistMasterThesisDAO;
    
    @Autowired
    private mmDefenseSessionsDAO mmlistDefenseSessionDAO;
    
    @Autowired
    private mmStudentDAO mmstudentDAO;
    
    @Autowired
    private mmStaffSpecialKeyWordsDAO mmstaffSpecialKeyWordsDAO;
    
    /**
     * Get MasterThesis list
     * @param String
     * @return object
     */
    @Override
    public List<mmMasterThesis> listMasterThesis(){
    	try {
            return mmmasterThesisDAO.listMasterThesis();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return null;
        }
    }
    
       
    /**
     * Get an MasterThesis by id
     * @param String
     * @return object
     */
     
    public mmMasterThesis loadMasterThesisById(int masterThesisID){
    	try {
            return mmmasterThesisDAO.getMasterThesisById(masterThesisID);
            
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Edit a MasterThesis
     * @param String
     * @return int
     */
    @Override
    public void editAMasterThesis(int ThesisID, String ThesisCode, String ThesisName, mmStudent student, mmStaff supervisor, HashSet<mmSpecializationKeyword> specializationKeywords){
    	
    	 mmMasterThesis masterThesis = mmmasterThesisDAO.getMasterThesisById(ThesisID);
    	 if(masterThesis != null){
    	   	masterThesis.setThesis_Name(ThesisName);
    	   	masterThesis.setThesis_Code(ThesisCode);
    	   	//masterThesis.setStudent(student);
    	   	//masterThesis.setSupervisor(supervisor);
    		masterThesis.setListSpecializationKeywords(specializationKeywords);
    	   	   		 
    		mmmasterThesisDAO.editAMasterThesis(masterThesis);
	    	return;
	    }
    	else
    		return;
    }
    
    /**
     * Save a MasterThesis
     * @param String
     * @return int
     */
    @Override
    public int saveAMasterThesis(String ThesisCode, String ThesisName, String studentCode, String supervisor, 
    								HashSet<mmSpecializationKeyword> specializationKeywords, mmStudent student){
    	
    	// Save info for MasterThesis
    	mmRawMasterThesis masterThesis = new mmRawMasterThesis();
    	masterThesis.setThesis_Name(ThesisName);
	   	masterThesis.setThesis_Code(ThesisCode);
	   	masterThesis.setThesis_StudentCode(studentCode);
	   	masterThesis.setThesis_SupervisorCode(supervisor);
	   	
	   	// Save special keywords for a staff
	   	if(specializationKeywords != null)
	   	{
	   		for (mmSpecializationKeyword specializationKeyword : specializationKeywords) {
	   			
	   			mmStaffSpecializationKeywords staffSpecializationKeywords = mmstaffSpecialKeyWordsDAO.getStaffSpecializationKeywordsByStaffAndCode(supervisor, specializationKeyword.getKW_Code());
	   			if(staffSpecializationKeywords == null)
	   			{
	   				mmStaffSpecializationKeywords newStaffSpecializationKeywords = new mmStaffSpecializationKeywords();
	   				
	   				newStaffSpecializationKeywords.setSTFKW_KeywordCode(specializationKeyword.getKW_Code());
	   				newStaffSpecializationKeywords.setSTFKW_StaffCode(supervisor);
	   				mmstaffSpecialKeyWordsDAO.saveAStaffSpecializationKeywords(newStaffSpecializationKeywords);
	   			}
	   				
			}
	   	}
		
		// Change status of a Student
		student.setStudent_StatusID(1);
		mmstudentDAO.editAStudent(student);
		
		return mmmasterThesisDAO.saveAMasterThesis(masterThesis);
    }
    
    /**
     * Remove a MasterThesis
     * @param int
     * @return int
     */
    @Override
    public int removeAMasterThesis(int masterThesisID){
    	return mmmasterThesisDAO.removeAMasterThesis(masterThesisID);
    }
    
    /**
     * 
     */
    @Override
    public List<mmListMasterThesis> getListMasterThesis(){
    	try {
            return mmlistMasterThesisDAO.getListMasterThesis();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get DefenseSession list
     * @param String
     * @return object
     */
    @Override
    public List<mmDefenseSession> listDefenseSession(){
    	try {
            return mmlistDefenseSessionDAO.getListDefenseSession();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return null;
        }
    }
}
