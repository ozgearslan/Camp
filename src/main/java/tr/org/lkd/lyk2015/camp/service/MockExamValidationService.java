package tr.org.lkd.lyk2015.camp.service;

import org.springframework.stereotype.Service;

@Service
public class MockExamValidationService implements ExamValidationService {

	@Override
	public boolean validate(Long tckn, String email) {
		// TODO Auto-generated method stub
		if (email.equals("ozge@gmail.com")) {
			return false;

		}
		return true;

	}

}