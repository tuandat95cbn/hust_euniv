/**
 * @author : HaTN 
 * @address : HUST K51
 * @modified : October 13th, 2015
 */
package vn.webapp.modules.usermanagement.controller.cp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.webapp.controller.BaseWeb;
import vn.webapp.modules.usermanagement.model.mDepartment;
import vn.webapp.modules.usermanagement.model.mFaculty;
import vn.webapp.modules.usermanagement.model.mFuncsPermission;
import vn.webapp.modules.usermanagement.model.mFunction;
import vn.webapp.modules.usermanagement.model.mStaff;
import vn.webapp.modules.usermanagement.model.mUser;
import vn.webapp.modules.usermanagement.model.mUsers;
import vn.webapp.modules.usermanagement.service.mDepartmentService;
import vn.webapp.modules.usermanagement.service.mFacultyService;
import vn.webapp.modules.usermanagement.service.mFuncsPermissionService;
import vn.webapp.modules.usermanagement.service.mStaffService;
import vn.webapp.modules.usermanagement.service.mUserService;
import vn.webapp.modules.usermanagement.validation.mUserValidation;
import vn.webapp.validation.SettingsValidation;

@Controller("cpmUser")
@RequestMapping(value = {"/cp"})
public class mUserController extends BaseWeb {

	@Autowired
    private mStaffService staffService;
	
	@Autowired
    private mUserService userService;
	
	@Autowired
    private mFacultyService facultyService;
	
	@Autowired
    private mDepartmentService departmentService;
	
	@Autowired
    private mFuncsPermissionService funcsPermissionService;
	
	/**
	 * Set status flag
	 */
	static final String status = "active";
	/**
	 * Set Super admin acc
	 */
    static final String SUPER_ADMIN = "SUPER_ADMIN";
    
    /**
     * Set staff category code
     */
    static final String STAFF_CAT_CODE = "LEC";
    
	/**
    *
    * @param model
    * @return
    */
   @RequestMapping(value = "/users", method = RequestMethod.GET)
   public String listUsers(ModelMap model, HttpSession session) {
   	//DataPage<Users> usersData = userService.list();
   	//List<Users> usersList = usersData.getData();
   	List<mStaff> staffsList = new ArrayList<mStaff>();
   	String userRole = session.getAttribute("currentUserRole").toString();
   	if(userRole.equals(mUserController.SUPER_ADMIN)){
   		staffsList = staffService.listStaffs();
   	}else{
    	String currentUserFaculty = session.getAttribute("currentUserFaculty").toString();
    	staffsList = staffService.listStaffsByFalcuty(currentUserFaculty);
   	}
   	
   	//model.put("listUsers", usersList);
   	model.put("staffsList", staffsList);
   	model.put("users", status);
       return "cp.users";
   }
   
   /**
   *
   * @param model
   * @return
   */
  @RequestMapping(value = "/add-an-user", method = RequestMethod.GET)
  public String addAnUser(ModelMap model, HttpSession session) {
	   String currentUserFacultyCode = session.getAttribute("currentUserFaculty").toString();
	   // Add the saved validationForm to the model
	   List<mFaculty> facultyList = this.getFacultyByUserRole(BaseWeb.sUserRole, currentUserFacultyCode);
	   List<mDepartment> departmentList = this.getDepartmentByUserRole(BaseWeb.sUserRole, currentUserFacultyCode);
	   List<mFunction> funcsPermissionList = BaseWeb.mFuncsPermissionList;
	   
	   model.put("listShowedPermission", funcsPermissionList);
	   model.put("permissionList", BaseWeb.mFuncsPermissionList);
	   model.put("facultyList", facultyList);
	   model.put("departmentList", departmentList);
	   model.put("currentUserName", BaseWeb.sUserName);
	   model.put("currentUserRole", BaseWeb.sUserRole);
	   model.put("userFormAdd", new mUserValidation());
  	   model.put("users", status);
       return "cp.addAnUser";
  }
  
