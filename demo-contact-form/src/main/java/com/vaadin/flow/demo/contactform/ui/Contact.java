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

package com.vaadin.flow.demo.contactform.ui;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

public class Contact implements Serializable, Cloneable {

    private String firstName = "";
    private String lastName = "";
    private String phone = "";
    private String email = "";
    private LocalDate birthDate;
    private boolean doNotCall;
    private Instant createdTimestamp;

    public Contact() {
        createdTimestamp = Instant.now();
    }

    public long getCreatedTimestamp() {
        return createdTimestamp.toEpochMilli();
    }

    public boolean isDoNotCall() {
        return doNotCall;
    }

    public void setDoNotCall(boolean doNotCall) {
        this.doNotCall = doNotCall;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "Contact{ firstName=" + firstName
                + ", lastName=" + lastName + ", phone=" + phone + ", email="
                + email + ", birthDate=" + birthDate + ", createdTimestamp="
                + createdTimestamp + ",doNotCall=" + doNotCall + '}';
    }

}
