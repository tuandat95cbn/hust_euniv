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

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;

import vn.webapp.modules.usermanagement.model.mFuncsPermission;
import vn.webapp.modules.usermanagement.model.mFunction;
import vn.webapp.modules.usermanagement.service.mFuncsPermissionService;

public class BaseWeb {

    @Autowired
    private HttpServletRequest request;
    protected String baseUrl;
    protected String assetsUrl;
    protected static String sUserCode;
    protected static String sUserName;
    protected static String sUserRole;
    public static List<mFunction> mFuncsPermissionList;
    
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
    }
}
