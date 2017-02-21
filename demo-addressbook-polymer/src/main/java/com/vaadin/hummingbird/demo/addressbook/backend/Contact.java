/*
 * Copyright 2000-2016 Vaadin Ltd.
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
package com.vaadin.hummingbird.demo.addressbook.backend;

import elemental.json.Json;
import elemental.json.JsonObject;
import org.apache.commons.beanutils.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;

/**
 * A simple DTO for the address book example.
 *
 * Serializable and cloneable Java Object that are typically persisted in the
 * database and can also be easily converted to different formats like JSON.
 */
// Backend DTO class. This is just a typical Java backend implementation
// class and nothing Vaadin specific.
public class Contact implements Serializable {

    private Integer id;

    private String firstName = "";
    private String lastName = "";
    private String phoneNumber = "";
    private String email = "";
    private LocalDate birthDate;

    public Contact() {

    }

    public Contact(JsonObject jsonObject) {
        id = (int) jsonObject.getNumber("id");
        firstName = jsonObject.getString("firstName");
        lastName = jsonObject.getString("lastName");
        phoneNumber = jsonObject.getString("phoneNumber");
        email = jsonObject.getString("email");
        birthDate = null;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name.
     *
     * @param firstName
     *            the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name.
     *
     * @param lastName
     *            the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the phone number.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number.
     *
     * @param phoneNumber
     *            the phone number to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the birth date.
     *
     * @return the birth date
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Sets the birth date.
     *
     * @param birthDate
     *            the birth date to set
     */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Makes a copy of the instance.
     *
     * @return the instance copy
     */
    public Contact copy() {
        try {
            return (Contact) BeanUtils.cloneBean(this);
        } catch (IllegalAccessException | InstantiationException
                | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Unable to clone the instance", e);
        }
    }

    @Override
    public String toString() {
        return "Contact{" + "id=" + id + ", firstName=" + firstName
                + ", lastName=" + lastName + ", phoneNumber=" + phoneNumber
                + ", email=" + email + ", birthDate=" + birthDate + '}';
    }

    public JsonObject toJsonObject() {
        JsonObject jsonContact = Json.createObject();
        jsonContact.put("id", id);
        jsonContact.put("firstName", firstName);
        jsonContact.put("lastName", lastName);
        jsonContact.put("phoneNumber", phoneNumber);
        jsonContact.put("email", email);
        return jsonContact;
    }
}
