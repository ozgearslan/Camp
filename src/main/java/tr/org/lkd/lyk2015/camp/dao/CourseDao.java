package tr.org.lkd.lyk2015.camp.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import tr.org.lkd.lyk2015.camp.dao.GenericDao;
import tr.org.lkd.lyk2015.camp.model.Course;


@Repository
public class CourseDao extends GenericDao<Course> {
	
	@Override
	public List<Course> getAll() {

		final Session session = sessionFactory.getCurrentSession();
		final Criteria criteria = session.createCriteria(type);
		criteria.add(Restrictions.eq("deleted", false));
		criteria.add(Restrictions.eq("active", true));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setFetchMode("*", FetchMode.JOIN);

		return criteria.list();
	}

	
	
	@SuppressWarnings("unchecked")
	public List<Course> getByIds(List<Long> ids) {
		// TODO Auto-generated method stub
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.in("id",ids));
		
		return criteria.list();
	}

}
