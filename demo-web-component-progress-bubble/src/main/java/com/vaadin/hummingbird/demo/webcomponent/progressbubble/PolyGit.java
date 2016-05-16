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
package com.vaadin.hummingbird.demo.webcomponent.progressbubble;

/**
 * Helper for managing a application wide PolyGit URL
 *
 * @author Vaadin Ltd
 */
public class PolyGit {

    // component+org+ver (given version)
    // component+org+:branch (tip of branch)
    // component+org+:* (latest release)
    public static final String BASE_URL = "http://polygit.org/"
            + "progress-bubble+tehapo+v1.2.0" //
            + "/" //
            + "polymer++v1.4.0" //
            + "/" //
            + "components/";

    private PolyGit() {
        // Static URL should be the only public API
    }
}