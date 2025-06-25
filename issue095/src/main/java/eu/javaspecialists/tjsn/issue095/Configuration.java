package eu.javaspecialists.tjsn.issue095;

import java.beans.*;
import java.util.*;

public interface Configuration {
    Object getProperty(String propertyName);

    Set<Map.Entry<String, Object>> getAllProperties();

    void addPropertyChangeListener(PropertyChangeListener listener);

    boolean removePropertyChangeListener(PropertyChangeListener listener);
}
