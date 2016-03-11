package vn.webapp.modules.mastermanagement.controller.cpservice;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itextpdf.text.DocumentException;

import vn.webapp.controller.BaseWeb;
import vn.webapp.libraries.FileUtil;
import vn.webapp.modules.mastermanagement.model.mmMasterDefenseJuryThesis;
import vn.webapp.modules.mastermanagement.model.mmMasterThesis;
import vn.webapp.modules.mastermanagement.model.mmStaff;
import vn.webapp.modules.mastermanagement.model.mmStudent;
import vn.webapp.modules.mastermanagement.service.mmJuryMemberService;
import vn.webapp.modules.mastermanagement.service.mmJuryRoomService;
import vn.webapp.modules.mastermanagement.service.mmJurySlotService;
import vn.webapp.modules.mastermanagement.service.mmMasterClassService;
import vn.webapp.modules.mastermanagement.service.mmMasterDefenseJuryService;
import vn.webapp.modules.mastermanagement.service.mmMasterThesisService;
import vn.webapp.modules.mastermanagement.service.mmStaffService;
import vn.webapp.modules.mastermanagement.service.mmStudentService;
import vn.webapp.modules.researchmanagement.controller.cp.nProjectController;
import vn.webapp.modules.researchmanagement.model.mProjectCalls;
import vn.webapp.modules.researchmanagement.validation.ProjectsValidation;

@Controller("mmServiceSchedule")
@RequestMapping(value = { "/mmservice" })
public class ScheduleController extends BaseWeb {
	    
    @Autowired
    private mmStaffService mmstaffService;
    
    @Autowired
    private mmMasterClassService mmmasterClassService;
    
    @Autowired
    private mmMasterThesisService mmmasterThesisService;
    
    @Autowired
    private mmMasterDefenseJuryService mmmasterDefenseJuryService;

    @Autowired
    private mmJuryMemberService mmjuryMemberService;
    
    @Autowired
    private mmJuryRoomService mmjuryRoomService;
    
    @Autowired
    private mmJurySlotService mmjurySlotService;
    
    @Autowired
    private mmStudentService mmstudentService;
    
     
    /**
     * 
     * @param sMasterThesisCode
     * @param sDefenseSessionCode
     * @param session
     * @return
     */
	@ResponseBody
	@RequestMapping(value = "/savemasterthesis", method = RequestMethod.POST, produces = "text/html; charset=UTF-8")
	public String savemasterthesis(@RequestParam(value = "sMasterThesisCode", defaultValue = "0") String sMasterThesisCode,
											@RequestParam(value = "sDefenseSessionCode", defaultValue = "0") String sDefenseSessionCode, HttpSession session) {
		
		String sReturn = "";
		// Get department lists
		if(!sMasterThesisCode.equals("") && !sDefenseSessionCode.equals(""))
		{
			String userCode = session.getAttribute("currentUserCode").toString();
			int iSaved = mmmasterDefenseJuryService.saveAMasterThesis(sMasterThesisCode, sDefenseSessionCode, userCode);
			
			if(iSaved > 0){
				System.out.println("Luu thanh cong");
				sReturn = "<span>Lưu thành công học viên</span>";
			}else{
				sReturn = "<span>Không thành công</span>";
				System.out.println("Luu khong thanh cong");
				
			}
		}
		return sReturn;
	}
	
	@ResponseBody
	@RequestMapping(value = "/removemasterthesis", method = RequestMethod.POST, produces = "text/html; charset=UTF-8")
	public String removemasterthesis(@RequestParam(value = "sMasterThesisCode", defaultValue = "0") String sMasterThesisCode,
											@RequestParam(value = "sDefenseSessionCode", defaultValue = "0") String sDefenseSessionCode, HttpSession session) {
		String sReturn = "";
		// Get department lists
		if(!sMasterThesisCode.equals("") && !sDefenseSessionCode.equals(""))
		{
			String userCode = session.getAttribute("currentUserCode").toString();
			
			int iSaved = mmmasterDefenseJuryService.removeAMasterThesis(userCode, sMasterThesisCode);
			if(iSaved > 0){
				sReturn = "<span>Lưu thành công !</span>";
			}else{
				sReturn = "<span>Không thành công</span>";
			}
		}
		return sReturn;
	}

