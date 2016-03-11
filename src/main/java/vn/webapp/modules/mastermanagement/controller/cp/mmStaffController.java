/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.webapp.modules.mastermanagement.controller.cp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Customize
 */
import vn.webapp.controller.BaseWeb;
import vn.webapp.modules.mastermanagement.model.mmDepartment;
import vn.webapp.modules.mastermanagement.model.mmSpecializationKeyword;
import vn.webapp.modules.mastermanagement.model.mmStaff;
import vn.webapp.modules.mastermanagement.model.mmStaffCategory;
import vn.webapp.modules.mastermanagement.model.mmUniversity;
import vn.webapp.modules.mastermanagement.model.mmUsers;
import vn.webapp.modules.mastermanagement.service.mmDepartmentService;
import vn.webapp.modules.mastermanagement.service.mmScientificFieldService;
import vn.webapp.modules.mastermanagement.service.mmSpecializationKeywordService;
import vn.webapp.modules.mastermanagement.service.mmStaffCategoryService;
import vn.webapp.modules.mastermanagement.service.mmStaffService;
import vn.webapp.modules.mastermanagement.service.mmUniversityService;
import vn.webapp.modules.mastermanagement.service.mmUserService;
import vn.webapp.modules.mastermanagement.validation.StaffValidation;

@Controller("cpStaff")
@RequestMapping(value = {"/mm"})
public class mmStaffController extends BaseWeb {

    @Autowired
    private mmStaffService mmstaffService;
    
    @Autowired
    private mmUniversityService mmuniversityService;
    
    @Autowired
    private mmDepartmentService mmdepartmentService;
    
    @Autowired
    private mmUserService mmuserService;

    @Autowired
    private mmScientificFieldService mmscientificFieldService;

    @Autowired
    private mmSpecializationKeywordService mmspecializationKeywordService;
    
    @Autowired
    private mmStaffCategoryService mmstaffCategoryService;
    
    /**
    *
    * @param model
    * @return
    */
   @RequestMapping(value = "/profile", method = RequestMethod.GET)
   public String staffInfo(ModelMap model, HttpSession session) {
	   String currentUserName = session.getAttribute("currentUserName").toString();
	   String userRole = session.getAttribute("currentUserRole").toString();
	   String userCode = session.getAttribute("currentUserCode").toString();
	   mmStaff staff = mmstaffService.loadStaffByUserCode(userCode);
	   
	   List<mmDepartment> departmentList = mmdepartmentService.loadDepartmentList();
	   model.put("staffFormEdit", new StaffValidation());
	   if(staff != null){
		   model.put("staffEmail", staff.getStaff_Email());
		   model.put("staffName", staff.getStaff_Name());
		   model.put("staffPhone", staff.getStaff_Phone());
		   model.put("staffCategory", staff.getStaffCategory().getStaff_Category_Name());
		   //model.put("staffDepartementName", staff.getDepartment().getDepartment_Name());
		   model.put("staffDepartementCode", staff.getDepartment().getDepartment_Code());
	   }
	   model.put("departmentList", departmentList);
       return "mm.profile";
   }
   
   /**
    * Show list all professor
    * @param model
    * @return
    */
   @RequestMapping(value = "/professors", method = RequestMethod.GET)
   public String paperList(ModelMap model, HttpSession session) {

	   System.out.println("OK");
	   List<mmStaff> professorList = mmstaffService.listStaffs();
	   
	   model.put("professorList", professorList);
	   model.put("departmentList",mmdepartmentService.loadDepartmentList());
	   return "mm.professors";
   }
   
