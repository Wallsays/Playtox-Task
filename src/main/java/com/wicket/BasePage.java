package com.wicket;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.resources.StyleSheetReference;

/**
 * Base page extended by all pages in the example application.
 */
public class BasePage extends WebPage {

    public BasePage(boolean homeLinkVisible, int pageCount, String searchString, int correntPage) {
        add(new StyleSheetReference("stylesheet", BasePage.class, "styles.css"));
        add(new Label("header", "Адресная книга"));
        add(new SearchPanel("searchPanel"));
        add(new NavigatePanel("navigatePanel", homeLinkVisible, pageCount, searchString, correntPage));
    }

}