  @RequestMapping(value="save-an-user", method=RequestMethod.POST)
  public String saveAnUser(HttpServletRequest request, @Valid @ModelAttribute("userFormAdd") mUserValidation userValid, BindingResult result,  Map model, HttpSession session) {
	   String userRole = session.getAttribute("currentUserRole").toString();
	   String currentUserFacultyCode = session.getAttribute("currentUserFaculty").toString();
	   // Add the saved validationForm to the model
	   List<mFaculty> facultyList = this.getFacultyByUserRole(userRole, currentUserFacultyCode);
	   List<mDepartment> departmentList = this.getDepartmentByUserRole(userRole, currentUserFacultyCode);
	   List<mFunction> funcsPermissionList = BaseWeb.mFuncsPermissionList;
	   String[] aFunctionsPermitted = request.getParameterValues("functions");
	   
	   model.put("listShowedPermission", funcsPermissionList);
	   model.put("users", status);
	   model.put("facultyList", facultyList);
	   model.put("departmentList", departmentList);
      if(result.hasErrors()) {
          return "cp.addAnUser";
      }else
      {
   	   String currentUserFaculty = session.getAttribute("currentUserFaculty").toString();
   	   String chosenFaculty = userValid.getStaffFaculty();
   	   String staffFaculty = (chosenFaculty != "") ? chosenFaculty : currentUserFaculty;
   	   String staffDepartment = userValid.getStaffDepartment();
   	   String username = userValid.getUsername();
   	   String password = DigestUtils.md5Hex(userValid.getPassword());
   	   String email = userValid.getEmail();
   	   String role = userValid.getRole();
   	   String salt = "salt-sequence";
   	   int activated = userValid.getActivated();
   	   
   	   mUser o_Username = userService.loadUserByUsername(username);
   	   if(o_Username != null)
          {
              model.put("err", "The username is exists.");
              return "cp.addAnUser";
          }
   	   int i_InsertUser = userService.saveAUser(username, password, salt, email, role, activated, aFunctionsPermitted);
   	   // Save a staff followed by an user
   	   String staffName = username;
   	   String staffEmail = email;
   	   String userCode = username;
   	   
   	   // TODO Asumption
   	   String staffCatCode = this.STAFF_CAT_CODE;
   	   String staffPhone = "0";
   	   staffService.saveAStaff(staffName, staffEmail, staffPhone, staffDepartment, userCode, staffCatCode, staffFaculty);
   	   
          //model.put("status", "Successfully saved user: " + username);
          return "redirect:" + this.baseUrl + "/cp/users.html";
      }
  }
  
  @RequestMapping("/userdetail/{id}")
  public String editAnUser(ModelMap model, @PathVariable("id") int id, HttpSession session) {
	   HashMap<String, String> user = userService.viewDetail(id);
	   String userRole = session.getAttribute("currentUserRole").toString();
	   String currentUserFacultyCode = session.getAttribute("currentUserFaculty").toString();
	   // Add the saved validationForm to the model
	   List<mFaculty> facultyList = this.getFacultyByUserRole(userRole, currentUserFacultyCode);
	   List<mDepartment> departmentList = this.getDepartmentByUserRole(userRole, currentUserFacultyCode);
	   List<mFuncsPermission> mCurrentUserFuncsPermissionList = funcsPermissionService.loadFunctionsPermissionByUserList(user.get("userCode"));
	   HashMap<List<String>, Integer> listShowedPermission = this.getUserPermissionList(user.get("userCode"));
	   
	   model.put("listShowedPermission", listShowedPermission);
	   model.put("facultyList", facultyList);
	   model.put("departmentList", departmentList);
       model.put("userFormEdit", new mUserValidation());
       model.put("dataUser", user);
       model.put("users", status);
	   model.put("currentUserName", session.getAttribute("currentUserName"));
	   model.put("currentUserRole", session.getAttribute("currentUserRole"));
      return "cp.editAnUser";
  }
  
  /**
   * 
   * @param sUserCode
   * @return
   */
  public HashMap<List<String>, Integer> getUserPermissionList(String sUserCode)
  {
	  	HashMap<List<String>, Integer> listShowedPermission = new HashMap<>();
	  	List<mFunction> FuncsPermissionList = BaseWeb.mFuncsPermissionList;
	  	List<mFuncsPermission> CurrentUserFuncsPermissionList = funcsPermissionService.loadFunctionsPermissionByUserList(sUserCode);
	  	String sCurrentUserFunctionCode = "";
	  	String sFunctionCode = "";
	  	for (mFunction mFuncsPermission : FuncsPermissionList) {
	  		if(CurrentUserFuncsPermissionList.size() > 0)
	  		{
	  			List<String> PermissionInfo= new ArrayList<String>();
	  			PermissionInfo.add(mFuncsPermission.getFUNC_CODE());
	  			PermissionInfo.add(mFuncsPermission.getFUNC_NAME());
				listShowedPermission.put(PermissionInfo, 0);
				
	  			sFunctionCode = mFuncsPermission.getFUNC_CODE();
		  		for (mFuncsPermission mCurrentUserFuncsPermission : CurrentUserFuncsPermissionList) {
					sCurrentUserFunctionCode = mCurrentUserFuncsPermission.getUSERFUNC_FUNCCODE();
					if(sCurrentUserFunctionCode != "" && sCurrentUserFunctionCode.equals(sFunctionCode))
					{
						listShowedPermission.remove(PermissionInfo, 0);
						PermissionInfo.add(mFuncsPermission.getFUNC_CODE());
						PermissionInfo.add(mFuncsPermission.getFUNC_NAME());
						listShowedPermission.put(PermissionInfo, 1);
					}
				}
	  		}else{
	  			List<String> PermissionInfo= new ArrayList<String>();
	  			PermissionInfo.add(mFuncsPermission.getFUNC_CODE());
				PermissionInfo.add(mFuncsPermission.getFUNC_NAME());
				listShowedPermission.put(PermissionInfo, 0);
	  		}
		}

		return listShowedPermission;
  }
  
