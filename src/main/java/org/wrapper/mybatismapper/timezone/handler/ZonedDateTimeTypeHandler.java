package org.wrapper.mybatismapper.timezone.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

/**
 * ZonedDateTime을 위한 커스텀 TypeHandler
 * 
 * log4jdbc의 ResultSetSpy가 getObject(String, Class) 메서드를 지원하지 않아서
 * 커스텀 TypeHandler를 구현합니다.
 * 
 * Timestamp를 사용하여 ZonedDateTime으로 변환합니다.
 */
@MappedTypes(ZonedDateTime.class)
@MappedJdbcTypes(JdbcType.TIMESTAMP)
public class ZonedDateTimeTypeHandler extends BaseTypeHandler<ZonedDateTime> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ZonedDateTime parameter, JdbcType jdbcType) throws SQLException {
        // ZonedDateTime을 Timestamp로 변환하여 저장
        ps.setTimestamp(i, Timestamp.from(parameter.toInstant()));
    }

    @Override
    public ZonedDateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
        // getTimestamp를 사용하여 log4jdbc 호환성 확보
        Timestamp timestamp = rs.getTimestamp(columnName);
        return convertToZonedDateTime(timestamp);
    }

    @Override
    public ZonedDateTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(columnIndex);
        return convertToZonedDateTime(timestamp);
    }

    @Override
    public ZonedDateTime getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Timestamp timestamp = cs.getTimestamp(columnIndex);
        return convertToZonedDateTime(timestamp);
    }

    /**
     * Timestamp를 ZonedDateTime으로 변환
     * 
     * @param timestamp 변환할 Timestamp (null 가능)
     * @return ZonedDateTime (null 가능)
     */
    private ZonedDateTime convertToZonedDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        
        // Timestamp를 Instant로 변환한 후 시스템 기본 타임존으로 ZonedDateTime 생성
        // JDBC URL의 serverTimezone 설정에 따라 이미 변환된 값이므로
        // 시스템 기본 타임존을 사용하여 ZonedDateTime 생성
        Instant instant = timestamp.toInstant();
        return ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}
