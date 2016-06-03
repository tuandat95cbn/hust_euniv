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
import java.util.HashSet;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import datamanipulation.submittedprojects.StaffJurySubmittedProjects;
import vn.webapp.controller.BaseWeb;
import vn.webapp.libraries.DateUtil;


//For project
import vn.webapp.modules.researchmanagement.model.mThreads;
import vn.webapp.modules.researchmanagement.service.mProjectCallsService;
import vn.webapp.modules.researchmanagement.service.nProjectService;

//For staff jury of submitted project
import vn.webapp.modules.researchmanagement.service.mStaffJuryOfSubmittedProjectService;
import vn.webapp.modules.researchmanagement.model.mStaffJuryOfSubmittedProject;
import vn.webapp.modules.researchmanagement.validation.mStaffJuryOfSubmittedProjectValidation;

//For staff
import vn.webapp.modules.usermanagement.model.mStaff;
import vn.webapp.modules.usermanagement.service.mStaffService;

//For jury of announced project call
import vn.webapp.modules.researchmanagement.service.mJuryOfAnnouncedProjectCallService;
import vn.webapp.modules.researchmanagement.model.Projects;
import vn.webapp.modules.researchmanagement.model.mJuryOfAnnouncedProjectCall;

//For project call
import vn.webapp.modules.researchmanagement.model.mProjectCalls;



@Controller("cpmStaffJuryOfSubmittedProject")
@RequestMapping(value = { "/cp" })
public class mStaffJuryOfSubmittedProjectController extends BaseWeb {

	@Autowired
	private mStaffService staffService;
	
	@Autowired
	private mStaffJuryOfSubmittedProjectService staffJuryOfSubmittedProjectService;
	
	@Autowired
	private nProjectService projectService;
	
	@Autowired
	private mJuryOfAnnouncedProjectCallService juryOfAnnouncedProjectCall;
		
	@Autowired
	private mProjectCallsService projectCallsService;
	
	static final String status = "active";
	
