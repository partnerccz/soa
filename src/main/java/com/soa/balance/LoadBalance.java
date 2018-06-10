package com.soa.balance;

import com.soa.invoke.NodeInfo;

import java.util.List;

public interface LoadBalance {
    NodeInfo doSelect(List<String> registryInfo);
}


