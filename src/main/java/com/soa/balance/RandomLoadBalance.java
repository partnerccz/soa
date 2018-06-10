package com.soa.balance;

import com.alibaba.fastjson.JSONObject;
import com.soa.invoke.NodeInfo;

import java.util.Collection;
import java.util.List;
import java.util.Random;

public class RandomLoadBalance implements LoadBalance{
    @Override
    public NodeInfo doSelect(List<String> registryInfo) {
        Random random = new Random();
        int nextInt = random.nextInt(registryInfo.size());
        String registry = registryInfo.get(nextInt);
        JSONObject parseObject = JSONObject.parseObject(registry);
        Collection<Object> values = parseObject.values();
        JSONObject jsonObject = new JSONObject();
        for (Object value : values) {
            jsonObject = JSONObject.parseObject(value.toString());
        }
        JSONObject protocol = jsonObject.getJSONObject("protocol");
        NodeInfo nodeInfo = new NodeInfo();
        nodeInfo.setHost(protocol.get("host") != null ? protocol.getString("host") : "");
        nodeInfo.setPort(protocol.get("port") != null ? protocol.getString("port") : "");
        nodeInfo.setContextPath(protocol.get("contextPath") != null ? protocol.getString("contextPath") : "");
        return nodeInfo;
    }
}
