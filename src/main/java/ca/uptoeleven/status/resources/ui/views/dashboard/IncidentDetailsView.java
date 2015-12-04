package ca.uptoeleven.status.resources.ui.views.dashboard;

import ca.uptoeleven.status.api.IncidentViewModel;
import ca.uptoeleven.status.api.ServiceViewModel;
import io.dropwizard.views.View;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class IncidentDetailsView extends View {

	@Getter
	private final IncidentViewModel incident;

	private final Map<String, String> serviceIdNameMap;

	public IncidentDetailsView(IncidentViewModel incident, List<ServiceViewModel> services) {
		super("incident.ftl");
		this.incident = incident;
		this.serviceIdNameMap = services.stream().collect(Collectors.toMap(ServiceViewModel::getId, ServiceViewModel::getName));
	}

	public String nameForServiceId(String serviceId) {
		return serviceIdNameMap.get(serviceId);
	}

}
