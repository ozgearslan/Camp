package tr.org.lkd.lyk2015.camp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import tr.org.lkd.lyk2015.camp.model.AbstractUser;
import tr.org.lkd.lyk2015.camp.model.Admin;
import tr.org.lkd.lyk2015.camp.service.AdminService;

@Controller
@RequestMapping("/admins")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
    public String list(Model model) {
        List<Admin> admins = adminService.getAll();

        model.addAttribute("adminList",admins );
        return "admin/listAdmin";
    }
	
	
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createForm(@ModelAttribute Admin admin) {
		
		return "admin/createAdminForm";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@ModelAttribute  Admin admin,@RequestParam("passwordAgain") String passwordAgain ) {
		if(!passwordAgain.equals(admin.getPassword()))
		{
			
		}
		
		adminService.create(admin);

		return "redirect:/admins";
	}
	
	
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String editGet(@PathVariable("id") Long id,
                     
                          Model model) {

        Admin admin = adminService.getById(id);
        model.addAttribute("admin", admin);
      

        return "admin/updateAdminForm";
    }
    
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String editPost(@PathVariable("id") Long id, @ModelAttribute Admin admin, Model model) {

        adminService.update(admin);
        return "redirect:/admins";
    }

}