   @RequestMapping("/professordetail/{id}")
   public String editAProfessor(ModelMap model, @PathVariable("id") int professorId, HttpSession session) {
	   
	   /*
	    * Put data back to view
	    */
	   String userRole = session.getAttribute("currentUserRole").toString();
	   mmStaff staff = mmstaffService.loadStaffById(userRole, professorId);
	   //System.out.println(userRole);
	   model.put("staffFormEdit", new StaffValidation());
	   	
	   if(staff != null)
	   {
		   List<mmDepartment> departmentList = mmdepartmentService.loadDepartmentList();
		   
		   model.put("staffEmail", staff.getStaff_Email());
		   model.put("staffName", staff.getStaff_Name());
		   model.put("staffPhone", staff.getStaff_Phone());
		   model.put("staffCategory", staff.getStaffCategory().getStaff_Category_Name());
		   model.put("departmentList", departmentList);
		   model.put("specializationKeywordList", mmspecializationKeywordService.loadSpecializationKeywordList());
		   model.put("staffId", professorId);
		   model.put("universityList", mmuniversityService.loadUniversityList());
		   model.put("staffDepartementCode", staff.getDepartment().getDepartment_Code());
		   
		   
		   model.put("specializationKeywords", mmspecializationKeywordService.loadStaffSpecializationKeywordList(staff.getStaff_Code()));
		   		   
	       return "mm.editAProfessor";
	   }
	   return "cp.notFound404";
   }
   
   @RequestMapping(value = "/add-a-professor", method = RequestMethod.GET)
   public String addProfessor(ModelMap model, HttpSession session) {
	  
	   /*
	    * Get current user name and role
	    */
	   String currentUserName = session.getAttribute("currentUserName").toString();
	   String userRole = session.getAttribute("currentUserRole").toString();
	   
	   /*
	    * Get paper's category
	    */
	   
	   List<mmDepartment> departmentList = mmdepartmentService.loadDepartmentList();
	   List<mmSpecializationKeyword> specializationKeywordList = mmspecializationKeywordService.loadSpecializationKeywordList();
	   /*
	    * Put data back to view
	    */
	   model.put("departmentList", departmentList);
	   model.put("staffFormAdd", new StaffValidation());
	   model.put("universityList", mmuniversityService.loadUniversityList());
	   model.put("specializationKeywordList", specializationKeywordList);
	   return "mm.addAProfessor";
   }
   
