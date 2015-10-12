package com.vaadin.hummingbird;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

public class NavView extends HorizontalLayout {
	public static final String ABOUT = "About";
	public static final String MAP = "Map";
	public static final String CUSTOMERS = "Customers";
	public static final String ANALYZE = "Analyze";

	public static final List<String> pages = new ArrayList<String>() {
		{
			add(ABOUT);
			add(MAP);
			add(CUSTOMERS);
			add(ANALYZE);
		}
	};
	public static final Map<String, URL> linkMap;

	static {
		Map<String, URL> links = new HashMap<String, URL>();
		try {
			links.put(ABOUT, new URL("http://./About"));
			links.put(MAP, new URL("http://./Map"));
			links.put(CUSTOMERS, new URL("http://./Customers"));
			links.put(ANALYZE, new URL("http://./Analyze"));
		} catch (MalformedURLException me) {
			throw new RuntimeException(me);
		}
		linkMap = Collections.unmodifiableMap(links);
	}

	private MenuBar buildMenu() {
		final MenuBar bar = new MenuBar();
		return bar;
	}

	public NavView(AbstractComponent content) {
		super();
		MenuBar mb = buildMenu();
		Command command = new Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
				addComponent(new Label(selectedItem.getText() + " clicked"));
			}
		};
		for (String page : pages) {
			final MenuItem pageItem = mb.addItem(page, null);
			pageItem.addItem("Foo", command);
			pageItem.addItem("Bar", command);
		}
		addComponent(new Label("before menubar"));
		addComponent(mb);
		addComponent(content);
		addStyleName("simplecrm");
	}

}