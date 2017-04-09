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
package com.vaadin.flow.demo.jquerytable.persistence;

import java.io.Serializable;

/**
 * Base class of all entities.
 */
public abstract class BaseEntity
        implements Serializable, Comparable<BaseEntity> {

    protected long id;

    /**
     * Gets the unique identifier of the entity.
     * 
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the entity.
     * 
     * @param id
     *            the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof BaseEntity) {
            BaseEntity that = (BaseEntity) obj;
            return id == that.id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }

    @Override
    public int compareTo(BaseEntity o) {
        return Long.compare(id, o.id);
    }

}
