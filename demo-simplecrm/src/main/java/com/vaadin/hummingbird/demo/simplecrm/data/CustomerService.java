package com.vaadin.hummingbird.demo.simplecrm.data;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.io.IOUtils;

import elemental.json.Json;
import elemental.json.JsonArray;

public class CustomerService {

    private List<Customer> customers = new ArrayList<>();
    private static CustomerService INSTANCE = new CustomerService();

    private CustomerService() {
        InputStream is = this.getClass()
                .getResourceAsStream("customers-snapshot.json");
        try {
            String json = IOUtils.toString(is, "UTF-8");
            is.close();
            JsonArray customerArray = Json.instance().parse(json);
            for (int i = 0; i < customerArray.length(); i++) {
                Customer c = Customer.fromJson(customerArray.getObject(i));
                customers.add(c);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static CustomerService get() {
        return INSTANCE;
    }

    public synchronized void deleteCustomer(int customerId) {
        customers.stream().filter(customer -> customer.getId() == customerId)
                .findFirst().ifPresent(customers::remove);
    }

    public synchronized void filterCustomers(String filterText) {
        // customerDataSource.removeAllContainerFilters();
        // if (filterText == null || ("".equals(filterText))) {
        // return;
        // }
        // Filter filter1 = new SimpleStringFilter("lastName", filterText, true,
        // false);
        // Filter filter2 = new SimpleStringFilter("firstName", filterText,
        // true,
        // false);
        // Filter filter3 = new SimpleStringFilter("email", filterText, true,
        // false);
        // Filter filter4 = new SimpleStringFilter("status", filterText, true,
        // true);
        // Or orFilters = new Or(filter1, filter2, filter3, filter4);
        // customerDataSource.addContainerFilter(orFilters);

    }

    public LinkedHashMap<String, Integer> getStatusCounts() {
        HashMap<String, Integer> keyCounts = getKeyCounts("status");

        return orderMapKeys(keyCounts, "ImportedLead", "NotContacted",
                "Contacted", "ClosedLost", "Customer");
    }

    private static <T> LinkedHashMap<String, T> orderMapKeys(
            HashMap<String, T> map, String... keyOrder) {
        LinkedHashMap<String, T> orderedCounts = new LinkedHashMap<>();
        for (String key : keyOrder) {
            if (map.containsKey(key)) {
                orderedCounts.put(key, map.remove(key));
            }
        }
        orderedCounts.putAll(map);
        return orderedCounts;
    }

    // private List<HashMap<String, Object>> getAgeCategories() {
    // List<HashMap<String, Object>> categories = new ArrayList<>();
    // HashMap<String, Object> category = new HashMap<>();
    // category.put("start", 0);
    // category.put("end", 14);
    // category.put("label", "0-15");
    // categories.add(category);
    //
    // category = new HashMap<>();
    // category.put("start", 15);
    // category.put("end", 29);
    // category.put("label", "15-30");
    // categories.add(category);
    //
    // category = new HashMap<>();
    // category.put("start", 30);
    // category.put("end", 59);
    // category.put("label", "30-60");
    // categories.add(category);
    //
    // category = new HashMap<>();
    // category.put("start", 60);
    // category.put("end", 100);
    // category.put("label", "60-100");
    // categories.add(category);
    //
    // return categories;
    // }

    public LinkedHashMap<String, Integer> getAgesCounts() {
        // LinkedHashMap<String, Integer> result = new LinkedHashMap<>();
        // List<HashMap<String, Object>> categories = getAgeCategories();
        // Collection ids = getDataSource().getItemIds();
        // Iterator iter = ids.iterator();
        // while (iter.hasNext()) {
        // Object id = iter.next();
        // String birthDate = (String) getDataSource().getItem(id)
        // .getItemProperty("birthDate").getValue();
        // LocalDate fromString = LocalDate.parse(birthDate);
        // Period period = Period.between(fromString, LocalDate.now());
        // for (Iterator<HashMap<String, Object>> categoryIter = categories
        // .iterator(); categoryIter.hasNext();) {
        // HashMap<String, Object> category = categoryIter.next();
        // Integer start = (Integer) category.get("start");
        // Integer end = (Integer) category.get("end");
        // String label = (String) category.get("label");
        // if (period.getYears() >= start && period.getYears() <= end) {
        // if (!result.containsKey(label)) {
        // result.put(label, 1);
        // } else {
        // result.put(label, (result.get(label) + 1));
        // }
        // }
        // }
        //
        // }
        LinkedHashMap<String, Integer> sorted = new LinkedHashMap<>();
        // for (Iterator<HashMap<String, Object>> categoryIter = categories
        // .iterator(); categoryIter.hasNext();) {
        // HashMap<String, Object> category = categoryIter.next();
        // if (result.containsKey(category.get("label"))) {
        // Integer value = result.get(category.get("label"));
        // sorted.put((String) category.get("label"), value);
        // }
        // }
        return sorted;
    }

    private HashMap<String, Integer> getKeyCounts(String key) {
        HashMap<String, Integer> result = new HashMap<>();
        // Collection ids = getDataSource().getItemIds();
        // Iterator iter = ids.iterator();
        // while (iter.hasNext()) {
        // Object id = iter.next();
        // String value = (String) getDataSource().getItem(id)
        // .getItemProperty(key).getValue();
        // if (!result.containsKey(value)) {
        // result.put(value, 1);
        // } else {
        // result.put(value, (result.get(value) + 1));
        // }
        //
        // }
        return result;
    }

    public HashMap<String, Integer> getGenderCounts() {
        return getKeyCounts("gender");
    }

    public List<HashMap<String, Object>> getLocations() {
        List<HashMap<String, Object>> locations = new ArrayList<>();

        // Collection ids = getDataSource().getItemIds();
        // Iterator iter = ids.iterator();
        // while (iter.hasNext()) {
        // Object id = iter.next();
        // Item item = getDataSource().getItem(id);
        // Double lat = Double.parseDouble(
        // (String) item.getItemProperty("lat").getValue());
        // Double lon = Double.parseDouble(
        // (String) item.getItemProperty("lon").getValue());
        // String firstName = (String) item.getItemProperty("firstName")
        // .getValue();
        // String lastName = (String) item.getItemProperty("lastName")
        // .getValue();
        // String fullName = lastName + ", " + firstName;
        // HashMap<String, Object> loc = new HashMap<>();
        // loc.put("lat", lat);
        // loc.put("lon", lon);
        // loc.put("name", fullName);
        // locations.add(loc);
        // }

        return locations;
    }

    public void updateCustomer(int id, Customer customer) {
        Optional<Customer> storedCustomer = customers.stream()
                .filter(c -> c.getId() == id).findFirst();
        if (!storedCustomer.isPresent()) {
            return;
        }

        int i = customers.indexOf(storedCustomer.get());
        customers.set(i, customer);
    }

    public synchronized Stream<Customer> getCustomers() {
        return customers.stream().map(Customer::clone);
    }

    public synchronized Optional<Customer> getCustomer(int customerId) {
        return customers.stream()
                .filter(customer -> customer.getId() == customerId).findFirst()
                .map(Customer::clone);
    }
}
