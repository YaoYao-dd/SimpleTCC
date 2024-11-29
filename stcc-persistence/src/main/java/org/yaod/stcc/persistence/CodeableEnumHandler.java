package org.yaod.stcc.persistence;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.yaod.stcc.core.transaction.enums.CodeableEnum;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Yaod
 **/
public class CodeableEnumHandler<E extends CodeableEnum> extends BaseTypeHandler<E> {

    private final Class<E> type;
    public CodeableEnumHandler(Class<E> aType) {
        if(aType==null || !aType.isEnum()){
            throw new IllegalArgumentException("Type argument cannot be null, and it must be enum and implement CodeableEnum interface.");
        }
        this.type= aType;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getCode());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int v=rs.getInt(columnName);
        return  enumValueOf(this.type,v);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int v=rs.getInt(columnIndex);
        return  enumValueOf(this.type,v);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int v=cs.getInt(columnIndex);
        return enumValueOf(this.type,v);
    }

    /**
     * Cache then return codeable enum by class and code.
     * Thread safe.
     * ConcurrentHashMap and atomic compute method ensure this  is thread safe.
     * @param clazz
     * @param code
     * @return enum instance as specified.
     */
    @SuppressWarnings("unchecked")
    private E enumValueOf(Class<E> clazz, int code){

        var clazzMap= innerCache.compute(clazz, (k,v)->{
            if(Objects.isNull(v)){
                var innerClazzMap=new HashMap<Integer,CodeableEnum>();
                CodeableEnum[] enumArray=clazz.getEnumConstants();
                for(var item: enumArray){
                    innerClazzMap.put(item.getCode(), item);
                }
                return Collections.unmodifiableMap(innerClazzMap);
            }else{
                return v;
            }
        });

        return (E)clazzMap.get(code);
    }

    static Map<Class<? extends CodeableEnum>, Map<Integer,CodeableEnum>> innerCache =new ConcurrentHashMap<>();
}
