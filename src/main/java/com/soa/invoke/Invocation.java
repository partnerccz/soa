package com.soa.invoke;

import com.soa.spring.config.Reference;

import java.lang.reflect.Method;

public class Invocation {
    private Method method;
    private Object [] objs;
    private String intf;
    private Reference reference;

    public Reference getReference() {
        return reference;
    }

    public void setReference(Reference reference) {
        this.reference = reference;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object[] getObjs() {
        return objs;
    }

    public void setObjs(Object[] objs) {
        this.objs = objs;
    }

    public String getIntf() {
        return intf;
    }

    public void setIntf(String intf) {
        this.intf = intf;
    }
}
