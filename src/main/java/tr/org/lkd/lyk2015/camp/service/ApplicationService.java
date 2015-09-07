package tr.org.lkd.lyk2015.camp.service;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import tr.org.lkd.lyk2015.camp.dao.ApplicationDao;
import tr.org.lkd.lyk2015.camp.dao.CourseDao;
import tr.org.lkd.lyk2015.camp.dao.StudentDao;
import tr.org.lkd.lyk2015.camp.model.Application;
import tr.org.lkd.lyk2015.camp.model.Course;
import tr.org.lkd.lyk2015.camp.model.Student;
import tr.org.lkd.lyk2015.camp.model.dto.ApplicationFormDto;

@Service
@Transactional // kendi databasimizden bilgi çekecek sessionu ouþturur
public class ApplicationService extends GenericService<Application> {

	@Autowired
	private EmailService emailService;

	@Autowired
	private ApplicationDao applicationDao;

	@Autowired
	private CourseDao courseDao;

	@Autowired
	private CourseService courseService;

	@Autowired
	private StudentDao studentDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private static final String URL_BASE = "htttp://ocalhost:8080/application/validate/";

	public void create(ApplicationFormDto applicationFormDto) {
		Application application = applicationFormDto.getApplication();
		Student student = applicationFormDto.getStudent();
		List<Long> courseIds = applicationFormDto.getPreferredCourseIds();

		// genereate password
		String password = UUID.randomUUID().toString();
		password = password.substring(0, 6);

		application.setYear(Calendar.getInstance().get(Calendar.YEAR));

		// Genereate email verification url
		String uuid = UUID.randomUUID().toString();
		String url = URL_BASE + uuid;

		String emailContent = "Dogrulamak icin týklayýnýz: " + url + "/parolanýz: " + password;
		password = this.passwordEncoder.encode(password);

		// todo send verification email
		this.emailService.sendEmail(student.getEmail(), "basvuru onayý", url);

		application.setValidationId(uuid);
		// add prefered courses to application entitiy

		List<Course> courses = this.courseDao.getByIds(courseIds);
		application.getPreferredCourses().clear();
		application.getPreferredCourses().addAll(courses);
		// chech if user exists
		Student studentFromDb = this.studentDao.getUserByTckn(student.getTckn());

		if (studentFromDb == null) {
			student.setPassword(password);
			this.studentDao.create(student);
			studentFromDb = student;
		}

		else {
			studentFromDb.setPassword(password);

		}
		// set application user
		application.setOwner(studentFromDb);
		this.applicationDao.create(application);

	}

	public boolean validate(String uuid) {

		Application application = this.applicationDao.getByUuid(uuid);
		if (application == null) {
			return false;
		} else {
			application.setValidated(true);
			this.applicationDao.update(application);
			return true;
		}
	}

}
