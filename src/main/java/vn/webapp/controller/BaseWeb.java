/**
 * @author : HaTN 
 * @address : HUST K51
 * @modified : December 27th, 2015
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.webapp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;

import vn.webapp.modules.usermanagement.model.mEditFunctions;
import vn.webapp.modules.usermanagement.model.mFuncsPermission;
import vn.webapp.modules.usermanagement.model.mFunction;
import vn.webapp.modules.usermanagement.model.mStaff;
import vn.webapp.modules.usermanagement.model.mUser;
import vn.webapp.modules.usermanagement.service.mFacultyService;
import vn.webapp.modules.usermanagement.service.mFuncsPermissionService;
import vn.webapp.modules.usermanagement.service.mStaffService;
import vn.webapp.modules.usermanagement.service.mUserService;

public class BaseWeb {

    @Autowired
    private HttpServletRequest request;
    protected String baseUrl;
    protected String assetsUrl;
    protected static String sUserCode;
    protected static String sUserName;
    protected static String sUserRole;
    protected static String facultyCode;
    public static List<mFunction> mFuncsPermissionList;
    public static List<mFunction> mFuncsChildrenPermissionList;
    public static List<mFunction> mFuncsParentsPermissionList;
    
    public int iMANAGEUSERS = 0;
    public int iMANAGEPAPERS = 0;
    public int iMANAGETOPICS = 0;
    public int iMANAGEPATENTS = 0;
    public int iMANAGESUMMARY = 0;
    public int iMANAGEPRODUCTS = 0;
	public int iMANAGEPROJECTCALLS = 0;
	public int iPROJECTSIGNUP = 0;
	public int iADDJURYSUBMITTEDPROJECTS = 0;
	public int iASSIGNJURYSUBMITTEDPROJECTS = 0;
	public int iMODIFYSUBMITTEDPROJECTS = 0;
	public int iREVIEWSUBMITTEDPROJECTS = 0;
                            
    @Autowired
    private mFuncsPermissionService funcsPermissionService;
    
    @Autowired
    private mStaffService staffService;
    
    public BaseWeb() {
		// TODO Auto-generated constructor stub
	}
    
    /**
     * Set permission
     * @param session
     */
    public void setPermission(HttpSession session)
    {
    	// set UserCode
    	BaseWeb.sUserCode = session.getAttribute("currentUserCode").toString();
    	// set User Role
    	BaseWeb.sUserRole = session.getAttribute("currentUserRole").toString();
    	// set User name
    	BaseWeb.sUserName = session.getAttribute("currentUserName").toString();
    	// set User permissions
    	BaseWeb.mFuncsPermissionList = funcsPermissionService.loadFunctionsList();
    	// set User permissions
    	BaseWeb.mFuncsChildrenPermissionList = funcsPermissionService.loadFunctionsChildHierachyList();
    	// set User permissions
    	BaseWeb.mFuncsParentsPermissionList = funcsPermissionService.loadFunctionsParentHierachyList();
    	
    	this.iMANAGEUSERS = (session.getAttribute("iMANAGEUSERS") != null) ? (int) session.getAttribute("iMANAGEUSERS") : 0;
    	this.iMANAGEPAPERS = (session.getAttribute("iMANAGEPAPERS") != null) ? (int) session.getAttribute("iMANAGEPAPERS") : 0;
    	this.iMANAGETOPICS = (session.getAttribute("iMANAGETOPICS") != null) ? (int) session.getAttribute("iMANAGETOPICS") : 0;
    	this.iMANAGEPATENTS = (session.getAttribute("iMANAGEPATENTS") != null) ? (int) session.getAttribute("iMANAGEPATENTS") : 0;
    	this.iMANAGESUMMARY = (session.getAttribute("iMANAGESUMMARY") != null) ? (int) session.getAttribute("iMANAGESUMMARY") : 0;
    	this.iMANAGEPRODUCTS = (session.getAttribute("iMANAGEPRODUCTS") != null) ? (int) session.getAttribute("iMANAGEPRODUCTS") : 0;
    	this.iMANAGEPROJECTCALLS = (session.getAttribute("iMANAGEPROJECTCALLS") != null) ? (int) session.getAttribute("iMANAGEPROJECTCALLS") : 0;
	   	this.iPROJECTSIGNUP = (session.getAttribute("iPROJECTSIGNUP") != null) ? (int) session.getAttribute("iPROJECTSIGNUP") : 0;
	   	this.iADDJURYSUBMITTEDPROJECTS = (session.getAttribute("iADDJURYSUBMITTEDPROJECTS") != null) ? (int) session.getAttribute("iADDJURYSUBMITTEDPROJECTS") : 0;
	   	this.iASSIGNJURYSUBMITTEDPROJECTS = (session.getAttribute("iASSIGNJURYSUBMITTEDPROJECTS") != null) ? (int) session.getAttribute("iASSIGNJURYSUBMITTEDPROJECTS") : 0;
	   	this.iMODIFYSUBMITTEDPROJECTS = (session.getAttribute("iMODIFYSUBMITTEDPROJECTS") != null) ? (int) session.getAttribute("iMODIFYSUBMITTEDPROJECTS") : 0;
	   	this.iREVIEWSUBMITTEDPROJECTS = (session.getAttribute("iREVIEWSUBMITTEDPROJECTS") != null) ? (int) session.getAttribute("iREVIEWSUBMITTEDPROJECTS") : 0;
    }
    
    
    
    /**
     * 
     * @param map
     * @param session
     */
    @ModelAttribute
    public void addGlobalAttr(ModelMap map, HttpSession session) {
    	
    	
    	// set permission
    	this.setPermission(session);
    	
    	// set base url
        switch (request.getRequestURI()) {
            case "/":
                baseUrl = request.getRequestURL().substring(0, request.getRequestURL().length() - 1).toString();
                break;
            case "":
                baseUrl = request.getRequestURL().toString();
                break;
            default:
                baseUrl = request.getRequestURL().toString().replace(request.getRequestURI(), request.getContextPath());
                break;
        }
        // Get current username 
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!"".equals(username)) map.put("username", username);
        
        mStaff staff = staffService.loadStaffByUserCode(username);
        String facultyCode = staff.getStaff_Faculty_Code();
        
        map.put("facultyCode", facultyCode);
        System.out.println("BaseWeb::addGlobalAttr, facultyCode = " + facultyCode);
        
        assetsUrl = baseUrl + "/assets";
        map.put("baseUrl", baseUrl);
        map.put("assetsUrl", assetsUrl);
        map.put("iMANAGEUSERS", this.iMANAGEUSERS);
        map.put("iMANAGEPAPERS", this.iMANAGEPAPERS);
        map.put("iMANAGETOPICS", this.iMANAGETOPICS);
        map.put("iMANAGEPATENTS", this.iMANAGEPATENTS);
        map.put("iMANAGESUMMARY", this.iMANAGESUMMARY);
        map.put("iMANAGEPRODUCTS", this.iMANAGEPRODUCTS);
        map.put("iMANAGEPROJECTCALLS", this.iMANAGEPROJECTCALLS);
        map.put("iPROJECTSIGNUP", this.iPROJECTSIGNUP);
        map.put("iADDJURYSUBMITTEDPROJECTS", this.iADDJURYSUBMITTEDPROJECTS);
        map.put("iASSIGNJURYSUBMITTEDPROJECTS", this.iASSIGNJURYSUBMITTEDPROJECTS);
        map.put("iMODIFYSUBMITTEDPROJECTS", this.iMODIFYSUBMITTEDPROJECTS);
        map.put("iREVIEWSUBMITTEDPROJECTS", this.iREVIEWSUBMITTEDPROJECTS);
        
        
       List<mEditFunctions> funcsEditParentsList = new ArrayList<>();
 	   List<mEditFunctions> funcsEditChildrenList = new ArrayList<>();
 	   List<mFunction> funcsChildrenPermissionList = BaseWeb.mFuncsChildrenPermissionList;
 	   List<mFunction> funcsParentsPermissionList = BaseWeb.mFuncsParentsPermissionList;
 	   List<mFuncsPermission> mCurrentUserFuncsPermissionList = funcsPermissionService.loadFunctionsPermissionByUserList(BaseWeb.sUserCode);
 	  
 	   for (mFunction mFunction : funcsChildrenPermissionList) {
 		   mEditFunctions temp = new mEditFunctions();
 		   temp.setFUNC_ID(mFunction.getFUNC_ID());
 		   temp.setFUNC_CODE(mFunction.getFUNC_CODE());
 		   temp.setFUNC_NAME(mFunction.getFUNC_NAME());
 		   temp.setFUNC_PARENTID(mFunction.getFUNC_PARENTID());
 		   temp.setFUNC_URL(mFunction.getFUNC_URL());
 		   temp.setSELECTED(0);
 		   temp.setFUNC_SELECTED_CLASS(mFunction.getFUNC_SELECTED_CLASS());
 		   temp.setFUNC_TITLE_CLASS(mFunction.getFUNC_TITLE_CLASS());
 		   temp.setFUNC_HAS_CHILDREN(mFunction.getFUNC_HAS_CHILDREN());
 		   if(mCurrentUserFuncsPermissionList.size() > 0){
 			   for (mFuncsPermission currentFunction : mCurrentUserFuncsPermissionList) {
 				   if(currentFunction.getUSERFUNC_FUNCCODE().equals(mFunction.getFUNC_CODE()))
 				   {
 					   temp.setSELECTED(1);
 				   }
 			   }
 		   }
 		   funcsEditChildrenList.add(temp);
 	   }
 	   
 	   for (mFunction mFunction : funcsParentsPermissionList) {
 		   mEditFunctions temp = new mEditFunctions();
 		   temp.setFUNC_ID(mFunction.getFUNC_ID());
 		   temp.setFUNC_CODE(mFunction.getFUNC_CODE());
 		   temp.setFUNC_NAME(mFunction.getFUNC_NAME());
 		   temp.setFUNC_PARENTID(mFunction.getFUNC_PARENTID());
 		   temp.setFUNC_URL(mFunction.getFUNC_URL());
 		   temp.setSELECTED(0);
 		   temp.setFUNC_SELECTED_CLASS(mFunction.getFUNC_SELECTED_CLASS());
		   temp.setFUNC_TITLE_CLASS(mFunction.getFUNC_TITLE_CLASS());
		   temp.setFUNC_HAS_CHILDREN(mFunction.getFUNC_HAS_CHILDREN());
 		   if(mCurrentUserFuncsPermissionList.size() > 0){
 			   for (mFuncsPermission currentFunction : mCurrentUserFuncsPermissionList) {
 				   if(currentFunction.getUSERFUNC_FUNCCODE().equals(mFunction.getFUNC_CODE()))
 				   {
 					   temp.setSELECTED(1);
 				   }
 			   }
 		   }
 		   funcsEditParentsList.add(temp);
 	   }
 	   
 	   map.put("funcsChildrenList", funcsEditChildrenList);
 	   map.put("funcsParentsList", funcsEditParentsList);
    }
}
