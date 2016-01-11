package vn.webapp.modules.researchmanagement.controller.cp;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import vn.webapp.controller.BaseWeb;
import vn.webapp.modules.researchdeclarationmanagement.model.mAcademicYear;
import vn.webapp.modules.researchdeclarationmanagement.model.mTopicCategory;
import vn.webapp.modules.researchdeclarationmanagement.service.mAcademicYearService;
import vn.webapp.modules.researchdeclarationmanagement.service.mJournalService;
import vn.webapp.modules.researchdeclarationmanagement.service.mPatentService;
import vn.webapp.modules.researchdeclarationmanagement.service.tProjectCategoryService;
import vn.webapp.modules.researchdeclarationmanagement.service.tProjectService;
import vn.webapp.modules.researchmanagement.model.Projects;
import vn.webapp.modules.researchmanagement.model.mProjectCalls;
import vn.webapp.modules.researchmanagement.model.mProjectStaffs;
import vn.webapp.modules.researchmanagement.model.mProjectStatus;
import vn.webapp.modules.researchmanagement.model.mThreads;
import vn.webapp.modules.researchmanagement.service.mProjectCallsService;
import vn.webapp.modules.researchmanagement.service.mProjectStaffsService;
import vn.webapp.modules.researchmanagement.service.mProjectStatusService;
import vn.webapp.modules.researchmanagement.service.nProjectService;
import vn.webapp.modules.researchmanagement.validation.ProjectsValidation;
import vn.webapp.modules.researchmanagement.validation.mThreadApproveValidation;
import vn.webapp.modules.researchmanagement.validation.mThreadExcellValidation;
import vn.webapp.modules.researchmanagement.validation.mThreadValidation;
import vn.webapp.modules.usermanagement.model.mDepartment;
import vn.webapp.modules.usermanagement.model.mFaculty;
import vn.webapp.modules.usermanagement.model.mStaff;
import vn.webapp.modules.usermanagement.service.mDepartmentService;
import vn.webapp.modules.usermanagement.service.mFacultyService;
import vn.webapp.modules.usermanagement.service.mStaffService;

@Controller("cpnProject")
@RequestMapping(value = { "/cp" })
public class nProjectController extends BaseWeb {
	@Autowired
	private tProjectService tProjectService;

	@Autowired
	private nProjectService threadService;

	@Autowired
	private tProjectCategoryService tProjectCategoryService;

	@Autowired
	private mJournalService journalService;

	@Autowired
	private mStaffService staffService;

	@Autowired
	private mDepartmentService departmentService;

	@Autowired
	private mPatentService patentService;

	@Autowired
	private mAcademicYearService academicYearService;

	@Autowired
	private mProjectStatusService projectStatusService;
	
	@Autowired
	private mProjectStaffsService projectStaffsService;
	
	@Autowired
	private mFacultyService facultyService;
	
	@Autowired
	private mProjectCallsService projectCallsService;

	static final String status = "active";
	
	/**
     * Size of a byte buffer to read/write file
     */
    private static final int BUFFER_SIZE = 4096;

