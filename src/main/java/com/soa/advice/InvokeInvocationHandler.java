package com.soa.advice;

import com.soa.invoke.Invocation;
import com.soa.invoke.Invoke;
import com.soa.spring.config.Reference;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class InvokeInvocationHandler implements InvocationHandler {
    private Invoke invoke;
    private Reference reference;

    public InvokeInvocationHandler(Invoke invoke, Reference reference) {
        this.invoke = invoke;
        this.reference = reference;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.printf("==============invoke InvokeInvocationHandler================");
        Invocation invocation = new Invocation();
        invocation.setIntf(reference.getIntf());
        invocation.setMethod(method);
        invocation.setObjs(args);
        return invoke.invoke(invocation);
    }
}
