package vn.webapp.modules.usermanagement.controller.cp;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("cpmAuth")
@RequestMapping(value = "/cp/auth")
public class mAuthController {
	/**
     * 
     * @param model
     * @param request     
     * @return 
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap model, 
            HttpServletRequest request) {                       
        return "signin";
    }
}
