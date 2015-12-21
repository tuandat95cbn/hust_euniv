package vn.webapp.modules.researchdeclarationmanagement.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tblprojectsdeclaration")
public class mTopics implements Serializable{
	@Id
    @GeneratedValue
    private int PROJDECL_ID;
    private String PROJDECL_Name;
    private String PROJDECL_ProjCategory_Code;
    private String PROJDECL_User_Code;
    private int PROJDECL_Budget;
    private int PROJDECL_ConvertedHours;
    private Integer PROJDECL_AuthorConvertedHours;
    private int PROJDECL_Year;
    private String PROJDECL_ReportingAcademicDate;

     @ManyToOne
     @JoinColumn(name="PROJDECL_ProjCategory_Code", referencedColumnName = "PROJCAT_Code", insertable = false, updatable = false)
     public mTopicCategory topicCategory;

	public int getPROJDECL_ID() {
		return PROJDECL_ID;
	}

	public void setPROJDECL_ID(int pROJDECL_ID) {
		PROJDECL_ID = pROJDECL_ID;
	}
	
	public String getPROJDECL_Name() {
		return PROJDECL_Name;
	}

	public void setPROJDECL_Name(String pROJDECL_Name) {
		PROJDECL_Name = pROJDECL_Name;
	}

	public int getPROJDECL_Year() {
		return PROJDECL_Year;
	}

	public void setPROJDECL_Year(int pROJDECL_Year) {
		PROJDECL_Year = pROJDECL_Year;
	}

	public String getPROJDECL_ProjCategory_Code() {
		return PROJDECL_ProjCategory_Code;
	}

	public void setPROJDECL_ProjCategory_Code(String pROJDECL_ProjCategory_Code) {
		PROJDECL_ProjCategory_Code = pROJDECL_ProjCategory_Code;
	}

	public String getPROJDECL_User_Code() {
		return PROJDECL_User_Code;
	}

	public void setPROJDECL_User_Code(String pROJDECL_User_Code) {
		PROJDECL_User_Code = pROJDECL_User_Code;
	}

	public int getPROJDECL_Budget() {
		return PROJDECL_Budget;
	}

	public void setPROJDECL_Budget(int pROJDECL_Budget) {
		PROJDECL_Budget = pROJDECL_Budget;
	}

	public int getPROJDECL_ConvertedHours() {
		return PROJDECL_ConvertedHours;
	}

	public void setPROJDECL_ConvertedHours(int pROJDECL_ConvertedHours) {
		PROJDECL_ConvertedHours = pROJDECL_ConvertedHours;
	}

	public Integer getPROJDECL_AuthorConvertedHours() {
		return PROJDECL_AuthorConvertedHours;
	}

	public void setPROJDECL_AuthorConvertedHours(Integer pROJDECL_AuthorConvertedHours) {
		PROJDECL_AuthorConvertedHours = pROJDECL_AuthorConvertedHours;
	}

	public mTopicCategory getTopicCategory() {
		return topicCategory;
	}

	public void setTopicCategory(mTopicCategory topicCategory) {
		this.topicCategory = topicCategory;
	}

	public String getPROJDECL_ReportingAcademicDate() {
		return PROJDECL_ReportingAcademicDate;
	}

	public void setPROJDECL_ReportingAcademicDate(
			String pROJDECL_ReportingAcademicDate) {
		PROJDECL_ReportingAcademicDate = pROJDECL_ReportingAcademicDate;
	}
}
