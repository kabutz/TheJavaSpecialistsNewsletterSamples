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

package eu.javaspecialists.tjsn.issue253;

public class Socket {
    private final int port;
    private final String host;
    private final boolean ssl;
    private final int timeout;

    private Socket(Builder builder) {
        // Better to pass Builder into constructor than individual
        // parameters - thanks Carlos Solórzano (@carlosalejand)
        this.port = builder.port;
        this.host = builder.host;
        this.ssl = builder.ssl;
        this.timeout = builder.timeout;
    }

    public static class Builder {
        private int port = 0;
        private String host = null;
        private boolean ssl = false;
        private int timeout = 0;

        public Builder port(int port) {
            this.port = port;
            return this;
        }

        public Builder host(String host) {
            this.host = host;
            return this;
        }

        public Builder ssl(boolean ssl) {
            this.ssl = ssl;
            return this;
        }

        public Builder timeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        public Socket build() {
            return new Socket(this);
        }
    }
}
