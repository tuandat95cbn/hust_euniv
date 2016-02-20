package vn.webapp.modules.mastermanagement.dao;

import java.util.List;

import vn.webapp.modules.mastermanagement.model.mmJuryMember;

public interface mmJuryMemberDAO {
	List<mmJuryMember> listJuryMembers(String defenseSessionCode, String staffCode);
	
	mmJuryMember getAJuryMemberByCode(String sJuryMemberCode, String sStaffCode);
	
	List<mmJuryMember> listJuryMembers(String staffCode);
	
	int saveJuryMember(mmJuryMember juryMember);
	
	public int removeAJuryMemberByCode(mmJuryMember aJuryMember);
}
