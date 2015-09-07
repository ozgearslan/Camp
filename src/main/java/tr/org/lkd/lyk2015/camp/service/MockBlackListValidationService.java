package tr.org.lkd.lyk2015.camp.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

@Service
@Transactional
public class MockBlackListValidationService implements BlackListValidationService {

	@Override
	public boolean validate(String name, String surname, Long tckn, String email) {

		if (tckn.equals(22222222222L)) {
			return false;

		}
		return true;

	}

}
