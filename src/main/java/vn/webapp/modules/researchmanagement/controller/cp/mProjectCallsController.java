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
import vn.webapp.modules.researchdeclarationmanagement.service.mAcademicYearService;
import vn.webapp.modules.researchdeclarationmanagement.service.mJournalService;
import vn.webapp.modules.researchdeclarationmanagement.service.mPatentService;
import vn.webapp.modules.researchdeclarationmanagement.service.tProjectCategoryService;
import vn.webapp.modules.researchdeclarationmanagement.service.tProjectService;
import vn.webapp.modules.researchmanagement.model.mProducts;
import vn.webapp.modules.researchmanagement.model.mProjectCalls;
import vn.webapp.modules.researchmanagement.model.mProjectStatus;
import vn.webapp.modules.researchmanagement.model.mThreads;
import vn.webapp.modules.researchmanagement.service.mProductService;
import vn.webapp.modules.researchmanagement.service.mProjectCallsService;
import vn.webapp.modules.researchmanagement.service.mProjectStatusService;
import vn.webapp.modules.researchmanagement.service.nProjectService;
import vn.webapp.modules.researchmanagement.validation.mProductValidation;
import vn.webapp.modules.researchmanagement.validation.mProjectCallsValidation;
import vn.webapp.modules.usermanagement.service.mDepartmentService;
import vn.webapp.modules.usermanagement.service.mStaffService;

@Controller("cpmProjectCalls")
@RequestMapping(value = { "/cp" })
public class mProjectCallsController extends BaseWeb {
	@Autowired
	private tProjectService tProjectService;

	@Autowired
	private mProductService productService;
	
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
	private mProjectCallsService projectCallsService;

	@Autowired
	private mProjectStatusService projectStatusService;

	static final String status = "active";

	/**
	 * Show list all threads
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/project-call-open", method = RequestMethod.GET)
	public String projectCallsList(ModelMap model, HttpSession session) {
		// Get topic's category
		List<mProjectCalls> projectCallsList = projectCallsService.loadProjectCallsList();
		
		// Put data back to view
		model.put("projectCallsList", projectCallsList);
		model.put("projectcalls", status);
		return "cp.projectcalls";
	}

	/**
	 * Adding a thread
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add-a-projectcall", method = RequestMethod.GET)
	public String addAProjectCall(ModelMap model, HttpSession session) {

		// Get topic's category
		List<mTopicCategory> topicCategory = tProjectCategoryService.list();
		
		model.put("topicCategory", topicCategory);
		model.put("projectCallFormAdd", new mProjectCallsValidation());
		model.put("projectcalls", status);
		return "cp.addAProjectCall";
	}

	/**
	 * Save a product
	 * 
	 * @param productValid
	 * @param result
	 * @param model
	 * @param session
	 * @return String
	 */
	@RequestMapping(value = "save-a-projectcall", method = RequestMethod.POST)
	public String saveAProjectCall( HttpServletRequest request, @Valid @ModelAttribute("projectCallFormAdd") mProjectCallsValidation projectCallValid, BindingResult result, Map model, HttpSession session) {
		
		// Get topic's category
		List<mTopicCategory> topicCategory = tProjectCategoryService.list();

		// Put data back to view
		model.put("topicCategory", topicCategory);
		model.put("projectcalls", status);
		if (result.hasErrors()) {
			return "cp.addAProjectCall";
		} else {
			// Prepare data for inserting DB
			String PROJCALL_PROJCATCODE = projectCallValid.getProjectCallCatCode();
			String PROJCALL_NAME 		= projectCallValid.getProjectCallName();
			String PROJCALL_DATE 		= DateUtil.s_fConvertDateFormatType1(projectCallValid.getProjectCallDate());
			String sPROJCALL_CODE 		= "T"+DateUtil.s_fGetCurrentDate();
			int i_InsertAProjectCall = projectCallsService.saveAProjectCall(sPROJCALL_CODE, PROJCALL_PROJCATCODE, PROJCALL_NAME, PROJCALL_DATE);
			if (i_InsertAProjectCall > 0) {
				model.put("status", "Lưu thành công");
			}
			return "cp.addAProjectCall";
		}
	}


	@RequestMapping("/projectcalldetail/{id}")
	public String editAProjectCall(ModelMap model, @PathVariable("id") int productId, HttpSession session) {

		String userRole = session.getAttribute("currentUserRole").toString();
		String userCode = session.getAttribute("currentUserCode").toString();
		mProducts product = productService.loadAProductByIdAndUserCode(userRole, userCode, productId);
		// Get list statues
		List<mProjectStatus> projectStatuses = projectStatusService.list();
		// Put data back to view
		model.put("projectStatuses", projectStatuses);
		model.put("thread", status);
		if (product != null) {
			// Get topic's category
			List<mThreads> threadsList = threadService.loadThreadsListByStaff(userRole, userCode);
			// Put journal list and topic category to view
			model.put("threadsList", threadsList);
			model.put("productFormEdit", new mProductValidation());
			model.put("productId", productId);
			model.put("productThreadCode", product.getThread().getPROJ_Code());
			model.put("productCode", product.getPROD_Code());
			model.put("productName", product.getPROD_Name());
			model.put("productEndDate", product.getPROD_EndDate());
			model.put("productStartDate", product.getPROD_StartDate());
			model.put("productStatus", product.getPROD_Status_Code());
			model.put("productBudget", product.getPROD_Budget());
			return "cp.editAProjectCall";
		}
		return "cp.notFound404";
	}
	
