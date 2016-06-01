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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	public String name(){
		return "mJuryOfAnnouncedProjectCallController";
	}
	@ResponseBody
    @RequestMapping(value = "/getJuryOfProjectCall", method = RequestMethod.POST, produces = "text/html; charset=UTF-8")
    public String getJuryOfAProjectCall(HttpServletRequest  request, HttpSession session, 
    		@RequestParam(value = "projectCallCode", defaultValue = "0") String projectCallCode) {
		String html = "";
		System.out.println(name() + "::getJuryOfAProjectCall, projectCallCode = " + projectCallCode);
		
		List<mJuryOfAnnouncedProjectCall> juryOfAnnouncedProjectCallList = 
				juryOfAnnouncedProjectCallService.loadListJuryOfAnnouncedProjectCallByProjectCallCode(projectCallCode);
		
		List<mStaff> staffs = staffService.listStaffs();
		HashMap<String, String> mStaffCode2Name = new HashMap<String, String>();
		for(mStaff st: staffs)
			mStaffCode2Name.put(st.getStaff_Code(), st.getStaff_Name());
		
		html +=
		"<thead>"
		+ "<tr>"
		+ "<th title=\"Name of project call \" > Đợt gọi đề tài </th>"
		+	"<th title=\"Name of staff \">Thành viên </th>"
		+	"<th title=\"Name of role \">Vai trò</th>"
		+	"<th></th>"
		+ "</tr>"
		+ "</thead>";
	
		
		html += "<tbody>";
		for(mJuryOfAnnouncedProjectCall jury: juryOfAnnouncedProjectCallList){
			html += "<tr class=\"gradeX\">";
			
			html += "<td><c:out value=\"" + projectCallCode + "\"/></td>";
			html += "<td><c:out value=\"" + jury.getJUSUPRJ_STAFFCODE() + "\"/></td>";
			html += "<td><c:out value=\"" + jury.getJUPSURJ_ROLECODE() + "\"/></td>";
			
			//html += "<td><c:out value=" + projectCallCode + "/></td>";
			//html += "<td><c:out value=" + jury.getJUSUPRJ_STAFFCODE() + "/></td>";
			//html += "<td><c:out value=" + jury.getJUPSURJ_ROLECODE() + "/></td>";
			
			html += "<td class=\"center\">"
			+ "<button type=\"button\" id=\"removeJuryOfAnnouncedProjectCall\" onclick=\"v_fRemoveJuryOfAnnouncedProjectCall(" + jury.getJUSUPRJ_ID() + ");\" class=\"btn btn-danger btn-xs\" title=\"Remove\">Xoá</button>"
			+ "<br>"
			+ "</td>";
			html += "</tr>";
		}
		html += "</tbody>";
		
		System.out.println(name() + "::getJuryOfAProjectCall, return html = " + html);
		return html;
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
		model.put("projectCallList", projectCallList);

		return "cp.addJuryOfAnnouncedProjectCall";
	}

	/**
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/list-jury-submitted-projects", method = RequestMethod.POST)
	public String showListJuryOfSubmittedProjects(HttpServletRequest request, ModelMap model, HttpSession session) {

		// Get data
		String projectCallCode = request.getParameter("JUSUPRJ_PRJCALLCODE");
		
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
		
		List<mJuryOfAnnouncedProjectCall> juryOfAnnouncedProjectCallList = null;
		//Get jury of announced project call
		if(!"".equals(projectCallCode)){
			juryOfAnnouncedProjectCallList = juryOfAnnouncedProjectCallService.loadListJuryOfAnnouncedProjectCallByProjectCallCode(projectCallCode);
		}else{
			juryOfAnnouncedProjectCallList = juryOfAnnouncedProjectCallService.loadAllJuryOfAnnouncedProjectCall();
		}
		
		for(int i = 0; i < juryOfAnnouncedProjectCallList.size(); i++){
			juryOfAnnouncedProjectCallList.get(i).setJUSUPRJ_PRJCALLCODE(projectCallHashMap.get(juryOfAnnouncedProjectCallList.get(i).getJUSUPRJ_PRJCALLCODE()));
			juryOfAnnouncedProjectCallList.get(i).setJUSUPRJ_STAFFCODE(staffHashMap.get(juryOfAnnouncedProjectCallList.get(i).getJUSUPRJ_STAFFCODE()));
			juryOfAnnouncedProjectCallList.get(i).setJUPSURJ_ROLECODE(roleHashMap.get(juryOfAnnouncedProjectCallList.get(i).getJUPSURJ_ROLECODE()));
		}
		
		model.put("projectCallCode", projectCallCode);
		model.put("staffList", staffList);
		model.put("listFaculty", listFaculty);
		model.put("juryRoleOfSubmittedProjecsList", juryRoleOfSubmittedProjecsList);
		model.put("juryOfAnnouncedProjectCallFormAdd", new mJuryOfAnnouncedProjectCallValidation());
		model.put("juryOfAnnouncedProjectCallList", juryOfAnnouncedProjectCallList);
					
		return "cp.listJuryOfAnnouncedProjectCall";
	}
	
	/**
	 * 
	 * @param request
	 * @param juryOfAnnouncedProjectCallValid
	 * @param result
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/save-jury-of-announced-project-call", method = RequestMethod.POST)
	public String saveJuryOfAnnouncedProjectCall( HttpServletRequest request, @Valid @ModelAttribute("juryOfAnnouncedProjectCallFormAdd") mJuryOfAnnouncedProjectCallValidation juryOfAnnouncedProjectCallValid, BindingResult result, ModelMap model, HttpSession session) {
		
		if (result.hasErrors()) {
			return "cp.listJuryOfAnnouncedProjectCall";
		} else {
			// Prepare data for inserting DB
			String JUSUPRJ_STAFFCODE = juryOfAnnouncedProjectCallValid.getJUSUPRJ_STAFFCODE();
			String JUPSURJ_ROLECODE = juryOfAnnouncedProjectCallValid.getJUPSURJ_ROLECODE();
			// Get data
			String JUSUPRJ_PRJCALLCODE = request.getParameter("JUSUPRJ_PRJCALLCODE");
			mJuryOfAnnouncedProjectCall oJuryProject = juryOfAnnouncedProjectCallService.loadListJuryOfAnnouncedProjectCallByProjectCallAndStaffCode(JUSUPRJ_PRJCALLCODE, JUSUPRJ_STAFFCODE);
			if(oJuryProject != null)
			{
				model.put("err", "Thành viên này đã tồn tại.");
			}else{
				//Save item
				int iSaveResult = juryOfAnnouncedProjectCallService.saveJuryOfAnnouncedProjectCall(JUSUPRJ_STAFFCODE, JUSUPRJ_PRJCALLCODE, JUPSURJ_ROLECODE);
				
				if (iSaveResult > 0) {
					model.put("code", 1);
					model.put("success", "Staff with code " + JUSUPRJ_STAFFCODE + " has become a member of examinater for project call of code " + JUSUPRJ_PRJCALLCODE + " as the role of code "+ JUPSURJ_ROLECODE);
				}
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
			List<mJuryOfAnnouncedProjectCall> juryOfAnnouncedProjectCallList;
			if(!"".equals(JUSUPRJ_PRJCALLCODE)){
				juryOfAnnouncedProjectCallList = juryOfAnnouncedProjectCallService.loadListJuryOfAnnouncedProjectCallByProjectCallCode(JUSUPRJ_PRJCALLCODE);
			}else{
				juryOfAnnouncedProjectCallList = juryOfAnnouncedProjectCallService.loadAllJuryOfAnnouncedProjectCall();
			}
			
			for(int i = 0; i < juryOfAnnouncedProjectCallList.size(); i++){
				juryOfAnnouncedProjectCallList.get(i).setJUSUPRJ_PRJCALLCODE(projectCallHashMap.get(juryOfAnnouncedProjectCallList.get(i).getJUSUPRJ_PRJCALLCODE()));
				juryOfAnnouncedProjectCallList.get(i).setJUSUPRJ_STAFFCODE(staffHashMap.get(juryOfAnnouncedProjectCallList.get(i).getJUSUPRJ_STAFFCODE()));
				juryOfAnnouncedProjectCallList.get(i).setJUPSURJ_ROLECODE(roleHashMap.get(juryOfAnnouncedProjectCallList.get(i).getJUPSURJ_ROLECODE()));
			}
			
			List<mFaculty> listFaculty = facultyService.loadFacultyList();
			model.put("projectCallCode", JUSUPRJ_PRJCALLCODE);
			model.put("staffList", staffList);
			model.put("listFaculty", listFaculty);
			model.put("juryRoleOfSubmittedProjecsList", juryRoleOfSubmittedProjecsList);
			model.put("juryOfAnnouncedProjectCallFormAdd", new mJuryOfAnnouncedProjectCallValidation());
			model.put("juryOfAnnouncedProjectCallList", juryOfAnnouncedProjectCallList);
						
			return "cp.listJuryOfAnnouncedProjectCall";
		}
	}
	
	/**
	 * Remove a thread
	 * @param model
	 * @return
	 */
	 @RequestMapping(value = "/delete-jury-of-announced-project-call/{id}", method = RequestMethod.GET)
	 public String removeAProjectCall(ModelMap model, @PathVariable("id") int juryOfAnnouncedProjectCallId, HttpSession session) {
		 
		 mJuryOfAnnouncedProjectCall juryOfAnnouncedProjectCall = juryOfAnnouncedProjectCallService.loadAJuryOfAnnouncedProjectCallById(juryOfAnnouncedProjectCallId);
		 
		 String projectCallCode = "";
		 if(juryOfAnnouncedProjectCall != null){
			 projectCallCode = juryOfAnnouncedProjectCall.getJUSUPRJ_PRJCALLCODE();
			 //Delete the selected item
			 juryOfAnnouncedProjectCallService.deleteJuryOfAnnouncedProjectCall(juryOfAnnouncedProjectCallId);
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
		List<mJuryOfAnnouncedProjectCall> juryOfAnnouncedProjectCallList;
		if(!"".equals(projectCallCode)){
			juryOfAnnouncedProjectCallList = juryOfAnnouncedProjectCallService.loadListJuryOfAnnouncedProjectCallByProjectCallCode(projectCallCode);
		}else{
			juryOfAnnouncedProjectCallList = juryOfAnnouncedProjectCallService.loadAllJuryOfAnnouncedProjectCall();
		}
		
		for(int i = 0; i < juryOfAnnouncedProjectCallList.size(); i++){
			juryOfAnnouncedProjectCallList.get(i).setJUSUPRJ_PRJCALLCODE(projectCallHashMap.get(juryOfAnnouncedProjectCallList.get(i).getJUSUPRJ_PRJCALLCODE()));
			juryOfAnnouncedProjectCallList.get(i).setJUSUPRJ_STAFFCODE(staffHashMap.get(juryOfAnnouncedProjectCallList.get(i).getJUSUPRJ_STAFFCODE()));
			juryOfAnnouncedProjectCallList.get(i).setJUPSURJ_ROLECODE(roleHashMap.get(juryOfAnnouncedProjectCallList.get(i).getJUPSURJ_ROLECODE()));
		}
		List<mFaculty> listFaculty = facultyService.loadFacultyList();
		
		model.put("projectCallCode", (!"".equals(projectCallCode)) ? projectCallCode : projectCallList.get(0).getPROJCALL_CODE());
		model.put("staffList", staffList);
		model.put("listFaculty", listFaculty);
		model.put("juryRoleOfSubmittedProjecsList", juryRoleOfSubmittedProjecsList);
		model.put("juryOfAnnouncedProjectCallFormAdd", new mJuryOfAnnouncedProjectCallValidation());
		model.put("juryOfAnnouncedProjectCallList", juryOfAnnouncedProjectCallList);
					
		return "cp.listJuryOfAnnouncedProjectCall";
	 }
	 	
}
