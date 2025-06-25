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

package eu.javaspecialists.tjsn.issue049;

import com.sun.javadoc.*;

public class MethodChecker extends ExecutableChecker {
    private final MethodDoc doc;

    public MethodChecker(ClassChecker parent, MethodDoc doc) {
        super(parent, doc);
        this.doc = doc;
    }

    public void checkReturnComments() {
        Tag[] tags = doc.tags("return");
        if ("void".equals(doc.returnType().qualifiedTypeName())) {
            if (tags.length != 0) {
                foundCommentsForNonExistentReturnValue();
            }
        } else if (tags.length == 0 || isEmpty(tags[0].text())) {
            error("missing return comment");
        } else if (tags.length > 1) {
            error("has multiple return comments");
        }
    }
}
