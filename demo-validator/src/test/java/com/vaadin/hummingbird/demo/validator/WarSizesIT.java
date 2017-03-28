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

package com.vaadin.hummingbird.demo.validator;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;

/**
 * @author Vaadin Ltd.
 */
public class WarSizesIT {
    private static final String PROJECT_BASE_DIRECTORY_PROPERTY_NAME = "projectBaseDirectory";
    // 20 MB
    private static final long MAX_ALLOWED_WAR_SIZE_BYTES = 20 * 1024 * 1024;

    @Test
    public void checkWarSizes() throws Exception {
        Path projectBaseDirectory = getProjectBaseDirectory();
        List<Path> warFiles = Files
                .walk(projectBaseDirectory, FileVisitOption.FOLLOW_LINKS)
                .filter(path -> Files.isRegularFile(path)).filter(path -> path
                        .toString().toLowerCase(Locale.ENGLISH).endsWith("war"))
                .collect(Collectors.toList());
        assertTrue(String.format("Could not find war files in %s",
                projectBaseDirectory), warFiles.size() > 0);

        Set<Path> warsExceedingSize = warFiles.stream()
                .filter(path -> getFileSize(path) > MAX_ALLOWED_WAR_SIZE_BYTES)
                .collect(Collectors.toSet());

        assertTrue(
                String.format(
                        "Found war files that exceed maximum allowed size (%s b), list of files: %s",
                        MAX_ALLOWED_WAR_SIZE_BYTES, warsExceedingSize),
                warsExceedingSize.isEmpty());
    }

    private static long getFileSize(Path path) {
        try {
            return Files.size(path);
        } catch (IOException ioe) {
            throw new IllegalArgumentException(
                    "Could not read size of a file, path=" + path, ioe);
        }
    }

    private Path getProjectBaseDirectory() {
        String baseDirectoryPropertyValue = System
                .getProperty(PROJECT_BASE_DIRECTORY_PROPERTY_NAME);
        assertTrue(
                String.format("`%s` property is not set",
                        PROJECT_BASE_DIRECTORY_PROPERTY_NAME),
                baseDirectoryPropertyValue != null
                        && !baseDirectoryPropertyValue.isEmpty());
        Path projectBaseDirectory = Paths.get(baseDirectoryPropertyValue);
        assertTrue(
                String.format(
                        "`%s` is invalid, should be pointing at project directory, projectBaseDirectory=%s",
                        PROJECT_BASE_DIRECTORY_PROPERTY_NAME,
                        baseDirectoryPropertyValue),
                Files.isDirectory(projectBaseDirectory));
        return projectBaseDirectory;
    }
}
