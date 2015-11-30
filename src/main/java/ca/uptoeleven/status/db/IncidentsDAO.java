package ca.uptoeleven.status.db;

import ca.uptoeleven.status.core.Incident;
import com.google.common.collect.ImmutableList;
import org.skife.jdbi.v2.TransactionIsolationLevel;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RegisterMapper(IncidentMapper.class)
public abstract class IncidentsDAO {

	@Transaction(TransactionIsolationLevel.REPEATABLE_READ)
	public Incident create(Incident incident) {
		insert(incident);
		for (String serviceId : incident.getAffectedServicesIds()) {
			insertAffectedServiceId(incident.getId(), serviceId);
		}
		return incident;
	}

	@SqlUpdate("insert into incidents (id, title, state, startTime, createdAt, updatedAt) values (:id, :title, :state, :startTime, :createdAt, :updatedAt)")
	abstract void insert(@BindBean Incident incident);

	@SqlUpdate("update incidents set state=:newState, updatedAt=:updatedAt where id=:id")
	public abstract void update(@Bind("id") String id, @Bind("newState") String newState, @Bind("updatedAt") LocalDateTime updatedAt);

	public Incident findById(String id) {
		Incident incident = findByIdBare(id);
		if (incident != null) {
			return incident.withAffectedServicesIds(ImmutableList.copyOf(findAffectedServiceIds(incident.getId())));
		}
		return null;
	}

	@SqlQuery("select id, title, state, startTime, createdAt, updatedAt from incidents where id = :id")
	public abstract Incident findByIdBare(@Bind("id") String id);

	@Transaction(TransactionIsolationLevel.REPEATABLE_READ)
	public List<Incident> findAllIncidents() {
		List<Incident> populatedIncidents = findAllIncidentsBare().stream().map(incident -> {
			List<String> affectedServiceIds = findAffectedServiceIds(incident.getId());
			return incident.withAffectedServicesIds(ImmutableList.copyOf(affectedServiceIds));
		}).collect(Collectors.toList());
		return populatedIncidents;
	}

	@Transaction(TransactionIsolationLevel.REPEATABLE_READ)
	public List<Incident> findActiveIncidents() {
		List<Incident> populatedIncidents = findActiveIncidentsBare().stream().map(incident -> {
			List<String> affectedServiceIds = findAffectedServiceIds(incident.getId());
			return incident.withAffectedServicesIds(ImmutableList.copyOf(affectedServiceIds));
		}).collect(Collectors.toList());
		return populatedIncidents;
	}

	@SqlQuery("select id, title, state, startTime, createdAt, updatedAt from incidents order by createdAt desc")
	abstract List<Incident> findAllIncidentsBare();

	@SqlQuery("select id, title, state, startTime, createdAt, updatedAt from incidents where state != 'resolved' order by createdAt desc")
	abstract List<Incident> findActiveIncidentsBare();

	@SqlUpdate("insert into incidentAffectedServices (incidentId, serviceId) values (:incidentId, :serviceId)")
	abstract void insertAffectedServiceId(@Bind("incidentId") String incidentId, @Bind("serviceId") String serviceId);

	@SqlQuery("select serviceId from incidentAffectedServices where incidentId=:incidentId")
	abstract List<String> findAffectedServiceIds(@Bind("incidentId") String incidentId);
}