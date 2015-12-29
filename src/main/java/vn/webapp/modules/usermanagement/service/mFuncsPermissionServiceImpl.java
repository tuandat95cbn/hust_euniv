/**
 * @author : HaTN 
 * @address : HUST K51
 * @modified : October 14th, 2015
 */
/**
 * @author : HaTN 
 * @address : HUST K51
 * @modified : December 27th, 2015
 */
package vn.webapp.modules.usermanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import vn.webapp.modules.usermanagement.dao.mDepartmentDAO;
import vn.webapp.modules.usermanagement.dao.mFuncsPermissionDAO;
import vn.webapp.modules.usermanagement.dao.mFunctionsDAO;
import vn.webapp.modules.usermanagement.model.mDepartment;
import vn.webapp.modules.usermanagement.model.mFuncsPermission;
import vn.webapp.modules.usermanagement.model.mFunction;

@Service("mFuncsPermissionService")
public class mFuncsPermissionServiceImpl implements mFuncsPermissionService {
	
	@Autowired
    private mDepartmentDAO departmentDAO;
	
	@Autowired
    private mFuncsPermissionDAO funcsPermissionDAO;
	
	@Autowired
    private mFunctionsDAO functionsDAO;

    /**
     * Get permissions by user code
     * @param String
     * @return object
     * @throws UsernameNotFoundException
     */
    @Override
    public List<mFuncsPermission> loadFunctionsPermissionByUserList(String sUserCode) {
        try {
            return funcsPermissionDAO.loadFunctionsPermissionByUserList(sUserCode);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get function list
     * @param String
     * @return object
     * @throws UsernameNotFoundException
     */
    @Override
    public List<mFunction> loadFunctionsList(){
    	try {
    		return functionsDAO.loadFunctionsList();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return null;
        }
    }

}
