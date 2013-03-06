package com.wicket;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

public class SearchPanel extends Panel {

    public SearchPanel(String id) {
        super(id);
        add(new SearchForm("searchForm"));
    }

    private class SearchForm extends Form {

        private String searchString;
        
        public SearchForm(String id) {
            super(id);
            add(new BookmarkablePageLink("addContact", EditContact.class));
            add(new TextField("searchString", new PropertyModel(this, "searchString")));
            setMarkupId("search-form");
        }

        public void onSubmit() {
            PageParameters params = new PageParameters();
            params.add("searchString", getSearchString());
            setResponsePage(ListContacts.class, params);
        }

        public String getSearchString() {
            return searchString;
        }

        public void setSearchString(String searchString) {
            this.searchString = searchString;
        }
    }

}
