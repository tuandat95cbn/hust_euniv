package vn.webapp.modules.usermanagement.controller.cp;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.webapp.controller.BaseWeb;
import vn.webapp.modules.usermanagement.model.mEditFunctions;
import vn.webapp.modules.usermanagement.model.mFuncsPermission;
import vn.webapp.modules.usermanagement.model.mFunction;
import vn.webapp.modules.usermanagement.model.mStaff;
import vn.webapp.modules.usermanagement.model.mUser;
import vn.webapp.modules.usermanagement.service.mFuncsPermissionService;
import vn.webapp.modules.usermanagement.service.mStaffService;
import vn.webapp.modules.usermanagement.service.mUserService;

@Controller("cpIndex")
@RequestMapping(value = {"/cp"})
public class IndexController {
	
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
    private mUserService userService;
    
    @Autowired
    private mStaffService staffService;
    
    @Autowired
    private mFuncsPermissionService funcsPermissionService;

    /**
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(ModelMap model, HttpSession session) {
        return "signin";
    }

    
    /**
    *
    * @param model
    * @return
    */
   @RequestMapping(value = "/home", method = RequestMethod.GET)
   public String home(ModelMap map, HttpSession session) {
	   String username = SecurityContextHolder.getContext().getAuthentication().getName();
	   session.setAttribute("currentUserName", username);
	   mUser user = userService.loadUserByUsername(username);
	   if(user != null){
		   String sUserCode = user.getUser_Code();
		   mStaff staff = staffService.loadStaffByUserCode(sUserCode);
		   //System.out.println(staff.getStaff_Name());
		   String userFaculty = staff.getDepartment().getDepartment_Faculty_Code();
		   String userDepartment = staff.getDepartment().getDepartment_Code();
		   if(!userFaculty.equals(""))
		   {
			   session.setAttribute("currentUserFaculty", userFaculty);
		   }
		   if(!userDepartment.equals(""))
		   {
			   session.setAttribute("currentUserDepartment", userDepartment);
		   }
		   session.setAttribute("currentUserRole", user.getUser_Role());
		   session.setAttribute("currentUserCode", sUserCode);
		   
		   // Set current user permissions
	       /*List<mFuncsPermission> currentUserFunctionsPermissionList = funcsPermissionService.loadFunctionsPermissionByUserList(sUserCode);
	       String sFuncsCode;
   		   for (mFuncsPermission mFuncsPermission : currentUserFunctionsPermissionList) {
   				sFuncsCode = mFuncsPermission.getUSERFUNC_FUNCCODE();
	   			if("MANAGE-USERS".equals(sFuncsCode))
	   			{
	   				session.setAttribute("iMANAGEUSERS", 1);
	   			}else if("MANAGE-PAPERS".equals(sFuncsCode)){
	   				session.setAttribute("iMANAGEPAPERS", 1);
	   			}else if("MANAGE-TOPICS".equals(sFuncsCode)){
	   				session.setAttribute("iMANAGETOPICS", 1);
	   			}else if("MANAGE-PATENTS".equals(sFuncsCode)){
	   				session.setAttribute("iMANAGEPATENTS", 1);
	   			}else if("MANAGE-SUMMARY".equals(sFuncsCode)){
	   				session.setAttribute("iMANAGESUMMARY", 1);
	   			}else if("MANAGE-PRODUCTS".equals(sFuncsCode)){
	   				session.setAttribute("iMANAGEPRODUCTS", 1);
	   			}else if("MANAGE-PROJECTCALLS".equals(sFuncsCode)){
	   				session.setAttribute("iMANAGEPROJECTCALLS", 1);
	   			}else if("PROJECT-SIGNUP".equals(sFuncsCode)){
	   				session.setAttribute("iPROJECTSIGNUP", 1);
	   			}else if("ADD-JURY-SUBMITTED-PROJECTS".equals(sFuncsCode)){
	   				session.setAttribute("iADDJURYSUBMITTEDPROJECTS", 1);
	   			}else if("ASSIGN-JURY-SUBMITTED-PROJECTS".equals(sFuncsCode)){
	   				session.setAttribute("iASSIGNJURYSUBMITTEDPROJECTS", 1);
	   			}else if("REVIEW-SUBMITTED-PROJECTS".equals(sFuncsCode)){
	   				session.setAttribute("iMODIFYSUBMITTEDPROJECTS", 1);
	   			}else if("MODIFY-SUBMITTED-PROJECTS".equals(sFuncsCode)){
	   				session.setAttribute("iREVIEWSUBMITTEDPROJECTS", 1);
	   			}
			}*/
   		   
   		   // New settings for permission functions
   		   List<mEditFunctions> funcsEditParentsList = new ArrayList<>();
	   	   List<mEditFunctions> funcsEditChildrenList = new ArrayList<>();
	   	   List<mFunction> funcsChildrenPermissionList = funcsPermissionService.loadFunctionsChildHierachyList();
	   	   List<mFunction> funcsParentsPermissionList = funcsPermissionService.loadFunctionsParentHierachyList();
	   	   List<mFuncsPermission> mCurrentUserFuncsPermissionList = funcsPermissionService.loadFunctionsPermissionByUserList(sUserCode);
	   	  
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
	    /*this.iMANAGEUSERS = (session.getAttribute("iMANAGEUSERS") != null) ? (int) session.getAttribute("iMANAGEUSERS") : 0;
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
        map.put("iREVIEWSUBMITTEDPROJECTS", this.iREVIEWSUBMITTEDPROJECTS);*/
        
        return "cp.home";
   }
   
   
}
