package com.vaadin.hummingbird;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.vaadin.teemu.jsoncontainer.JsonContainer;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.filter.SimpleStringFilter;

public class CustomerData {
	
	private JsonContainer dataSource = null;
	
	public CustomerData() {
		InputStream is = this.getClass().getResourceAsStream("customers-snapshot.json");
		try {
			String json = IOUtils.toString(is, "UTF-8");
			dataSource = JsonContainer.Factory.newInstance(json);
			is.close();
		} catch (Exception e) {
			System.out.println("Creating DataSource exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void deleteCustomer(Object itemId) {
		dataSource.removeItem(itemId);
	}
	
	public void filterCustomers(String filterText) {
		dataSource.removeAllContainerFilters();
		if (filterText == null || ("".equals(filterText))) {
			return;
		}
		Filter filter1 = new SimpleStringFilter("lastName", filterText, true, false);
		Filter filter2 = new SimpleStringFilter("firstName", filterText, true, false);
		Filter filter3 = new SimpleStringFilter("email", filterText, true, false);
		Filter filter4 = new SimpleStringFilter("status", filterText, true, true);
		Or orFilters = new Or(filter1,filter2,filter3,filter4);
		dataSource.addContainerFilter(orFilters);

	}
	
	public Container.Indexed getDataSource() {
		return dataSource;
	}
	
	public Item getItem(Object itemId) {
		return dataSource.getItem(itemId);
	}
	
	public void modelFromItemId(Object itemId, CustomerModel cm) {
		Item item = dataSource.getItem(itemId);
		mapItemToModel(item, cm);
	}
	
	protected void updateCustomer(Object itemId, CustomerModel customer) {
		Item item = dataSource.getItem(itemId);
		mapModelToItem(customer, item);
	}
	
	private void mapItemToModel(Item item, CustomerModel cm) {
		cm.setEmail((String) item.getItemProperty("email").getValue());
		cm.setFirstName((String) item.getItemProperty("firstName").getValue());
		cm.setLastName((String) item.getItemProperty("lastName").getValue());
		cm.setBirthDate((String) item.getItemProperty("birthDate").getValue());
		cm.setGender((String) item.getItemProperty("gender").getValue());
		cm.setStatus((String) item.getItemProperty("status").getValue());
	}
	
	private void mapModelToItem(CustomerModel cm, Item item) {
		item.getItemProperty("email").setValue(cm.getEmail());
		item.getItemProperty("firstName").setValue(cm.getFirstName());
		item.getItemProperty("lastName").setValue(cm.getLastName());
		item.getItemProperty("birthDate").setValue(cm.getBirthDate());	
		item.getItemProperty("gender").setValue(cm.getGender());
		item.getItemProperty("status").setValue(cm.getStatus());
	}

}
