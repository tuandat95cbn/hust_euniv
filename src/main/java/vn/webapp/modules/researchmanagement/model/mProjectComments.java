package vn.webapp.modules.researchmanagement.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tblcommentssubmittedprojects")
public class mProjectComments implements Serializable{
	@Id
    @GeneratedValue
    private int COMPROJ_ID;
    private String COMPROJ_CODE;
    private String COMPROJ_STAFFCODE;
    private String COMPROJ_PRJCODE;
    private String COMPROJ_COMMENT;
    private String COMPROJ_UpdateDate;
    private String COMPROJ_Lock;
    
	public int getCOMPROJ_ID() {
		return COMPROJ_ID;
	}
	public void setCOMPROJ_ID(int cOMPROJ_ID) {
		COMPROJ_ID = cOMPROJ_ID;
	}
	public String getCOMPROJ_CODE() {
		return COMPROJ_CODE;
	}
	public void setCOMPROJ_CODE(String cOMPROJ_CODE) {
		COMPROJ_CODE = cOMPROJ_CODE;
	}
	public String getCOMPROJ_STAFFCODE() {
		return COMPROJ_STAFFCODE;
	}
	public void setCOMPROJ_STAFFCODE(String cOMPROJ_STAFFCODE) {
		COMPROJ_STAFFCODE = cOMPROJ_STAFFCODE;
	}
	public String getCOMPROJ_PRJCODE() {
		return COMPROJ_PRJCODE;
	}
	public void setCOMPROJ_PRJCODE(String cOMPROJ_PRJCODE) {
		COMPROJ_PRJCODE = cOMPROJ_PRJCODE;
	}
	public String getCOMPROJ_COMMENT() {
		return COMPROJ_COMMENT;
	}
	public void setCOMPROJ_COMMENT(String cOMPROJ_COMMENT) {
		COMPROJ_COMMENT = cOMPROJ_COMMENT;
	}
	public String getCOMPROJ_UpdateDate() {
		return COMPROJ_UpdateDate;
	}
	public void setCOMPROJ_UpdateDate(String cOMPROJ_UpdateDate) {
		COMPROJ_UpdateDate = cOMPROJ_UpdateDate;
	}
	public String getCOMPROJ_Lock() {
		return COMPROJ_Lock;
	}
	public void setCOMPROJ_Lock(String cOMPROJ_Lock) {
		COMPROJ_Lock = cOMPROJ_Lock;
	}
}
