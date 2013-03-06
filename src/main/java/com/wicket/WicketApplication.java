package com.wicket;

import com.wicket.dao.ContactDao;
import com.wicket.dao.SessionFactoryUtil;
import com.wicket.model.Contact;

import org.apache.wicket.protocol.http.WebApplication;
import org.hibernate.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Application object for your web application.
 * <p/>
 * If you want to run this application without deploying, run the Start class.
 */
public class WicketApplication extends WebApplication {

	public Class getHomePage() {
		return ListContacts.class;
	}

	private ApplicationContext getContext() {
		return WebApplicationContextUtils
				.getRequiredWebApplicationContext(getServletContext());
	}

	public ContactDao getContactDao() {
		return (ContactDao) getContext().getBean("PgHbContactDao");
	}

	public static WicketApplication get() {
		return (WicketApplication) WebApplication.get();
	}

	protected void init() {
		super.init();
	}
}
