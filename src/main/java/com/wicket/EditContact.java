package com.wicket;

import com.wicket.model.Contact;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.apache.wicket.validation.validator.StringValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * Page used to create and edit contacts.  
 */
public class EditContact extends BasePage {

    public EditContact() {
    	super(true,-1,null,-1);
        setModel(new CompoundPropertyModel(new LoadableDetachableModel() {
            protected Object load() {
                return new Contact();
            }
        }));
        init();
    }

    public EditContact(final Long contactId) {
    	super(true,-1,null,-1);
        setModel(new CompoundPropertyModel(new LoadableDetachableModel() {
            protected Object load() {
                return WicketApplication.get().getContactDao().get(contactId);
            }
        }));
        init();
    }

    private void init() {
        add(new FeedbackPanel("feedback"));
        add(new ContactForm("form", getModel()));
    }

    private class ContactForm extends Form {

        public ContactForm(String id, IModel m) {
            super(id, m);

            TextField firstName = new TextField("firstName");
            firstName.setRequired(true);
            firstName.add(StringValidator.maximumLength(15));
            add(firstName);

            TextField lastName = new TextField("lastName");
            lastName.setRequired(true);
            lastName.add(StringValidator.maximumLength(20));
            add(lastName);

            TextField email = new TextField("email");
            email.add(StringValidator.maximumLength(150));
            email.add(EmailAddressValidator.getInstance());
            add(email);

            TextArea notes = new TextArea("notes");
            notes.add(StringValidator.maximumLength(500));
            add(notes);

            DropDownChoice group = new DropDownChoice("group");
            group.setChoices(new AbstractReadOnlyModel() {
                public Object getObject() {
                    List<String> l = new ArrayList<String>(3);
                    l.add("Друг");
                    l.add("Знакомый");
                    l.add("Некто");
                    return l;
                }
            });
            add(group);

            add(new Button("save") {
                public void onSubmit() {
                    Contact c = (Contact) getForm().getModelObject();
                    WicketApplication.get().getContactDao().save(c);
                    setResponsePage(ListContacts.class);
                }
            });
            add(new Button("cancel") {
                public void onSubmit() {
                    setResponsePage(ListContacts.class);
                }
            }.setDefaultFormProcessing(false));
        }
    }
}
