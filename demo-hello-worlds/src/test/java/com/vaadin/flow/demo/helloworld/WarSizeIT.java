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
package com.vaadin.flow.demo.helloworld;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class WarSizeIT {

    // Max size : 10 Mbytes
    private static final long MAX_SIZE = 10 * 1024 * 1024;

    private Set<Path> visited = new HashSet<>();

    @Test
    public void checkWarSize() throws URISyntaxException, IOException {
        URL resource = WarSizeIT.class.getResource("/");
        File file = new File(resource.toURI());

        Assert.assertTrue(file.exists());

        File war = null;
        do {
            war = findWar(file);
            visited.add(file.toPath());
            file = file.getParentFile();
        } while (war == null);

        Assert.assertNotNull(war);

        Assert.assertTrue("The size of war file '" + war + "' is too big: "
                + war.length(), war.length() < MAX_SIZE);
    }

    private File findWar(File directory) throws IOException {
        if (directory == null) {
            return null;
        }
        class WarFinderVisitor extends SimpleFileVisitor<Path> {
            private Path war;

            @Override
            public FileVisitResult visitFile(Path file,
                    BasicFileAttributes attrs) throws IOException {
                if (isWarFile(file)) {
                    war = file;
                    return FileVisitResult.TERMINATE;
                } else if (visited.contains(file)) {
                    return FileVisitResult.SKIP_SUBTREE;
                }
                return FileVisitResult.CONTINUE;
            }
        }
        WarFinderVisitor visitor = new WarFinderVisitor();
        Files.walkFileTree(directory.toPath(), visitor);
        if (visitor.war == null) {
            return null;
        }
        return visitor.war.toFile();
    }

    private static boolean isWarFile(Path file) {
        return file.getFileName().toString().toLowerCase(Locale.ENGLISH)
                .endsWith(".war");
    }
}