   /**
   *
   * @param model
   * @return
   */
  @RequestMapping(value = "/save-a-professor", method = RequestMethod.POST)
  public String saveAProfessor(@Valid @ModelAttribute("staffFormAdd") StaffValidation staffFormAdd, BindingResult result,  Map model, HttpSession session) {
	 List<mmDepartment> departmentList = mmdepartmentService.loadDepartmentList();
	 List<mmSpecializationKeyword> specializationKeywordList = mmspecializationKeywordService.loadSpecializationKeywordList();
	   
	 String staffEmail = staffFormAdd.getStaffEmail();
	 String staffName = staffFormAdd.getStaffName();
	 String staffPhone = staffFormAdd.getStaffPhone();
	 String staffDepartmentCode = staffFormAdd.getStaffDepartment();
	 String staffUniversityCode = staffFormAdd.getStaffUniversity();
	 
	 ArrayList<String> staffKeywords = staffFormAdd.getStaffKeywords();
	 HashSet<mmSpecializationKeyword> specializationKeywords = new HashSet<mmSpecializationKeyword>();
	 
	 if(staffKeywords != null){
		 for(String code:staffKeywords){
			 mmSpecializationKeyword speKW = mmspecializationKeywordService.getSpecializationKeywordByCode(code);
			 specializationKeywords.add(speKW);
		 }
	 }
	 
	 
	 model.put("staffEmail", staffEmail);
	 model.put("staffName", staffName);
	 model.put("staffPhone", staffPhone);
	 model.put("departmentList", departmentList);
	 model.put("specializationKeywordList", specializationKeywordList);
	 model.put("specializationKeywords", specializationKeywords);
	 
		 
	 if(result.hasErrors()) {		
		 return "mm.addAProfessor";
     }else
     {
    	 	 String staffCatCode = "LEC";
    	 	 String userCode = session.getAttribute("currentUserCode").toString();
    	 	 String userRole = session.getAttribute("currentUserRole").toString();
    	 	 
    	 	 mmStaffCategory staffCategory = mmstaffCategoryService.getByCode(staffCatCode);
    	 	 System.out.println(staffCategory.getStaff_Category_AsciiName());
    	 	 mmDepartment staffDepartment;
    	 	 if(staffUniversityCode.equals("HUST")){
    	 		 staffDepartment = mmdepartmentService.loadDepartmentByCode(staffDepartmentCode);
    	 	 }
    	 	 else{
    	 		 staffDepartment = mmdepartmentService.loadDepartmentByCode("DIFF");
    	 	 }
    	 		 
    	 	 //mmUniversity staffUniversity = mmuniversityService.loadAUniversityByCodes(staffUniversityCode);
    	 	     	 	 
    	 	 int temp;
    	 	 for(temp = 0; temp < staffEmail.length(); temp++){
    	 		 if(staffEmail.charAt(temp) == '@')
    	 			 break;
    	 	 }
    	 	 System.out.print(temp);
    	 	 //String staffCode = staffEmail.substring(0, temp);
    	 	 String staffCode = staffEmail;
    	 	 
    	 	 mmUsers user = new mmUsers();
    	 	 user.setUsername(staffCode);
    		 
    	 	 mmstaffService.saveAStaff(staffCode, staffName, staffEmail, staffPhone, staffDepartment, user, userRole, staffCategory, specializationKeywords);
	    	 String status = "Bạn đã lưu thành công thông tin của giảng viên";
	    	 model.put("status", status);
    	 
    	 return "mm.addAProfessor";
     }
  }
   
   
   /**
   *
   * @param model
   * @return
   */
  @RequestMapping(value = "/edit-staff", method = RequestMethod.POST)
  public String editStaffInfo(@Valid @ModelAttribute("staffFormEdit") StaffValidation staffFormEdit, BindingResult result,  Map model, HttpSession session) {
	 List<mmDepartment> departmentList = mmdepartmentService.loadDepartmentList();
	 int staffId = staffFormEdit.getStaffId();
	 String staffEmail = staffFormEdit.getStaffEmail();
	 String staffName = staffFormEdit.getStaffName();
	 String staffPhone = staffFormEdit.getStaffPhone();
	 String staffUniversityCode = staffFormEdit.getStaffUniversity();
	 String staffDepartmentCode = staffFormEdit.getStaffDepartment();
	 ArrayList<String> staffKeywords = staffFormEdit.getStaffKeywords();
	 HashSet<mmSpecializationKeyword> specializationKeywords = new HashSet<mmSpecializationKeyword>();
	
	 if(staffKeywords != null){
		 for(String code:staffKeywords){
			 mmSpecializationKeyword speKW = mmspecializationKeywordService.getSpecializationKeywordByCode(code);
			 specializationKeywords.add(speKW);
		 }
	 }
	 
	 
	 System.out.println("staff Name : " + staffName);
	 model.put("staffId", staffId);
	 model.put("staffEmail", staffEmail);
	 model.put("staffName", staffName);
	 model.put("staffPhone", staffPhone);
	 model.put("departmentList", departmentList);
	 model.put("specializationKeywordList", mmspecializationKeywordService.loadSpecializationKeywordList());
	 model.put("specializationKeywords", specializationKeywords);
	 
	 if(result.hasErrors()) {		
		 System.out.print("Failed");
		 return "mm.editAProfessor";
     }else
     {
    	 String staffCatCode = "LEC";
	 	 String userCode = session.getAttribute("currentUserCode").toString();
	 	 String userRole = session.getAttribute("currentUserRole").toString();
	 	 
	 	 mmStaffCategory staffCategory = mmstaffCategoryService.getByCode(staffCatCode);
	 	 //mmDepartment staffDepartment = mmdepartmentService.loadDepartmentByCode(staffDepartmentCode);
	 	 //mmUniversity staffUniversity = mmuniversityService.loadAUniversityByCodes(staffUniversityCode);
	 	 mmDepartment staffDepartment;
	 	 if(staffUniversityCode.equals("HUST")){
	 		 staffDepartment = mmdepartmentService.loadDepartmentByCode(staffDepartmentCode);
	 	 }
	 	 else{
	 		 staffDepartment = mmdepartmentService.loadDepartmentByCode("DIFF");
	 	 }
	 	 
	 	 int temp;
	 	 for(temp = 0; temp < staffEmail.length(); temp++){
	 		 if(staffEmail.charAt(temp) == '@')
	 			 break;
	 	 }
	 	 System.out.print(temp);
	 	 //String staffCode = staffEmail.substring(0, temp);
	 	 String staffCode = staffEmail;
	 	 
	 	 mmUsers user = new mmUsers();
	 	 user.setUsername(staffCode);
		 
	 	 
	 	 mmstaffService.editAStaff(staffId, staffCode, staffName, staffEmail, staffPhone, staffDepartment, user, userRole, staffCategory, specializationKeywords);
	    	
	    	 
	     model.put("staffDepartementCode", staffDepartmentCode);
	     model.put("staffUniversityCode", staffUniversityCode);
	     String status = "Bạn đã lưu thành công thông tin của giảng viên";
	     model.put("status", status);
    	
    	 return "mm.editAProfessor";
     }
  }
   
