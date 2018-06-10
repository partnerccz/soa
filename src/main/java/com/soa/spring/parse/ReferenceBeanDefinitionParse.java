package com.soa.spring.parse;

import com.soa.spring.config.Reference;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class ReferenceBeanDefinitionParse implements BeanDefinitionParser{
    private Class<?> beanClass;

    public ReferenceBeanDefinitionParse(Class<?> beanClass) {
        this.beanClass= beanClass;
    }
    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition();
        rootBeanDefinition.setBeanClass(beanClass);
        rootBeanDefinition.setLazyInit(false);
        String id = element.getAttribute("id");
        String intf = element.getAttribute("intf");
        String check = element.getAttribute("check");
        String protocol = element.getAttribute("protocol");
        String loadbalance = element.getAttribute("loadbalance");

        if (id != null && !"".equals(id)) {
            if (parserContext.getRegistry().containsBeanDefinition(id)) {
                throw new IllegalStateException("Spring has the id:" + id);
            }
            rootBeanDefinition.getPropertyValues().add("id", id);
        }else{
            id = beanClass.getName().substring(0, 1).toLowerCase() + beanClass.getName().substring(1);
            rootBeanDefinition.getPropertyValues().add("id", id);
        }
        rootBeanDefinition.getPropertyValues().add("intf", intf);
        rootBeanDefinition.getPropertyValues().add("check", check);
        rootBeanDefinition.getPropertyValues().add("protocol", protocol);
        rootBeanDefinition.getPropertyValues().add("loadbalance", loadbalance);
        parserContext.getRegistry().registerBeanDefinition(id, rootBeanDefinition);
        return rootBeanDefinition;
    }

}
