package org.yaod.stcc.core.transaction.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Yaod
 **/
public class App {

    public static void main(String[] args){

        var t= valueOf(TransactionRoleEnum.class,1);
        TransactionRoleEnum d=valueOf(TransactionRoleEnum.class,1);
        System.out.println("hello world");
    }

   @SuppressWarnings("unchecked")
    static <T> T valueOf(Class<? extends CodeableEnum> clazz, int code){
        var clazzMap= cache.get(clazz);
        if(Objects.isNull(clazzMap)){
            clazzMap=new HashMap<>();
            CodeableEnum[] enumArray= clazz.getEnumConstants();
            for(var item: enumArray){
                clazzMap.put(item.getCode(), item);
            }
        }
        return (T)clazzMap.get(code);
    }

    static Map<Class<Enum<?>>, Map<Integer,CodeableEnum>> cache=new HashMap<>();
}
