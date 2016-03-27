package vn.webapp.modules.researchdeclarationmanagement.model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tblpapercategory")
public class mPaperCategory implements Serializable{
	@Id
    @GeneratedValue
    private int PCAT_ID;
    private String PCAT_Name;
    private String PCAT_Description;
    private String PCAT_Code;
    private String PCAT_Journal;
    private Integer PCAT_ConvertedHours;
    private int PCAT_Budget;
    
    @OneToMany(mappedBy="paperCategory")
    private Set<mPapers> papers;
    
    @OneToMany(mappedBy="paperCate")
    private Set<mPapersCategoryHourBudget> papersCategoryHourBudget;

	public int getPCAT_ID() {
		return PCAT_ID;
	}

	public void setPCAT_ID(int pCAT_ID) {
		PCAT_ID = pCAT_ID;
	}

	public String getPCAT_Name() {
		return PCAT_Name;
	}

	public void setPCAT_Name(String pCAT_Name) {
		PCAT_Name = pCAT_Name;
	}

	public String getPCAT_Description() {
		return PCAT_Description;
	}

	public void setPCAT_Description(String pCAT_Description) {
		PCAT_Description = pCAT_Description;
	}

	public String getPCAT_Code() {
		return PCAT_Code;
	}

	public void setPCAT_Code(String pCAT_Code) {
		PCAT_Code = pCAT_Code;
	}

	public String getPCAT_Journal() {
		return PCAT_Journal;
	}

	public void setPCAT_Journal(String pCAT_Journal) {
		PCAT_Journal = pCAT_Journal;
	}

	public Set<mPapers> getPapers() {
		return papers;
	}

	public void setPapers(Set<mPapers> papers) {
		this.papers = papers;
	}

	public Integer getPCAT_ConvertedHours() {
		return PCAT_ConvertedHours;
	}

	public void setPCAT_ConvertedHours(Integer pCAT_ConvertedHours) {
		PCAT_ConvertedHours = pCAT_ConvertedHours;
	}

	public int getPCAT_Budget() {
		return PCAT_Budget;
	}

	public void setPCAT_Budget(int pCAT_Budget) {
		PCAT_Budget = pCAT_Budget;
	}

	public Set<mPapersCategoryHourBudget> getPapersCategoryHourBudget() {
		return papersCategoryHourBudget;
	}

	public void setPapersCategoryHourBudget(
			Set<mPapersCategoryHourBudget> papersCategoryHourBudget) {
		this.papersCategoryHourBudget = papersCategoryHourBudget;
	}
	
}
