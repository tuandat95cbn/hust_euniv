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
    }
}
