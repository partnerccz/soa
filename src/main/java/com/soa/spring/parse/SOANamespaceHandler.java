package com.soa.spring.parse;

import com.soa.spring.config.Protocol;
import com.soa.spring.config.Reference;
import com.soa.spring.config.Registry;
import com.soa.spring.config.Service;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class SOANamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        this.registerBeanDefinitionParser("registry", new RegistryBeanDefinitionParse(Registry.class));
        this.registerBeanDefinitionParser("service", new ServiceBeanDefinitionParse(Service.class));
        this.registerBeanDefinitionParser("protocol", new ProtocolBeanDefinitionParse(Protocol.class));
        this.registerBeanDefinitionParser("reference", new ReferenceBeanDefinitionParse(Reference.class));
    }
}
