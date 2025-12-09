package org.wrapper.mybatismapper.timezone.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

/**
 * LocalDateTime을 위한 커스텀 TypeHandler
 * 
 * log4jdbc의 ResultSetSpy가 getObject(String, Class) 메서드를 지원하지 않아서
 * 커스텀 TypeHandler를 구현합니다.
 * 
 * Timestamp를 사용하여 LocalDateTime으로 변환합니다.
 */
@MappedTypes(LocalDateTime.class)
@MappedJdbcTypes(JdbcType.TIMESTAMP)
public class LocalDateTimeTypeHandler extends BaseTypeHandler<LocalDateTime> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, LocalDateTime parameter, JdbcType jdbcType) throws SQLException {
        // LocalDateTime을 Timestamp로 변환하여 저장
        ps.setTimestamp(i, Timestamp.valueOf(parameter));
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
        // getTimestamp를 사용하여 log4jdbc 호환성 확보
        Timestamp timestamp = rs.getTimestamp(columnName);
        return convertToLocalDateTime(timestamp);
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(columnIndex);
        return convertToLocalDateTime(timestamp);
    }

    @Override
    public LocalDateTime getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Timestamp timestamp = cs.getTimestamp(columnIndex);
        return convertToLocalDateTime(timestamp);
    }

    /**
     * Timestamp를 LocalDateTime으로 변환
     * 
     * @param timestamp 변환할 Timestamp (null 가능)
     * @return LocalDateTime (null 가능)
     */
    private LocalDateTime convertToLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        
        // Timestamp를 LocalDateTime으로 직접 변환
        // JDBC URL의 serverTimezone 설정에 따라 이미 변환된 값이므로
        // 그대로 LocalDateTime으로 변환
        return timestamp.toLocalDateTime();
    }
}