	/**
	 * 
	 * @param sJuryMemberCode
	 * @param sDefenseSessionCode
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/savejurymember", method = RequestMethod.POST, produces = "text/html; charset=UTF-8")
	public String saveAJuryMember(@RequestParam(value = "sJuryMemberCode", defaultValue = "0") String sJuryMemberCode,
			@RequestParam(value = "sDefenseSessionCode", defaultValue = "0") String sDefenseSessionCode,
			HttpSession session){
		String sReturn = "";
		if(!sJuryMemberCode.equals("") && !sDefenseSessionCode.equals("")){
			String userCode = session.getAttribute("currentUserCode").toString();
			int iSaved = mmjuryMemberService.saveAJuryMember(sJuryMemberCode, sDefenseSessionCode, userCode);
			if(iSaved > 0){
				sReturn = "<span>Lưu thành công !</span>";
			}else{
				sReturn = "<span>Add A Jury Member --> Không thành công" + 
						"cpservice/ScheduleController::saveAJuryMember, juryMemberCode = " + sJuryMemberCode + ", defenseSessionCode = " + sDefenseSessionCode 
						+ "</span>";
			}
		}
		return sReturn;
	}
	
	@ResponseBody
	@RequestMapping(value = "/removejurymember", method = RequestMethod.POST, produces = "text/html; charset=UTF-8")
	public String removeAJuryMember(@RequestParam(value = "sJuryMemberCode", defaultValue = "0") String sJuryMemberCode,
			@RequestParam(value = "sDefenseSessionCode", defaultValue = "0") String sDefenseSessionCode,
			HttpSession session){
		String sReturn = "";
		if(!sJuryMemberCode.equals("") && !sDefenseSessionCode.equals("")){
			String userCode = session.getAttribute("currentUserCode").toString();
			int iSaved = mmjuryMemberService.removeAJuryMember(userCode, sJuryMemberCode);
			if(iSaved > 0){
				sReturn = "<span>Lưu thành công !</span>";
			}else{
				sReturn = "<span>Add A Jury Member --> Không thành công" + 
						"cpservice/ScheduleController::saveAJuryMember, juryMemberCode = " + sJuryMemberCode + ", defenseSessionCode = " + sDefenseSessionCode 
						+ "</span>";
			}
		}
		return sReturn;
	}
	
	/**
	 * 
	 * @param sRoomCode
	 * @param sDefenseSessionCode
	 * @param sOrder
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/savejuryaroom", method = RequestMethod.POST, produces = "text/html; charset=UTF-8")
	public String saveARoom(@RequestParam(value = "sRoomCode", defaultValue = "0") String sRoomCode,
			@RequestParam(value = "sDefenseSessionCode", defaultValue = "0") String sDefenseSessionCode,
			@RequestParam(value = "sOrder", defaultValue = "0") String sOrder,
			HttpSession session){
		String sReturn = "";
		if(!sRoomCode.equals("") && !sDefenseSessionCode.equals("")){
			String userCode = session.getAttribute("currentUserCode").toString();
			int iOrder = Integer.parseInt(sOrder);
			int iSaved = mmjuryRoomService.saveAJuryRoom(iOrder, sRoomCode, sDefenseSessionCode, userCode);
			if(iSaved > 0){
				sReturn = "<span>Lưu thành công !</span>";
			}else{
				sReturn = "<span>Không thành công. </span>";
			}
		}
		return sReturn;
	}
	
	/**
	 * 
	 * @param sRoomCode
	 * @param sDefenseSessionCode
	 * @param sOrder
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/removejuryaroom", method = RequestMethod.POST, produces = "text/html; charset=UTF-8")
	public String removeARoom(@RequestParam(value = "sRoomCode", defaultValue = "0") String sRoomCode,
			@RequestParam(value = "sDefenseSessionCode", defaultValue = "0") String sDefenseSessionCode,
			HttpSession session){
		String sReturn = "";
		if(!sRoomCode.equals("") && !sDefenseSessionCode.equals("")){
			String userCode = session.getAttribute("currentUserCode").toString();
			int iSaved = mmjuryRoomService.removeAJuryRoom(userCode, sRoomCode);
			if(iSaved > 0){
				sReturn = "<span>Lưu thành công !</span>";
			}else{
				sReturn = "<span>Không thành công. </span>";
			}
		}
		return sReturn;
	}
	
	/**
	 * 
	 * @param sRoomCode
	 * @param sDefenseSessionCode
	 * @param sOrder
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/savejuryslot", method = RequestMethod.POST, produces = "text/html; charset=UTF-8")
	public String saveASlot(@RequestParam(value = "sTime", defaultValue = "0") String sTime,
			@RequestParam(value = "sDefenseSessionCode", defaultValue = "0") String sDefenseSessionCode,
			@RequestParam(value = "sOrder", defaultValue = "0") String sOrder,
			HttpSession session){
		String sReturn = "";
		if(!sTime.equals("") && !sDefenseSessionCode.equals("")){
			String userCode = session.getAttribute("currentUserCode").toString();
			int iOrder = Integer.parseInt(sOrder);
			int iSaved = mmjurySlotService.saveAJurySlot(iOrder, sTime, sDefenseSessionCode, userCode);
			if(iSaved > 0){
				sReturn = "<span>Lưu thành công !</span>";
			}else{
				sReturn = "<span>Không thành công. </span>";
			}
		}
		return sReturn;
	}

	/**
	 * 
	 * @param sRoomCode
	 * @param sDefenseSessionCode
	 * @param sOrder
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/removejuryslot", method = RequestMethod.POST, produces = "text/html; charset=UTF-8")
	public String removeASlot(@RequestParam(value = "sJurySlotCode", defaultValue = "0") String sJurySlotCode,
			@RequestParam(value = "sDefenseSessionCode", defaultValue = "0") String sDefenseSessionCode,			
			HttpSession session){
		String sReturn = "";
		if(!sJurySlotCode.equals("") && !sDefenseSessionCode.equals("")){
			String userCode = session.getAttribute("currentUserCode").toString();
			int iSaved = mmjurySlotService.removeAJurySlot(userCode, sJurySlotCode);
			if(iSaved > 0){
				sReturn = "<span>Lưu thành công !</span>";
			}else{
				sReturn = "<span>Không thành công. </span>";
			}
		}
		return sReturn;
	}
	
	
	/*@ResponseBody
	@RequestMapping(value = "/generateJuryPdf", method = RequestMethod.POST, produces="application/pdf")
	public byte[] generateJuryPdf(
			@RequestParam(value = "studentName") String studentName,
			@RequestParam(value = "thesisCode") String thesisCode,
			@RequestParam(value = "mentorName") String mentorName,
			@RequestParam(value = "defender01") String defender01,
			@RequestParam(value = "defender02") String defender02,
			@RequestParam(value = "president") String president,
			@RequestParam(value = "secretary") String secretary,
			@RequestParam(value = "commissioner") String commissioner,
			@RequestParam(value = "slot") String slot,
			@RequestParam(value = "room") String room,
									
			HttpSession session){
	
		
		//final ServletContext servletContext = session.getServletContext();
	    //final File tempDirectory = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
	    //final String temperotyFilePath = tempDirectory.getAbsolutePath();
		String filePath = session.getServletContext().getRealPath("/"); 
		
	    mmMasterThesis masterThesis = mmmasterThesisService.loadMasterThesisByCode(thesisCode);
	    mmMasterDefenseJuryThesis masterDefenseJuryThesis = mmmasterDefenseJuryService.getMasterDefenseJuryThesisByThesisCode(thesisCode);
	    if(masterThesis == null || masterDefenseJuryThesis == null){
	    	return null;
	    }
	    String fileName = filePath+"upload\\mastermanagement\\"+masterDefenseJuryThesis.getMASDEFJury_Code()+".pdf";
	    System.out.println(fileName);
		PDFGenerator pdfGenerator = new PDFGenerator("html/test.html",fileName);
	    	try {
				pdfGenerator.v_fGenerator();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			    
	    	FileInputStream fileInputStream=null;
	        
	        File file = new File(fileName);
	        
	        byte[] bFile = new byte[(int) file.length()];
	        
	        try {
				fileInputStream = new FileInputStream(file);
				fileInputStream.read(bFile);				
				fileInputStream.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	   
	    	
	    	return bFile;
	    	//return masterDefenseJuryThesis.getMASDEFJury_ID()+"";
	    //return fileName;
	}*/
	
