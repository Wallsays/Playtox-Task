package com.wicket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;

public class NavigatePanel extends Panel {
	private boolean isHomeLinkVisible;
	private int pageCount, correntPage;
	private String searchString;

	public NavigatePanel(String id, boolean homeLinkVisible, int pageCount,
			String searchString, int correntPage) {
		super(id);

		setIsHomeLinkVisible(homeLinkVisible);
		setPageCount(pageCount);
		setSearchString(searchString);
		setCorrentPage(correntPage);

		add(new NavigateForm("navigateForm"));
	}

	public void setIsHomeLinkVisible(boolean homeLinkVisible) {
		this.isHomeLinkVisible = homeLinkVisible;
	}

	public boolean getHomeLinkVisible() {
		return this.isHomeLinkVisible;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageCount() {
		return this.pageCount;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public int getCorrentPage() {
		return correntPage;
	}

	public String getCorrentPageToString() {
		return this.searchString.toString();
	}

	public void setCorrentPage(int correntPage) {

		this.correntPage = correntPage;
	}

	private class NavigateForm extends Form {
		private int NAVIGATE_PAGE_COUNT = 10;

		public NavigateForm(String id) {
			super(id);

			StringBuilder pageName = new StringBuilder("");
			switch (getCorrentPage()) {
			case 0:
				pageName.append("Главная");
				break;
			case -1:
				pageName.append("");
				break;
			case -2:
				pageName.append("Поиск: " + getSearchString());
				break;
			default:
				pageName.append("Страница: "
						+ ((Integer) getCorrentPage()).toString());
				break;
			}

			add(new Label("correntPage", pageName.toString()));
			add(new Link("home", getModel()) {
				public void onClick() {
					setResponsePage(ListContacts.class);
				}
			}.setVisible(isHomeLinkVisible));

			List<Integer> list = new ArrayList<Integer>(Arrays.asList(1));

			if (getPageCount() == -1) {
			} else {
				float preI = (getPageCount() > 0) ? (getPageCount())
						: WicketApplication.get().getContactDao()
								.PageCount(getSearchString());
				int i = 0;
				if (preI % 25 == 0) {
					i = (int) (preI / 25);
				} else {
					i = (int) (preI / 25);
					i++;
				}

				int j = 0;
				if (i > NAVIGATE_PAGE_COUNT) {
					int correntPage = getCorrentPage();
					if (correntPage <= NAVIGATE_PAGE_COUNT / 2) {
						i = NAVIGATE_PAGE_COUNT;
						j = 0;
					} else {
						if ((i - correntPage) >= NAVIGATE_PAGE_COUNT) {
							i = NAVIGATE_PAGE_COUNT;
							j = correntPage - NAVIGATE_PAGE_COUNT / 2;
						} else {
							j = i - NAVIGATE_PAGE_COUNT;
							i = NAVIGATE_PAGE_COUNT;
						}

					}
				}

				list = new ArrayList<Integer>();
				while (i > 0) {
					j++;
					list.add(j);
					i--;

				}
			}

			ListView listview = new ListView("listview", list) {
				protected void populateItem(ListItem item) {
					Label l = new Label("myl", item.getModel());
					Link view = new Link("label", item.getModel()) {
						public void onClick() {
							Integer pageNumber = (Integer) getModelObject();
							PageParameters pageParameters = new PageParameters();
							pageParameters.add("pageNumber",
									pageNumber.toString());
							pageParameters.add("searchString",
									getSearchString());
							setResponsePage(new ListContacts(pageParameters));
						}
					};
					view.add(l);
					if (getPageCount() == -1) {
						view.setVisible(false);
					}
					item.add(view);
				}
			};
			add(listview);

			setMarkupId("navigate-form");
		}
	}
}
