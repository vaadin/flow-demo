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
package com.vaadin.hummingbird.demo.jquerytable.persistence;

import org.vaadin.bugrap.domain.BugrapRepository;

/**
 * Wrapper class of the {@link BugrapRepository} that ensures only a single
 * instance of the repository is used across the application.
 *
 */
public class BugrapPersistence {

    private static BugrapRepository repository;

    /**
     * Connects to the {@link BugrapRepository}. This method should be called
     * before any view is rendered.
     */
    public static synchronized void connect() {
        if (repository == null) {
            repository = new BugrapRepository("./bugrap-database");
            repository.populateWithTestData();
        }
    }

    /**
     * Gets the singleton instance of {@link BugrapRepository}.
     * 
     * @see #connect()
     */
    public static BugrapRepository getRepository() {
        assert repository != null : "The repository was not configured properly. Call the connect() method before getting the repository.";
        return repository;
    }

}
