package ca.uptoeleven.status.resources;

import ca.uptoeleven.status.api.IncidentUpdateCreateModel;
import ca.uptoeleven.status.api.IncidentUpdateViewModel;
import ca.uptoeleven.status.api.IncidentViewModel;
import ca.uptoeleven.status.core.*;
import ca.uptoeleven.status.resources.api.IncidentsResource;
import com.google.common.collect.Lists;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IncidentResourceTests {

	private static final IncidentService service = mock(IncidentService.class);

	private final Incident incident = Incident.newIncident("aTitle", "anUpdate", IncidentState.Unplanned.IDENTIFIED, ServiceStatus.OK.getId(), Lists.newArrayList("service1"));

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

	@Test
	public void testUpdateIncident() {
		IncidentUpdateCreateModel update = new IncidentUpdateCreateModel("an update", IncidentState.Unplanned.MONITORING, ServiceStatus.DEGRADED.getId());

		when(service.getIncident(incident.getId())).thenReturn(incident);
		when(service.updateIncident(incident.getId(), update)).thenReturn(incident.withAdditionalUpdate(IncidentUpdate.createNew(update.getDescription(), update.getState(), update.getServiceStatusId())));

		IncidentsResource resource = new IncidentsResource(service);
		IncidentViewModel updated = resource.updateIncident(incident.getId(), update);
		assertEquals(2, updated.getIncidentUpdates().size());
		IncidentUpdateViewModel updateViewModel = updated.getIncidentUpdates().get(1);
		assertEquals(update.getDescription(), updateViewModel.getDescription());
		assertEquals(update.getState(), updateViewModel.getState());
	}
}
