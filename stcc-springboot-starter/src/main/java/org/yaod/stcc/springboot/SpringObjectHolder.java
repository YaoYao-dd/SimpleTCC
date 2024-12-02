package org.yaod.stcc.springboot;

import org.springframework.context.ApplicationContext;
import org.yaod.stcc.core.objectholder.ObjectHolder;

/**
 * @author Yaod
 **/
public class SpringObjectHolder implements ObjectHolder {
    private final ApplicationContext appContext;

    public SpringObjectHolder(ApplicationContext appContext) {
        this.appContext = appContext;
    }

    @Override
    public <T> T getObject(Class<T> clazz) {
        return this.appContext.getBean(clazz);
    }
}
