package ca.uptoeleven.status.db;

import ca.uptoeleven.status.core.IncidentUpdate;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RegisterMapper(IncidentUpdateMapper.class)
public abstract class IncidentUpdatesDAO {

    public IncidentUpdate create(IncidentUpdate incidentUpdate) {
        LocalDateTime now = LocalDateTime.now();
        incidentUpdate = incidentUpdate
                .withId(UUID.randomUUID().toString())
                .withCreatedAt(now)
                .withUpdatedAt(now);
        insert(incidentUpdate);
        return incidentUpdate;
    }

    @SqlUpdate("insert into incidentUpdates (id , incidentId, description, newState, newServiceStatusId, createdAt, updatedAt) values (:id, :incidentId, :description, :newState, :newServiceStatusId, :createdAt, :updatedAt)")
    abstract void insert(@BindBean IncidentUpdate update);

    @SqlQuery("select id, incidentId, description, newState, newServiceStatusId, createdAt, updatedAt from incidentUpdates where id = :id ")
    abstract IncidentUpdate findById(@Bind("id") String id);

    @SqlQuery("select id, incidentId, description, newState, newServiceStatusId, createdAt, updatedAt from incidentUpdates where incidentId = :incidentId")
    abstract List<IncidentUpdate> findByIncidentId(@Bind("incidentId") String incidentId);
}