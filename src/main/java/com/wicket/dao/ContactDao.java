package com.wicket.dao;

import com.wicket.model.Contact;

import javax.sql.DataSource;
import java.util.List;

/**
 * Persistence interface for the Contact object.
 */
public interface ContactDao {

    void setDataSource(DataSource ds);

    void save(Contact c);

    void delete(Long contactId);

    Contact get(Long contactId);

    List<Contact> find(String searchString, String goToPage);

    int PageCount(String searchString);

}
