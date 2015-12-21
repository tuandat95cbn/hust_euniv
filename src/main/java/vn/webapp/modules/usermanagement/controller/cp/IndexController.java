package vn.webapp.modules.usermanagement.controller.cp;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.webapp.modules.usermanagement.model.mStaff;
import vn.webapp.modules.usermanagement.model.mUser;
import vn.webapp.modules.usermanagement.service.mStaffService;
import vn.webapp.modules.usermanagement.service.mUserService;

@Controller("cpIndex")
@RequestMapping(value = {"/cp"})
public class IndexController {
	
	@Autowired
    private mUserService userService;
    
    @Autowired
    private mStaffService staffService;

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
		   mStaff staff = staffService.loadStaffByUserCode(user.getUser_Code());
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
		   session.setAttribute("currentUserCode", user.getUser_Code());
	   }
       return "cp.home";
   }
}
