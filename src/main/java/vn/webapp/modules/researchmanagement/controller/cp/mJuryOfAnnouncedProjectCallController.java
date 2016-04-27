/**
 * @author : HaTN 
 * @address : HUST K51
 * @modified : December 27th, 2015
 */
package vn.webapp.modules.researchmanagement.controller.cp;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
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

import vn.webapp.controller.BaseWeb;
import vn.webapp.libraries.DateUtil;

//For project call
import vn.webapp.modules.researchmanagement.service.mProjectCallsService;
import vn.webapp.modules.researchmanagement.model.mProjectCalls;

//For jury role of submitted project
import vn.webapp.modules.researchmanagement.service.mJuryRoleOfSubmittedProjectsService;
import vn.webapp.modules.researchmanagement.model.mJuryRoleOfSubmittedProjects;

//For jury of announced project call
import vn.webapp.modules.researchmanagement.service.mJuryOfAnnouncedProjectCallService;
import vn.webapp.modules.researchmanagement.model.mJuryOfAnnouncedProjectCall;
import vn.webapp.modules.usermanagement.model.mFaculty;
//For staff
import vn.webapp.modules.usermanagement.model.mStaff;
import vn.webapp.modules.usermanagement.service.mFacultyService;
import vn.webapp.modules.usermanagement.service.mStaffService;

//Validation
import vn.webapp.modules.researchmanagement.validation.mJuryOfAnnouncedProjectCallValidation;


@Controller("cpmJuryOfAnnouncedProjectCall")
@RequestMapping(value = { "/cp" })
public class mJuryOfAnnouncedProjectCallController extends BaseWeb {

	@Autowired
	private mStaffService staffService;

	@Autowired
	private mFacultyService facultyService;

	@Autowired
	private mProjectCallsService projectCallsService;
	
	@Autowired
	private mJuryRoleOfSubmittedProjectsService juryRoleOfSubmittedProjectsService;
	
	@Autowired
	private mJuryOfAnnouncedProjectCallService juryOfAnnouncedProjectCallService;
	
	static final String status = "active";

	/**
	 * Show list all threads
	 * 
	 * @param model
	 * @return
	 */
	
	@RequestMapping(value = "/list-jury-of-announced-project-call", method = RequestMethod.GET)
	public String jurySubmittedProjectList(ModelMap model, HttpSession session) {
		
		// Get project call list
		List<mProjectCalls> projectCallList = projectCallsService.loadProjectCallsList();
		
		// Get staff list
		List<mStaff> staffList = staffService.listStaffs();
		
		//Get jury role of submitted projects
		List<mJuryRoleOfSubmittedProjects> juryRoleOfSubmittedProjecsList = juryRoleOfSubmittedProjectsService.loadAllJuryRoleOfSubmittedProjects();
		
		model.put("projectCallList", projectCallList);
		model.put("staffList", staffList);
		model.put("juryRoleOfSubmittedProjecsList", juryRoleOfSubmittedProjecsList);
			
		return "cp.listJuryOfAnnouncedProjectCall";
	}

