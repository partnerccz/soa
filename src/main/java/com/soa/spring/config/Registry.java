package com.soa.spring.config;

import com.soa.registry.BaseRegistry;
import com.soa.registry.RedisRegistry;

import java.util.HashMap;
import java.util.Map;

public class Registry {
    private String id;
    private String protocol;
    private String address;
    private static Map<String, BaseRegistry> registryMap;

    static {
        registryMap = new HashMap<>();
        registryMap.put("redis", new RedisRegistry());
        registryMap.put("zookeeper", null);
        registryMap.put("mongodb", null);
    }

    public static Map<String, BaseRegistry> getRegistryMap() {
        return registryMap;
    }

    public static void setRegistryMap(Map<String, BaseRegistry> registryMap) {
        Registry.registryMap = registryMap;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
