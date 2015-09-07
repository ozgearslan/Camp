package tr.org.lkd.lyk2015.camp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tr.org.lkd.lyk2015.camp.dao.UserDao;
import tr.org.lkd.lyk2015.camp.model.AbstractUser;

@Transactional
@Service
public class UserDetailsImpl implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		AbstractUser abstractUser = this.userDao.findByEmail(s);
		if (abstractUser == null) {
			throw new UsernameNotFoundException("no such user");
		}
		return abstractUser;

	}

}
