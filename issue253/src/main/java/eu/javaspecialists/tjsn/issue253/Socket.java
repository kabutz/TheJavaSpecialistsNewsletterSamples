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
