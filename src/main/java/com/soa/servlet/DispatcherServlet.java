package com.soa.servlet;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.soa.spring.config.Service;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class DispatcherServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject httpProcess = httpProcess(req, resp);
        String serviceId = httpProcess.getString("serviceId");
        String methodName = httpProcess.getString("methodName");
        JSONArray paramTypes = httpProcess.getJSONArray("paramTypes");
        JSONArray methodParams = httpProcess.getJSONArray("methodParams");

        Object[] objs=null;
        if (methodParams != null) {
            objs = new Object[methodParams.size()];
            int i=0;
            for (Object o : methodParams) {
                objs[i++]=o;
            }
        }
        ApplicationContext applicationContext = Service.getApplicationContext();
        Object bean = applicationContext.getBean(serviceId);
        Method method = getMethod(bean, methodName, paramTypes);
        if (method != null) {
            Object invoke = null;
            try {
                invoke = method.invoke(bean, objs);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            PrintWriter printWriter = resp.getWriter();
            printWriter.write(invoke.toString());

        }else {
            PrintWriter printWriter = resp.getWriter();
            printWriter.write("==============no such method====================" + methodName);
        }
    }

    private Method getMethod(Object bean, String methodName, JSONArray paramTypes) {
        Method[] methods = bean.getClass().getMethods();
        List<Method> methodList = new ArrayList<>();
        for (Method method : methods) {
            if (methodName.equals(method.getName())) {
                methodList.add(method);
            }
        }
        if (methodList.size() == 1) {
            return methodList.get(0);
        }

        boolean isSameSize=false;
        boolean isSameType = false;
        partner:for (Method method : methodList) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length == paramTypes.size()) {
                isSameSize = true;
            }
            if (!isSameSize) {
                continue;
            }
            for (int i = 0; i < parameterTypes.length; i++) {
                if (parameterTypes[i].toString().contains(paramTypes.getString(i))) {
                    isSameType = true;
                }else {
                    isSameType = false;
                }
                if (!isSameType) {
                    continue partner;
                }
            }
            if (isSameType) {
                return method;
            }
        }
        return null;
    }

    private JSONObject httpProcess(HttpServletRequest request, HttpServletResponse response) {
        StringBuffer sb = new StringBuffer();
        try {
            ServletInputStream in = request.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String s = "";
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
            if (sb.toString().length() <= 0) {
                return null;
            }else {
                return JSONObject.parseObject(sb.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
