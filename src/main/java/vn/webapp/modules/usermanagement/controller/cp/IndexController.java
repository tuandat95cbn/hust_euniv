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
   public String home(ModelMap model, HttpSession session) {
	   String username = SecurityContextHolder.getContext().getAuthentication().getName();
	   session.setAttribute("currentUserName", username);
	   mUser user = userService.loadUserByUsername(username);
	   if(user != null){
		   String sUserCode = user.getUser_Code();
		   mStaff staff = staffService.loadStaffByUserCode(sUserCode);
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
	   				this.iMANAGEUSERS = 1;
	   				session.setAttribute("iMANAGEUSERS", 1);
	   			}else if("MANAGE-PAPERS".equals(sFuncsCode)){
	   				this.iMANAGEPAPERS = 1;
	   				session.setAttribute("iMANAGEPAPERS", 1);
	   			}else if("MANAGE-TOPICS".equals(sFuncsCode)){
	   				this.iMANAGETOPICS = 1;
	   				session.setAttribute("iMANAGETOPICS", 1);
	   			}else if("MANAGE-PATENTS".equals(sFuncsCode)){
	   				this.iMANAGEPATENTS = 1;
	   				session.setAttribute("iMANAGEPATENTS", 1);
	   			}else if("MANAGE-SUMMARY".equals(sFuncsCode)){
	   				this.iMANAGESUMMARY = 1;
	   				session.setAttribute("iMANAGESUMMARY", 1);
	   			}else if("MANAGE-PRODUCTS".equals(sFuncsCode)){
	   				this.iMANAGEPRODUCTS = 1;
	   				session.setAttribute("iMANAGEPRODUCTS", 1);
	   			}
			}
	   }
	   model.put("iMANAGEUSERS", this.iMANAGEUSERS);
	   model.put("iMANAGEPAPERS", this.iMANAGEPAPERS);
	   model.put("iMANAGETOPICS", this.iMANAGETOPICS);
	   model.put("iMANAGEPATENTS", this.iMANAGEPATENTS);
	   model.put("iMANAGESUMMARY", this.iMANAGESUMMARY);
	   model.put("iMANAGEPRODUCTS", this.iMANAGEPRODUCTS);
	   
       return "cp.home";
   }
   
   
}