	/**
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/add-jury-submitted-projects", method = RequestMethod.GET)
	public String addJuryOfSubmittedProjects(ModelMap model, HttpSession session) {

		// Get project call list
		List<mProjectCalls> projectCallList = projectCallsService.loadProjectCallsList();
		
		HashMap<String, String> projectCallHashMap = new HashMap<String, String>();
		for(int i = 0; i <  projectCallList.size(); i++){
			projectCallHashMap.put(projectCallList.get(i).getPROJCALL_CODE(),projectCallList.get(i).getPROJCALL_NAME());
		}
		
		List<mFaculty> listFaculty = facultyService.loadFacultyList();
		
		// Get staff list
		List<mStaff> staffList = staffService.listStaffs();
		
		HashMap<String, String> staffHashMap = new HashMap<String, String>();
		for(int i = 0; i < staffList.size(); i++){
			staffHashMap.put(staffList.get(i).getStaff_Code(), staffList.get(i).getStaff_Name());
		}
				
		//Get jury role of submitted projects
		List<mJuryRoleOfSubmittedProjects> juryRoleOfSubmittedProjecsList = juryRoleOfSubmittedProjectsService.loadAllJuryRoleOfSubmittedProjects();
		
		HashMap<String, String> roleHashMap = new HashMap<String, String>();
		for(int i = 0; i < juryRoleOfSubmittedProjecsList.size(); i++){
			roleHashMap.put(juryRoleOfSubmittedProjecsList.get(i).getJUPRJROL_CODE(), juryRoleOfSubmittedProjecsList.get(i).getJUPRJROL_NAME());
		}
		
		//Get jury of announced project call
		List<mJuryOfAnnouncedProjectCall> juryOfAnnouncedProjectCallList = juryOfAnnouncedProjectCallService.loadAllJuryOfAnnouncedProjectCall();
		
		for(int i = 0; i < juryOfAnnouncedProjectCallList.size(); i++){
			juryOfAnnouncedProjectCallList.get(i).setJUSUPRJ_PRJCALLCODE(projectCallHashMap.get(juryOfAnnouncedProjectCallList.get(i).getJUSUPRJ_PRJCALLCODE()));
			juryOfAnnouncedProjectCallList.get(i).setJUSUPRJ_STAFFCODE(staffHashMap.get(juryOfAnnouncedProjectCallList.get(i).getJUSUPRJ_STAFFCODE()));
			juryOfAnnouncedProjectCallList.get(i).setJUPSURJ_ROLECODE(roleHashMap.get(juryOfAnnouncedProjectCallList.get(i).getJUPSURJ_ROLECODE()));
		}
		
		model.put("projectCallList", projectCallList);
		model.put("staffList", staffList);
		model.put("listFaculty", listFaculty);
		model.put("juryRoleOfSubmittedProjecsList", juryRoleOfSubmittedProjecsList);
		model.put("juryOfAnnouncedProjectCallFormAdd", new mJuryOfAnnouncedProjectCallValidation());
		model.put("juryOfAnnouncedProjectCallList", juryOfAnnouncedProjectCallList);
					
		return "cp.addJuryOfAnnouncedProjectCall";
	}

	
	@RequestMapping(value = "/save-jury-of-announced-project-call", method = RequestMethod.POST)
	public String saveJuryOfAnnouncedProjectCall( HttpServletRequest request, @Valid @ModelAttribute("juryOfAnnouncedProjectCallFormAdd") mJuryOfAnnouncedProjectCallValidation juryOfAnnouncedProjectCallValid, BindingResult result, ModelMap model, HttpSession session) {
		
		if (result.hasErrors()) {
			return "cp.addJuryOfAnnouncedProjectCall";
		} else {
			// Prepare data for inserting DB
			String JUSUPRJ_STAFFCODE = juryOfAnnouncedProjectCallValid.getJUSUPRJ_STAFFCODE();
			String JUSUPRJ_PRJCALLCODE = juryOfAnnouncedProjectCallValid.getJUSUPRJ_PRJCALLCODE();
			String JUPSURJ_ROLECODE = juryOfAnnouncedProjectCallValid.getJUPSURJ_ROLECODE();

			//Save item
			int iSaveResult = juryOfAnnouncedProjectCallService.saveJuryOfAnnouncedProjectCall(JUSUPRJ_STAFFCODE, JUSUPRJ_PRJCALLCODE, JUPSURJ_ROLECODE);
			
			if (iSaveResult > 0) {
				model.put("code", 1);
				model.put("success", "Staff with code " + JUSUPRJ_STAFFCODE + " has become a member of examinater for project call of code " + JUSUPRJ_PRJCALLCODE + " as the role of code "+ JUPSURJ_ROLECODE);
				
				
			}
			
			//PREPARING DATA FOR SHOWING
			// Get project call list
			List<mProjectCalls> projectCallList = projectCallsService.loadProjectCallsList();
			
			HashMap<String, String> projectCallHashMap = new HashMap<String, String>();
			for(int i = 0; i <  projectCallList.size(); i++){
				projectCallHashMap.put(projectCallList.get(i).getPROJCALL_CODE(),projectCallList.get(i).getPROJCALL_NAME());
			}
			
			// Get staff list
			List<mStaff> staffList = staffService.listStaffs();
			
			HashMap<String, String> staffHashMap = new HashMap<String, String>();
			for(int i = 0; i < staffList.size(); i++){
				staffHashMap.put(staffList.get(i).getStaff_Code(), staffList.get(i).getStaff_Name());
			}
					
			//Get jury role of submitted projects
			List<mJuryRoleOfSubmittedProjects> juryRoleOfSubmittedProjecsList = juryRoleOfSubmittedProjectsService.loadAllJuryRoleOfSubmittedProjects();
			
			HashMap<String, String> roleHashMap = new HashMap<String, String>();
			for(int i = 0; i < juryRoleOfSubmittedProjecsList.size(); i++){
				roleHashMap.put(juryRoleOfSubmittedProjecsList.get(i).getJUPRJROL_CODE(), juryRoleOfSubmittedProjecsList.get(i).getJUPRJROL_NAME());
			}
			
			//Get jury of announced project call
			List<mJuryOfAnnouncedProjectCall> juryOfAnnouncedProjectCallList = juryOfAnnouncedProjectCallService.loadAllJuryOfAnnouncedProjectCall();
			
			for(int i = 0; i < juryOfAnnouncedProjectCallList.size(); i++){
				juryOfAnnouncedProjectCallList.get(i).setJUSUPRJ_PRJCALLCODE(projectCallHashMap.get(juryOfAnnouncedProjectCallList.get(i).getJUSUPRJ_PRJCALLCODE()));
				juryOfAnnouncedProjectCallList.get(i).setJUSUPRJ_STAFFCODE(staffHashMap.get(juryOfAnnouncedProjectCallList.get(i).getJUSUPRJ_STAFFCODE()));
				juryOfAnnouncedProjectCallList.get(i).setJUPSURJ_ROLECODE(roleHashMap.get(juryOfAnnouncedProjectCallList.get(i).getJUPSURJ_ROLECODE()));
			}
			
			model.put("projectCallList", projectCallList);
			model.put("staffList", staffList);
			model.put("juryRoleOfSubmittedProjecsList", juryRoleOfSubmittedProjecsList);
			model.put("juryOfAnnouncedProjectCallFormAdd", new mJuryOfAnnouncedProjectCallValidation());
			model.put("juryOfAnnouncedProjectCallList", juryOfAnnouncedProjectCallList);
						
			return "cp.addJuryOfAnnouncedProjectCall";
		}
	}
	
	/**
	 * Remove a thread
	 * @param model
	 * @return
	 */
	 @RequestMapping(value = "/delete-jury-of-announced-project-call/{id}", method = RequestMethod.GET)
	 public String removeAProjectCall(ModelMap model, @PathVariable("id") int juryOfAnnouncedProjectCallId, HttpSession session) {
		 
		 //Delete the selected item
		 juryOfAnnouncedProjectCallService.deleteJuryOfAnnouncedProjectCall(juryOfAnnouncedProjectCallId);
		 	 
		 //PREPARING DATA FOR SHOWING
		// Get project call list
		List<mProjectCalls> projectCallList = projectCallsService.loadProjectCallsList();
		
		HashMap<String, String> projectCallHashMap = new HashMap<String, String>();
		for(int i = 0; i <  projectCallList.size(); i++){
			projectCallHashMap.put(projectCallList.get(i).getPROJCALL_CODE(),projectCallList.get(i).getPROJCALL_NAME());
		}
		
		// Get staff list
		List<mStaff> staffList = staffService.listStaffs();
		
		HashMap<String, String> staffHashMap = new HashMap<String, String>();
		for(int i = 0; i < staffList.size(); i++){
			staffHashMap.put(staffList.get(i).getStaff_Code(), staffList.get(i).getStaff_Name());
		}
				
		//Get jury role of submitted projects
		List<mJuryRoleOfSubmittedProjects> juryRoleOfSubmittedProjecsList = juryRoleOfSubmittedProjectsService.loadAllJuryRoleOfSubmittedProjects();
		
		HashMap<String, String> roleHashMap = new HashMap<String, String>();
		for(int i = 0; i < juryRoleOfSubmittedProjecsList.size(); i++){
			roleHashMap.put(juryRoleOfSubmittedProjecsList.get(i).getJUPRJROL_CODE(), juryRoleOfSubmittedProjecsList.get(i).getJUPRJROL_NAME());
		}
		
		//Get jury of announced project call
		List<mJuryOfAnnouncedProjectCall> juryOfAnnouncedProjectCallList = juryOfAnnouncedProjectCallService.loadAllJuryOfAnnouncedProjectCall();
		
		for(int i = 0; i < juryOfAnnouncedProjectCallList.size(); i++){
			juryOfAnnouncedProjectCallList.get(i).setJUSUPRJ_PRJCALLCODE(projectCallHashMap.get(juryOfAnnouncedProjectCallList.get(i).getJUSUPRJ_PRJCALLCODE()));
			juryOfAnnouncedProjectCallList.get(i).setJUSUPRJ_STAFFCODE(staffHashMap.get(juryOfAnnouncedProjectCallList.get(i).getJUSUPRJ_STAFFCODE()));
			juryOfAnnouncedProjectCallList.get(i).setJUPSURJ_ROLECODE(roleHashMap.get(juryOfAnnouncedProjectCallList.get(i).getJUPSURJ_ROLECODE()));
		}
		
		model.put("projectCallList", projectCallList);
		model.put("staffList", staffList);
		model.put("juryRoleOfSubmittedProjecsList", juryRoleOfSubmittedProjecsList);
		model.put("juryOfAnnouncedProjectCallFormAdd", new mJuryOfAnnouncedProjectCallValidation());
		model.put("juryOfAnnouncedProjectCallList", juryOfAnnouncedProjectCallList);
					
		return "cp.addJuryOfAnnouncedProjectCall";
	 }
	 	
}
