package vn.webapp.modules.researchmanagement.service;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import vn.webapp.libraries.DateUtil;
import vn.webapp.modules.researchdeclarationmanagement.dao.mAcademicYearDAO;
import vn.webapp.modules.researchdeclarationmanagement.dao.tProjectCategoryDAO;
import vn.webapp.modules.researchdeclarationmanagement.dao.tProjectDAO;
import vn.webapp.modules.researchdeclarationmanagement.model.mAcademicYear;
import vn.webapp.modules.researchdeclarationmanagement.model.mTopicCategory;
import vn.webapp.modules.researchdeclarationmanagement.model.mTopics;
import vn.webapp.modules.researchmanagement.dao.mProjectCallsDAO;
import vn.webapp.modules.researchmanagement.dao.mProjectStaffsDAO;
import vn.webapp.modules.researchmanagement.dao.mProjectStatusDAO;
import vn.webapp.modules.researchmanagement.dao.nProjectDAO;
import vn.webapp.modules.researchmanagement.model.mProjectCalls;
import vn.webapp.modules.researchmanagement.model.mProjectStaffs;
import vn.webapp.modules.researchmanagement.model.mProjectStatus;
import vn.webapp.modules.researchmanagement.model.mThreads;
import vn.webapp.modules.usermanagement.dao.mDepartmentDAO;
import vn.webapp.modules.usermanagement.dao.mFacultyDAO;
import vn.webapp.modules.usermanagement.dao.mStaffDAO;
import vn.webapp.modules.usermanagement.dao.mUserDAO;
import vn.webapp.modules.usermanagement.model.mDepartment;
import vn.webapp.modules.usermanagement.model.mFaculty;
import vn.webapp.modules.usermanagement.model.mStaff;

@Service("mProjectCallsService")
public class mProjectCallsServiceImpl implements mProjectCallsService {

	@Autowired
	private mProjectCallsDAO projectCallsDAO;

	/**
	 * Get a list Threads by user code
	 * 
	 * @param String
	 * @return object
	 */
	@Override
	public List<mProjectCalls> loadProjectCallsList(){
		try {
			return projectCallsDAO.loadProjectCallsList();
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return null;
		}
	}

	/**
	 * 
	 */
	@Override
	public int saveAProjectCall(String sPROJCALL_CODE, String sPROJCALL_PROJCATCODE, String sPROJCALL_NAME, String sPROJCALL_DATE, String sPROJCALL_STATUS){
		try {
			if (sPROJCALL_CODE != null && sPROJCALL_NAME != null) {
				mProjectCalls projectCalls = new mProjectCalls();
				projectCalls.setPROJCALL_CODE(sPROJCALL_CODE);
				projectCalls.setPROJCALL_PROJCATCODE(sPROJCALL_PROJCATCODE);
				projectCalls.setPROJCALL_NAME(sPROJCALL_NAME);
				projectCalls.setPROJCALL_DATE(sPROJCALL_DATE);
				projectCalls.setPROJCALL_STATUS(sPROJCALL_STATUS);
				
				// Create 
				int i_InsertedAProjectCallId = projectCallsDAO.saveAProjectCall(projectCalls);
				mProjectCalls insertedProjectCalls = projectCallsDAO.loadAProjectCallById(i_InsertedAProjectCallId);
				LocalDate o_fFormatDateByFormat = DateUtil.o_fFormatDateByFormatType2(sPROJCALL_DATE);
				sPROJCALL_CODE = "T"+o_fFormatDateByFormat.getYear()+i_InsertedAProjectCallId;
				insertedProjectCalls.setPROJCALL_CODE(sPROJCALL_CODE);
				insertedProjectCalls.setPROJCALL_PROJCATCODE(sPROJCALL_PROJCATCODE);
				insertedProjectCalls.setPROJCALL_NAME(sPROJCALL_NAME);
				insertedProjectCalls.setPROJCALL_DATE(sPROJCALL_DATE);
				
				projectCallsDAO.editAProjectCall(insertedProjectCalls);
				return 1;
			}
			return 0;
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return 0;
		}
	}

	/**
	 * load a project call id
	 * 
	 * @param String
	 * @param int
	 * @return object
	 */
	@Override
	public mProjectCalls loadAProjectCallById(int iProjectCallId){
		try {
			return projectCallsDAO.loadAProjectCallById(iProjectCallId);
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * 
	 */
	@Override
	public mProjectCalls loadAProjectCallByCode(String sProjectCallCode){
		try {
			return projectCallsDAO.loadAProjectCallByCode(sProjectCallCode);
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * 
	 */
	@Override
	public mProjectCalls loadAProjectCallByName(String sProjectCallName){
		try {
			return projectCallsDAO.loadAProjectCallByName(sProjectCallName);
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * 
	 */
	@Override
	public int checkingExistProjectCallByName(int projectCallId, String sPROJCALL_NAME){
		try {
			return projectCallsDAO.checkingExistProjectCallByName(projectCallId, sPROJCALL_NAME);
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return 0;
		}
	}

	/**
	 * Edit a project call
	 * 
	 * @param String
	 * @param int
	 * @return null
	 */
	@Override
	public void editAProjectCall(int iProjectCallId, String sPROJCALL_CODE, String PROJCALL_PROJCATCODE, String PROJCALL_NAME, String PROJCALL_DATE, String sPROJCALL_STATUS){
		try {
			mProjectCalls projectCalls = projectCallsDAO.loadAProjectCallById(iProjectCallId);
			if (projectCalls != null) {
				projectCalls.setPROJCALL_CODE(sPROJCALL_CODE);
				projectCalls.setPROJCALL_DATE(PROJCALL_DATE);
				projectCalls.setPROJCALL_PROJCATCODE(PROJCALL_PROJCATCODE);
				projectCalls.setPROJCALL_NAME(PROJCALL_NAME);
				projectCalls.setPROJCALL_STATUS(sPROJCALL_STATUS);
				projectCallsDAO.editAProjectCall(projectCalls);
			}
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}
	}

	/**
	 * Remove a thread
	 * 
	 * @param int
	 * @return int
	 */
	@Override
	public int removeAProjectCall(int iProjectCallId){
		mProjectCalls projectCalls = projectCallsDAO.loadAProjectCallById(iProjectCallId);
		return projectCallsDAO.removeAProjectCall(projectCalls);
	}
	
	/**
	 * 
	 */
	public int i_fCheckExistProjectCall(String sPROJCALL_NAME){
		try {
			mProjectCalls projectCalls = projectCallsDAO.loadAProjectCallByName(sPROJCALL_NAME);
			if(projectCalls != null){
				return 1;
			}else{
				return 0;
			}
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return 0;
		}
	}

}
