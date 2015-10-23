package ca.uptoeleven.status.db;

import ca.uptoeleven.status.core.IncidentUpdate;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IncidentUpdateMapper implements ResultSetMapper<IncidentUpdate>
{
    public IncidentUpdate map(int index, ResultSet resultSet, StatementContext statementContext) throws SQLException
    {
        return new IncidentUpdate(
                resultSet.getString("ID"),
                resultSet.getString("DESCRIPTION"),
                resultSet.getString("NEWSTATE"),
                resultSet.getString("NEWSERVICESTATUSID" ),
                resultSet.getTimestamp("CREATEDAT" ).toLocalDateTime(),
                resultSet.getTimestamp("UPDATEDAT" ).toLocalDateTime());
    }
}