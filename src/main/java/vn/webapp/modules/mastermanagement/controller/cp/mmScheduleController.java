/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.webapp.modules.mastermanagement.controller.cp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;














/**
 * Customize
 */
import vn.webapp.controller.BaseWeb;
import vn.webapp.modules.mastermanagement.controller.cpservice.PDFGenerator;
import vn.webapp.modules.mastermanagement.model.mmDefenseSession;
import vn.webapp.modules.mastermanagement.model.mmJuryMember;
import vn.webapp.modules.mastermanagement.model.mmJuryRoom;
import vn.webapp.modules.mastermanagement.model.mmJurySlot;
import vn.webapp.modules.mastermanagement.model.mmListMasterThesis;
import vn.webapp.modules.mastermanagement.model.mmMasterDefenseJuryThesis;
import vn.webapp.modules.mastermanagement.model.mmRooms;
import vn.webapp.modules.mastermanagement.model.mmShowedViewMasterDefenseThesis;
import vn.webapp.modules.mastermanagement.model.mmStaff;
import vn.webapp.modules.mastermanagement.service.mmDefenseSessionService;
import vn.webapp.modules.mastermanagement.service.mmJuryMemberService;
import vn.webapp.modules.mastermanagement.service.mmJuryRoomService;
import vn.webapp.modules.mastermanagement.service.mmJurySlotService;
import vn.webapp.modules.mastermanagement.service.mmMasterClassService;
import vn.webapp.modules.mastermanagement.service.mmMasterDefenseJuryService;
import vn.webapp.modules.mastermanagement.service.mmMasterThesisService;
import vn.webapp.modules.mastermanagement.service.mmRoomsService;
import vn.webapp.modules.mastermanagement.service.mmStaffService;

@Controller("mmSchedule")
@RequestMapping(value = {"/mm"})
public class mmScheduleController extends BaseWeb {
	   
    @Autowired
    private mmStaffService mmstaffService;
    
    @Autowired
    private mmMasterClassService mmmasterClassService;
    
    @Autowired
    private mmMasterThesisService mmmasterThesisService;
    
    @Autowired
    private mmMasterDefenseJuryService mmmasterDefenseJuryService;
    
    @Autowired
    private mmRoomsService mmroomsService;
 
    @Autowired 
    private mmJuryMemberService mmjuryMemberService;
 
    @Autowired
    private mmJuryRoomService mmjuryRoomService;
    
    @Autowired
    private mmJurySlotService mmjurySlotService;
    
    @Autowired
    private mmDefenseSessionService mmdefenseSessionService;

    static final String scheduling = "active";
    static final boolean disableHeader = true;
    
    /**
    * Scheduling
    * @param model
    * @param session
    * @return
    */
    
