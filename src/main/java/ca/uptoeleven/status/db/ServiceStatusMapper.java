package ca.uptoeleven.status.db;

import ca.uptoeleven.status.core.ServiceStatus;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceStatusMapper implements ResultSetMapper<ServiceStatus>
{
    public ServiceStatus map(int index, ResultSet resultSet, StatementContext statementContext) throws SQLException
    {
        return new ServiceStatus(resultSet.getString("ID"), resultSet.getString("NAME"), resultSet.getString("DISPLAYCOLOR"), resultSet.getInt("SORTORDER" ));
    }
}