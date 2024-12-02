package org.yaod.stcc.springboot.feign;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.yaod.stcc.core.annotation.ParticipateTCC;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;

/**
 * @author Yaod
 **/
public class OpenFeignPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
        //for feign, the bean instance is always proxied, as developer only care about interface.
        if (!Proxy.isProxyClass(bean.getClass())) {
            return bean;
        }

        ParticipateTCC ptccOnClazz = AnnotationUtils.findAnnotation(bean.getClass(), ParticipateTCC.class);
        if(ptccOnClazz!=null) {
            InvocationHandler originalHandler = Proxy.getInvocationHandler(bean);
            var handler = new OpenFeignProxyHandler(originalHandler);
            Class<?> clazz = bean.getClass();
            Class<?>[] interfaces = clazz.getInterfaces();
            ClassLoader classLoader = clazz.getClassLoader();
            return Proxy.newProxyInstance(classLoader, interfaces, handler);
        }
//        final Method[] methods = ReflectionUtils.getAllDeclaredMethods(bean.getClass());
//        for (Method method : methods) {
//            ParticipateTCC ptcc = AnnotationUtils.findAnnotation(method, ParticipateTCC.class);
//            if (ptcc!=null) {
//                var handler = new OpenFeignProxyHandler(originalHandler);
//                Class<?> clazz = bean.getClass();
//                Class<?>[] interfaces = clazz.getInterfaces();
//                ClassLoader classLoader = clazz.getClassLoader();
//                return Proxy.newProxyInstance(classLoader, interfaces, handler);
//            }
//        }
        return bean;
    }
}