	/**
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	public String name(){
		return "mStaffJuryOfSubmittedProjectController";
	}
	@ResponseBody
	@RequestMapping(value = "/loadSubmittedProjectsAndJuryStaffs.html", method = RequestMethod.POST)
	public String getSubmittedProjectsAndJuryStaffsOfProjectCall(HttpSession session,
			@RequestParam(value="projectcallcode", defaultValue = "0") String projectCallCode){
		System.out.println(name() + "::getSubmittedProjectsAndJuryStaffsOfProjectCall, projectCallCode = " 
			+ projectCallCode);
		
		// Get all projects in project calls whose present is the current present
		List<Projects> projectList = projectService.loadProjectByProjectCallId(projectCallCode);
		
		HashMap<String, String> projectHashMap = new HashMap<String, String>();
		for(int i = 0; i < projectList.size(); i++){
			projectHashMap.put(projectList.get(i).getPROJ_Code(),projectList.get(i).getPROJ_Name());
		}
		
		System.out.println("Size of projectList : " + projectList.size());
		
		// Get staff list
		List<mStaff> staffList = juryOfAnnouncedProjectCall.loadStaffsOfJuryOfAProjecCall(projectCallCode);//staffService.listStaffs();
		System.out.println(name() + "::addJuryOfSubmittedProjects, staffList = ");
		for(mStaff st: staffList) System.out.println(st.getStaff_Code());
		
		HashMap<String, String> staffHashMap = new HashMap<String, String>();
		for(int i = 0; i < staffList.size(); i++){
			staffHashMap.put(staffList.get(i).getStaff_Code(), 
					staffList.get(i).getStaff_Name());
		}
		
		//Get staff jury of submitted project
		List<mStaffJuryOfSubmittedProject> staffJuryOfSubmittedProjectList = staffJuryOfSubmittedProjectService.loadAllStaffJuryOfSubmittedProject();
		System.out.println(name() + "::addJuryOfSubmittedProjects, staffJuryOfSubmittedProjectList = ");
		for(mStaffJuryOfSubmittedProject sjp: staffJuryOfSubmittedProjectList){
			System.out.println(sjp.getSTFJUPRJ_CODE() + "\t" + sjp.getSTFJUPRJ_STAFFJURCODE());
		}
		
		for(int i = 0; i < staffJuryOfSubmittedProjectList.size(); i++){
			staffJuryOfSubmittedProjectList.get(i).setSTFJUPRJ_PRJCODE(projectHashMap.get(staffJuryOfSubmittedProjectList.get(i).getSTFJUPRJ_PRJCODE()));
			staffJuryOfSubmittedProjectList.get(i).setSTFJUPRJ_STAFFJURCODE(staffHashMap.get(staffJuryOfSubmittedProjectList.get(i).getSTFJUPRJ_STAFFJURCODE()));
		}

		String json = "{";
			json += "\"projectLists\":[";
			for(int i = 0; i < projectList.size(); i++){
				Projects prj = projectList.get(i);
				json += "{\"code\":\"" + prj.getPROJ_Code() + "\",\"name\":\"" + prj.getPROJ_Name() + "\"}";
				if(i < projectList.size()-1)
					json += ",";
			}
			json += "]\n";
			json += ",\"staffLists\":[";
			for(int i = 0; i < staffList.size(); i++){
				mStaff st = staffList.get(i);
				json += "{\"code\":\"" + st.getStaff_Code() + "\",\"name\":\"" + st.getStaff_Name() + "\"}";
				if(i < staffList.size()-1)
					json += ",";
			}
			json += "]\n";
			json += ",\"staffJuryList\":[";
			for(int i = 0; i < staffJuryOfSubmittedProjectList.size(); i++){
				mStaffJuryOfSubmittedProject sjsp = staffJuryOfSubmittedProjectList.get(i);
				json += "{\"projectname\":\"" + sjsp.getSTFJUPRJ_PRJCODE() + "\",\"staffname\":\"" + 
				sjsp.getSTFJUPRJ_STAFFJURCODE() + "\",\"ID\":" + sjsp.getSTFJUPRJ_ID() + "}";
				if(i < staffJuryOfSubmittedProjectList.size()-1)
					json += ",\n";
			}
			json += "]";
		json += "}";
		return json;
	}
	
	/**
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/assign-jury-submitted-projects", method = RequestMethod.GET)
	public String addJuryOfSubmittedProjects(ModelMap model, HttpSession session) {
		// Get current user name and role
		String userCode = session.getAttribute("currentUserCode").toString();
		String userRole = session.getAttribute("currentUserRole").toString();
		// Get project call list
		List<mProjectCalls> projectCallList = projectCallsService.loadProjectCallsList();
		model.put("projectCallList", projectCallList);
		return "cp.addStaffJuryOfSubmittedProject";
	}
	
	/**
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/list-assign-jury-submitted-projects", method = RequestMethod.POST)
	public String listJuryOfSubmittedProjects(HttpServletRequest request, ModelMap model, HttpSession session) {

		// Get current user name and role
		String userCode = session.getAttribute("currentUserCode").toString();
		String userRole = session.getAttribute("currentUserRole").toString();
		String projectCallCode = request.getParameter("PROJCALL_CODE");
		String projectCallName = projectCallCode;
		
		System.out.println(name() + "::listJuryOfSubmittedProjects, userCode = " + userCode + ", userRole = " + userRole + ", projectCallCode = " + projectCallCode);

		// Get project call list
		//List<mProjectCalls> projectCallList = projectCallsService.loadProjectCallsList();
		mProjectCalls projectCall = projectCallsService.loadAProjectCallByCode(projectCallCode);
		List<mProjectCalls> projectCallList = new ArrayList<mProjectCalls>();
		projectCallList.add(projectCall);
		
		HashMap<String, String> mProjectCallCode2Name = new HashMap<String, String>();
		for(mProjectCalls pc: projectCallList){
			if(projectCallCode != null && projectCallCode.equals(pc.getPROJCALL_CODE())){
				projectCallName = pc.getPROJCALL_NAME();
			}
		}
		
		List<mStaff> juries = juryOfAnnouncedProjectCall.loadStaffsOfJuryOfAProjecCall(projectCallCode);
		System.out.println(name() + "::listJuryOfSubmittedProjects, juries = " + juries.size());
		
		List<mStaff> staffs = staffService.listStaffs();
		HashMap<String, String> mStaffCode2Name = new HashMap<String, String>();
		for(mStaff st: staffs){
			mStaffCode2Name.put(st.getStaff_Code(), st.getStaff_Name());
		}
		
		//List<mThreads> projects = projectService.listAll();
		List<Projects> projects = projectService.loadProjectByProjectCallId(projectCallCode);
		
		HashMap<String, String> mProjectCode2Name = new HashMap<String, String>();
		for(Projects prj: projects){
			mProjectCode2Name.put(prj.getPROJ_Code(), prj.getPROJ_Name());
		}
		
		List<mStaffJuryOfSubmittedProject> staffJuryOfSubmittedProjectList;
		if(!"".equals(projectCallCode)){
			staffJuryOfSubmittedProjectList = staffJuryOfSubmittedProjectService.loadListStaffJuryOfSubmittedProjectByProjectCallCode(projectCallCode);
			System.out.println(name() + "::listJuryOfSubmittedProjects, listJuryOfSubmittedProjects.sz = " + staffJuryOfSubmittedProjectList.size());
		}else{
			staffJuryOfSubmittedProjectList = staffJuryOfSubmittedProjectService.loadAllStaffJuryOfSubmittedProject();
		}
		for(mStaffJuryOfSubmittedProject sjsp: staffJuryOfSubmittedProjectList){
			sjsp.setSTFJUPRJ_PRJCODE(mProjectCode2Name.get(sjsp.getSTFJUPRJ_PRJCODE()));
			sjsp.setSTFJUPRJ_STAFFJURCODE(mStaffCode2Name.get(sjsp.getSTFJUPRJ_STAFFJURCODE()));
		}
		
		// Return to view
		model.put("projectList", projects);
		//model.put("staffList", staffs);
		model.put("staffList", juries);
		model.put("staffJuryOfSubmittedProjectFormAdd", new mStaffJuryOfSubmittedProjectValidation());
		model.put("staffJuryOfSubmittedProjectList", staffJuryOfSubmittedProjectList);
		model.put("projectCallList", projectCallList);
		model.put("projectCallCode", projectCallCode);
		model.put("projectCallName", projectCallName);
		return "cp.listStaffJuryOfSubmittedProject";
	}

	/**
	 * 
	 * @param request
	 * @param staffJuryOfSubmittedProjectValid
	 * @param result
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/save-staff-jury-of-submitted-project", method = RequestMethod.POST)
	public String saveJuryOfAnnouncedProjectCall( HttpServletRequest request, 
			@Valid @ModelAttribute("staffJuryOfSubmittedProjectFormAdd") mStaffJuryOfSubmittedProjectValidation staffJuryOfSubmittedProjectValid, 
			BindingResult result, ModelMap model, HttpSession session) {
		
		// Get project call list
		List<mProjectCalls> projectCallList = projectCallsService.loadProjectCallsList();
		List<mStaffJuryOfSubmittedProject> staffJuryOfSubmittedProjectList = staffJuryOfSubmittedProjectService.loadAllStaffJuryOfSubmittedProject();
		String selectedProjectCallCode = request.getParameter("PROJCALL_CODE");
		String projectCallCodeFiltering = request.getParameter("projectCallCodeFiltering");
		String projectCallNameFiltering = projectCallCodeFiltering;
		
		System.out.println(name() + "::saveJuryOfAnnouncedProjectCall, staffs = " + staffJuryOfSubmittedProjectValid.getSTFJUPRJ_STAFFJURCODE() + 
				", projects = " + staffJuryOfSubmittedProjectValid.getSTFJUPRJ_PRJCODE());
		
		if(projectCallList != null)
		{
			for (mProjectCalls projectCall : projectCallList) {
				if(projectCallCodeFiltering != null && projectCallCodeFiltering.equals(projectCall.getPROJCALL_CODE())){
					projectCallNameFiltering = projectCall.getPROJCALL_NAME();
				}
			}
		}
		model.put("staffJuryOfSubmittedProjectList", staffJuryOfSubmittedProjectList);
		model.put("projectCallList", projectCallList);
		model.put("projectCallCode", projectCallCodeFiltering);
		model.put("projectCallName", projectCallNameFiltering);
		if (result.hasErrors()) {
			return "cp.listStaffJuryOfSubmittedProject";
		} else {
			// Prepare data for inserting DB
			String STFJUPRJ_STAFFJURCODE = staffJuryOfSubmittedProjectValid.getSTFJUPRJ_STAFFJURCODE();
			String STFJUPRJ_PRJCODE = staffJuryOfSubmittedProjectValid.getSTFJUPRJ_PRJCODE();

			String[] projectCodes;
			if(STFJUPRJ_PRJCODE.contains(","))
			{
				projectCodes = STFJUPRJ_PRJCODE.split("\\,");
			}else{
				projectCodes = new String[1];
				projectCodes[0] = STFJUPRJ_PRJCODE;
			}
			
			// Save data into DB
			for (String projectCode : projectCodes) {
				int iSaveResult = staffJuryOfSubmittedProjectService.saveStaffJuryOfSubmittedProject(STFJUPRJ_STAFFJURCODE, projectCode, selectedProjectCallCode);
			}
			model.put("staffJuryOfSubmittedProjectFormAdd", new mStaffJuryOfSubmittedProjectValidation());
			return "redirect:" + this.baseUrl + "/cp/assign-jury-submitted-projects.html";
			//return "redirect:" + this.baseUrl + "/cp/list-assign-jury-submitted-projects.html";
		}
	}
	
	/**
	 * Remove a thread
	 * @param model
	 * @return
	 */
	 @RequestMapping(value = "/delete-staff-jury-of-submitted-project/{id}", method = RequestMethod.GET)
	 public String removeAProjectCall(ModelMap model, @PathVariable("id") int staffJuryOfSubmittedProjectId, HttpSession session) {
		 
		 //Delete the selected item
		 staffJuryOfSubmittedProjectService.deleteStaffJuryOfSubmittedProject(staffJuryOfSubmittedProjectId);
		 	 
		 //PREPARING DATA FOR SHOWING
		// Get current user name and role
		String userCode = session.getAttribute("currentUserCode").toString();
		String userRole = session.getAttribute("currentUserRole").toString();
		
		System.out.println("User code : " + userCode);

		//Get list of projected calls whose already-assigned present is the current logged-in user
		List<mJuryOfAnnouncedProjectCall> juryOfAnnouncedProjectCallList = juryOfAnnouncedProjectCall.loadListJuryOfAnnouncedProjectCallByPresentCode(userCode);
		
		System.out.println("Size of juryOfAnnouncedProjectCallList : " + juryOfAnnouncedProjectCallList.size());
		
		// Get all projects in project calls whose present is the current present
		List<Projects> projectList = new ArrayList<Projects>();
		for(int i = 0; i < juryOfAnnouncedProjectCallList.size(); i++){
			projectList.addAll( projectService.loadProjectByProjectCallId(juryOfAnnouncedProjectCallList.get(i).getJUSUPRJ_PRJCALLCODE()));
		}
		
		HashMap<String, String> projectHashMap = new HashMap<String, String>();
		for(int i = 0; i < projectList.size(); i++){
			projectHashMap.put(projectList.get(i).getPROJ_Code(),projectList.get(i).getPROJ_Name());
		}
		
		// Get staff list
		List<mStaff> staffList = staffService.listStaffs();
		
		HashMap<String, String> staffHashMap = new HashMap<String, String>();
		for(int i = 0; i < staffList.size(); i++){
			staffHashMap.put(staffList.get(i).getStaff_Code(), staffList.get(i).getStaff_Name());
		}
		
		//Get staff jury of submitted project
		List<mStaffJuryOfSubmittedProject> staffJuryOfSubmittedProjectList = staffJuryOfSubmittedProjectService.loadAllStaffJuryOfSubmittedProject();
		
		for(int i = 0; i < staffJuryOfSubmittedProjectList.size(); i++){
			staffJuryOfSubmittedProjectList.get(i).setSTFJUPRJ_PRJCODE(projectHashMap.get(staffJuryOfSubmittedProjectList.get(i).getSTFJUPRJ_PRJCODE()));
			staffJuryOfSubmittedProjectList.get(i).setSTFJUPRJ_STAFFJURCODE(staffHashMap.get(staffJuryOfSubmittedProjectList.get(i).getSTFJUPRJ_STAFFJURCODE()));
			
		}
		
		model.put("projectList", projectList);
		model.put("staffList", staffList);
	
		model.put("staffJuryOfSubmittedProjectFormAdd", new mStaffJuryOfSubmittedProjectValidation());
		model.put("staffJuryOfSubmittedProjectList", staffJuryOfSubmittedProjectList);
					
		return "redirect:" + this.baseUrl + "/cp/assign-jury-submitted-projects.html";
	 } 	
}
