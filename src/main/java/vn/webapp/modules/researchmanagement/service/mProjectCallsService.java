package vn.webapp.modules.researchmanagement.service;

import java.util.List;
import vn.webapp.modules.researchmanagement.model.mProjectCalls;

public interface mProjectCallsService {
	/**
	 * 
	 * @param userRole
	 * @param userCode
	 * @return
	 */
	public List<mProjectCalls> loadProjectCallsList();

	/**
	 * 
	 * @param sPROJCALL_CODE
	 * @param PROJCALL_PROJCATCODE
	 * @param PROJCALL_NAME
	 * @param PROJCALL_DATE
	 * @return
	 */
	public int saveAProjectCall(String sPROJCALL_CODE, String PROJCALL_PROJCATCODE, String PROJCALL_NAME, String PROJCALL_DATE);

	/**
	 * 
	 * @return
	 */
	public mProjectCalls loadAProjectCallById(int iProjectCallId);
	
	/**
	 * 
	 * @return
	 */
	public mProjectCalls loadAProjectCallByCode(String sProjectCallCode);

	/**
	 * 
	 * @param iProjectCallId
	 * @param sPROJCALL_CODE
	 * @param PROJCALL_PROJCATCODE
	 * @param PROJCALL_NAME
	 * @param PROJCALL_DATE
	 */
	public void editAProjectCall(int iProjectCallId, String sPROJCALL_CODE, String sPROJCALL_PROJCATCODE, String sPROJCALL_NAME, String sPROJCALL_DATE);
	
	/**
	 * 
	 * @param threadId
	 * @return
	 */
	public int removeAProjectCall(int iProjectCallId);
	
	/**
	 * 
	 * @return
	 */
	public int i_fCheckExistProjectCall(String sPROJCALL_NAME);
	
}
