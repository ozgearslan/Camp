package tr.org.lkd.lyk2015.camp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import tr.org.lkd.lyk2015.camp.model.Admin;
import tr.org.lkd.lyk2015.camp.model.Course;
import tr.org.lkd.lyk2015.camp.model.Instructor;
import tr.org.lkd.lyk2015.camp.service.CourseService;

@Controller
@RequestMapping("/courses")
public class CourseController {
	
	@Autowired
	private CourseService courseService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
    public String list(Model model) {
        List<Course> courses = courseService.getAll();

        model.addAttribute("courseList",courses );
        return "admin/listCourse";
    }
	
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createForm(@ModelAttribute Course course) {
		
		return "admin/createCourseForm";
	}

	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@ModelAttribute  Course course  ) {
		
		
		courseService.create(course);

		return "redirect:/courses";
	}
	
	
	 @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	    public String editGet(@PathVariable("id") Long id,
	                     
	                          Model model) {

	        Course course = courseService.getById(id);
	        model.addAttribute("course", course);
	      

	        return "admin/updateCourseForm";
	    }
	    
	    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
	    public String editPost(@PathVariable("id") Long id, @ModelAttribute Course course, Model model) {

	        courseService.update(course);
	        return "redirect:/courses";
	    }

	
	
	
}
