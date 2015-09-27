package ca.uptoeleven.status.db;

import ca.uptoeleven.status.db.models.IncidentUpdate;
import ca.uptoeleven.status.db.models.IncidentUpdateMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(IncidentUpdateMapper.class)
public interface IncidentUpdatesDAO {
    @SqlUpdate("insert into incidentUpdates (id , incidentId, description, newState, newServiceStatusId, createdAt, updatedAt) values (:id, :incidentId, :description, :newState, :newServiceStatusId, :createdAt, :updatedAt)")
    void insert(@BindBean IncidentUpdate update);

    @SqlQuery("select id, incidentId, description, newState, newServiceStatusId, createdAt, updatedAt from incidentUpdates where id = :id ")
    IncidentUpdate findById(@Bind("id") String id);

    @SqlQuery("select id, incidentId, description, newState, newServiceStatusId, createdAt, updatedAt from incidentUpdates where incidentId = :incidentId")
    List<IncidentUpdate> findByIncidentId(@Bind("incidentId") String incidentId);
}