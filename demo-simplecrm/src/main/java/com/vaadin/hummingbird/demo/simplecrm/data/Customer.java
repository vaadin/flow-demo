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
package com.vaadin.hummingbird.demo.simplecrm.data;

import java.time.LocalDate;

import elemental.json.JsonObject;

public class Customer {
    private int id;
    private String firstName, lastName;
    private double lat, lon;
    private LocalDate birthDate;
    private String email;
    // private ??? estimate;
    private Gender gender;
    private boolean persisted;
    private int progress;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public boolean isPersisted() {
        return persisted;
    }

    public void setPersisted(boolean persisted) {
        this.persisted = persisted;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static Customer fromJson(JsonObject data) {
        Customer customer = new Customer();
        customer.id = (int) data.getNumber("id");
        customer.lat = data.getNumber("lat");
        customer.lon = data.getNumber("lon");
        // "birthDate": "1959-06-14",
        customer.email = data.getString("email");
        // "estimate": "[18.0,5.0,13.0,8.0,17.0]",
        customer.firstName = data.getString("firstName");
        customer.lastName = data.getString("lastName");
        customer.gender = Gender.from(data.getString("gender"));
        customer.persisted = data.getBoolean("persisted");
        customer.progress = (int) data.getNumber("progress");
        customer.status = data.getString("status");
        return customer;
    }

    @Override
    public Customer clone() {
        Customer c = new Customer();
        c.id = id;
        c.lat = lat;
        c.lon = lon;
        c.email = email;
        c.firstName = firstName;
        c.lastName = lastName;
        c.gender = gender;
        c.persisted = persisted;
        c.progress = progress;
        c.status = status;
        return c;

    }

}
