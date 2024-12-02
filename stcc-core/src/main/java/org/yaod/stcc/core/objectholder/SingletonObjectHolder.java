package org.yaod.stcc.core.objectholder;

/**
 * @author Yaod
 **/
public class SingletonObjectHolder {

    public static SingletonObjectHolder INST=new SingletonObjectHolder();

    private ObjectHolder holder;
    private SingletonObjectHolder(){}

    public void setObjectHolder(ObjectHolder aObjectHolder){
        this.holder=aObjectHolder;
    }

    public <T> T getObject(Class<T> clazz){
        return holder.getObject(clazz);
    }


}
