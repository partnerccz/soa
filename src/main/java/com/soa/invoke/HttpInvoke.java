package com.soa.invoke;

import com.alibaba.fastjson.JSONObject;
import com.soa.balance.LoadBalance;
import com.soa.spring.config.Reference;
import com.soa.spring.config.Registry;
import org.springframework.http.HttpRequest;

import java.util.List;

public class HttpInvoke implements Invoke {

    @Override
    public String invoke(Invocation invocation) throws Exception {
        Reference reference = invocation.getReference();
        List<String> registryList = reference.getRegistryInfo();
        String loadbalance = reference.getLoadbalance();
        LoadBalance loadBalance = reference.getLoadBalanceMap().get(loadbalance);
        NodeInfo nodeInfo = loadBalance.doSelect(registryList);
        JSONObject sendParam = new JSONObject();
        sendParam.put("methodName", invocation.getMethod().getName());
        sendParam.put("serviceId", reference.getId());
        sendParam.put("methodParams", invocation.getObjs());
        sendParam.put("paramTypes", invocation.getMethod().getTypeParameters());
        String url = "http://" + nodeInfo.getHost() + ":" + nodeInfo.getPort() + nodeInfo.getContextPath();
        String result = com.soa.invoke.HttpRequest.sendPost(url, sendParam.toJSONString());

        return result;
    }
}
