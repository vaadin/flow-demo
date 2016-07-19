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
package com.vaadin.hummingbird.demo.webcomponent.polygit;

import com.vaadin.hummingbird.demo.webcomponent.polygit.component.ProgressBubble;

/**
 * Helper for managing a application wide PolyGit URL.
 *
 * @author Vaadin Ltd
 */
public class PolyGit {

    // component+org+ver (given version)
    // component+org+:branch (tip of branch)
    // component+org+:* (latest release)

    private static final String POLYMER_POLYGIT_DEFINITION = "polymer++v1.4.0";

    /**
     * The URL to use for polygit.org when loading web components. Starts with
     * "//" to support both http and https.
     */
    public static final String BASE_URL = "//polygit.org/"
            + ProgressBubble.POLYGIT_DEFINITION //
            + "/" //
            + "vaadin-combo-box+vaadin+v1.1.1" //
            + "/" //
            + POLYMER_POLYGIT_DEFINITION //
            + "/" //
            + "components/";

    private PolyGit() {
        // The static URL is the only API
    }
}
