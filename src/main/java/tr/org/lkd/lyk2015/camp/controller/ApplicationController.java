package tr.org.lkd.lyk2015.camp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tr.org.lkd.lyk2015.camp.controller.validator.ApplicationFormValidator;
import tr.org.lkd.lyk2015.camp.model.Application;
import tr.org.lkd.lyk2015.camp.model.Course;
import tr.org.lkd.lyk2015.camp.model.dto.ApplicationFormDto;
import tr.org.lkd.lyk2015.camp.service.ApplicationService;
import tr.org.lkd.lyk2015.camp.service.CourseService;

@Controller
@RequestMapping("/application")
public class ApplicationController {

	@Autowired
	private ApplicationFormValidator applicationFormValidator;

	@Autowired
	private CourseService courseService;

	@Autowired
	private ApplicationService applicationService;

	@InitBinder
	protected void InitBinder(final WebDataBinder binder) {
		binder.addValidators(this.applicationFormValidator);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String form(@ModelAttribute("form") ApplicationFormDto applicationFormDto, Model model) {

		List<Course> courses = this.courseService.getAll();
		model.addAttribute("courses", courses);

		return "applicationForm";
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public String postApplicationCreate(@ModelAttribute("form") @Valid ApplicationFormDto applicationFormDto,
			BindingResult bindingResult, Model model) {
		// bindingresult her zaman solundaki formun hatalarýný alýr

		if (bindingResult.hasErrors()) {
			model.addAttribute("courses", this.courseService.getAll());
			return "applicationForm";
		} else {
			this.applicationService.create(applicationFormDto);
			return "applicationSuccess";
			// returnde html'in yeri, redirectde url veriir
		}

	}

	@RequestMapping(value = "/validate/{uuid}", method = RequestMethod.GET)
	public String emailForm(@PathVariable String uuid, @ModelAttribute("form") ApplicationFormDto applicationFormDto,
			Model model) {

		if (this.applicationService.validate(uuid)) {
			model.addAttribute("message", "email onayýnýz yapýldý");
		} else {
			model.addAttribute("message", "email onayýnýz baþarýsýz");
		}
		return "validated";
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {

		List<Application> applications = this.applicationService.getAll();

		model.addAttribute("applicationList", applications);
		return "listApplication";
	}

}
