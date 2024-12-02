package org.yaod.stcc.persistence;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.yaod.stcc.core.serializer.InvocationSerializer;
import org.yaod.stcc.core.transaction.context.Invocation;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Yaod
 **/
public class InvocationTypeHandler extends BaseTypeHandler<Invocation> {

    InvocationSerializer serializer=InvocationSerializer.INST;
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Invocation parameter, JdbcType jdbcType) throws SQLException {
        ps.setBytes(i, serializer.serialize(parameter));
    }

    @Override
    public Invocation getNullableResult(ResultSet rs, String columnName) throws SQLException {
        byte[] result = rs.getBytes(columnName);
        return serializer.deSerialize(result);
    }

    @Override
    public Invocation getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        byte[] result = rs.getBytes(columnIndex);
        return serializer.deSerialize(result);
    }

    @Override
    public Invocation getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        byte[] result = cs.getBytes(columnIndex);
        return serializer.deSerialize(result);
    }
}
