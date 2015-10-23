package ca.uptoeleven.status.db;

import ca.uptoeleven.status.core.IncidentUpdate;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(IncidentUpdateMapper.class)
public abstract class IncidentUpdatesDAO {

    @SqlUpdate("insert into incidentUpdates (id , incidentId, description, newState, newServiceStatusId, createdAt, updatedAt) values (:id, :incidentId, :description, :newState, :newServiceStatusId, :createdAt, :updatedAt)")
    abstract void insert(@Bind("incidentId") String incidentId, @BindBean IncidentUpdate update);

    @SqlQuery("select id, incidentId, description, newState, newServiceStatusId, createdAt, updatedAt from incidentUpdates where id = :id ")
    abstract IncidentUpdate findById(@Bind("id") String id);

    @SqlQuery("select id, incidentId, description, newState, newServiceStatusId, createdAt, updatedAt from incidentUpdates where incidentId = :incidentId")
    abstract List<IncidentUpdate> findByIncidentId(@Bind("incidentId") String incidentId);

    @SqlCall("delete from incidentUpdates where incidentId = :incidentId")
    abstract void clear(@Bind("incidentId") String incidentId);
}