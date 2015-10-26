package ca.uptoeleven.status.resources;

import ca.uptoeleven.status.api.IncidentViewModel;
import ca.uptoeleven.status.core.Incident;
import ca.uptoeleven.status.core.IncidentService;
import ca.uptoeleven.status.core.IncidentState;
import ca.uptoeleven.status.core.ServiceStatus;
import ca.uptoeleven.status.resources.api.IncidentsResource;
import com.google.common.collect.Lists;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IncidentResourceTests {

	private static final IncidentService service = mock(IncidentService.class);

	private final Incident incident = Incident.newIncident("aTitle", "anUpdate", IncidentState.IDENTIFIED, ServiceStatus.OK.getId(), Lists.newArrayList("service1"));

	@Test
	public void testGetIncident() {
		when(service.getIncident(incident.getId())).thenReturn(incident);

		IncidentsResource resource = new IncidentsResource(service);
		IncidentViewModel ivm = resource.getIncident(incident.getId());
		assertEquals(incident.getId(), ivm.getId());
		assertEquals(incident.getTitle(), ivm.getTitle());
		assertEquals(incident.getState(), ivm.getState());
		assertEquals(incident.getCreatedAt(), ivm.getCreatedAt());
	}
}