	@ResponseBody
	@RequestMapping(value = "/generateJuryPdf", method = RequestMethod.POST)
	public String generateJuryPdf(
			@RequestParam(value = "studentName") String studentName,
			@RequestParam(value = "thesisCode") String thesisCode,
			@RequestParam(value = "mentorName") String mentorName,
			@RequestParam(value = "defender01") String defender01Code,
			@RequestParam(value = "defender02") String defender02Code,
			@RequestParam(value = "president") String presidentCode,
			@RequestParam(value = "secretary") String secretaryCode,
			@RequestParam(value = "commissioner") String commissionerCode,
			@RequestParam(value = "slot") String slot,
			@RequestParam(value = "room") String room,
									
			HttpSession session) throws IOException{
	
		
		String filePath = session.getServletContext().getRealPath("/"); 
		
	    mmMasterThesis masterThesis = mmmasterThesisService.loadMasterThesisByCode(thesisCode);
	    mmMasterDefenseJuryThesis masterDefenseJuryThesis = mmmasterDefenseJuryService.getMasterDefenseJuryThesisByThesisCode(thesisCode);
	    if(masterThesis == null || masterDefenseJuryThesis == null){
	    	return -1+"";
	    }
	    String fileName = filePath+"upload\\mastermanagement\\"+masterDefenseJuryThesis.getMASDEFJury_Code()+".pdf";
	    //System.out.println(fileName);
	    
	    ClassLoader classLoader = getClass().getClassLoader();
	    File o_FontFile = new File(classLoader.getResource("html/juryTemplate.html").getFile());
    	String sFilePath = o_FontFile.getAbsolutePath();
	    StringBuilder sTemplateContent = FileUtil.sGetFileContent(sFilePath);
    	
	    Date date = new Date();
	    Calendar calendar = new GregorianCalendar();
	    calendar.setTime(date);
	    int year = calendar.get(Calendar.YEAR);
	    //Add one to month {0 - 11}
	    int month = calendar.get(Calendar.MONTH) + 1;
	    int day = calendar.get(Calendar.DAY_OF_MONTH);
	    
    	// Replace year
    	sTemplateContent = FileUtil.sReplaceAll(sTemplateContent, "__Year__", year+"");
    	sTemplateContent = FileUtil.sReplaceAll(sTemplateContent, "__Month__", month+"");
    	sTemplateContent = FileUtil.sReplaceAll(sTemplateContent, "__Day__", day+"");
    	
    	//mmStudent student = mmstudentService.loadStudentByCode(masterThesis.getThesis_StudentCode());
    	sTemplateContent = FileUtil.sReplaceAll(sTemplateContent, "__StudentName__", studentName);
    	sTemplateContent = FileUtil.sReplaceAll(sTemplateContent, "__ThesisName__", masterThesis.getThesis_Name());
    	sTemplateContent = FileUtil.sReplaceAll(sTemplateContent, "__SupervisorName__", mentorName);
    	
    	mmStaff chairman = mmstaffService.loadStaffByStaffCode(presidentCode);
    	mmStaff examiner1 = mmstaffService.loadStaffByStaffCode(defender01Code);
    	mmStaff examiner2 = mmstaffService.loadStaffByStaffCode(defender02Code);
    	mmStaff secretary = mmstaffService.loadStaffByStaffCode(secretaryCode);
    	mmStaff commissioner = mmstaffService.loadStaffByStaffCode(commissionerCode);
    	
    	sTemplateContent = FileUtil.sReplaceAll(sTemplateContent, "__ChairmanName__", chairman.getStaff_Name());
    	sTemplateContent = FileUtil.sReplaceAll(sTemplateContent, "__Examiner1Name__", examiner1.getStaff_Name());
    	sTemplateContent = FileUtil.sReplaceAll(sTemplateContent, "__Examiner1University__", examiner1.getDepartment().getFaculty().getUniversity().getUniversity_Name());
    	sTemplateContent = FileUtil.sReplaceAll(sTemplateContent, "__Examiner2Name__", examiner2.getStaff_Name());
    	sTemplateContent = FileUtil.sReplaceAll(sTemplateContent, "__SecretaryName__", secretary.getStaff_Name());
    	sTemplateContent = FileUtil.sReplaceAll(sTemplateContent, "__CommissionerName__", commissioner.getStaff_Name());
    	sTemplateContent = FileUtil.sReplaceAll(sTemplateContent, "__CommissionerUniversity__", commissioner.getDepartment().getFaculty().getUniversity().getUniversity_Name());
    	
    	
    	
    	// Write completed content into file
    	File o_CompletedContentFile = new File(filePath+"upload\\mastermanagement\\"+masterDefenseJuryThesis.getMASDEFJury_Code()+".html");
    	
    	FileUtil.v_fWriteContentIntoAFile(o_CompletedContentFile, sTemplateContent);
	    	    
		PDFGenerator pdfGenerator = new PDFGenerator(filePath+"upload\\mastermanagement\\"+masterDefenseJuryThesis.getMASDEFJury_Code()+".html",fileName);
	    	try {
				pdfGenerator.v_fGenerator();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			    
	  	return masterDefenseJuryThesis.getMASDEFJury_ID()+"";
	    //return fileName;
	}
	
	
	
	
	/*@ResponseBody
	@RequestMapping(value = "/viewpdf", produces="application/pdf")
	public byte[] download(
			@RequestParam(value = "masterDefenseJuryThesis_ID") int masterDefenseJuryThesis_ID,
			HttpServletResponse response, HttpSession session) throws IOException {
	    response.setContentType("application/pdf");
	    mmMasterDefenseJuryThesis masterDefenseJuryThesis = mmmasterDefenseJuryService.getMasterDefenseJuryThesisById(masterDefenseJuryThesis_ID);
  	   
	   	   String filePath = session.getServletContext().getRealPath("/"); 
	   	   String fileName = filePath+"upload\\mastermanagement\\"+masterDefenseJuryThesis.getMASDEFJury_Code()+".pdf";
	    
	    
	   	FileInputStream fileInputStream=null;
        
        File file = new File(fileName);
        
        byte[] bFile = new byte[(int) file.length()];
        
        try {
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);				
			fileInputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	   
    	
    	return bFile;
    	
	}*/

	
}
