package vn.webapp.modules.researchmanagement.model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tblprojects")
public class mThreads implements Serializable{
	@Id
    @GeneratedValue
    private int PROJ_ID;
    private String PROJ_Code;
    private String PROJ_AcaYear_Code;
    private String PROJ_Name;
    private String PROJ_ProjCat_Code;
    private String PROJ_StartDate;
    private String PROJ_EndDate;
    private String PROJ_Motivation;
    private String PROJ_Content;
    private String PROJ_Result;
    private String PROJ_SourceFile;
    private String PROJ_Status_Code;
    private int PROJ_TotalBudget;
    private String PROJ_User_Code;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJ_User_Code", referencedColumnName = "Staff_User_Code", insertable=false, updatable=false)
    private mStaffThread staff;
    
    @OneToMany(mappedBy="threads")
    private Set<mProducts> products;

	public int getPROJ_ID() {
		return PROJ_ID;
	}

	public void setPROJ_ID(int pROJ_ID) {
		PROJ_ID = pROJ_ID;
	}

	public String getPROJ_Code() {
		return PROJ_Code;
	}

	public void setPROJ_Code(String pROJ_Code) {
		PROJ_Code = pROJ_Code;
	}

	public String getPROJ_AcaYear_Code() {
		return PROJ_AcaYear_Code;
	}

	public void setPROJ_AcaYear_Code(String pROJ_AcaYear_Code) {
		PROJ_AcaYear_Code = pROJ_AcaYear_Code;
	}

	public String getPROJ_Name() {
		return PROJ_Name;
	}

	public void setPROJ_Name(String pROJ_Name) {
		PROJ_Name = pROJ_Name;
	}

	public String getPROJ_ProjCat_Code() {
		return PROJ_ProjCat_Code;
	}

	public void setPROJ_ProjCat_Code(String pROJ_ProjCat_Code) {
		PROJ_ProjCat_Code = pROJ_ProjCat_Code;
	}

	public String getPROJ_StartDate() {
		return PROJ_StartDate;
	}

	public void setPROJ_StartDate(String pROJ_StartDate) {
		PROJ_StartDate = pROJ_StartDate;
	}

	public String getPROJ_EndDate() {
		return PROJ_EndDate;
	}

	public void setPROJ_EndDate(String pROJ_EndDate) {
		PROJ_EndDate = pROJ_EndDate;
	}

	public String getPROJ_Motivation() {
		return PROJ_Motivation;
	}

	public void setPROJ_Motivation(String pROJ_Motivation) {
		PROJ_Motivation = pROJ_Motivation;
	}

	public String getPROJ_Content() {
		return PROJ_Content;
	}

	public void setPROJ_Content(String pROJ_Content) {
		PROJ_Content = pROJ_Content;
	}

	public String getPROJ_Result() {
		return PROJ_Result;
	}

	public void setPROJ_Result(String pROJ_Result) {
		PROJ_Result = pROJ_Result;
	}

	public String getPROJ_SourceFile() {
		return PROJ_SourceFile;
	}

	public void setPROJ_SourceFile(String pROJ_SourceFile) {
		PROJ_SourceFile = pROJ_SourceFile;
	}

	public String getPROJ_Status_Code() {
		return PROJ_Status_Code;
	}

	public void setPROJ_Status_Code(String pROJ_Status_Code) {
		PROJ_Status_Code = pROJ_Status_Code;
	}

	public int getPROJ_TotalBudget() {
		return PROJ_TotalBudget;
	}

	public void setPROJ_TotalBudget(int pROJ_TotalBudget) {
		PROJ_TotalBudget = pROJ_TotalBudget;
	}

	public String getPROJ_User_Code() {
		return PROJ_User_Code;
	}

	public void setPROJ_User_Code(String pROJ_User_Code) {
		PROJ_User_Code = pROJ_User_Code;
	}

	public Set<mProducts> getProducts() {
		return products;
	}

	public void setProducts(Set<mProducts> products) {
		this.products = products;
	}

	public mStaffThread getStaff() {
		return staff;
	}

	public void setStaff(mStaffThread staff) {
		this.staff = staff;
	}
}
