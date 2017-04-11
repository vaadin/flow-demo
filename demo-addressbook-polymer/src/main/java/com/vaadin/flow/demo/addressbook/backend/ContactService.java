/*
 * Copyright 2000-2017 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.flow.demo.addressbook.backend;

import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Separate Java service class. Backend implementation for the address book
 * application, with "detached entities" simulating real world DAO. Typically
 * these something that the Java EE or Spring backend services provide.
 */
// Backend service class. This is just a typical Java backend implementation
// class and nothing Vaadin specific.
public class ContactService {

    // Create dummy data by randomly combining first and last names
    static String[] fnames = { "Peter", "Alice", "John", "Mike", "Olivia",
            "Nina", "Alex", "Rita", "Dan", "Umberto", "Henrik", "Rene", "Lisa",
            "Linda", "Timothy", "Daniel", "Brian", "George", "Scott",
            "Jennifer" };
    static String[] lnames = { "Smith", "Johnson", "Williams", "Jones", "Brown",
            "Davis", "Miller", "Wilson", "Moore", "Taylor", "Anderson",
            "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson",
            "Young", "King", "Robinson" };

    private static final ContactService INSTANCE = new ContactService();

    private LinkedHashMap<Integer, Contact> contacts = new LinkedHashMap<>();
    private int nextId = 0;

    private ContactService() {
        Random random = new Random(0);
        LocalDate time = LocalDate.of(1930, Month.JANUARY, 1);
        for (int i = 0; i < 100; i++) {
            Contact contact = new Contact();
            contact.setFirstName(fnames[random.nextInt(fnames.length)]);
            contact.setLastName(lnames[random.nextInt(fnames.length)]);
            contact.setEmail(contact.getFirstName().toLowerCase(Locale.ENGLISH)
                    + "@" + contact.getLastName().toLowerCase(Locale.ENGLISH)
                    + ".com");
            contact.setPhoneNumber("+ 358 555 " + (100 + random.nextInt(900)));
            time = time.plusYears(random.nextInt(70));
            time = time.plusMonths(random.nextInt(11));
            time = time.plusDays(random.nextInt(27));
            contact.setBirthDate(time);
            save(contact);
        }
    }

    /**
     * Gets a mock service.
     *
     * @return the service
     */
    public static ContactService getDemoService() {
        return INSTANCE;
    }

    /**
     * Finds all contacts using the given string filter.
     *
     * @param stringFilter
     *            the filter to use, or <code>null</code>
     * @return all contacts matching the filter
     */
    public synchronized List<Contact> findAll(String stringFilter) {
        Stream<Contact> result = contacts.values().stream();
        if (stringFilter != null && !stringFilter.isEmpty()) {
            String filter = stringFilter.toLowerCase(Locale.ENGLISH);
            result = contacts.values().stream()
                    .filter(contact -> contactMatches(filter, contact));
        }
        return result.map(Contact::copy).collect(Collectors.toList());
    }

    /**
     * Retuns the contact matching the given ID or an empty optional.
     *
     * @param id
     *            the id of the contact to get
     * @return the contact matching the id or an empty optional if none found
     */
    public synchronized Optional<Contact> findById(Integer id) {
        Contact contact = contacts.get(id);
        return Optional.ofNullable(contact).map(Contact::copy);
    }

    /**
     * Gets the number of contacts.
     *
     * @return number of contacts
     */
    public synchronized long count() {
        return contacts.size();
    }

    /**
     * Deletes the given contact.
     *
     * @param value
     *            the contact to delete, not <code>null</code>
     */
    public synchronized void delete(Contact value) {
        contacts.remove(value.getId());
    }

    /**
     * Saves the given contact.
     *
     * @param entry
     *            the contact to save
     * @return the saved contact
     */
    public synchronized Contact save(Contact entry) {
        if (entry.getId() == null) {
            entry.setId(nextId);
            nextId++;
        }
        contacts.put(entry.getId(), entry);

        return entry;
    }

    private static boolean contactMatches(String filter, Contact contact) {
        return contact.getEmail().toLowerCase(Locale.ENGLISH).contains(filter)
                || contact.getFirstName().toLowerCase(Locale.ENGLISH)
                        .contains(filter)
                || contact.getLastName().toLowerCase(Locale.ENGLISH)
                        .contains(filter)
                || contact.getPhoneNumber().toLowerCase(Locale.ENGLISH)
                        .contains(filter);
    }
}
