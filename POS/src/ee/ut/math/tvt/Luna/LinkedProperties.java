package ee.ut.math.tvt.Luna;

import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Properties;

public class LinkedProperties extends Properties {

    // Don't know why it should be here :) , can be deleted probably
	private static final long serialVersionUID = -3100972789677440935L;
	
	private final LinkedHashSet<Object> keys = new LinkedHashSet<Object>();

    @Override
    public Enumeration<?> propertyNames() {
        return Collections.enumeration(keys);
    }

    @Override
    public synchronized Enumeration<Object> elements() {
        return Collections.enumeration(keys);
    }

    public Enumeration<Object> keys() {
        return Collections.enumeration(keys);
    }

    public Object put(Object key, Object value) {
        keys.add(key);
        return super.put(key, value);
    }

    @Override
    public synchronized Object remove(Object key) {
        keys.remove(key);
        return super.remove(key);
    }

    @Override
    public synchronized void clear() {
        keys.clear();
        super.clear();
    }
}