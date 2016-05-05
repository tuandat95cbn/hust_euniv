

package vn.webapp.modules.researchmanagement.controller.cp;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.DateFormat;
import java.util.Calendar;

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
import vn.webapp.modules.researchdeclarationmanagement.model.mTopicCategory;

//For projects
import vn.webapp.modules.researchmanagement.model.Projects;
import vn.webapp.modules.researchmanagement.service.nProjectService;
import vn.webapp.modules.researchmanagement.validation.DetailCommentsSubmittedProjectsValidation;
import vn.webapp.modules.researchmanagement.validation.mCommentsOfSubmittedProjectsValidation;
import vn.webapp.modules.researchmanagement.validation.mJuryOfAnnouncedProjectCallValidation;
import vn.webapp.modules.researchmanagement.validation.mProjectCallsValidation;
import vn.webapp.modules.usermanagement.model.mStaff;
import vn.webapp.modules.researchmanagement.model.DetailCommentSubmittedProjects;
//For comments of submitted projects
import vn.webapp.modules.researchmanagement.model.mCommentsOfSubmittedProjects;
import vn.webapp.modules.researchmanagement.model.mJuryOfAnnouncedProjectCall;
import vn.webapp.modules.researchmanagement.model.mJuryRoleOfSubmittedProjects;
import vn.webapp.modules.researchmanagement.model.mProjectCalls;
import vn.webapp.modules.researchmanagement.service.mCommentsOfSubmittedProjectsService;

//For staff juries of submitted projects
import vn.webapp.modules.researchmanagement.model.mStaffJuryOfSubmittedProject;
import vn.webapp.modules.researchmanagement.service.mStaffJuryOfSubmittedProjectService;

@Controller("cpmCommentsOfSubmittedProjects")
@RequestMapping(value = { "/cp" })
public class mCommentsOfSubmittedProjectsController extends BaseWeb {
	
	@Autowired
	private mCommentsOfSubmittedProjectsService commentsOfSubmittedProjectsService;
	
	
	@Autowired
	private mStaffJuryOfSubmittedProjectService staffJuryOfSubmittedProjectService;
	
	@Autowired 
	private nProjectService projectService;
	

	static final String status = "active";
	 
