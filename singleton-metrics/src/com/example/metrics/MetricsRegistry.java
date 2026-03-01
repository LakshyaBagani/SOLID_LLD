package com.example.metrics;

import java.io.*;
import java.util.*;

public class MetricsRegistry implements Serializable {

    private static final long serialVersionUID = 1L;

    private static boolean instanceCreated = false;
    private final Map<String, Long> counters = new HashMap<>();

    private MetricsRegistry() {
        if (instanceCreated) {
            throw new IllegalStateException("Use getInstance()");
        }
        instanceCreated = true;
    }

    private static class Holder {
        private static final MetricsRegistry INSTANCE = new MetricsRegistry();
    }

    public static MetricsRegistry getInstance() {
        return Holder.INSTANCE;
    }

    public synchronized void setCount(String key, long value) {
        counters.put(key, value);
    }

    public synchronized void increment(String key) {
        counters.put(key, getCount(key) + 1);
    }

    public synchronized long getCount(String key) {
        return counters.getOrDefault(key, 0L);
    }

    public synchronized Map<String, Long> getAll() {
        return Collections.unmodifiableMap(new HashMap<>(counters));
    }

    private Object readResolve() {
        return getInstance();
    }
}
