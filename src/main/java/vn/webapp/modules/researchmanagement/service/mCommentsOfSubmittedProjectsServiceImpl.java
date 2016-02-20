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

//For comments of submitted projects 
import vn.webapp.modules.researchmanagement.dao.mCommentsOfSubmittedProjectsDAO;
import vn.webapp.modules.researchmanagement.model.mCommentsOfSubmittedProjects;;


@Service("mCommentsOfSubmittedProjectsService")
public class mCommentsOfSubmittedProjectsServiceImpl implements mCommentsOfSubmittedProjectsService {

	@Autowired
	private mCommentsOfSubmittedProjectsDAO commentsOfSubmittedProjectsDAO;

	/**
	 * 
	 */
	@Override
	public List<mCommentsOfSubmittedProjects> loadAllCommentsOfSubmittedProjects(){
		try {
			return commentsOfSubmittedProjectsDAO.loadAllCommentsOfSubmittedProjects();
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return null;
		}
	}

	/**
	 * 
	 */
	@Override
	public int saveCommentsOfSubmittedProjects(String COMPROJ_STAFFCODE, String COMPROJ_PRJCODE, String COMPROJ_COMMENT, String COMPROJ_UpdateDate, boolean COMPROJ_Lock){
		try {
			if (COMPROJ_STAFFCODE != null && COMPROJ_PRJCODE != null) {
				mCommentsOfSubmittedProjects commentsOfSubmittedProjects = new mCommentsOfSubmittedProjects();
				
				commentsOfSubmittedProjects.setCOMPROJ_CODE(COMPROJ_PRJCODE + "-" + COMPROJ_STAFFCODE);
				commentsOfSubmittedProjects.setCOMPROJ_PRJCODE(COMPROJ_PRJCODE);
				commentsOfSubmittedProjects.setCOMPROJ_STAFFCODE(COMPROJ_STAFFCODE);
				commentsOfSubmittedProjects.setCOMPROJ_COMMENT(COMPROJ_COMMENT);
				commentsOfSubmittedProjects.setCOMPROJ_UpdateDate(COMPROJ_UpdateDate);
				commentsOfSubmittedProjects.setCOMPROJ_Lock(COMPROJ_Lock);
				
				
				int iInsertResult = commentsOfSubmittedProjectsDAO.saveCommentsOfSubmittedProjects(commentsOfSubmittedProjects);
				
				return iInsertResult;
			}else{
				return 0;
			}
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return 0;
		}
	}

	/**
	 * 
	 */
	public int submitCommentsOfSubmittedProjects(mCommentsOfSubmittedProjects commentsOfSubmittedProjects){
		try {
			commentsOfSubmittedProjects.setCOMPROJ_Lock(true);
			commentsOfSubmittedProjectsDAO.editCommentsOfSubmittedProjects(commentsOfSubmittedProjects);
				
			return 1;
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return 0;
		}
	}
	/**
	 * 
	 */
	@Override
	public mCommentsOfSubmittedProjects loadCommentsOfSubmittedProjectsById(int iCommentsOfSubmittedProjectsId){
		try {
			return commentsOfSubmittedProjectsDAO.loadCommentsOfSubmittedProjectsById(iCommentsOfSubmittedProjectsId);
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * 
	 */
	public mCommentsOfSubmittedProjects loadCommentsOfSubmittedProjectByStaffCodeProjectCode(String COMPROJ_STAFFCODE, String COMPROJ_PRJCODE){
		try {
			return commentsOfSubmittedProjectsDAO.loadCommentsOfSubmittedProjectByStaffCodeProjectCode(COMPROJ_STAFFCODE, COMPROJ_PRJCODE);
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return null;
		}
	}

	/**
	 * 
	 */
	@Override
	public void editCommentsOfSubmittedProjects(int COMPROJ_ID, String COMPROJ_COMMENT){
		try {
			mCommentsOfSubmittedProjects commentsOfSubmittedProjects = commentsOfSubmittedProjectsDAO.loadCommentsOfSubmittedProjectsById(COMPROJ_ID);
			System.out.println("Comments at service " + commentsOfSubmittedProjects.getCOMPROJ_COMMENT());
			
			if (commentsOfSubmittedProjects != null) {
				commentsOfSubmittedProjects.setCOMPROJ_COMMENT(COMPROJ_COMMENT);
				
				commentsOfSubmittedProjectsDAO.editCommentsOfSubmittedProjects(commentsOfSubmittedProjects);
			}
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}
	}


	/**
	 * 
	 */
	@Override
	public int deleteCommentsOfSubmittedProjects(int COMPROJ_ID){
		mCommentsOfSubmittedProjects commentsOfSubmittedProjects = commentsOfSubmittedProjectsDAO.loadCommentsOfSubmittedProjectsById(COMPROJ_ID);
		return commentsOfSubmittedProjectsDAO.deleteCommentsOfSubmittedProjects(commentsOfSubmittedProjects);
	}
}