	/**
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	
	@RequestMapping(value = "/review-submitted-projects", method = RequestMethod.GET)
	public String commentsOfSubmittedProjectsList(ModelMap model, HttpSession session) {
		
		// Get current user name and role
		String userCode = session.getAttribute("currentUserCode").toString();
		String userRole = session.getAttribute("currentUserRole").toString();
		System.out.println("User " + userCode);
		
		//List of staff juries of submitted projects whose reviewer is the current user
		List<mStaffJuryOfSubmittedProject> staffJuryOfSubmittedProjectList = staffJuryOfSubmittedProjectService.loadListStaffJuryOfSubmittedProjectByStaffCode(userCode);
		
		//List of projects 
		List<Projects> projectList = new ArrayList<Projects>();
		for(int i = 0; i < staffJuryOfSubmittedProjectList.size(); i++){
			projectList.addAll(projectService.loadListProjectsByCode(staffJuryOfSubmittedProjectList.get(i).getSTFJUPRJ_PRJCODE()));
		}
		System.out.println("Size of projectList : " + projectList.size());
		
		// Put data back to view
		model.put("projectList", projectList);
		 
		return "cp.listProjectsToBeReviewed";
	}
	
	/**
	 * 
	 * @param model
	 * @param projectId
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/add-comments-of-submitted-projects/{id}", method = RequestMethod.GET)
	public String addAProjectCall(ModelMap model, @PathVariable("id") int projectId, HttpSession session) {
		String userCode = session.getAttribute("currentUserCode").toString();
		String userRole = session.getAttribute("currentUserRole").toString();
		System.out.println("mCommentOfSubmittedProjectController::addAProjectCall, userCode = " + userCode + ", userRole = " + userRole + ", projectId = " + projectId);
		Projects project = projectService.loadAProjectByIdAndUserCode("ROLE_ADMIN", userCode, projectId);
		
		String sComment ="";
		mCommentsOfSubmittedProjects commentsOfSubmittedProject = commentsOfSubmittedProjectsService.loadCommentsOfSubmittedProjectByStaffCodeProjectCode(userCode, project.getPROJ_Code());
		if(commentsOfSubmittedProject != null){
			sComment = commentsOfSubmittedProject.getCOMPROJ_COMMENT();
		}
		
		System.out.println("sComment : " + sComment);
		
		model.put("project", project);
		model.put("sComment", sComment);
		model.put("commentsOfSubmittedProjectsFormAdd", new mCommentsOfSubmittedProjectsValidation());
		
		return "cp.addCommentsOfSubmittedProjects";
	}
	 
	@RequestMapping(value = "/save-comments-of-submitted-projects", method = RequestMethod.POST)
	public String saveCommentsOfSubmittedProjects( HttpServletRequest request, @Valid @ModelAttribute("commentsOfSubmittedProjectsFormAdd") mCommentsOfSubmittedProjectsValidation commentsOfSubmittedProjectsValid, BindingResult result, ModelMap model, HttpSession session) {
	 	String action = request.getParameter("action");
		System.out.println("ACTION : " + action);
		System.out.println("Length of action : " + action.length());
		if(action == "save"){
			System.out.println("XXXX");
		}
		
		if(action == "submit"){
			System.out.println("YYYY");
		}
	 	
		if (result.hasErrors()) {
			System.out.println("Errors");
			return "cp.addCommentsOfSubmittedProjects";
		} else {
			// Prepare data for inserting DB
			String userCode = session.getAttribute("currentUserCode").toString();
			
			String COMPROJ_PRJCODE = commentsOfSubmittedProjectsValid.getCOMPROJ_PRJCODE();
			String COMPROJ_COMMENT = commentsOfSubmittedProjectsValid.getCOMPROJ_COMMENT();

			//get current date time with Date()
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			String COMPROJ_UpdateDate = dateFormat.format(date); 
			
			mCommentsOfSubmittedProjects commentsOfSubmittedProject = commentsOfSubmittedProjectsService.loadCommentsOfSubmittedProjectByStaffCodeProjectCode(userCode, COMPROJ_PRJCODE);
			
			
			if(commentsOfSubmittedProject==null){
				//Insert an item
				if(action.equals("save")){
					//save
					commentsOfSubmittedProjectsService.saveCommentsOfSubmittedProjects(userCode, COMPROJ_PRJCODE, COMPROJ_COMMENT, COMPROJ_UpdateDate, false);
				}else{
					//submit
					commentsOfSubmittedProjectsService.saveCommentsOfSubmittedProjects(userCode, COMPROJ_PRJCODE, COMPROJ_COMMENT, COMPROJ_UpdateDate, true);
				}
			}else{
				
				if(commentsOfSubmittedProject.isCOMPROJ_Lock() == false){
					System.out.println("It is not yet locked");
					
					//Update comments of an item
					commentsOfSubmittedProjectsService.editCommentsOfSubmittedProjects(commentsOfSubmittedProject.getCOMPROJ_ID(), COMPROJ_COMMENT);
					
					if(action.equals("submit")){
						System.out.println("Come here");
						
						mCommentsOfSubmittedProjects temp = commentsOfSubmittedProjectsService.loadCommentsOfSubmittedProjectsById(commentsOfSubmittedProject.getCOMPROJ_ID());
						commentsOfSubmittedProjectsService.submitCommentsOfSubmittedProjects(temp);
					}
					
				}
			}
			
			//Go back to list of projects to be reviewed			
			//List of staff juries of submitted projects whose reviewer is the current user
			List<mStaffJuryOfSubmittedProject> staffJuryOfSubmittedProjectList = staffJuryOfSubmittedProjectService.loadListStaffJuryOfSubmittedProjectByStaffCode(userCode);
			
			//List of projects 
			List<Projects> projectList = new ArrayList<Projects>();
			for(int i = 0; i < staffJuryOfSubmittedProjectList.size(); i++){
				projectList.addAll(projectService.loadListProjectsByCode(staffJuryOfSubmittedProjectList.get(i).getSTFJUPRJ_PRJCODE()));
			}
			
			// Put data back to view
			model.put("projectList", projectList);
			 
			return "cp.listProjectsToBeReviewed";
		}
	}

	/**
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/details-comment-submitted-projects", method = RequestMethod.GET)
	public String detailsCommentsOfSubmittedProjectsList(ModelMap model, HttpSession session) {
		
		// Get current user name and role
		String userCode = session.getAttribute("currentUserCode").toString();
		String userRole = session.getAttribute("currentUserRole").toString();
		
		//List of staff juries of submitted projects whose reviewer is the current user
		List<mStaffJuryOfSubmittedProject> staffJuryOfSubmittedProjectList = staffJuryOfSubmittedProjectService.loadListStaffJuryOfSubmittedProjectByStaffCode(userCode);
		
		//List of projects 
		List<Projects> projectList = new ArrayList<Projects>();
		for(int i = 0; i < staffJuryOfSubmittedProjectList.size(); i++){
			projectList.addAll(projectService.loadListProjectsByCode(staffJuryOfSubmittedProjectList.get(i).getSTFJUPRJ_PRJCODE()));
		}
		
		// Put data back to view
		model.put("projectList", projectList);
		 
		return "cp.detailsCommentsSubmittedProjects";
	}
	
	/**
	 * 
	 * @param model
	 * @param projectId
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/add-details-comments-submitted-projects/{id}", method = RequestMethod.GET)
	public String addADetailCommentProject(ModelMap model, @PathVariable("id") int projectId, HttpSession session) {
		String userCode = session.getAttribute("currentUserCode").toString();
		String userRole = session.getAttribute("currentUserRole").toString(); 
		Projects project = projectService.loadAProjectByIdAndUserCode("ROLE_ADMIN", userCode, projectId);
		if(project != null)
		{
			DetailCommentSubmittedProjects detailCommentSubmittedProjects = commentsOfSubmittedProjectsService.loadDetailsCommentsOfSubmittedProjectsByProjectCode(project.getPROJ_Code());
			String sComment ="";
			mCommentsOfSubmittedProjects commentsOfSubmittedProject = commentsOfSubmittedProjectsService.loadCommentsOfSubmittedProjectByStaffCodeProjectCode(userCode, project.getPROJ_Code());
			if(commentsOfSubmittedProject != null){
				sComment = commentsOfSubmittedProject.getCOMPROJ_COMMENT();
			}
			
			model.put("detailCommentSubmittedProjects", detailCommentSubmittedProjects);
		}
		model.put("project", project);
		model.put("projectId", projectId);
		model.put("detailCommentsSubmittedProjectsFormAdd", new DetailCommentsSubmittedProjectsValidation());
		
		return "cp.addADetailCommentProject";
	}
	
	
	@RequestMapping(value = "/save-detail-comments-submitted-projects", method = RequestMethod.POST)
	public String saveDetailCommentsSubmittedProjects( HttpServletRequest request, @Valid @ModelAttribute("detailCommentsSubmittedProjectsFormAdd") DetailCommentsSubmittedProjectsValidation detailCommentsSubmittedProjectsFormAdd, 
														BindingResult result, ModelMap model, HttpSession session) {

		if (result.hasErrors()) {
			return "cp.addADetailCommentProject";
		} else {
			// Prepare data for inserting DB
			String userCode = session.getAttribute("currentUserCode").toString();
			
			int Eval_Motivation = detailCommentsSubmittedProjectsFormAdd.getCMTSUBPRJ_Eval_Motivation();
			int Eval_Innovation = detailCommentsSubmittedProjectsFormAdd.getCMTSUBPRJ_Eval_Innovation();
			int Eval_Applicability = detailCommentsSubmittedProjectsFormAdd.getCMTSUBPRJ_Eval_Applicability();
			int Eval_RearchMethodology = detailCommentsSubmittedProjectsFormAdd.getCMTSUBPRJ_Eval_RearchMethodology();
			int Eval_ResearchContent = detailCommentsSubmittedProjectsFormAdd.getCMTSUBPRJ_Eval_ResearchContent();
			int Eval_Paper = detailCommentsSubmittedProjectsFormAdd.getCMTSUBPRJ_Eval_Paper();
			int Eval_Product = detailCommentsSubmittedProjectsFormAdd.getCMTSUBPRJ_Eval_Product();
			int Eval_Patent = detailCommentsSubmittedProjectsFormAdd.getCMTSUBPRJ_Eval_Patent();
			int Eval_Graduate_Student = detailCommentsSubmittedProjectsFormAdd.getCMTSUBPRJ_Eval_Graduate_Student();
			int Eval_Young_Rearcher = detailCommentsSubmittedProjectsFormAdd.getCMTSUBPRJ_Eval_Young_Rearcher();
			int Eval_Education_Graduate = detailCommentsSubmittedProjectsFormAdd.getCMTSUBPRJ_Eval_Education_Graduate();
			int Eval_Reasonable_Budget = detailCommentsSubmittedProjectsFormAdd.getCMTSUBPRJ_Eval_Reasonable_Budget();
			String Eval_Classification = detailCommentsSubmittedProjectsFormAdd.getCMTSUBPRJ_Eval_Classification();
		    String Eval_Conclusion = detailCommentsSubmittedProjectsFormAdd.getCMTSUBPRJ_Eval_Conclusion();
			int projectId = detailCommentsSubmittedProjectsFormAdd.getProjectId();
			
			Projects project = projectService.loadProjectsById(projectId);
			if(project.getPROJ_Code() != null)
			{
				commentsOfSubmittedProjectsService.saveDetailsCommentsOfSubmittedProjects(userCode, project.getPROJ_Code(), project.getPROJ_PRJCall_Code(), Eval_Motivation, Eval_Innovation, Eval_Applicability, 
						Eval_RearchMethodology, Eval_ResearchContent, Eval_Paper, Eval_Product, Eval_Patent, Eval_Graduate_Student, Eval_Young_Rearcher, Eval_Education_Graduate, Eval_Reasonable_Budget, Eval_Classification, Eval_Conclusion, projectId);
				
				model.put("status", "Cập nhật thông tin thành công.");
			}else{
				model.put("err", "Lỗi cập nhật. Hãy thử lại với thông tin chính xác.");
			}
			return "cp.addADetailCommentProject";
		}
	}
 
}
