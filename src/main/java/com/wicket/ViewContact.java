package com.wicket;

import com.wicket.model.Contact;

import org.apache.wicket.extensions.markup.html.basic.SmartLinkLabel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;

/**
 * View contact
 */
public class ViewContact extends BasePage {

    public ViewContact(final Long contactId) {
    	super(true,-1,null,-1);
        setModel(new CompoundPropertyModel(new LoadableDetachableModel() {
            public Object load() {
                return WicketApplication.get().getContactDao().get(contactId);
            }
        }));

        add(new Label("firstName"));
        add(new Label("lastName"));
        add(new SmartLinkLabel("email"));
        add(new Label("notes"));
        add(new Label("group"));

        add(new Link("edit", getModel()) {
            public void onClick() {
                Contact c = (Contact) getModelObject();
                setResponsePage(new EditContact(c.getId()));
            }
        });
        add(new Link("delete", getModel()) {
            public void onClick() {
                Contact c = (Contact) getModelObject();
                WicketApplication.get().getContactDao().delete(c.getId());
                setResponsePage(ListContacts.class);
            }
        });
    }

}
