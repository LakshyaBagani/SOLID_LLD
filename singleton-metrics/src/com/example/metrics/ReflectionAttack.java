package com.example.metrics;

import java.lang.reflect.*;

public class ReflectionAttack {

    public static void main(String[] args) {
        MetricsRegistry singleton = MetricsRegistry.getInstance();
        System.out.println("Singleton identity: " + System.identityHashCode(singleton));

        try {
            Constructor<MetricsRegistry> ctor = MetricsRegistry.class.getDeclaredConstructor();
            ctor.setAccessible(true);
            MetricsRegistry evil = ctor.newInstance();
            System.out.println("BUG: reflection created a second instance: " + System.identityHashCode(evil));
        } catch (Exception e) {
            System.out.println("Reflection attack blocked: " + e.getCause().getMessage());
        }
    }
}
