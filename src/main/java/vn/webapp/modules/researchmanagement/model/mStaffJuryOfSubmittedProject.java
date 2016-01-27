package vn.webapp.modules.researchmanagement.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tblstaffjurysubmittedprojects")
public class mStaffJuryOfSubmittedProject implements Serializable{
	@Id
    @GeneratedValue
    private int STFJUPRJ_ID;
	
	private String STFJUPRJ_STAFFJURCODE;
	
	private String STFJUPRJ_PRJCODE;
	
	private String STFJUPRJ_CODE;

	public int getSTFJUPRJ_ID() {
		return STFJUPRJ_ID;
	}

	public void setSTFJUPRJ_ID(int sTFJUPRJ_ID) {
		STFJUPRJ_ID = sTFJUPRJ_ID;
	}

	public String getSTFJUPRJ_STAFFJURCODE() {
		return STFJUPRJ_STAFFJURCODE;
	}

	public void setSTFJUPRJ_STAFFJURCODE(String sTFJUPRJ_STAFFJURCODE) {
		STFJUPRJ_STAFFJURCODE = sTFJUPRJ_STAFFJURCODE;
	}

	public String getSTFJUPRJ_PRJCODE() {
		return STFJUPRJ_PRJCODE;
	}

	public void setSTFJUPRJ_PRJCODE(String sTFJUPRJ_PRJCODE) {
		STFJUPRJ_PRJCODE = sTFJUPRJ_PRJCODE;
	}

	public String getSTFJUPRJ_CODE() {
		return STFJUPRJ_CODE;
	}

	public void setSTFJUPRJ_CODE(String sTFJUPRJ_CODE) {
		STFJUPRJ_CODE = sTFJUPRJ_CODE;
	}

	
  }
