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
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Vaadin Ltd.
 */
public class WarSizesIT {
    private static final String PROJECT_BASE_DIRECTORY_ENV_VARIABLE_NAME = "projectBaseDirectory";
    // 20 MB
    private static final long MAX_ALLOWED_WAR_SIZE_BYTES = 20 * 1024 * 1024;
    private static final Set<String> EXCLUDED_DIRECTORY_NAMES = Stream
            .of(".git", "bower_components", "node", "node_modules")
            .collect(Collectors.toSet());

    private long warFilesAnalysed;

    @Test
    public void checkWarSizes() throws Exception {
        warFilesAnalysed = 0;

        Files.walkFileTree(getProjectBaseDirectory(),
                new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file,
                            BasicFileAttributes attrs) throws IOException {
                        String fileName = file.toString()
                                .toLowerCase(Locale.ENGLISH);
                        if (fileName.endsWith(".war")) {
                            long fileSizeBytes = Files.size(file);
                            assertTrue(
                                    String.format(
                                            "File %s has size %s bytes, which is bigger than threshold: %s bytes",
                                            fileName, fileSizeBytes,
                                            MAX_ALLOWED_WAR_SIZE_BYTES),
                                    fileSizeBytes < MAX_ALLOWED_WAR_SIZE_BYTES);
                            warFilesAnalysed += 1;
                        }
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult preVisitDirectory(Path dir,
                            BasicFileAttributes attrs) {
                        String dirPath = dir.toString()
                                .toLowerCase(Locale.ENGLISH);
                        if (EXCLUDED_DIRECTORY_NAMES.stream()
                                .anyMatch(dirPath::endsWith)) {
                            return FileVisitResult.SKIP_SUBTREE;
                        }
                        return FileVisitResult.CONTINUE;
                    }
                });

        assertTrue("No war files were analysed.", warFilesAnalysed > 0);
        Assert.fail("FU");
    }

    private Path getProjectBaseDirectory() {
        String baseDirectoryEnvValue = System
                .getProperty(PROJECT_BASE_DIRECTORY_ENV_VARIABLE_NAME);
        assertTrue(
                String.format("`%s` environment variable not set",
                        PROJECT_BASE_DIRECTORY_ENV_VARIABLE_NAME),
                baseDirectoryEnvValue != null
                        && !baseDirectoryEnvValue.isEmpty());
        Path projectBaseDirectory = Paths.get(baseDirectoryEnvValue);
        assertTrue(
                String.format(
                        "`%s` is invalid, should be pointing at project directory, projectBaseDirectory=%s",
                        PROJECT_BASE_DIRECTORY_ENV_VARIABLE_NAME,
                        baseDirectoryEnvValue),
                Files.isDirectory(projectBaseDirectory));
        return projectBaseDirectory;
    }
}
