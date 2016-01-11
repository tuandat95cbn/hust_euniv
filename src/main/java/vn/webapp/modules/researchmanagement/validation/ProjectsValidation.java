package vn.webapp.modules.researchmanagement.validation;

import java.util.List;

import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;
import vn.webapp.validation.UploadFileMaxSize;

public class ProjectsValidation{
	/** Set rules for fields */
	private int projectId;

	@NotEmpty
	private String projectName;

	private String projectContent;

	private String projectMotivation;

	private String projectResult;
	
	private int projectBudget;
	
	@NotEmpty
	private String projectCallCode;

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectContent() {
		return projectContent;
	}

	public void setProjectContent(String projectContent) {
		this.projectContent = projectContent;
	}

	public String getProjectMotivation() {
		return projectMotivation;
	}

	public void setProjectMotivation(String projectMotivation) {
		this.projectMotivation = projectMotivation;
	}

	public String getProjectResult() {
		return projectResult;
	}

	public void setProjectResult(String projectResult) {
		this.projectResult = projectResult;
	}

	public int getProjectBudget() {
		return projectBudget;
	}

	public void setProjectBudget(int projectBudget) {
		this.projectBudget = projectBudget;
	}

	public String getProjectCallCode() {
		return projectCallCode;
	}

	public void setProjectCallCode(String projectCallCode) {
		this.projectCallCode = projectCallCode;
	}

}
