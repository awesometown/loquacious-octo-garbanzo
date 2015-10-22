package ca.uptoeleven.status.db;

import ca.uptoeleven.status.core.Incident;
import org.skife.jdbi.v2.TransactionIsolationLevel;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RegisterMapper(IncidentMapper.class)
public abstract class IncidentsDAO {

    @Transaction(TransactionIsolationLevel.REPEATABLE_READ)
    public Incident create(Incident incident) {
        incident = incident.withId(UUID.randomUUID().toString());
        insert(incident);
        for(String serviceId : incident.getAffectedServicesIds()) {
            insertAffectedServiceId(incident.getId(), serviceId);
        }
        return incident;
    }

    @SqlUpdate("insert into incidents (id, title, state, startTime, createdAt, updatedAt) values (:id, :title, :state, :startTime, :createdAt, :updatedAt)")
    abstract void insert(@BindBean Incident incident);

    @SqlUpdate("update incidents set state=:newState where id=:id")
    public abstract void updateState(@Bind("id") String id, @Bind("newState") String newState);

    public Incident findById(String id) {
        Incident incident = findByIdBare(id);
        if (incident != null) {
            return incident.withAffectedServicesIds(findAffectedServiceIds(incident.getId()));
        }
        return null;
    }

    @SqlQuery("select id, title, state, startTime, createdAt, updatedAt from incidents where id = :id")
    public abstract Incident findByIdBare(@Bind("id") String id);

    @Transaction(TransactionIsolationLevel.REPEATABLE_READ)
    public List<Incident> findAllIncidents() {
        List<Incident> populatedIncidents = findAllIncidentsBare().stream().map(incident -> {
            List<String> affectedServiceIds = findAffectedServiceIds(incident.getId());
            return incident.withAffectedServicesIds(affectedServiceIds);
        }).collect(Collectors.toList());
        return populatedIncidents;
    }

    @SqlQuery("select id, title, state, startTime, createdAt, updatedAt from incidents")
    abstract List<Incident> findAllIncidentsBare();

    @SqlUpdate("insert into incidentAffectedServices (incidentId, serviceId) values (:incidentId, :serviceId)")
    abstract void insertAffectedServiceId(@Bind("incidentId") String incidentId, @Bind("serviceId") String serviceId);

    @SqlQuery("select serviceId from incidentAffectedServices where incidentId=:incidentId")
    abstract List<String> findAffectedServiceIds(@Bind("incidentId") String incidentId);
}