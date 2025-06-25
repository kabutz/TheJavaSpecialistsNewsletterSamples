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

package eu.javaspecialists.tjsn.issue221;

public abstract class Transformer<T> {
    public final <R, U, X extends Exception> R transform(
            U u, BiFunctionWithCE<T, U, R, X> transformer) throws X {
        T t = takeTransformer();
        try {
            return transformer.apply(t, u);
        } finally {
            putTransformer(t);
        }
    }

    protected abstract T takeTransformer();

    protected abstract void putTransformer(T t);
}