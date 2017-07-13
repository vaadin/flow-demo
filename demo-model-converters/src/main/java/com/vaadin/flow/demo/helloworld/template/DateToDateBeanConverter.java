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

import java.util.Date;

import com.vaadin.flow.template.model.ModelConverter;

/**
 * Convert a {@link Date} to a {@link DateBean}.
 *
 * @author Vaadin Ltd
 *
 */
public class DateToDateBeanConverter implements ModelConverter<Date, DateBean> {

    @Override
    public Class<Date> getApplicationType() {
        return Date.class;
    }

    @Override
    public Class<DateBean> getModelType() {
        return DateBean.class;
    }

    @Override
    public DateBean toModel(Date applicationValue) {
        if (applicationValue == null) {
            return null;
        }
        DateBean bean = new DateBean();
        bean.setDay(Integer.toString(applicationValue.getDay()));
        bean.setMonth(Integer.toString(applicationValue.getMonth() + 1));
        bean.setYear(Integer.toString(applicationValue.getYear() + 1900));
        return bean;
    }

    @Override
    public Date toApplication(DateBean modelValue) {
        if (modelValue == null) {
            return null;
        }
        int year = Integer.parseInt(modelValue.getYear()) - 1900;
        int day = Integer.parseInt(modelValue.getDay());
        int month = Integer.parseInt(modelValue.getMonth()) - 1;
        return new Date(year, month, day);
    }

}