    @RequestMapping(value = "/jurymembers/{dsid}", method = RequestMethod.GET)
    public String juryMember(ModelMap model, @PathVariable("dsid") int dsid, HttpSession session){
    	String userCode = session.getAttribute("currentUserCode").toString();
    	List<mmStaff> listStaff = mmstaffService.listStaffs();
    	List<mmStaff> listMembers = new ArrayList<mmStaff>();
    	mmDefenseSession defenseSession = mmdefenseSessionService.getDefenseSessionById(dsid);
    	List<mmJuryMember> listJuryMembers = mmjuryMemberService.listJuryMembers(defenseSession.getDEFSESS_Code(), userCode);
    	
    	if(listJuryMembers != null){
  		    for(mmStaff st:listStaff){
  			   int flag = 0;
  			   String currentStaffCode = st.getStaff_Code();
  			   for(mmJuryMember jm:listJuryMembers){
  				   if(jm.getJuryMem_MemberCode().equals(currentStaffCode)){
  					   flag = 1;
  					   break;
  				   }
  			   }
  			   if (flag == 0)
  				   listMembers.add(st);
  		    }
  		    model.put("listMembers", listMembers);     	
  	    }
    	else{
    		model.put("listMembers", listStaff);
    	}
    	
    	
    	model.put("department", mmstaffService.loadStaffByStaffCode(userCode).getDepartment().getDepartment_Code());
    	model.put("defenseSession", defenseSession);
    	model.put("listJuryMembers", listJuryMembers);
    	model.put("disableHeader", mmScheduleController.disableHeader);
    	return "mm.juryMembers";
    }
    @RequestMapping(value = "/juryrooms/{dsid}", method = RequestMethod.GET)
    public String juryRooms(ModelMap model, @PathVariable("dsid") int dsid, HttpSession session){
    	String userCode = session.getAttribute("currentUserCode").toString();
    	mmDefenseSession defenseSession = mmdefenseSessionService.getDefenseSessionById(dsid);
    	List<mmJuryRoom> listJuryRooms = mmjuryRoomService.listJuryRooms(defenseSession.getDEFSESS_Code(),userCode);
    	List<mmRooms> listAllRooms = mmroomsService.listRooms();
    	List<mmRooms> listRooms = new ArrayList<mmRooms>();
    	
    	if(listJuryRooms != null){
  		    for(mmRooms st:listAllRooms){
  			   int flag = 0;
  			   String currentRoomsCode = st.getR_Code();
  			   for(mmJuryRoom jm:listJuryRooms){
  				   if(jm.getJuryRoom_Code().equals(currentRoomsCode)){
  					   flag = 1;
  					   break;
  				   }
  			   }
  			   if (flag == 0)
  				   listRooms.add(st);
  		    }
  		    model.put("listRooms", listRooms);     	
  	    }
    	else{
    		model.put("listRooms", listAllRooms);
    	}
    	
    	System.out.println(userCode);
    	model.put("department", mmstaffService.loadStaffByStaffCode(userCode).getDepartment().getDepartment_Code());
    	model.put("disableHeader", mmScheduleController.disableHeader);
    	model.put("defenseSession", defenseSession);
    	model.put("listJuryRooms", listJuryRooms);    	
    	return "mm.juryRooms";
    }
    @RequestMapping(value = "/juryslots/{dsid}", method = RequestMethod.GET)
    public String jurySlots(ModelMap model,@PathVariable("dsid") int dsid,HttpSession session){
    	String userCode = session.getAttribute("currentUserCode").toString();
    	mmDefenseSession defenseSession = mmdefenseSessionService.getDefenseSessionById(dsid);
    	List<mmDefenseSession> listDefenseSessions = mmmasterThesisService.listDefenseSession();
    	List<mmJurySlot> listJurySlots = mmjurySlotService.listJurySlots(userCode);
    	
    	model.put("department", mmstaffService.loadStaffByStaffCode(userCode).getDepartment().getDepartment_Code());
    	model.put("defenseSession", defenseSession);
    	model.put("disableHeader", mmScheduleController.disableHeader);
    	model.put("listDefenseSessions", listDefenseSessions);
    	model.put("listJurySlots", listJurySlots);
    	return "mm.jurySlots";
    }
 
   
   @RequestMapping(value = "/scheduling/{dsid}", method = RequestMethod.GET)
   public String scheduling(ModelMap model, @PathVariable("dsid") int dsid, HttpSession session) { 
	   String userCode = session.getAttribute("currentUserCode").toString();
	   mmDefenseSession defenseSession = mmdefenseSessionService.getDefenseSessionById(dsid);
	   List<mmListMasterThesis> listMasterThesis= mmmasterThesisService.getListMasterThesis();
	   //List<ShowedViewMasterDefenseThesis> listMasterDefenseJuryThesis = masterDefenseJuryService.getListMasterDefenseJuryThesisByOwner(userCode);
	   List<mmShowedViewMasterDefenseThesis> listMasterDefenseJuryThesis = mmmasterDefenseJuryService.getListMasterDefenseJuryThesisByDefenseSessionAndOwner(defenseSession.getDEFSESS_Code(), userCode);
	   List<mmShowedViewMasterDefenseThesis> listAllMasterDefenseJuryThesis = mmmasterDefenseJuryService.getListMasterDefenseJuryThesis();
	   List<mmListMasterThesis> listMasterThesisSatisfy = new ArrayList<mmListMasterThesis>();
	   //System.out.println(listAllMasterDefenseJuryThesis.size());
	   if(listMasterDefenseJuryThesis != null){
		   for(mmListMasterThesis mt:listMasterThesis){
			   int flag = 0;
			   String currentThesisCode = mt.getThesis_Code();
			   if(listAllMasterDefenseJuryThesis != null){
				   for(mmShowedViewMasterDefenseThesis smt:listAllMasterDefenseJuryThesis){
					   if(smt.getThesisCode().equals(currentThesisCode)){
						   flag = 1;
						   break;
					   }
				   }
			   }
			   if (flag == 0)
				   listMasterThesisSatisfy.add(mt);
		   }
	   }
	   
	   model.put("department", mmstaffService.loadStaffByStaffCode(userCode).getDepartment().getDepartment_Code());
	   model.put("listMasterDefenseJuryThesis", listMasterDefenseJuryThesis);
	   model.put("listMasterThesis", listMasterThesisSatisfy);
	   model.put("defenseSession", defenseSession);
	   model.put("scheduling", mmScheduleController.scheduling);
	   model.put("disableHeader", mmScheduleController.disableHeader);
	   return "mm.scheduling";
   }
   
