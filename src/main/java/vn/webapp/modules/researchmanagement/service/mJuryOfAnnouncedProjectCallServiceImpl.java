package vn.webapp.modules.researchmanagement.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.webapp.modules.researchmanagement.dao.mJuryOfAnnouncedProjectCallDAO;
import vn.webapp.modules.researchmanagement.model.mJuryOfAnnouncedProjectCall;
import vn.webapp.modules.researchmanagement.model.mProjectCalls;




@Service("mJuryOfAnnouncedProjectCallService")
public class mJuryOfAnnouncedProjectCallServiceImpl implements mJuryOfAnnouncedProjectCallService {
	@Autowired
	private mJuryOfAnnouncedProjectCallDAO juryOfAnnouncedProjectCallDAO;

	/**
	 * 
	 */
	@Override
	public List<mJuryOfAnnouncedProjectCall> loadAllJuryOfAnnouncedProjectCall() {
		try {
			return juryOfAnnouncedProjectCallDAO.loadAllJuryOfAnnouncedProjectCall();
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * 
	 */
	public mJuryOfAnnouncedProjectCall loadAJuryOfAnnouncedProjectCallById(int JUSUPRJ_ID){
		try {
			return juryOfAnnouncedProjectCallDAO.loadAJuryOfAnnouncedProjectCallById(JUSUPRJ_ID);
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * 
	 */
	public List<mJuryOfAnnouncedProjectCall> loadListJuryOfAnnouncedProjectCallByPresentCode(String JUSUPRJ_STAFFCODE){
		try {
			return juryOfAnnouncedProjectCallDAO.loadListJuryOfAnnouncedProjectCallByPresentCode(JUSUPRJ_STAFFCODE);
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * 
	 */
	@Override
	public int saveJuryOfAnnouncedProjectCall(String JUSUPRJ_STAFFCODE,String JUSUPRJ_PRJCALLCODE,String JUPSURJ_ROLECODE) {
		if (JUSUPRJ_STAFFCODE.length() >= 1 && JUSUPRJ_PRJCALLCODE.length() >= 1 && JUPSURJ_ROLECODE.length() >= 1) {
			
			mJuryOfAnnouncedProjectCall jury = new mJuryOfAnnouncedProjectCall();
			
			jury.setJUSUPRJ_STAFFCODE(JUSUPRJ_STAFFCODE);
			jury.setJUSUPRJ_PRJCALLCODE(JUSUPRJ_PRJCALLCODE);
			jury.setJUPSURJ_ROLECODE(JUPSURJ_ROLECODE);
			
			System.out.println("Service");
			System.out.println("Staff code : " + JUSUPRJ_STAFFCODE);
			System.out.println("Project call code : " + JUSUPRJ_PRJCALLCODE);
			System.out.println("Role code : " + JUPSURJ_ROLECODE);

			int resultSaveJuryOfAnnouncedProjectCall = juryOfAnnouncedProjectCallDAO.saveJuryOfAnnouncedProjectCall(jury);
			
			
			return resultSaveJuryOfAnnouncedProjectCall;
		}
		return 0;
	}
	
	public void editJuryOfAnnouncedProjectCall(int JUSUPRJ_ID, String JUSUPRJ_STAFFCODE,String JUSUPRJ_PRJCALLCODE,String JUPSURJ_ROLECODE){
		try {
			mJuryOfAnnouncedProjectCall juryOfAnnouncedProjectCall = juryOfAnnouncedProjectCallDAO.loadAJuryOfAnnouncedProjectCallById(JUSUPRJ_ID);
			if(juryOfAnnouncedProjectCall != null){
				juryOfAnnouncedProjectCall.setJUSUPRJ_PRJCALLCODE(JUSUPRJ_PRJCALLCODE);
				juryOfAnnouncedProjectCall.setJUSUPRJ_STAFFCODE(JUSUPRJ_STAFFCODE);
				juryOfAnnouncedProjectCall.setJUPSURJ_ROLECODE(JUPSURJ_ROLECODE);
				
				juryOfAnnouncedProjectCallDAO.editJuryOfAnnouncedProjectCall(juryOfAnnouncedProjectCall);
			}
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}
	}

	/**
	 * 
	 */
	public int deleteJuryOfAnnouncedProjectCall(int JUSUPRJ_ID){
		
		mJuryOfAnnouncedProjectCall juryOfAnnouncedProjectCall = juryOfAnnouncedProjectCallDAO.loadAJuryOfAnnouncedProjectCallById(JUSUPRJ_ID);
		return juryOfAnnouncedProjectCallDAO.deleteJuryOfAnnouncedProjectCall(juryOfAnnouncedProjectCall);
	}
	
}
