package com.soa.spring.config;

import com.soa.balance.LoadBalance;
import com.soa.balance.RandomLoadBalance;
import com.soa.balance.RoundRobinBalance;
import com.soa.invoke.HttpInvoke;
import com.soa.invoke.Invoke;
import com.soa.invoke.NettyInvoke;
import com.soa.invoke.RmiInvoke;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reference {
    private String id;
    private String check;
    private String intf;
    private String protocol;
    private String loadbalance;
    private List<String> registryInfo;

    private static Map<String, LoadBalance> loadBalanceMap = new HashMap<>();
    private static Map<String, Invoke> invokeMap = new HashMap<>();
    private List<Registry> registryList = new ArrayList<>();

    static {
        invokeMap.put("http", new HttpInvoke());
        invokeMap.put("rmi", new RmiInvoke());
        invokeMap.put("netty", new NettyInvoke());

        loadBalanceMap.put("random", new RandomLoadBalance());
        loadBalanceMap.put("roundrob", new RoundRobinBalance());
    }

    public List<String> getRegistryInfo() {
        return registryInfo;
    }

    public void setRegistryInfo(List<String> registryInfo) {
        this.registryInfo = registryInfo;
    }

    public String getLoadbalance() {
        return loadbalance;
    }

    public void setLoadbalance(String loadbalance) {
        this.loadbalance = loadbalance;
    }

    public static Map<String, Invoke> getInvokeMap() {
        return invokeMap;
    }

    public static void setInvokeMap(Map<String, Invoke> invokeMap) {
        Reference.invokeMap = invokeMap;
    }

    public static Map<String, LoadBalance> getLoadBalanceMap() {
        return loadBalanceMap;
    }

    public static void setLoadBalanceMap(Map<String, LoadBalance> loadBalanceMap) {
        Reference.loadBalanceMap = loadBalanceMap;
    }

    public List<Registry> getRegistryList() {
        return registryList;
    }

    public void setRegistryList(List<Registry> registryList) {
        this.registryList = registryList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getIntf() {
        return intf;
    }

    public void setIntf(String intf) {
        this.intf = intf;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
