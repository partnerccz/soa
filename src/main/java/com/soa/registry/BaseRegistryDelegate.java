package com.soa.registry;

import com.soa.spring.config.Registry;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class BaseRegistryDelegate {
    public static boolean registry(String ref, ApplicationContext applicationContext) {
        Registry registry = applicationContext.getBean(Registry.class);
        BaseRegistry baseRegistry = registry.getRegistryMap().get(registry.getProtocol());
        return baseRegistry.registry(ref, applicationContext);
    }

    public static List<String> getRegistry(String id, ApplicationContext applicationContext) {
        Registry registry = applicationContext.getBean(Registry.class);
        BaseRegistry baseRegistry = registry.getRegistryMap().get(registry.getProtocol());
        return baseRegistry.getRegistry(id, applicationContext);
    }
}
