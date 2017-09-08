package com.yt.common;

/**
 * Created by jackdeng on 2017/9/6.
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class HandlerAdapter extends RequestMappingHandlerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(HandlerAdapter.class);

    private boolean includeDefaults = true;
    //
//    private CacheProcessor cacheProcessor = null;
//    private DegradeProcessor degradeProcessor = null;
//    private ResponseProcessor responseProcessor = null;
    private Set<Method> responseBodySet = null;
    private Set<Method> requestBodySet = null;
    private List<String> skipCustomHandlerAdapterWithMethod = new ArrayList<String>();


    public HandlerAdapter() {

        //初始化默认排序
        setOrder(Ordered.LOWEST_PRECEDENCE - 1);
//        //初始化缓存处理器
//        cacheProcessor = new CacheProcessor();
//        //初始化降级处理器
//        degradeProcessor = new DegradeProcessor();
//        //初始化结果判断集合(@ResponseBody)
//        responseBodySet = Sets.newConcurrentHashSet();
//        //初始化请求体判断集合
//        requestBodySet = Sets.newConcurrentHashSet();
//        //初始化默认响应转换器
//        responseProcessor = new DefaultResponseProcessor();
    }


    @Override
    protected ModelAndView handleInternal(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) throws Exception {

//        if( requestBodySet.contains(handlerMethod.getMethod()) && !(request instanceof BodyReaderHttpServletRequestWrapper)){
//            return super.handleInternal( new BodyReaderHttpServletRequestWrapper(request) , response, handlerMethod);
//        }
        return super.handleInternal(request, response, handlerMethod);
    }

    protected ServletInvocableHandlerMethod createInvocableHandlerMethod(final HandlerMethod handlerMethod) {
//        if(skipCustomHandlerAdapterWithMethod.contains(getHandlerMethodFullName(handlerMethod))){
//            return super.createInvocableHandlerMethod(handlerMethod);
//        }

        return super.createInvocableHandlerMethod(handlerMethod);
    }


    protected void initApplicationContext(ApplicationContext context) {

        super.initApplicationContext(context);
    }


    @SuppressWarnings({"unchecked", "rawtypes"})
    private boolean hasAnnotation(Class annotation, HandlerMethod handlerMethod, boolean includeParameters) {

        if (null != handlerMethod.getMethodAnnotation(annotation)) {
            return true;
        }

        if (includeParameters) {
            MethodParameter[] methodParameter = handlerMethod.getMethodParameters();
            if (null != methodParameter) {
                for (MethodParameter parameter : methodParameter) {
                    if (parameter.hasParameterAnnotation(annotation)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isIncludeDefaults() {
        return includeDefaults;
    }

    public void setIncludeDefaults(boolean includeDefaults) {
        this.includeDefaults = includeDefaults;
    }

    public void setSkipCustomHandlerAdapterWithMethod(
            List<String> skipCustomHandlerAdapterWithMethod) {
        this.skipCustomHandlerAdapterWithMethod = skipCustomHandlerAdapterWithMethod;
    }

    String getHandlerMethodFullName(HandlerMethod handlerMethod) {
        if (null != handlerMethod) {
            Method method = handlerMethod.getMethod();
            StringBuilder sb = new StringBuilder(method.getDeclaringClass().getTypeName());
            sb.append(".");
            sb.append(method.getName());
            return sb.toString();
        }
        return "";
    }
}


