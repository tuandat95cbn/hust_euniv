package vn.webapp.modules.mastermanagement.controller.cpservice;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import vn.webapp.controller.BaseWeb;
import vn.webapp.modules.mastermanagement.service.mmJuryMemberService;
import vn.webapp.modules.mastermanagement.service.mmJuryRoomService;
import vn.webapp.modules.mastermanagement.service.mmJurySlotService;
import vn.webapp.modules.mastermanagement.service.mmMasterClassService;
import vn.webapp.modules.mastermanagement.service.mmMasterDefenseJuryService;
import vn.webapp.modules.mastermanagement.service.mmMasterThesisService;
import vn.webapp.modules.mastermanagement.service.mmStaffService;

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

	
}