  /**
  *
  * @param validationForm
  * @param result
  * @param model
  * @return
  */
 @RequestMapping(value = "/edit-user-detail", method = RequestMethod.POST)
 public String processValidationForm(HttpServletRequest request, @Valid @ModelAttribute("userFormEdit") mUserValidation userFormEdit, BindingResult result, Map model, HttpSession session) {
	   String userRole = session.getAttribute("currentUserRole").toString();
	   String currentUserFacultyCode = session.getAttribute("currentUserFaculty").toString();
	   HashMap<List<String>, Integer> listShowedPermission = this.getUserPermissionList(userFormEdit.getUserCode());
	   
	   // Add the saved validationForm to the model
	   List<mFaculty> facultyList = this.getFacultyByUserRole(userRole, currentUserFacultyCode);
	   List<mDepartment> departmentList = this.getDepartmentByUserRole(userRole, currentUserFacultyCode);
	   String[] aFunctionsPermitted = request.getParameterValues("functions");
	   
	   model.put("listShowedPermission", listShowedPermission);
	   model.put("facultyList", facultyList);
	   model.put("departmentList", departmentList);
	   model.put("users", status);
      if (result.hasErrors()) {
   	   	return "cp.editAnUser";
      }else
      {
   	   String username = userFormEdit.getUsername();
   	   String password = (userFormEdit.getEpassword() != "") ? DigestUtils.md5Hex(userFormEdit.getEpassword()) : "";
   	   String role = userFormEdit.getRole();
   	   String email = userFormEdit.getEmail();
   	   int activated = userFormEdit.getActivated();
   	   int userId = userFormEdit.getUserId();
   	   int userRoleId = userFormEdit.getUserRoleId();
   	   int staffId = userFormEdit.getStaffId();
   	   String userDepartment = userFormEdit.getStaffDepartment();
         
   	   mUsers o_Username = userService.loadUserByUsernameAndId(username, userId);
   	   if(o_Username != null)
   	   {
   		   model.put("err", "The username is exists.");
   		   return "cp.editAnUser";
   	   }
   	   userService.editAnUser(userId, username, password, email, role, activated, userRoleId, staffId, userDepartment, aFunctionsPermitted);
   	   model.put("status", "Successfully edited user: " + username);
   	   return "redirect:" + this.baseUrl + "/cp/users.html";
          //return "cp.editAnUser";
     }
 }
 
 /**
  * 
  * Change password
  * @param model
  * @return String
  */
 @RequestMapping(value = "/changepass", method = RequestMethod.GET)
 public String changePass(ModelMap model, HttpSession session) {
	  
	  model.put("settingForm", new SettingsValidation());
	  model.put("users", status);
	  return "cp.changePass";
 }
 
 /**
  * 
  * Save settings
  * @param model
  * @return String
  */
 @RequestMapping(value = "/save-settings", method = RequestMethod.POST)
 public String saveSettings(@Valid @ModelAttribute("settingForm") SettingsValidation settingForm, BindingResult result, Map model, HttpSession session) {
	  model.put("users", status);
	  String currentUserName = session.getAttribute("currentUserName").toString();
	  String userRole = session.getAttribute("currentUserRole").toString();
	  if (result.hasErrors()) {
	      return "cp.changePass";
     }else
     {
   	  String password = DigestUtils.md5Hex(settingForm.getPassword());
   	  userService.saveSettings(currentUserName, password);
   	  model.put("status", "Successfully change password");
   	  return "cp.changePass";
     }
 }
 
 /**
  * Get faculty List by user role
  * @param userRole
  * @param session
  * @return
  */
 public List<mFaculty> getFacultyByUserRole(String userRole, String currentUserFacultyCode)
 {
	  List<mFaculty> facultyList = new ArrayList<mFaculty>();
	  facultyList = facultyService.loadFacultyList();
	  /*if(userRole.equals(mUserController.SUPER_ADMIN) || currentUserFacultyCode.equals(null)){
		   facultyList = facultyService.loadFacultyList();
	   }else{
		   facultyList = facultyService.loadAFacultyByCode(currentUserFacultyCode);
	   }*/
	  return facultyList;
 }
 
 /**
  * Get faculty List by user role
  * @param userRole
  * @param session
  * @return
  */
 public List<mDepartment> getDepartmentByUserRole(String userRole, String currentUserFacultyCode)
 {
	  List<mDepartment> departmentList = new ArrayList<mDepartment>();
	  if(userRole.equals(mUserController.SUPER_ADMIN) || currentUserFacultyCode.equals(null)){
		   departmentList = departmentService.loadDepartmentList();
	   }else{
		   departmentList = departmentService.loadDepartmentListByFaculty(currentUserFacultyCode);
	   }
	  return departmentList;
 }
}
