package com.yuhuayuan.core.typehandler;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class StringMapHandler extends BaseTypeHandler<Map<String, String>> {

    private static final TypeReference<Map<String, String>> TYPE = new TypeReference<Map<String, String>>() {
    };

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Map<String, String> parameter,
            JdbcType jdbcType) throws SQLException {
        String val = JSON.toJSONString(parameter);
        ps.setString(i, val);
    }

    @Override
    public Map<String, String> getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        String val = rs.getString(columnName);
        if (val == null)
            return null;
        Map<String, String> ret = null;
        try {
            ret = JSON.parseObject(val, TYPE);
        } catch (Exception e) {
            
        }
        return ret;
    }

    @Override
    public Map<String, String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String val = rs.getString(columnIndex);
        if (val == null)
            return null;
        Map<String, String> ret = null;
        try {
            ret = JSON.parseObject(val, TYPE);
        } catch (Exception e) {
            
        }
        return ret;
    }

    @Override
    public Map<String, String> getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        String val = cs.getString(columnIndex);
        if (val == null)
            return null;
        Map<String, String> ret = null;
        try {
            ret = JSON.parseObject(val, TYPE);
        } catch (Exception e) {
            
        }
        return ret;
    }

}
