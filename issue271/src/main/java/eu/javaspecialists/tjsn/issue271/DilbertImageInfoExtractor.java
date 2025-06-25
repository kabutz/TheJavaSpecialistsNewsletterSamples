/*
 * Copyright 2000-2025 Heinz Max Kabutz
 * All rights reserved.
 *
 * From The Java Specialists' Newsletter (https://www.javaspecialists.eu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.javaspecialists.tjsn.issue271;

import java.time.*;
import java.time.format.*;

public class DilbertImageInfoExtractor implements ImageInfoExtractor {
    private final LocalDate date;

    public DilbertImageInfoExtractor(LocalDate date) {
        this.date = date;
    }

    public String getUrl() {
        return "https://dilbert.com/strip/" +
                DateTimeFormatter.ISO_DATE.format(date);
    }

    public ImageInfo extract(String body) {
        var title = findProperty(body, "title");
        var imagePath = findProperty(body, "image");
        return new ImageInfo(title, imagePath);
    }

    private static String findProperty(String body,
                                       String filter) {
        String search = "meta name=\"twitter:" + filter +
                "\" content=\"";
        return body.lines().filter(line -> line.contains(search))
                .findFirst()
                .map(line -> line.replaceAll(".*" + search, ""))
                .map(line -> line.replaceAll("\".*", ""))
                .orElseThrow(() -> new IllegalStateException(
                        "Could not find \"" + filter + "\""));
    }
}