    /**
	 * Show list all threads
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list-projects", method = RequestMethod.GET)
	public String getListProjects(ModelMap model, HttpSession session) {
		String userCode = session.getAttribute("currentUserCode").toString();
		String userRole = session.getAttribute("currentUserRole").toString();
		List<Projects> projectsList = threadService.loadProjectsListByStaff(userRole, userCode);

		model.put("projectsList", projectsList);
		model.put("projects", status);
		return "cp.projectsList";
	}
	
	/**
	 * Show list all threads
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/threads", method = RequestMethod.GET)
	public String topicsList(ModelMap model, HttpSession session) {
		String userCode = session.getAttribute("currentUserCode").toString();
		String userRole = session.getAttribute("currentUserRole").toString();
		List<mThreads> threadsList = threadService.loadThreadsListByStaff(userRole, userCode);
		// Get topic's category
		List<mTopicCategory> threadCategory = tProjectCategoryService.list();
		// Get list project statuses
		List<mProjectStatus> threadStatuses = projectStatusService.list();
		List<mAcademicYear> threadAcademicYears = academicYearService.list();
		List<mFaculty> threadFaculties = facultyService.loadFacultyList();
		List<mDepartment> threadDepartments = departmentService.loadDepartmentList();
		List<mStaff> threadStaffs = staffService.listStaffs();
				
		model.put("threadExcellForm", new mThreadExcellValidation());
		model.put("threadsList", threadsList);
		model.put("threadCategory", threadCategory);
		model.put("threadStatuses", threadStatuses);
		model.put("threadAcademicYears", threadAcademicYears);
		model.put("threadFaculties", threadFaculties);
		model.put("threadDepartments", threadDepartments);
		model.put("threadStaffs", threadStaffs);
		model.put("threads", status);
		return "cp.threads";
	}

	@RequestMapping(value = "/threads-listadd", method = RequestMethod.GET)
	public String topicsListToAdd(ModelMap model, HttpSession session) {
		String userCode = session.getAttribute("currentUserCode").toString();
		String userRole = session.getAttribute("currentUserRole").toString();
		List<mThreads> threadsList = threadService.loadThreadsListByStaff(userRole, userCode);
		// Get topic's category
		List<mTopicCategory> threadCategory = tProjectCategoryService.list();
		// Get list project statuses
		List<mProjectStatus> threadStatuses = projectStatusService.list();
		List<mAcademicYear> threadAcademicYears = academicYearService.list();
		List<mFaculty> threadFaculties = facultyService.loadFacultyList();
		List<mDepartment> threadDepartments = departmentService.loadDepartmentList();
		List<mStaff> threadStaffs = staffService.listStaffs();
				
		model.put("threadsList", threadsList);
		model.put("threadCategory", threadCategory);
		model.put("threadStatuses", threadStatuses);
		model.put("threadAcademicYears", threadAcademicYears);
		model.put("threadFaculties", threadFaculties);
		model.put("threadDepartments", threadDepartments);
		model.put("threadStaffs", threadStaffs);
		model.put("threads", status);
		return "cp.threads-listadd";
	}

	@RequestMapping(value = "/threads-approve", method = RequestMethod.GET)
	public String topicsListToApprove(ModelMap model, HttpSession session) {
		String userCode = session.getAttribute("currentUserCode").toString();
		String userRole = session.getAttribute("currentUserRole").toString();
		List<mThreads> threadsList = threadService.loadThreadsListByStaff(userRole, userCode);
		// Get topic's category
		List<mTopicCategory> threadCategory = tProjectCategoryService.list();
		// Get list project statuses
		List<mProjectStatus> threadStatuses = projectStatusService.list();
		List<mAcademicYear> threadAcademicYears = academicYearService.list();
		List<mFaculty> threadFaculties = facultyService.loadFacultyList();
		List<mDepartment> threadDepartments = departmentService.loadDepartmentList();
		List<mStaff> threadStaffs = staffService.listStaffs();
	
		List<mThreads> submittedThread = new ArrayList<mThreads>();
		for(mThreads t : threadsList){
			if(t.getPROJ_Status_Code().equals("SUBMIT"))
				submittedThread.add(t);
		}
		for(mThreads t : submittedThread){
			System.out.println("ThreadController::topicListToApprove, submitted thread " + t.getPROJ_Name());
		}
		
		List<mProjectStatus> submittedThreadStatuses = new ArrayList<mProjectStatus>();
		for(mProjectStatus ps : threadStatuses){
			if(ps.getPROJSTAT_Code().equals("SUBMIT"))
				submittedThreadStatuses.add(ps);
		}
		//model.put("threadsList", threadsList);
		model.put("threadsList",submittedThread);
		model.put("threadCategory", threadCategory);
		//model.put("threadStatuses", threadStatuses);
		model.put("threadStatuses", submittedThreadStatuses);
		model.put("threadAcademicYears", threadAcademicYears);
		model.put("threadFaculties", threadFaculties);
		model.put("threadDepartments", threadDepartments);
		model.put("threadStaffs", threadStaffs);
		model.put("threads", status);
		return "cp.threads-approve";
	}

	@RequestMapping(value = "/threads-evaluate", method = RequestMethod.GET)
	public String topicsListToEvaluate(ModelMap model, HttpSession session) {
		String userCode = session.getAttribute("currentUserCode").toString();
		String userRole = session.getAttribute("currentUserRole").toString();
		List<mThreads> threadsList = threadService.loadThreadsListByStaff(userRole, userCode);
		// Get topic's category
		List<mTopicCategory> threadCategory = tProjectCategoryService.list();
		// Get list project statuses
		List<mProjectStatus> threadStatuses = projectStatusService.list();
		List<mAcademicYear> threadAcademicYears = academicYearService.list();
		List<mFaculty> threadFaculties = facultyService.loadFacultyList();
		List<mDepartment> threadDepartments = departmentService.loadDepartmentList();
		List<mStaff> threadStaffs = staffService.listStaffs();
	
		List<mThreads> submittedThread = new ArrayList<mThreads>();
		for(mThreads t : threadsList){
			if(t.getPROJ_Status_Code().equals("APPROVED"))
				submittedThread.add(t);
		}
		for(mThreads t : submittedThread){
			System.out.println("ThreadController::topicListToApprove, submitted thread " + t.getPROJ_Name());
		}
		
		List<mProjectStatus> submittedThreadStatuses = new ArrayList<mProjectStatus>();
		for(mProjectStatus ps : threadStatuses){
			if(ps.getPROJSTAT_Code().equals("APPROVED"))
				submittedThreadStatuses.add(ps);
		}
		//model.put("threadsList", threadsList);
		model.put("threadsList",submittedThread);
		model.put("threadCategory", threadCategory);
		//model.put("threadStatuses", threadStatuses);
		model.put("threadStatuses", submittedThreadStatuses);
		model.put("threadAcademicYears", threadAcademicYears);
		model.put("threadFaculties", threadFaculties);
		model.put("threadDepartments", threadDepartments);
		model.put("threadStaffs", threadStaffs);
		model.put("threads", status);
		return "cp.threads-evaluate";
	}

	/**
	 * Adding a thread
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add-a-thread", method = RequestMethod.GET)
	public String addAThread(ModelMap model, HttpSession session) {
		// Get current user name and role
		String userCode = session.getAttribute("currentUserCode").toString();
		String userRole = session.getAttribute("currentUserRole").toString();

		// Get topic's category
		List<mTopicCategory> topicCategory = tProjectCategoryService.list();
		// Get list reportingYear
		List<mAcademicYear> threadReportingAcademicDateList = academicYearService.list();
		// Get list project statuses
		List<mProjectStatus> projectStatuses = projectStatusService.list();
		
		// Get list faculty
		List<mFaculty> listFaculty = facultyService.loadFacultyList();

		// Put data back to view
		model.put("threadReportingAcademicDate", threadReportingAcademicDateList);
		model.put("listFaculty", listFaculty);
		model.put("threadCategory", topicCategory);
		model.put("projectStatuses", projectStatuses);
		model.put("threadFormAdd", new mThreadValidation());
		model.put("thread", status);
		return "cp.addAThread";
	}
	
	/**
	 * Adding a project
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add-a-newproject", method = RequestMethod.GET)
	public String addAProject(ModelMap model, HttpSession session) {		
		// Get list of project calls
		List<mProjectCalls> projectCallsList = projectCallsService.loadProjectCallsList();
			
		// Put data back to view
		model.put("projectCallsList", projectCallsList);
		model.put("projectsAddForm", new ProjectsValidation());
		model.put("projects", status);
		return "cp.addAProject";
	}
	
	@RequestMapping(value = "save-a-project", method = RequestMethod.POST)
	public String saveAProject( HttpServletRequest request, @Valid @ModelAttribute("projectsAddForm") ProjectsValidation projectValid, BindingResult result, Map model, HttpSession session) {
		// Get list of project calls
		List<mProjectCalls> projectCallsList = projectCallsService.loadProjectCallsList();
					
		// Put data back to view
		model.put("projectCallsList", projectCallsList);
		model.put("projects", status);
		if (result.hasErrors()) {
			return "cp.addAProject";
		} else {
			// Prepare data for inserting DB
			String userRole 			= session.getAttribute("currentUserRole").toString();
			String userCode 			= session.getAttribute("currentUserCode").toString();
			String projectName 			= projectValid.getProjectName();
			String projectCallCode 		= projectValid.getProjectCallCode();
			String projectContent 		= projectValid.getProjectContent();
			String projectMotivation 	= projectValid.getProjectMotivation();
			String projectResult 		= projectValid.getProjectResult();
			int projectBudget 			= projectValid.getProjectBudget();
			String projectCode 			= "PROJECT-CODE-" + projectCallCode;

			int i_InsertAProject = threadService.saveAProject(userRole, userCode, projectCallCode, projectName, projectContent, projectMotivation, projectResult, projectBudget, projectCode);
			if (i_InsertAProject > 0) {
				model.put("status", "Thêm mới thành công!");
			}
			//return "cp.addAThread";
			return "redirect:" + this.baseUrl + "/cp/threads-listadd.html";
		}
	}

	/**
	 * Save a thread
	 * 
	 * @param threadValid
	 * @param result
	 * @param model
	 * @param session
	 * @return String
	 */
	@RequestMapping(value = "save-a-thread", method = RequestMethod.POST)
	public String saveTheThread( HttpServletRequest request, @Valid @ModelAttribute("threadFormAdd") mThreadValidation threadValid, BindingResult result, Map model, HttpSession session) {
		// Get topic's category
		List<mTopicCategory> topicCategoryList = tProjectCategoryService.list();
		// Get list reportingYear
		List<mAcademicYear> topicReportingAcademicDateList = academicYearService.list();
		// Get list project statuses
		List<mProjectStatus> projectStatuses = projectStatusService.list();

		// Get list faculty
		List<mFaculty> listFaculty = facultyService.loadFacultyList();
				
		// Put data back to view
		model.put("threadReportingAcademicDate", topicReportingAcademicDateList);
		model.put("listFaculty", listFaculty);
		model.put("threadCategory", topicCategoryList);
		model.put("projectStatuses", projectStatuses);
		model.put("thread", status);
		if (result.hasErrors()) {
			return "cp.addAThread";
		} else {
			// Prepare data for inserting DB
			String userCode 			= session.getAttribute("currentUserCode").toString();
			String threadName 			= threadValid.getThreadName();
			String threadCategory 		= threadValid.getThreadCatCode();
			String threadContent 		= threadValid.getThreadContent();
			String threadStartDate 		= threadValid.getThreadStartDate();
			String threadEndDate 		= threadValid.getThreadEndDate();
			String threadMotivation 	= threadValid.getThreadMotivation();
			String threadReportingDate 	= threadValid.getThreadReportingAcademicDate();
			String threadResult 		= threadValid.getThreadResult();
			String threadStatus 		= threadValid.getThreadStatus();
			String joiningStaffs 		= threadValid.getStaff();
			List<String> listStaffs 	= threadValid.getStaffsRole();
			List<String> listStaffRoles = threadValid.getRoleList();
			int threadBudget 			= threadValid.getThreadBudget();
			String threadSourceUploadFileSrc = "";
			String threadCode 			= "thread";

			// Creating Date in java with today's date.
			Date currentDate = new Date();
			// change date into string yyyyMMdd format example "20110914"
			SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat("HHmmssddMMyyyy");
			String sCurrentDate = dateformatyyyyMMdd.format(currentDate);

			threadCode = threadCode + userCode.concat(sCurrentDate);
			/**
			 * Uploading file
			 */
			MultipartFile threadSourceUploadFile = threadValid.getThreadSourceFile();
			String fileName = threadSourceUploadFile.getOriginalFilename();
			if (fileName != "") {
				try {
					byte[] bytes = threadSourceUploadFile.getBytes();
					String path = request.getServletContext().getRealPath("uploads");
					File dir = new File(path + "/threads");
					if (!dir.exists()) {
						dir.mkdirs();
					}

					// Create a file
					String currentUserName = session.getAttribute("currentUserName").toString();
					fileName = currentUserName + "_" + sCurrentDate + "_" + fileName;
					File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);
					if (serverFile != null) {
						threadSourceUploadFileSrc = dir.getAbsolutePath() + File.separator + fileName;
					}
					// Save data into file
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();
				} catch (Exception e) {
					model.put("status", "You failed to upload " + fileName);
				}
			}

			int i_InsertATopic = threadService.saveAThread( userCode,  threadName,  threadCategory,  threadContent,  threadStartDate, 
					 threadEndDate,  threadMotivation,  threadReportingDate,  threadResult,  threadStatus, 
					 threadBudget,  threadSourceUploadFileSrc,  threadCode,  listStaffs, listStaffRoles);
			if (i_InsertATopic > 0) {
				model.put("status", "Successfully saved a topic");
			}
			//return "cp.addAThread";
			return "redirect:" + this.baseUrl + "/cp/threads-listadd.html";
		}
	}

	/**
	 * 
	 * @param model
	 * @param threadId
	 * @param session
	 * @return
	 */
	@RequestMapping("/threaddetail/{id}")
	public String editAThread(ModelMap model, @PathVariable("id") int threadId, HttpSession session) {

		String userRole = session.getAttribute("currentUserRole").toString();
		String userCode = session.getAttribute("currentUserCode").toString();
		mThreads thread = threadService.loadAThreadByIdAndUserCode(userRole,userCode, threadId);
		// Get list reportingYear
		List<mAcademicYear> threadReportingAcademicDateList = academicYearService.list();
		// Get list statues
		List<mProjectStatus> projectStatuses = projectStatusService.list();
		// Get list faculty
		List<mFaculty> listFaculty = facultyService.loadFacultyList();
		
		List<mProjectStaffs> listProjectStaffs = projectStaffsService.loadAProjectStaffByProjectCode(thread.getPROJ_Code());
		List<mStaff> listStaffs = new ArrayList<mStaff>();
		if(listProjectStaffs != null){
			for(mProjectStaffs projectStaff : listProjectStaffs)
			{
				mStaff staff = staffService.loadStaffByUserCode(projectStaff.getPROJSTAFF_Staff_Code());
				listStaffs.add(staff);
			}
		}
		
		// Put data back to view
		model.put("threadReportingAcademicDate",threadReportingAcademicDateList);
		model.put("listFaculty", listFaculty);
		model.put("projectStatuses", projectStatuses);
		model.put("listStaffs", listStaffs);
		model.put("thread", status);
		if (thread != null) {
			// Get topic's category
			List<mTopicCategory> topicCategoryList = tProjectCategoryService.list();

			// Put journal list and topic category to view
			model.put("threadCategory", topicCategoryList);
			model.put("threadFormEdit", new mThreadValidation());
			model.put("threadId", threadId);
			model.put("threadCatCode", thread.getPROJ_ProjCat_Code());
			model.put("reportingDate", thread.getPROJ_AcaYear_Code());
			model.put("threadCode", thread.getPROJ_Code());
			model.put("threadName", thread.getPROJ_Name());
			model.put("threadEndDate", thread.getPROJ_EndDate());
			model.put("threadMotivation", thread.getPROJ_Motivation());
			model.put("threadReportingDate", thread.getPROJ_AcaYear_Code());
			model.put("threadStartDate", thread.getPROJ_StartDate());
			model.put("threadStatus", thread.getPROJ_Status_Code());
			model.put("threadBudget", thread.getPROJ_TotalBudget());
			model.put("threadContent", thread.getPROJ_Content());
			
			return "cp.editAThread";
		}
		return "cp.notFound404";
	}
	
	/**
	 * 
	 * @param model
	 * @param threadId
	 * @param session
	 * @return
	 */
	@RequestMapping("/aprojectdetail/{id}")
	public String editAProject(ModelMap model, @PathVariable("id") int projectId, HttpSession session) {

		String userRole = session.getAttribute("currentUserRole").toString();
		String userCode = session.getAttribute("currentUserCode").toString();
		Projects project = threadService.loadAProjectByIdAndUserCode(userRole,userCode, projectId);
		
		// Get list of project calls
	    List<mProjectCalls> projectCallsList = projectCallsService.loadProjectCallsList();
							
     	 // Put data back to view
		 model.put("projectCallsList", projectCallsList);
		model.put("projects", status);
		if (project != null) {
			// Put journal list and topic category to view
			model.put("projectEdit", project);
			model.put("projectFormEdit", new ProjectsValidation());
			model.put("projectId", projectId);
			return "cp.editAProject";
		}
		return "cp.notFound404";
	}
	
	/**
	 * 
	 * @param model
	 * @param threadId
	 * @param session
	 * @return
	 */
	@RequestMapping("/threadshow/{id}")
	public String showAThread(ModelMap model, @PathVariable("id") int threadId, HttpSession session) {

		String userRole = session.getAttribute("currentUserRole").toString();
		String userCode = session.getAttribute("currentUserCode").toString();
		mThreads thread = threadService.loadAThreadByIdAndUserCode(userRole,userCode, threadId);
		// Get list reportingYear
		List<mAcademicYear> threadReportingAcademicDateList = academicYearService.list();
		// Get list faculty
		List<mFaculty> listFaculty = facultyService.loadFacultyList();
		
		List<mProjectStaffs> listProjectStaffs = projectStaffsService.loadAProjectStaffByProjectCode(thread.getPROJ_Code());
		List<mStaff> listStaffs = new ArrayList<mStaff>();
		if(listProjectStaffs != null){
			for(mProjectStaffs projectStaff : listProjectStaffs)
			{
				mStaff staff = staffService.loadStaffByUserCode(projectStaff.getPROJSTAFF_Staff_Code());
				listStaffs.add(staff);
			}
		}
		
		// Put data back to view
		model.put("threadReportingAcademicDate",threadReportingAcademicDateList);
		model.put("listFaculty", listFaculty);
		model.put("listStaffs", listStaffs);
		model.put("thread", status);
		if (thread != null) {
			model.put("threadFormEdit", new mThreadValidation());
			model.put("threadId", threadId);
			model.put("threadCatName", tProjectCategoryService.getTopicCategoryByCode(thread.getPROJ_ProjCat_Code()).getPROJCAT_Name());
			model.put("threadAcaYearCode", thread.getPROJ_AcaYear_Code());
			model.put("threadCode", thread.getPROJ_Code());
			model.put("threadName", thread.getPROJ_Name());
			model.put("threadEndDate", thread.getPROJ_EndDate());
			model.put("threadMotivation", thread.getPROJ_Motivation());
			model.put("threadReportingDate", thread.getPROJ_AcaYear_Code());
			model.put("threadStartDate", thread.getPROJ_StartDate());
			model.put("threadStatus", projectStatusService.loadAProjectStatusByProjectCode(thread.getPROJ_Status_Code()).getPROJSTAT_Description());
			model.put("threadBudget", thread.getPROJ_TotalBudget());
			model.put("threadContent", thread.getPROJ_Content());
			
			return "cp.showAThread";
		}
		return "cp.notFound404";
	}

	@RequestMapping("/threadshowtoupdate/{id}")
	public String showAThreadToUpdate(ModelMap model, @PathVariable("id") int threadId, HttpSession session) {

		String userRole = session.getAttribute("currentUserRole").toString();
		String userCode = session.getAttribute("currentUserCode").toString();
		mThreads thread = threadService.loadAThreadByIdAndUserCode(userRole,userCode, threadId);
		// Get list reportingYear
		List<mAcademicYear> threadReportingAcademicDateList = academicYearService.list();
		// Get list faculty
		List<mFaculty> listFaculty = facultyService.loadFacultyList();
		
		List<mProjectStaffs> listProjectStaffs = projectStaffsService.loadAProjectStaffByProjectCode(thread.getPROJ_Code());
		List<mStaff> listStaffs = new ArrayList<mStaff>();
		if(listProjectStaffs != null){
			for(mProjectStaffs projectStaff : listProjectStaffs)
			{
				mStaff staff = staffService.loadStaffByUserCode(projectStaff.getPROJSTAFF_Staff_Code());
				listStaffs.add(staff);
			}
		}
		
		// Put data back to view
		model.put("threadReportingAcademicDate",threadReportingAcademicDateList);
		model.put("listFaculty", listFaculty);
		model.put("listStaffs", listStaffs);
		model.put("thread", status);
		if (thread != null) {
			model.put("threadFormEdit", new mThreadValidation());
			model.put("threadId", threadId);
			model.put("threadCatName", tProjectCategoryService.getTopicCategoryByCode(thread.getPROJ_ProjCat_Code()).getPROJCAT_Name());
			model.put("threadAcaYearCode", thread.getPROJ_AcaYear_Code());
			model.put("threadCode", thread.getPROJ_Code());
			model.put("threadName", thread.getPROJ_Name());
			model.put("threadEndDate", thread.getPROJ_EndDate());
			model.put("threadMotivation", thread.getPROJ_Motivation());
			model.put("threadReportingDate", thread.getPROJ_AcaYear_Code());
			model.put("threadStartDate", thread.getPROJ_StartDate());
			model.put("threadStatus", projectStatusService.loadAProjectStatusByProjectCode(thread.getPROJ_Status_Code()).getPROJSTAT_Description());
			model.put("threadBudget", thread.getPROJ_TotalBudget());
			model.put("threadContent", thread.getPROJ_Content());
			
			return "cp.showAThreadToUpdate";
		}
		return "cp.notFound404";
	}
	
	@RequestMapping("/threadshowtoapprove/{id}")
	public String showAThreadToApprove(ModelMap model, @PathVariable("id") int threadId, HttpSession session) {

		String userRole = session.getAttribute("currentUserRole").toString();
		String userCode = session.getAttribute("currentUserCode").toString();
		mThreads thread = threadService.loadAThreadByIdAndUserCode(userRole,userCode, threadId);
		// Get list reportingYear
		List<mAcademicYear> threadReportingAcademicDateList = academicYearService.list();
		// Get list faculty
		List<mFaculty> listFaculty = facultyService.loadFacultyList();
		
		List<mProjectStaffs> listProjectStaffs = projectStaffsService.loadAProjectStaffByProjectCode(thread.getPROJ_Code());
		List<mStaff> listStaffs = new ArrayList<mStaff>();
		if(listProjectStaffs != null){
			for(mProjectStaffs projectStaff : listProjectStaffs)
			{
				mStaff staff = staffService.loadStaffByUserCode(projectStaff.getPROJSTAFF_Staff_Code());
				listStaffs.add(staff);
			}
		}
		
		List<mProjectStatus> projectStatus = projectStatusService.list();
		List<mProjectStatus> selectedStatus = new ArrayList<mProjectStatus>();
		for(mProjectStatus ps : projectStatus){
			if(ps.getPROJSTAT_Code().equals("APPROVED") || ps.getPROJSTAT_Code().equals("REJECT"))
				selectedStatus.add(ps);
		}
		for(mProjectStatus ps : selectedStatus){
			System.out.println("ThreadController::showAThreadToApprove, selected ps = " + ps.getPROJSTAT_Code());
		}
		
		// Put data back to view
		model.put("threadReportingAcademicDate",threadReportingAcademicDateList);
		model.put("listFaculty", listFaculty);
		model.put("listStaffs", listStaffs);
		model.put("thread", status);
		if (thread != null) {
			model.put("projectFormApprove", new mThreadValidation());
			model.put("threadId", threadId);
			model.put("threadCatName", tProjectCategoryService.getTopicCategoryByCode(thread.getPROJ_ProjCat_Code()).getPROJCAT_Name());
			model.put("threadAcaYearCode", thread.getPROJ_AcaYear_Code());
			model.put("threadCode", thread.getPROJ_Code());
			model.put("threadName", thread.getPROJ_Name());
			model.put("threadEndDate", thread.getPROJ_EndDate());
			model.put("threadMotivation", thread.getPROJ_Motivation());
			model.put("threadReportingDate", thread.getPROJ_AcaYear_Code());
			model.put("threadStartDate", thread.getPROJ_StartDate());
			//model.put("threadStatus", projectStatusService.loadAProjectStatusByProjectCode(thread.getPROJ_Status_Code()).getPROJSTAT_Description());
			model.put("projectStatus", selectedStatus);
			model.put("threadBudget", thread.getPROJ_TotalBudget());
			model.put("threadContent", thread.getPROJ_Content());
			
			System.out.println("ThreadController::showAThreadToApprove, return cp.showAThreadToApprove");
			return "cp.showAThreadToApprove";
		}
		return "cp.notFound404";
	}

	@RequestMapping("/threadshowtoevaluate/{id}")
	public String showAThreadToEvaluate(ModelMap model, @PathVariable("id") int threadId, HttpSession session) {

		String userRole = session.getAttribute("currentUserRole").toString();
		String userCode = session.getAttribute("currentUserCode").toString();
		mThreads thread = threadService.loadAThreadByIdAndUserCode(userRole,userCode, threadId);
		// Get list reportingYear
		List<mAcademicYear> threadReportingAcademicDateList = academicYearService.list();
		// Get list faculty
		List<mFaculty> listFaculty = facultyService.loadFacultyList();
		
		List<mProjectStaffs> listProjectStaffs = projectStaffsService.loadAProjectStaffByProjectCode(thread.getPROJ_Code());
		List<mStaff> listStaffs = new ArrayList<mStaff>();
		if(listProjectStaffs != null){
			for(mProjectStaffs projectStaff : listProjectStaffs)
			{
				mStaff staff = staffService.loadStaffByUserCode(projectStaff.getPROJSTAFF_Staff_Code());
				listStaffs.add(staff);
			}
		}
		
		List<mProjectStatus> projectStatus = projectStatusService.list();
		List<mProjectStatus> selectedStatus = new ArrayList<mProjectStatus>();
		for(mProjectStatus ps : projectStatus){
			if(ps.getPROJSTAT_Code().equals("SUCCESS") || ps.getPROJSTAT_Code().equals("FAILED"))
				selectedStatus.add(ps);
		}
		
		// Put data back to view
		model.put("threadReportingAcademicDate",threadReportingAcademicDateList);
		model.put("listFaculty", listFaculty);
		model.put("listStaffs", listStaffs);
		model.put("thread", status);
		if (thread != null) {
			model.put("projectFormApprove", new mThreadValidation());
			model.put("threadId", threadId);
			model.put("threadCatName", tProjectCategoryService.getTopicCategoryByCode(thread.getPROJ_ProjCat_Code()).getPROJCAT_Name());
			model.put("threadAcaYearCode", thread.getPROJ_AcaYear_Code());
			model.put("threadCode", thread.getPROJ_Code());
			model.put("threadName", thread.getPROJ_Name());
			model.put("threadEndDate", thread.getPROJ_EndDate());
			model.put("threadMotivation", thread.getPROJ_Motivation());
			model.put("threadReportingDate", thread.getPROJ_AcaYear_Code());
			model.put("threadStartDate", thread.getPROJ_StartDate());
			//model.put("threadStatus", projectStatusService.loadAProjectStatusByProjectCode(thread.getPROJ_Status_Code()).getPROJSTAT_Description());
			model.put("projectStatus", selectedStatus);
			model.put("threadBudget", thread.getPROJ_TotalBudget());
			model.put("threadContent", thread.getPROJ_Content());
			
			System.out.println("ThreadController::showAThreadToApprove, return cp.showAThreadToApprove");
			return "cp.showAThreadToEvaluate";
		}
		return "cp.notFound404";
	}
	
	 /**
	 * Editing a project
	 * @param model
	 * @return
	 */
	 @RequestMapping(value = "/edit-a-project", method = RequestMethod.POST)
	 public String updateAProject(HttpServletRequest request, @Valid @ModelAttribute("projectFormEdit") ProjectsValidation projectFormEdit, BindingResult result, Map model, HttpSession session) {

		 // Get list of project calls
		 List<mProjectCalls> projectCallsList = projectCallsService.loadProjectCallsList();
					
		 // Put data back to view
		 model.put("projectCallsList", projectCallsList);
		 model.put("projects", status);

		 if (result.hasErrors()) {
			 return "cp.editAProject";
		 }else
		 {
				// Prepare data for inserting DB
				String userRole 			= session.getAttribute("currentUserRole").toString();
				String userCode 			= session.getAttribute("currentUserCode").toString();
				String projectName 			= projectFormEdit.getProjectName();
				String projectCallCode 		= projectFormEdit.getProjectCallCode();
				String projectContent 		= projectFormEdit.getProjectContent();
				String projectMotivation 	= projectFormEdit.getProjectMotivation();
				String projectResult 		= projectFormEdit.getProjectResult();
				int projectBudget 			= projectFormEdit.getProjectBudget();
				int projectEditId 			= projectFormEdit.getProjectId();
				String projectCode 			= projectCallCode + projectEditId;

			 threadService.editAProject(projectEditId, userRole, userCode, projectCallCode, projectName, projectContent, projectMotivation, projectResult, projectBudget, projectCode);
			 model.put("status", "Chỉnh sửa thành công!");
			 return "redirect:" + this.baseUrl + "/cp/list-projects.html";
		 }
	 }

	 	/**
		 * Editing a thread
		 * @param model
		 * @return
		 */
		 @RequestMapping(value = "/edit-a-thread", method = RequestMethod.POST)
		 public String updateAThead(HttpServletRequest request, @Valid @ModelAttribute("projectFormEdit") mThreadValidation threadFormEdit, BindingResult result, Map model, HttpSession session) {
		
			 // Get topic's category
			 List<mTopicCategory> topicCategoryList = tProjectCategoryService.list();
			 // Get list reportingYear
			 List<mAcademicYear> threadReportingAcademicDateList = academicYearService.list();
			 // Get list statues
			 List<mProjectStatus> projectStatuses = projectStatusService.list();
			 // Get list faculty
			 List<mFaculty> listFaculty = facultyService.loadFacultyList();
			 
			 if(!threadFormEdit.getThreadCode().equals("")){
				 List<mProjectStaffs> listProjectStaffs = projectStaffsService.loadAProjectStaffByProjectCode(threadFormEdit.getThreadCode());
				 List<mStaff> listStaffs = new ArrayList<mStaff>();
				 if(listProjectStaffs != null){
					for(mProjectStaffs projectStaff : listProjectStaffs)
					{
						mStaff staff = staffService.loadStaffByUserCode(projectStaff.getPROJSTAFF_Staff_Code());
						listStaffs.add(staff);
					}
				 }
				 model.put("listStaffs", listStaffs);
			 }
				
			 // Put data back to view
			 model.put("threadReportingAcademicDate", threadReportingAcademicDateList);
			 model.put("listFaculty", listFaculty);
			 model.put("threadCategory", topicCategoryList);
			 model.put("projectStatuses", projectStatuses);
			 model.put("thread", status);
			 if (result.hasErrors()) {
				 return "cp.editAThread";
			 }else
			 {
				 String userRole = session.getAttribute("currentUserRole").toString();
				 String userCode = session.getAttribute("currentUserCode").toString();
				
				 // Prepare data for inserting DB
				 String threadName 					= threadFormEdit.getThreadName();
				 String threadCatCode 				= threadFormEdit.getThreadCatCode();
				 String threadEndDate 				= threadFormEdit.getThreadEndDate();
				 String threadMotivation 			= threadFormEdit.getThreadMotivation();
				 String threadReportingDate 		= threadFormEdit.getThreadReportingAcademicDate();
				 String threadResult 				= threadFormEdit.getThreadResult();
				 String threadStartDate 			= threadFormEdit.getThreadStartDate();
				 String threadStatus 				= threadFormEdit.getThreadStatus();
				 int threadBudget 					= threadFormEdit.getThreadBudget();
				 String threadCode 					= threadFormEdit.getThreadCode();
				 String threadContent				= threadFormEdit.getThreadContent();
				 List<String> listStaffs 			= threadFormEdit.getStaffsRole();
				 List<String> listStaffRoles 		= threadFormEdit.getRoleList();
				 String joiningStaffs 				= threadFormEdit.getStaff();
				 String threadSourceUploadFileSrc 	= "";
				 int threadId						= threadFormEdit.getThreadId();

				 /**
				 * Uploading file
				 */
				 MultipartFile threadSourceUploadFile = threadFormEdit.getThreadSourceFile();
				 String fileName = threadSourceUploadFile.getOriginalFilename();
				 if (fileName != "") {
					try {
						// Creating Date in java with today's date.
						Date currentDate = new Date();
						// change date into string yyyyMMdd format example "20110914"
						SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat("HHmmssddMMyyyy");
						String sCurrentDate = dateformatyyyyMMdd.format(currentDate);
						 
						byte[] bytes = threadSourceUploadFile.getBytes();
						String path = request.getServletContext().getRealPath("uploads");
						File dir = new File(path + "/threads");
						if (!dir.exists()) {
							dir.mkdirs();
						}

						// Create a file
						String currentUserName = session.getAttribute("currentUserName").toString();
						fileName = currentUserName + "_" + sCurrentDate + "_" + fileName;
						File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);
						if (serverFile != null) {
							threadSourceUploadFileSrc = dir.getAbsolutePath() + File.separator + fileName;
						}
						// Save data into file
						BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
						stream.write(bytes);
						stream.close();
					} catch (Exception e) {
						model.put("status", "You failed to upload " + fileName);
					}
				 }
					
				 threadService.editAThread(userRole, userCode, threadName, threadCatCode, threadContent, 
						 					threadStartDate, threadEndDate, threadMotivation, threadReportingDate, threadResult, 
						 					threadStatus, threadBudget, threadSourceUploadFileSrc, threadCode, threadId, listStaffs, listStaffRoles);
				 model.put("status", "Successfully edited project");
				 //return "cp.editAThread";
				 return "redirect:" + this.baseUrl + "/cp/threads-listadd.html";
			 }
		 }
		 
	 /**
	  * 
	  * @param request
	  * @param projectFormApprove
	  * @param result
	  * @param model
	  * @param session
	  * @return
	  */
	 @RequestMapping(value = "/approve-a-project", method = RequestMethod.POST)
	 public String approveAProject(HttpServletRequest request, @Valid @ModelAttribute("projectFormApprove") 
	 mThreadApproveValidation projectFormApprove, BindingResult result, Map model, HttpSession session) {
	
		 
		 if(!projectFormApprove.getThreadCode().equals("")){
			 List<mProjectStaffs> listProjectStaffs = projectStaffsService.loadAProjectStaffByProjectCode(projectFormApprove.getThreadCode());
			 List<mStaff> listStaffs = new ArrayList<mStaff>();
			 if(listProjectStaffs != null){
				for(mProjectStaffs projectStaff : listProjectStaffs)
				{
					mStaff staff = staffService.loadStaffByUserCode(projectStaff.getPROJSTAFF_Staff_Code());
					listStaffs.add(staff);
				}
			 }
			 model.put("listStaffs", listStaffs);
		 }
			
		 // Put data back to view
		 model.put("thread", status);
		 if (result.hasErrors()) {
			 return "cp.editAThread";
		 }else
		 {
			 String userRole = session.getAttribute("currentUserRole").toString();
			 String userCode = session.getAttribute("currentUserCode").toString();
			
			 // Prepare data for inserting DB
			 String threadStatus 				= projectFormApprove.getThreadStatus();
			 int threadId						= projectFormApprove.getThreadId();


			 
			 threadService.editStatusThread(userRole, userCode, threadId, threadStatus);
			 
			 model.put("status", "Successfully edited project");
			 //return editAThread((ModelMap)model, threadId, session);
			 //return "cp.editAThread";
			 return "redirect:" + this.baseUrl + "/cp/threads-approve.html";
		 }
	 }
	 


	 @RequestMapping(value = "/evaluate-a-project", method = RequestMethod.POST)
	 public String evaluateAProject(HttpServletRequest request, @Valid @ModelAttribute("projectFormApprove") 
	 mThreadApproveValidation projectFormApprove, BindingResult result, Map model, HttpSession session) {
		 //return approveAProject(request, projectFormApprove, result, model, session);
		 if(!projectFormApprove.getThreadCode().equals("")){
			 List<mProjectStaffs> listProjectStaffs = projectStaffsService.loadAProjectStaffByProjectCode(projectFormApprove.getThreadCode());
			 List<mStaff> listStaffs = new ArrayList<mStaff>();
			 if(listProjectStaffs != null){
				for(mProjectStaffs projectStaff : listProjectStaffs)
				{
					mStaff staff = staffService.loadStaffByUserCode(projectStaff.getPROJSTAFF_Staff_Code());
					listStaffs.add(staff);
				}
			 }
			 model.put("listStaffs", listStaffs);
		 }
			
		 // Put data back to view
		 model.put("thread", status);
		 if (result.hasErrors()) {
			 return "cp.editAThread";
		 }else
		 {
			 String userRole = session.getAttribute("currentUserRole").toString();
			 String userCode = session.getAttribute("currentUserCode").toString();
			
			 // Prepare data for inserting DB
			 String threadStatus 				= projectFormApprove.getThreadStatus();
			 int threadId						= projectFormApprove.getThreadId();


			 
			 threadService.editStatusThread(userRole, userCode, threadId, threadStatus);
			 
			 model.put("status", "Successfully edited project");
			 //return editAThread((ModelMap)model, threadId, session);
			 //return "cp.editAThread";
			 return "redirect:" + this.baseUrl + "/cp/threads-evaluate.html";
		 }
	 }
	 
	 /**
	 * Remove a thread
	 * @param model
	 * @return
	 */
	 @RequestMapping(value = "/remove-a-thread/{id}", method = RequestMethod.GET)
	 public String removeAThread(ModelMap model, @PathVariable("id") int threadId, HttpSession session) {
		 String userCode = session.getAttribute("currentUserCode").toString();
		 String userRole = session.getAttribute("currentUserRole").toString();
		 mThreads thread = threadService.loadAThreadByIdAndUserCode(userRole, userCode, threadId);
		 model.put("threads", status);
		 if(thread != null){
			 threadService.removeAThread(threadId);
			 List<mThreads> threadsList = threadService.loadThreadsListByStaff(userRole, userCode);
			 model.put("threadsList", threadsList);
			 return "cp.threads";
		 }
		 return "cp.notFound404";
	 }
	 
	 /**
		 * Remove a project
		 * @param model
		 * @return
		 */
		 @RequestMapping(value = "/remove-a-project/{id}", method = RequestMethod.GET)
		 public String removeAProject(ModelMap model, @PathVariable("id") int projectId, HttpSession session) {
			 String userCode = session.getAttribute("currentUserCode").toString();
			 String userRole = session.getAttribute("currentUserRole").toString();
			 Projects project = threadService.loadAProjectByIdAndUserCode(userRole, userCode, projectId);
			 model.put("projects", status);
			 if(project != null){
				 threadService.removeAProject(projectId);
				 List<Projects> projectsList = threadService.loadProjectsListByStaff(userRole, userCode);
				 model.put("projectsList", projectsList);
				 return "cp.projectsList";
			 }
			 return "cp.notFound404";
		 }
	 
	 /**
	    * Download a file source
	    * @param model
	    * @return
	    */
	   @RequestMapping(value = "/download-thread/{id}", method = RequestMethod.GET)
	   public void downloadPaper(ModelMap model, @PathVariable("id") int threadId, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
		   String userCode = session.getAttribute("currentUserCode").toString();
		   String userRole = session.getAttribute("currentUserRole").toString();
		   model.put("threads", status);
		   mThreads thread = threadService.loadAThreadByIdAndUserCode(userRole, userCode, threadId);
		   if(thread != null)if(thread.getPROJ_SourceFile() != null){
			   ServletContext context = request.getServletContext();
			   
			   File downloadFile = new File(thread.getPROJ_SourceFile());
			   if(downloadFile.exists()){
			       FileInputStream inputStream = new FileInputStream(downloadFile);
			       
			       String mimeType = context.getMimeType(thread.getPROJ_SourceFile());
			        if (mimeType == null) {
			            // set to binary type if MIME mapping not found
			            mimeType = "application/octet-stream";
			        }
			        
			        // set content attributes for the response
			        response.setContentType(mimeType);
			        response.setContentLength((int) downloadFile.length());
			        
			        // set headers for the response
			        String headerKey = "Content-Disposition";
			        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
			        response.setHeader(headerKey, headerValue);
			        
			        // get output stream of the response
			        OutputStream outStream = response.getOutputStream();
			 
			        byte[] buffer = new byte[BUFFER_SIZE];
			        int bytesRead = -1;
			 
			        // write bytes read from the input stream into the output stream
			        while ((bytesRead = inputStream.read(buffer)) != -1) {
			            outStream.write(buffer, 0, bytesRead);
			        }
			 
			        inputStream.close();
			        outStream.close();
			   }
		   }
		   
	   }
	   
	   /**
		 * Handle request to download an Excel 97-2003 document 
		 */
		@RequestMapping(value = "/threadsExcell", method = RequestMethod.POST)
		public ModelAndView downloadExcelThreads(@Valid @ModelAttribute("threadExcellForm") mThreadExcellValidation threadValidExcell, BindingResult result, Map model, HttpSession session) {
			List<mAcademicYear> patentReportingAcademicDateList = academicYearService.list();
		    model.put("reportingAcademicDate", patentReportingAcademicDateList);
		    // create some sample data
			 if(result.hasErrors()) {
		          return new ModelAndView("cp.generateTopics");
		     }else
		     {
		    	/**
		    	 * Get list of all Projects (Topics)
		    	 */
				String yearForGenerating = threadValidExcell.getThreadYear();
				String threadCategory = threadValidExcell.getThreadCatCode();
				String threadStatus = threadValidExcell.getThreadStatus();
				String threadFaculty = threadValidExcell.getThreadFaculty();
				String threadDepartment = threadValidExcell.getThreadDepartment();
				String threadStaff = threadValidExcell.getThreadStaff();
				// Get list of Threads
				List<mThreads> threadsList = threadService.loadThreadsListForReporting(threadCategory, threadStatus, threadFaculty, threadDepartment, threadStaff, yearForGenerating);
				
				List<List<String>> summaryThreadList = new ArrayList<>();
				for(mThreads threads : threadsList)
				{
					String leaderFaculty = threadService.getFacultyName(threads.getStaff().getStaff_Faculty_Code());
					String leaderDepartment = threadService.getDepartmentName(threads.getStaff().getStaff_Department_Code());
					
					//String sFacultyname = threadService.
					List<String> summaryThread = new ArrayList<>();
					summaryThread.add(threads.getPROJ_User_Code());
					summaryThread.add(leaderFaculty);
					summaryThread.add(leaderDepartment);
					summaryThread.add(threads.getPROJ_Name());
					summaryThread.add(Integer.toString(threads.getPROJ_TotalBudget()));
					
					summaryThreadList.add(summaryThread);
				}
			    /**
			     * Get list of all Patents
			     */
			    model.put("summaryThreadList", summaryThreadList);
				model.put("yearOfPaper", yearForGenerating);
				// return a view which will be resolved by an excel view resolver
				return new ModelAndView("excelThreadsView");
		     }
		}
}
