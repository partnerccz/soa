package com.soa.balance;

import com.alibaba.fastjson.JSONObject;
import com.soa.invoke.NodeInfo;

import java.util.Collection;
import java.util.List;

public class RoundRobinBalance implements LoadBalance {
    private static Integer index = 0;

    @Override
    public NodeInfo doSelect(List<String> registryInfo) {
        String registry = "";
        synchronized (index) {
            if (index > registryInfo.size()) {
                index = 0;
            }
            registry = registryInfo.get(index);
            index++;
        }
        JSONObject parseObject = JSONObject.parseObject(registry);
        Collection<Object> values = parseObject.values();
        for (Object value : values) {
            parseObject = JSONObject.parseObject(value.toString());
        }
        JSONObject protocol = parseObject.getJSONObject("protocol");
        NodeInfo nodeInfo = new NodeInfo();
        nodeInfo.setHost(protocol.get("host") != null ? protocol.getString("host") : "");
        nodeInfo.setPort(protocol.get("port") != null ? protocol.getString("host") : "");
        nodeInfo.setContextPath(protocol.get("contextPath") != null ? protocol.getString("contextPath") : "");
        return nodeInfo;
    }
}
