package com.wicket.dao;

import java.util.List;

import javax.sql.DataSource;

import org.hibernate.Query;
import org.hibernate.Session;

import com.wicket.dao.SessionFactoryUtil;
import com.wicket.model.Contact;

/**
 * PostgreSQL implementation of the ContactDao. It uses Hibernate stuff to make
 * things simple.
 */
public class PgHbContactDao implements ContactDao {

	public void setDataSource(DataSource ds) {
	}

	public void save(Contact c) {
		Session session = SessionFactoryUtil.getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		if (c.getId() == null) {
			session.save(c);
		} else {
			Query query = session
					.createQuery("update Contact set firstName =:newFN, lastName = :newLN, email = :newEm, notes = :newN, _group = :newG " // "
							+ " where id = :contactId");
			query.setParameter("contactId", c.getId());
			query.setParameter("newFN", c.getFirstName());
			query.setParameter("newLN", c.getLastName());
			query.setParameter("newEm", c.getEmail());
			query.setParameter("newN", c.getNotes());
			query.setParameter("newG", c.getGroup());
			// int result =
			query.executeUpdate();
		}
		session.getTransaction().commit();
	}

	public void delete(Long contactId) {
		Session session = SessionFactoryUtil.getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session
				.createQuery("delete Contact where id = :contactId");
		query.setParameter("contactId", contactId);
		query.executeUpdate();
		session.getTransaction().commit();
	}

	public Contact get(Long contactId) {
		Session session = SessionFactoryUtil.getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("from Contact where id=:contactId ");
		query.setParameter("contactId", contactId);
		List<Contact> list = query.list();
		session.getTransaction().commit();
		return list.get(0);
	}

	public List<Contact> find(String searchString, String goToPage) {
		final String ss = (searchString == null || searchString.length() == 0) ? "%"
				: searchString + "%";
		List<Contact> list = null;
		int page = Integer.parseInt(goToPage);
		Session session = SessionFactoryUtil.getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session
				.createQuery(
						"from Contact where (firstName like ?  or lastName like ? or email like ? or notes like ? or group like ?) order by lastName")
				.setString(0, ss).setString(1, ss).setString(2, ss)
				.setString(3, ss).setString(4, ss); // lastName, firstName
		int recs = (page - 1) * 25;
		query.setFirstResult(recs);
		query.setMaxResults(25);
		list = query.list();
		session.getTransaction().commit();
		return list;
	}

	public int PageCount(String searchString) {
		final String ss = (searchString == null || searchString.length() == 0) ? "%"
				: searchString + "%";
		Session session = SessionFactoryUtil.getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Query query = session
				.createQuery(
						"Select count(*) from Contact where (firstName like ?  or lastName like ? or email like ? or notes like ? or group like ?)")
				.setString(0, ss).setString(1, ss).setString(2, ss)
				.setString(3, ss).setString(4, ss);
		List<Long> list = query.list();
		session.getTransaction().commit();
		return Integer.parseInt(list.get(0).toString());
	}

}