   /**
    * Setting juries
    * @param model
    * @param session
    * @return
    */
   @RequestMapping(value = "/settingjuries/{dsid}", method = RequestMethod.GET)
   public String settingJuries(ModelMap model, @PathVariable("dsid") int dsid, HttpSession session) {
	   String userCode = session.getAttribute("currentUserCode").toString();
	   mmDefenseSession defenseSession = mmdefenseSessionService.getDefenseSessionById(dsid);
	   //List<Staff> listStaffs = staffService.listStaffs();
	   //List<Rooms> listRooms = roomsService.listRooms();
	   List<mmListMasterThesis> listMasterThesis  = mmmasterThesisService.getListMasterThesis();
	   List<mmShowedViewMasterDefenseThesis> listMasterDefenseJuryThesis = mmmasterDefenseJuryService.getListMasterDefenseJuryThesisByDefenseSessionAndOwner(defenseSession.getDEFSESS_Code(), userCode);
	   System.out.println(defenseSession.getDEFSESS_Code());
	   List<mmJuryRoom> listJuryRooms = mmjuryRoomService.listJuryRooms(defenseSession.getDEFSESS_Code(),userCode);
	   List<mmJurySlot> listJurySlot = mmjurySlotService.listJurySlots(defenseSession.getDEFSESS_Code(),userCode);
	   List<mmJuryMember> listJuryMembers = mmjuryMemberService.listJuryMembers(defenseSession.getDEFSESS_Code(), userCode);
	   
   	   List<mmJuryMember> listInnerJuryMembers = new ArrayList<mmJuryMember>();
   	   List<mmJuryMember> listOuterJuryMembers = new ArrayList<mmJuryMember>();
   	   for(mmJuryMember jm:listJuryMembers){
   		   if(!jm.getMemJuryMember().getStaff_Department_Code().equals("DIFF")){
   			   listInnerJuryMembers.add(jm);
   		   }
   		   else{
   			   listOuterJuryMembers.add(jm);
   		   }
   	   }
	   //List<ShowedViewMasterDefenseThesis> listMasterDefenseJuryThesis = masterDefenseJuryService.getListMasterDefenseJuryThesis();
	   
		 
	   model.put("defenseSession", defenseSession);		 
	   model.put("department", mmstaffService.loadStaffByStaffCode(userCode).getDepartment().getDepartment_Code());
	   model.put("listInnerJuryMembers", listInnerJuryMembers);
	   model.put("listOuterJuryMembers", listOuterJuryMembers);
	   //model.put("listStaffs", listStaffs);
	   //model.put("listRooms", listRooms);
	   model.put("listJuryRooms", listJuryRooms);
	   model.put("listJurySlot", listJurySlot);	   
	   model.put("listMasterThesis", listMasterThesis);
	   model.put("listMasterDefenseJuryThesis", listMasterDefenseJuryThesis);
	   	   
	   model.put("scheduling", mmScheduleController.scheduling);
	   model.put("disableHeader", mmScheduleController.disableHeader);
	   return "mm.settingjuries";
   }
   
