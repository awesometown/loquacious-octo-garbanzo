package ca.uptoeleven.status.db;

import ca.uptoeleven.status.db.models.Incident;
import ca.uptoeleven.status.db.models.IncidentMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(IncidentMapper.class)
public interface IncidentsDAO {

    @SqlUpdate("insert into incidents (id, title, state, startTime, createdAt, updatedAt) values (:id, :title, :state, :startTime, :createdAt, :updatedAt)")
    void insert(@BindBean Incident incident);

    @SqlUpdate("update incidents set state=:newState where id=:id")
    void updateState(@Bind("id") String id, @Bind("newState") String newState);

    @SqlQuery("select id, title, state, startTime, createdAt, updatedAt from incidents where id = :id")
    Incident findById(@Bind("id") String id);

    @SqlQuery("select id, title, state, startTime, createdAt, updatedAt from incidents")
    List<Incident> findAllIncidents();
}