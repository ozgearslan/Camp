package tr.org.lkd.lyk2015.camp.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tr.org.lkd.lyk2015.camp.model.Admin;
import tr.org.lkd.lyk2015.camp.model.Instructor;
import tr.org.lkd.lyk2015.camp.dao.GenericDao;

@Repository
public class InstructorDao extends GenericDao< Instructor> {
	
	public Instructor getByWithCourses(Long id){
		Criteria c= createCriteria();
		c.add(Restrictions.idEq(id));
		c.setFetchMode("courses", FetchMode.JOIN);
		
		return (Instructor) c.uniqueResult();
	}
	

}