   /**
    * Setting juries
    * @param model
    * @param session
    * @return
    */
   @RequestMapping(value = "/save-defenses", method=RequestMethod.POST)
   public String saveDefences(HttpServletRequest request, HttpServletResponse response, ModelMap model, HttpSession session) {
	   
	   String userCode = session.getAttribute("currentUserCode").toString();
	   String userRole = session.getAttribute("currentUserRole").toString();
	   String[] masterDefenseThesis = request.getParameterValues("masterDefenseThesis");
	   String[] defenseder01 = request.getParameterValues("defenseder01");
	   String[] defenseder02 = request.getParameterValues("defenseder02");
	   String[] president = request.getParameterValues("president");
	   String[] secretary = request.getParameterValues("secretary");
	   String[] commissioner = request.getParameterValues("commissioner");
	   String[] slot = request.getParameterValues("slot");
	   String[] room = request.getParameterValues("room");
	   String[] no = request.getParameterValues("no");
	   
	   String[] sessionCode = request.getParameterValues("defenseSession");
	   
	   //Update info
	   boolean isUpdated = mmmasterDefenseJuryService.updateAMasterDefenseJuryThesis(masterDefenseThesis, defenseder01, defenseder02, president, secretary, commissioner, slot, room, no, userCode, sessionCode[0]);
	   String status = "";
	   if(isUpdated)
	   {
		   status = "Chỉnh sửa thành công !";
	   }
	   List<mmStaff> listStaffs = mmstaffService.listStaffs();
	   List<mmRooms> listRooms = mmroomsService.listRooms();
	   List<mmListMasterThesis> listMasterThesis  = mmmasterThesisService.getListMasterThesis();
	   List<mmShowedViewMasterDefenseThesis> listMasterDefenseJuryThesis = mmmasterDefenseJuryService.getListMasterDefenseJuryThesis();
	   
	   model.put("listMasterDefenseJuryThesis", listMasterDefenseJuryThesis);
	   model.put("status", status);
	   model.put("listStaffs", listStaffs);
	   model.put("listRooms", listRooms);
	   model.put("listMasterThesis", listMasterThesis);
	   model.put("scheduling", mmScheduleController.scheduling);
	   model.put("disableHeader", mmScheduleController.disableHeader);
	   
	   return "mm.settingjuries";
   }

   /**
    * 
    * @param model
    * @param masterThesisCode
    * @param session
    * 
    * @return
    */
   @RequestMapping(value = "/remove-a-masterthesis/{code}", method = RequestMethod.GET)
   public String removeAMasterThesis(ModelMap model, @PathVariable("code") String masterThesisCode, HttpSession session) {
 	   String userCode = session.getAttribute("currentUserCode").toString();
 	   model.put("scheduling", mmScheduleController.scheduling);
	   model.put("disableHeader", mmScheduleController.disableHeader);
 	   int isRemoved = mmmasterDefenseJuryService.removeAMasterThesis(userCode, masterThesisCode);
 	   if(isRemoved > 0 ){
 		   List<mmDefenseSession> listDefenseSession  = mmmasterThesisService.listDefenseSession();
 		   List<mmListMasterThesis> listMasterThesis= mmmasterThesisService.getListMasterThesis();
 		   List<mmShowedViewMasterDefenseThesis> listMasterDefenseJuryThesis = mmmasterDefenseJuryService.getListMasterDefenseJuryThesisByOwner(userCode);
 		   
 		   model.put("listMasterDefenseJuryThesis", listMasterDefenseJuryThesis);
 		   model.put("listMasterThesis", listMasterThesis);
 		   model.put("listDefenseSession", listDefenseSession);
 		   
 		   return "mm.scheduling";
 	   }
 	  return "mm.scheduling";
   }
   
   /**
    * 
    * @param model
    * @param masterThesisCode
    * @param session
    * 
    * @return
    */
   @RequestMapping(value = "/remove-a-jury-member/{code}", method = RequestMethod.GET)
   public String removeAJuryMember(ModelMap model, @PathVariable("code") String sJuryMemberCode, HttpSession session) {
 	   String userCode = session.getAttribute("currentUserCode").toString();
 	   int isRemoved = mmjuryMemberService.removeAJuryMember(userCode, sJuryMemberCode);
 	   if(isRemoved > 0 ){
 		   List<mmDefenseSession> listDefenseSessions = mmmasterThesisService.listDefenseSession();
 		  	List<mmStaff> listMembers = mmstaffService.listStaffs();
 		  	List<mmJuryMember> listJuryMembers = mmjuryMemberService.listJuryMembers(userCode);
 		  	
 		  	model.put("listDefenseSessions", listDefenseSessions);
 		  	model.put("listMembers", listMembers);
 		  	model.put("listJuryMembers", listJuryMembers);
 		   
 		  return "mm.juryMembers";
 	   }
 	  return "mm.juryMembers";
   }
   
