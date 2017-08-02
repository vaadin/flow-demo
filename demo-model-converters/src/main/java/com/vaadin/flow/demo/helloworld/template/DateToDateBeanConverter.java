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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.vaadin.flow.template.model.ModelConverter;

/**
 * Convert a {@link Date} to a {@link DateBean}.
 *
 * @author Vaadin Ltd
 *
 */
public class DateToDateBeanConverter implements ModelConverter<Date, DateBean> {

    @Override
    public DateBean toPresentation(Date modelValue) {
        if (modelValue == null) {
            return null;
        }
        DateBean bean = new DateBean();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(modelValue);
        bean.setDay(Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)));
        bean.setMonth(Integer.toString(calendar.get(Calendar.MONTH) + 1));
        bean.setYear(Integer.toString(calendar.get(Calendar.YEAR)));
        return bean;
    }

    @Override
    public Date toModel(DateBean presentationValue) {
        if (presentationValue == null) {
            return null;
        }
        int year = Integer.parseInt(presentationValue.getYear());
        int day = Integer.parseInt(presentationValue.getDay());
        int month = Integer.parseInt(presentationValue.getMonth()) - 1;
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }

}
