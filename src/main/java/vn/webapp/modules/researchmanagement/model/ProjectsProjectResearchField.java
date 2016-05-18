package vn.webapp.modules.researchmanagement.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tblprojectprojectresearchfields")
public class ProjectsProjectResearchField implements Serializable{
	@Id
    @GeneratedValue
    private int PRJPRJRSHF_ID;
	private String PRJPRJRSHF_Code;
	private String PRJPRJRSHF_Name;
	public int getPRJPRJRSHF_ID() {
		return PRJPRJRSHF_ID;
	}
	public void setPRJPRJRSHF_ID(int pRJPRJRSHF_ID) {
		PRJPRJRSHF_ID = pRJPRJRSHF_ID;
	}
	public String getPRJPRJRSHF_Code() {
		return PRJPRJRSHF_Code;
	}
	public void setPRJPRJRSHF_Code(String pRJPRJRSHF_Code) {
		PRJPRJRSHF_Code = pRJPRJRSHF_Code;
	}
	public String getPRJPRJRSHF_Name() {
		return PRJPRJRSHF_Name;
	}
	public void setPRJPRJRSHF_Name(String pRJPRJRSHF_Name) {
		PRJPRJRSHF_Name = pRJPRJRSHF_Name;
	}
}
