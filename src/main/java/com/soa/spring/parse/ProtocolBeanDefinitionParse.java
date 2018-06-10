package com.soa.spring.parse;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class ProtocolBeanDefinitionParse implements BeanDefinitionParser {

    private Class<?> beanClass;

    public ProtocolBeanDefinitionParse(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition();
        rootBeanDefinition.setBeanClass(beanClass);
        rootBeanDefinition.setLazyInit(false);
        String id = element.getAttribute("id");
        String name = element.getAttribute("name");
        String host = element.getAttribute("host");
        String post = element.getAttribute("port");
        if (id != null && !"".equals(id)) {
            if (parserContext.getRegistry().containsBeanDefinition(id)) {
                throw new IllegalStateException("Spring has id:" + id);
            }
            rootBeanDefinition.getPropertyValues().add("id", id);
        }else{
            id=beanClass.getName().substring(0,1).toLowerCase()+beanClass.getName().substring(1);
            rootBeanDefinition.getPropertyValues().add("id", id);
        }
        rootBeanDefinition.getPropertyValues().add("name", name);
        rootBeanDefinition.getPropertyValues().add("host", host);
        rootBeanDefinition.getPropertyValues().add("port", post);
        parserContext.getRegistry().registerBeanDefinition(id, rootBeanDefinition);
        return rootBeanDefinition;
    }
}
