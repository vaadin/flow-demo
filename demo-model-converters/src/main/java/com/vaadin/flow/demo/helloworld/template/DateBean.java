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
package com.vaadin.flow.demo.helloworld.template;

import java.io.Serializable;

/**
 * Bean which contains a birthday as a 3 separate properties: year, month and
 * day.
 *
 * @author Vaadin Ltd
 *
 */
public class DateBean implements Serializable {

    private String day;
    private String month;
    private String year;

    /**
     * Gets the birthday.
     *
     * @return the birthday
     */
    public String getDay() {
        return day;
    }

    /**
     * Sets the birthday.
     *
     * @param day
     *            the birthday.
     */
    public void setDay(String day) {
        this.day = day;
    }

    /**
     * Gets the month of birth date.
     *
     * @return the birthday
     */
    public String getMonth() {
        return month;
    }

    /**
     * Sets the month of the birth date.
     *
     * @param month
     *            the month of the birth date
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * Gets the year of the birth date.
     *
     * @return the year of the birth date
     */
    public String getYear() {
        return year;
    }

    /**
     * Sets the year of the birth date.
     *
     * @param year
     *            the year of the birth date
     */
    public void setYear(String year) {
        this.year = year;
    }

}
