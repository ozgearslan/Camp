package tr.org.lkd.lyk2015.camp.controller.validator;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import tr.org.lkd.lyk2015.camp.model.Application.WorkStatus;
import tr.org.lkd.lyk2015.camp.model.Student;
import tr.org.lkd.lyk2015.camp.model.dto.ApplicationFormDto;
import tr.org.lkd.lyk2015.camp.service.BlackListValidationService;
import tr.org.lkd.lyk2015.camp.service.EmailService;
import tr.org.lkd.lyk2015.camp.service.ExamValidationService;
import tr.org.lkd.lyk2015.camp.service.TcknValidationService;

@Component
public class ApplicationFormValidator implements Validator {

	@Autowired
	TcknValidationService tcknValidationService;

	@Autowired
	BlackListValidationService blackListValidationService;

	@Autowired
	ExamValidationService examValidationService;

	@Autowired
	private EmailService emailService;

	@Override
	public boolean supports(Class<?> clazz) {

		return clazz.equals(ApplicationFormDto.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stubA
		ApplicationFormDto application = (ApplicationFormDto) target;
		System.out.println("===== validation====");
		System.out.println(application);

		if (application.getApplication().getWorkStatus() == WorkStatus.NOT_WORKING
				&& application.getApplication().isOfficer() == true) {
			errors.rejectValue("workStatus", "error.notWorkingOficcer", "Hem çaışmayıp hem nasıl memnunsun");
		}

		// check course selection size
		application.getPreferredCourseIds().removeAll(Collections.singleton(null));
		if (application.getPreferredCourseIds().size() == 0) {
			errors.rejectValue("PreferredCourseIds", "error.preferredCourseNoSeection", "En az bir kurs seçmelisiniz");
		}

		// prevent same course selection
		int listSize = application.getPreferredCourseIds().size();
		Set<Long> set = new HashSet<>(application.getPreferredCourseIds());
		int setSize = set.size();

		if (listSize != setSize) {
			errors.rejectValue("PreferredCourseIds", "error.preferredCourseSameSelection",
					"Aynı ursu bir kez seçebilirsin");
		}

		// Validate Tckn frm web service
		Student student = application.getStudent();
		boolean tcknValid = this.tcknValidationService.validate(student.getName(), student.getSurname(),
				student.getBirthDate(), student.getTckn());

		if (!tcknValid) {
			errors.rejectValue("student.tckn", "error.tcknInvalid", "TC Kimlik Numarasi Hatali");
		}

		// blacklist
		boolean blackListValid = this.blackListValidationService.validate(student.getName(), student.getSurname(),
				student.getTckn(), student.getEmail());

		if (!blackListValid) {
			errors.rejectValue("student.tckn", "error.blackListInVaid", "Kara Listedesiniz");
		}

		// exam

		boolean examValid = this.examValidationService.validate(student.getTckn(), student.getEmail());
		if (!examValid) {
			errors.rejectValue("student.email", "error.examFail", "Sinavi gecemediniz");
		}

		// email
		boolean sendMail = this.emailService.sendEmail(student.getEmail(), "confirmation", "buraya tikla");
		if (!sendMail) {
			errors.rejectValue("student.email", "error.checkConfirmation", "Email Gönderilemedi!");
		}
	}
}
