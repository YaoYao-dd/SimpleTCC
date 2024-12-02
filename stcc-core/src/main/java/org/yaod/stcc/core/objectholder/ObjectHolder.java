package org.yaod.stcc.core.objectholder;

/**
 * @author Yaod
 **/
public interface ObjectHolder {
    public <T> T getObject(Class<T> clazz);
}