	 /**
	 * Editing a thread
	 * @param model
	 * @return
	 */
	 @RequestMapping(value = "/edit-a-projectcall", method = RequestMethod.POST)
	 public String updateAProjectCall(HttpServletRequest request, @Valid @ModelAttribute("productFormEdit") mProductValidation productValid, BindingResult result, Map model, HttpSession session) {
	
		 // Get current user name and role
		 String userCode = session.getAttribute("currentUserCode").toString();
		 String userRole = session.getAttribute("currentUserRole").toString();
		
		 // Get topic's category
		 List<mThreads> threadsList = threadService.loadThreadsListByStaff(userRole, userCode);
		 // Get list project statuses
		 List<mProjectStatus> projectStatuses = projectStatusService.list();

		 // Put data back to view
		 model.put("threadsList", threadsList);
		 model.put("projectStatuses", projectStatuses);
		 model.put("product", status);
		 if (result.hasErrors()) {
			 return "cp.editAProjectCall";
		 }else
		 {
			 // Prepare data for inserting DB
			 // Prepare data for inserting DB
			 String productName 		= productValid.getproductName();
			 String productStartDate 	= productValid.getproductStartDate();
			 String productEndDate 		= productValid.getproductEndDate();
			 String productProject 		= productValid.getproductCatCode();
			 String productStatus 		= productValid.getproductStatus();
			 int productBudget 			= productValid.getproductBudget();
			 String productCode 		= productValid.getproductCode();
			 int productId				= productValid.getproductId();
			 String productSourceUploadFileSrc = "";
			 /**
			 * Uploading file
			 */
			 
			 /*
			 MultipartFile productSourceUploadFile = productValid.getproductSourceFile();
			 String fileName = productSourceUploadFile.getOriginalFilename();
			 try {
				 // Creating Date in java with today's date.
				 Date currentDate = new Date();
				 // change date into string yyyyMMdd format example "20110914"
				 SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat("HHmmssddMMyyyy");
				 String sCurrentDate = dateformatyyyyMMdd.format(currentDate);
				 
				 byte[] bytes = productSourceUploadFile.getBytes();
				 String path = request.getServletContext().getRealPath("uploads");
				 File dir = new File(path + "/products");
				 if (!dir.exists()) {
					dir.mkdirs();
				 }
				 if (!fileName.equals("")) {
					// Create a file
					String currentUserName = session.getAttribute("currentUserName").toString();
					fileName = currentUserName + "_" + sCurrentDate + "_" + fileName;
					System.out.println("file name :" + fileName);
					File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);
					// Save data into file
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();
					
					if(serverFile.exists()){
						productSourceUploadFileSrc = dir.getAbsolutePath()+ File.separator + fileName;
					}
				 }
			 } catch (Exception e) {
				model.put("status", "You failed to upload " + fileName);
			 }
			 */
			 
			 productService.editAProduct(userRole, userCode, productName, productProject, productStartDate, productEndDate,
											productStatus, productBudget, productCode, productSourceUploadFileSrc, productId);
			 model.put("status", "Successfully edited product");
			 return "cp.editAProjectCall";
		 }
	 }
	
	 /**
	 * Remove a thread
	 * @param model
	 * @return
	 */
	 @RequestMapping(value = "/remove-a-projectcall/{id}", method = RequestMethod.GET)
	 public String removeAProjectCall(ModelMap model, @PathVariable("id") int productId, HttpSession session) {
		 String userCode = session.getAttribute("currentUserCode").toString();
		 String userRole = session.getAttribute("currentUserRole").toString();
		 mProducts product = productService.loadAProductByIdAndUserCode(userRole, userCode, productId);
		 // Get topic's category
		 List<mThreads> threadsList = threadService.loadThreadsListByStaff(userRole, userCode);
		 // Get list project statuses
		 List<mProjectStatus> projectStatuses = projectStatusService.list();
		 
		 // Return value to view
		 model.put("threadsList", threadsList);
		 model.put("projectStatuses", projectStatuses);
		 model.put("product", status);
		 if(product != null){
			 productService.removeAProduct(productId);
			 List<mProducts> productsList = productService.loadProductsListByStaff(userRole, userCode);
			 model.put("productsList", productsList);
			 return "cp.products";
		 }
		 return "cp.notFound404";
	 }
	 
}
