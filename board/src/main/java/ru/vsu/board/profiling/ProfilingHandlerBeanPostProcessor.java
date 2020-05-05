package ru.vsu.board.profiling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.stereotype.Component;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;

@Component
public class ProfilingHandlerBeanPostProcessor implements BeanPostProcessor {

    public static final float NANOSEC_IN_MS = 1000000;
    public static final String PROFILING_LOG_MESSAGE = "Method '%s.%s()' completed for %.3f ms";
    private Map<String, Class> map = new HashMap<>();
    private ProfilingController controller = new ProfilingController();
    private Logger logger = LoggerFactory.getLogger(ProfilingHandlerBeanPostProcessor.class);

    public ProfilingHandlerBeanPostProcessor() throws Exception {
        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
        platformMBeanServer.registerMBean(controller, new ObjectName("profiling", "name", "controller"));
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        if(beanClass.isAnnotationPresent(Profiling.class)) {
            map.put(beanName, beanClass);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = map.get(beanName);
        if (beanClass != null) {
            return getProxy(beanClass, bean);
        }
        return bean;
    }

    private Object getProxy(Class<?> beanClass, Object bean) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanClass);
        enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
            if (controller.isEnabled()) {
                long before = System.nanoTime();
                Object retVal = method.invoke(bean, objects);
                long after = System.nanoTime();
                logger.info(String.format(PROFILING_LOG_MESSAGE, method.getDeclaringClass().getName(), method.getName(), nanoToMs(after - before)));
                return retVal;
            } else {
                return method.invoke(bean, objects);
            }
        });
        return beanClass.cast(enhancer.create());
    }

    private float nanoToMs(long nano) {
        return nano / NANOSEC_IN_MS;
    }

}
