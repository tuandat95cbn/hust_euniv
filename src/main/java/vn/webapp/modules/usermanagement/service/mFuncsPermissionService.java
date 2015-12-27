/**
 * @author : HaTN 
 * @address : HUST K51
 * @modified : December 27th, 2015
 */
package vn.webapp.modules.usermanagement.service;

import java.util.List;

import vn.webapp.modules.usermanagement.model.mDepartment;
import vn.webapp.modules.usermanagement.model.mFuncsPermission;

public interface mFuncsPermissionService {
	/**
	 * 
	 * @return
	 */
	public List<mFuncsPermission> loadFunctionsPermissionByUserList(String sUserCode);
    
	/**
	 * 
	 * @param facultyCode
	 * @return
	 */
    public List<mDepartment> loadDepartmentListByFaculty(String facultyCode);
    
    /**
     * 
     * @param departmentCode
     * @param falcutyCode
     * @return
     */
    public mDepartment loadADepartmentByCodes(String departmentCode, String falcutyCode);
    
    /**
     * 
     * @param falcutyCode
     * @return
     */
    public List<mDepartment> loadADepartmentByFaculty(String falcutyCode);
}
