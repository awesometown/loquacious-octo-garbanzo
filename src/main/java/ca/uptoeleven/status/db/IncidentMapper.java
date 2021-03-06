package ca.uptoeleven.status.db;

import ca.uptoeleven.status.core.Incident;
import com.google.common.collect.ImmutableList;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class IncidentMapper implements ResultSetMapper<Incident> {

	public Incident map(int index, ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return new Incident(
				resultSet.getString("ID"),
				resultSet.getString("TITLE"),
				resultSet.getString("STATE"),
				resultSet.getString("TYPE"),
				new ArrayList<>(),
				resultSet.getTimestamp("STARTTIME").toLocalDateTime(),
				resultSet.getTimestamp("CREATEDAT").toLocalDateTime(),
				resultSet.getTimestamp("UPDATEDAT").toLocalDateTime(),
				ImmutableList.of());
	}

}