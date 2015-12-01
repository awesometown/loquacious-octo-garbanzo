package ca.uptoeleven.status.resources.ui.views.admin;

import ca.uptoeleven.status.api.ServiceViewModel;
import ca.uptoeleven.status.core.IncidentState;
import ca.uptoeleven.status.core.ServiceStatus;
import ca.uptoeleven.status.api.IncidentViewModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import io.dropwizard.views.View;
import lombok.Getter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public class IncidentUpdateView extends View {

	@Getter
	private final IncidentViewModel incident;

	@Getter
	private final Map<String, ServiceViewModel> services;

	public IncidentUpdateView(IncidentViewModel incident, List<ServiceViewModel> services) {
		super("incident_update.ftl");
		this.incident = incident;
		this.services = services.stream().collect(Collectors.toMap(ServiceViewModel::getId, Function.identity()));
	}

	public List<ServiceStatus> getStatuses() {
		return Lists.newArrayList(ServiceStatus.OK, ServiceStatus.DEGRADED, ServiceStatus.MINOR, ServiceStatus.MAJOR);
	}

	public List<String> getStates() {
		return Lists.newArrayList(IncidentState.Unplanned.INVESTIGATING, IncidentState.Unplanned.IDENTIFIED, IncidentState.Unplanned.MONITORING, IncidentState.Unplanned.RESOLVED);
	}

	public String nameForServiceId(String serviceId) {
		return services.get(serviceId).getName();
	}

	public String getIncidentJson() throws IOException {
		return new ObjectMapper().writeValueAsString(incident);
	}
}