   /**
   *
   * @param model
   * @return
   */
  @RequestMapping(value = "/edit-staff-detail", method = RequestMethod.POST)
  public String saveStaffInfo(@Valid @ModelAttribute("staffFormEdit") StaffValidation staffFormEdit, BindingResult result,  Map model, HttpSession session) {
	 List<mmDepartment> departmentList = mmdepartmentService.loadDepartmentList();
	 String staffEmail = staffFormEdit.getStaffEmail();
	 String staffName = staffFormEdit.getStaffName();
	 String staffPhone = staffFormEdit.getStaffPhone();
	 String staffDepartment = staffFormEdit.getStaffDepartment();
	 
	 System.out.println("staff Name : " + staffName);
	 model.put("staffEmail", staffEmail);
	 model.put("staffName", staffName);
	 model.put("staffPhone", staffPhone);
	 
	 model.put("departmentList", departmentList);
	 if(result.hasErrors()) {
		 model.put("error", 1);
		 return "mm.profile";
     }else
     {
    	 String userCode = session.getAttribute("currentUserCode").toString();
    	 mmStaff staff = mmstaffService.loadStaffByUserCode(userCode);
    	 if(staff != null){
	    	 String staffCatCode = "LEC";
	    	 int staffID = staff.getStaff_ID();
	    	 
	    	 
	    	 staff.setStaff_AsciiName(staffName);
	     	 staff.setStaff_Name(staffName);
	     	 staff.setStaff_Phone(staffPhone);
	     	 //staff.setStaff_User_Code(userCode);
	     	 //staff.setStaff_Category_Code(staffCatCode);
	     	 staff.setStaff_Email(staffEmail);
	     	 staff.setStaff_Code(userCode);
	     	 
	    	 //staffService.editAStaff(staff);
	    	 model.put("staffCategory", staff.getStaffCategory().getStaff_Category_Name());
	    	 model.put("staffDepartementCode", staffDepartment);
    	 }
    	 return "mm.profile";
     }
  }
  
  @RequestMapping(value = "/remove-a-professor/{id}", method = RequestMethod.GET)
  public String removeAProfessor(ModelMap model, @PathVariable("id") int professorId, HttpSession session) {
	   String userCode = session.getAttribute("currentUserCode").toString();
	   String userRole = session.getAttribute("currentUserRole").toString();
	   mmStaff staff = mmstaffService.loadStaffById(userRole, professorId);
	   if(staff != null){
		   mmstaffService.removeAStaff(professorId);
		   List<mmStaff> professorList = mmstaffService.listStaffs();
		   model.put("professorList", professorList);
		   String status = "Bạn đã xóa thành công giảng viên";
		   model.put("status", status);
		   return "mm.professors";
	   }
	   return "cp.notFound404";
  }

}
