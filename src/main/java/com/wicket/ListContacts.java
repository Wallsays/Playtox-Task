package com.wicket;

import java.util.List;

import com.wicket.model.Contact;

import org.apache.wicket.PageParameters;
import org.apache.wicket.extensions.markup.html.basic.SmartLinkLabel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;

/**
 * Shows contacts && search results
 */
public class ListContacts extends BasePage {

	public ListContacts(PageParameters params) {
		super((params.getString("searchString") == null) ? false : true,
				WicketApplication.get().getContactDao()
						.PageCount(params.getString("searchString")), params
						.getString("searchString"), (params
						.getString("pageNumber") == null) ? ((params
						.getString("searchString") == null) ? 0 : -2) : Integer
						.parseInt(params.getString("pageNumber")));
		final String goToPage = (params.getString("pageNumber") == null) ? "1"
				: params.getString("pageNumber");
		final String searchString = params.getString("searchString");
		IModel contactsModel = new LoadableDetachableModel() {
			protected Object load() {
				return WicketApplication.get().getContactDao()
						.find(searchString, goToPage);
			}
		};

		ListView contacts = new ListView("contacts", contactsModel) {
			protected void populateItem(ListItem item) {
				Link view = new Link("view", item.getModel()) {
					public void onClick() {
						Contact c = (Contact) getModelObject();
						setResponsePage(new ViewContact(c.getId()));
					}
				};
				view.add(new Label("firstName", new PropertyModel(item
						.getModel(), "firstName")));
				view.add(new Label("lastName", new PropertyModel(item
						.getModel(), "lastName")));
				item.add(view);

				item.add(new SmartLinkLabel("email", new PropertyModel(item
						.getModel(), "email")));
				item.add(new Link("edit", item.getModel()) {
					public void onClick() {
						Contact c = (Contact) getModelObject();
						setResponsePage(new EditContact(c.getId()));
					}
				});
				item.add(new Link("delete", item.getModel()) {
					public void onClick() {
						Contact c = (Contact) getModelObject();
						WicketApplication.get().getContactDao()
								.delete(c.getId());
						PageParameters pageParameters = new PageParameters();
						pageParameters.add("pageNumber", goToPage);
						pageParameters.add("searchString", searchString);
						setResponsePage(new ListContacts(pageParameters));
					}
				});
			}
		};

		Label lbl = null;
		if (contacts.getViewSize() == 0) {
			lbl = new Label("searchResult",
					"По вашему запросу ничего не найдено");
		} else {
			lbl = new Label("searchResult", "");
		}
		add(lbl);

		add(contacts);
	}
}
