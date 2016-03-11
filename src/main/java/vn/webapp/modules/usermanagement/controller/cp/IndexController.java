package vn.webapp.modules.usermanagement.controller.cp;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.webapp.controller.BaseWeb;
import vn.webapp.modules.usermanagement.model.mFuncsPermission;
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
	       List<mFuncsPermission> currentUserFunctionsPermissionList = funcsPermissionService.loadFunctionsPermissionByUserList(sUserCode);
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
	   			}
			}
	   }
	    this.iMANAGEUSERS = (session.getAttribute("iMANAGEUSERS") != null) ? (int) session.getAttribute("iMANAGEUSERS") : 0;
	   	this.iMANAGEPAPERS = (session.getAttribute("iMANAGEPAPERS") != null) ? (int) session.getAttribute("iMANAGEPAPERS") : 0;
	   	this.iMANAGETOPICS = (session.getAttribute("iMANAGETOPICS") != null) ? (int) session.getAttribute("iMANAGETOPICS") : 0;
	   	this.iMANAGEPATENTS = (session.getAttribute("iMANAGEPATENTS") != null) ? (int) session.getAttribute("iMANAGEPATENTS") : 0;
	   	this.iMANAGESUMMARY = (session.getAttribute("iMANAGESUMMARY") != null) ? (int) session.getAttribute("iMANAGESUMMARY") : 0;
	   	this.iMANAGEPRODUCTS = (session.getAttribute("iMANAGEPRODUCTS") != null) ? (int) session.getAttribute("iMANAGEPRODUCTS") : 0;
   	
	    map.put("iMANAGEUSERS", this.iMANAGEUSERS);
        map.put("iMANAGEPAPERS", this.iMANAGEPAPERS);
        map.put("iMANAGETOPICS", this.iMANAGETOPICS);
        map.put("iMANAGEPATENTS", this.iMANAGEPATENTS);
        map.put("iMANAGESUMMARY", this.iMANAGESUMMARY);
        map.put("iMANAGEPRODUCTS", this.iMANAGEPRODUCTS);
	   
        return "cp.home";
   }
   
   
}
