package com.soa.registry;

import com.alibaba.fastjson.JSONObject;
import com.soa.spring.config.Protocol;
import com.soa.spring.config.Registry;
import com.soa.spring.config.Service;
import com.soa.redis.RedisApi;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RedisRegistry implements BaseRegistry {

    @Override
    public boolean registry(String ref, ApplicationContext applicationContext) {
        Protocol protocol = applicationContext.getBean(Protocol.class);
        Map<String, Service> mapService = applicationContext.getBeansOfType(Service.class);
        Registry registry = applicationContext.getBean(Registry.class);
        RedisApi.createJedisPool(registry.getAddress());
        for (Map.Entry<String, Service> serviceEntry : mapService.entrySet()) {
            if(serviceEntry.getValue().getRef().equals(ref)){
                JSONObject jo = new JSONObject();
                jo.put("protocol", JSONObject.toJSONString(protocol));
                jo.put("service", JSONObject.toJSONString(serviceEntry.getValue()));

                JSONObject hostObject = new JSONObject();
                hostObject.put(protocol.getHost() + ":" + protocol.getPort(),jo);
                lpush(hostObject,ref);
            }
        }
        return false;
    }

    private void lpush(JSONObject hostPort, String key) {
        if (RedisApi.exists(key)) {
            Set<String> keySet = hostPort.keySet();
            String hostportkey = "";
            for (String kk : keySet) {
                hostportkey = kk;
            }
            List<String> registryInfo = RedisApi.lrange(key);
            List<String> newRegistry = new ArrayList<>();
            boolean isold = false;

            //这个循环的目的，有可能生产者启动的时候修改了某一些配置信息，这时候就需要把该生产者原来的信息替换成修改后的信息
            for (String node : registryInfo) {
                JSONObject jo = JSONObject.parseObject(node);
                //如果registryInfo注册信息里面包含了这个service的配置信息，说明以前这个生产者注册过
                //这时候再启动的话，我要把之前的service的注册信息，替换
                if (jo.containsKey(hostportkey)) {
                    newRegistry.add(hostPort.toJSONString());
                    isold = true;
                }
                else {
                    //如果没有，那就说明之前这个service标签没有注册过，这时候可能是一个新的生产者启动
                    newRegistry.add(node);
                }
            }
            //如果有配置信息的修改，需要把之前的老配置的key删掉
            if (isold) {
                if (newRegistry.size() > 0) {
                    RedisApi.del(key);
                    String[] newRegistryStr = new String[newRegistry.size()];
                    for (int i = 0; i < newRegistry.size(); i++) {
                        newRegistryStr[i] = newRegistry.get(i);
                    }
                    RedisApi.lpush(key, newRegistryStr);
                }
            }
            else {
                //如果没有配置信息修改，就把心的hostport对象加入到list中
                //list.add()
                RedisApi.lpush(key, hostPort.toJSONString());
            }
        }else {
            //第一次这个服务注册
            RedisApi.lpush(key, hostPort.toJSONString());
        }
    }

    @Override
    public List<String> getRegistry(String id, ApplicationContext applicationContext) {
        try {
            Registry registry = applicationContext.getBean(Registry.class);
            RedisApi.createJedisPool(registry.getAddress());
            if (RedisApi.exists(id)) {
                return RedisApi.lrange(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
