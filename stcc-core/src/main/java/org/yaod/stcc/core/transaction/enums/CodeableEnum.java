package org.yaod.stcc.core.transaction.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Yaod
 **/
public interface CodeableEnum {
   int getCode();

   default Enum<? extends CodeableEnum> valueOf(Class<Enum<? extends CodeableEnum>> clazz, int code){
       var clazzMap= cache.get(clazz);
       if(Objects.isNull(clazzMap)){
           clazzMap=new HashMap<>();
           var enumArray= clazz.getEnumConstants();

           System.out.println(enumArray);
       }
       return clazzMap.get(code);
   }

   static Map<Class, Map<Integer,Enum>> cache=new HashMap<>();
}