   /**
    * 
    * @param model
    * @param masterThesisCode
    * @param session
    * 
    * @return
    */
   @RequestMapping(value = "/remove-a-jury-room/{code}", method = RequestMethod.GET)
   public String removeAJuryRoom(ModelMap model, @PathVariable("code") String sJuryRoomCode, HttpSession session) {
 	    String userCode = session.getAttribute("currentUserCode").toString();
 	    List<mmDefenseSession> listDefenseSessions = mmmasterThesisService.listDefenseSession();
	   	List<mmRooms> listRooms = mmroomsService.listRooms();
	   	
	   	model.put("listDefenseSessions", listDefenseSessions);
	   	model.put("listRooms", listRooms);
 	   int isRemoved = mmjuryRoomService.removeAJuryRoom(userCode, sJuryRoomCode);
 	   if(isRemoved > 0 ){
 			List<mmJuryRoom> listJuryRooms = mmjuryRoomService.listJuryRooms(userCode);
 			model.put("listJuryRooms", listJuryRooms);
 		    return "mm.juryRooms";
 	   }
 	  return "mm.juryRooms";
   }
   
   /**
    * 
    * @param model
    * @param masterThesisCode
    * @param session
    * 
    * @return
    */
   @RequestMapping(value = "/remove-a-jury-slot/{code}", method = RequestMethod.GET)
   public String removeAJurySlot(ModelMap model, @PathVariable("code") String sJurySlotCode, HttpSession session) {
 	   String userCode = session.getAttribute("currentUserCode").toString();
 	   List<mmDefenseSession> listDefenseSessions = mmmasterThesisService.listDefenseSession();
   	
	   model.put("listDefenseSessions", listDefenseSessions);
 	   int isRemoved = mmjurySlotService.removeAJurySlot(userCode, sJurySlotCode);
 	   if(isRemoved > 0 ){
 		  List<mmJurySlot> listJurySlots = mmjurySlotService.listJurySlots(userCode);
 		  model.put("listJurySlots", listJurySlots);
 		  return "mm.jurySlots";
 	   }
 	  return "mm.jurySlots";
   }
   
   @RequestMapping(value = "/viewPdf/{id}", method = RequestMethod.GET)
   public void viewPdf(HttpServletRequest request, HttpServletResponse response, ModelMap model, @PathVariable("id") int masterDefenseJuryThesis_ID, HttpSession session) throws IOException {
	   
	   mmMasterDefenseJuryThesis masterDefenseJuryThesis = mmmasterDefenseJuryService.getMasterDefenseJuryThesisById(masterDefenseJuryThesis_ID);
	     	   
   	   String filePath = session.getServletContext().getRealPath("/"); 
   	   String fileName = filePath+"\\upload\\mastermanagement\\"+masterDefenseJuryThesis.getMASDEFJury_Code()+".pdf";
   	   System.out.println(fileName);
   	   
   	   ServletOutputStream out = response.getOutputStream();
   	   
   	   
   	   
   	try{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos = PDFGenerator.convertPDFToByteArrayOutputStream(fileName);
        //response.setContentType("application/pdf");
        //response.setHeader("Content-Disposition", "attachment:filename=report.pdf");
        OutputStream os = response.getOutputStream();
        baos.writeTo(os);
        os.flush();
	}catch (Exception e)
	{
		e.printStackTrace();
	}
   	      	   
	   /*model.put("defenseSession", defenseSession);		 
	   model.put("department", mmstaffService.loadStaffByStaffCode(userCode).getDepartment().getDepartment_Code());
	   model.put("listInnerJuryMembers", listInnerJuryMembers);
	   model.put("listOuterJuryMembers", listOuterJuryMembers);
	   model.put("listJuryRooms", listJuryRooms);
	   model.put("listJurySlot", listJurySlot);	   
	   model.put("listMasterThesis", listMasterThesis);
	   model.put("listMasterDefenseJuryThesis", listMasterDefenseJuryThesis);
	   	   
	   model.put("scheduling", mmScheduleController.scheduling);
	   model.put("disableHeader", mmScheduleController.disableHeader);
	   return "mm.settingjuries";*/   
	   
   }
   
}
