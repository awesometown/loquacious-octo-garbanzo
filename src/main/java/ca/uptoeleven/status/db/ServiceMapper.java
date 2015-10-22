package ca.uptoeleven.status.db;

import ca.uptoeleven.status.core.Service;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceMapper implements ResultSetMapper<Service>
{
    public Service map(int index, ResultSet resultSet, StatementContext statementContext) throws SQLException
    {
        return new Service(
                resultSet.getString("ID"),
                resultSet.getString("NAME"),
                resultSet.getString("DESCRIPTION"),
                resultSet.getString("SERVICESTATUSID"),
                resultSet.getTimestamp("CREATEDAT" ).toLocalDateTime(),
                resultSet.getTimestamp("UPDATEDAT" ).toLocalDateTime());
    }
}