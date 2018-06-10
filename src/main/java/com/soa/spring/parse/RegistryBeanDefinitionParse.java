package com.soa.spring.parse;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class RegistryBeanDefinitionParse implements BeanDefinitionParser {
    private Class<?> beanClass;

    public RegistryBeanDefinitionParse(Class<?> beanClass) {

        this.beanClass = beanClass;
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition();
        rootBeanDefinition.setBeanClass(this.beanClass);
        rootBeanDefinition.setLazyInit(false);
        String id = element.getAttribute("id");
        String protocol = element.getAttribute("protocol");
        String address = element.getAttribute("address");
        if (id != null && !"".equals(id)) {
            if (parserContext.getRegistry().containsBeanDefinition(id)) {
                throw new IllegalStateException("Spring has this id");
            }
            rootBeanDefinition.getPropertyValues().add("id", id);
        } else {
            id = beanClass.getName().substring(0, 1).toLowerCase() + beanClass.getName().substring(1);
            rootBeanDefinition.getPropertyValues().add("id", id);
        }
        rootBeanDefinition.getPropertyValues().add("protocol", protocol);
        rootBeanDefinition.getPropertyValues().add("address", address);
        parserContext.getRegistry().registerBeanDefinition(id, rootBeanDefinition);
        return rootBeanDefinition;
    }
}
