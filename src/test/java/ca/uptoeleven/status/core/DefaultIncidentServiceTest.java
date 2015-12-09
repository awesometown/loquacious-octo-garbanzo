package ca.uptoeleven.status.core;

import ca.uptoeleven.status.api.IncidentCreateModel;
import ca.uptoeleven.status.core.events.IncidentCreatedEvent;
import ca.uptoeleven.status.db.IncidentsRepository;
import ca.uptoeleven.status.db.ServicesDAO;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import lombok.Data;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static ca.uptoeleven.status.core.EntityHelpers.newIncidentForTest;
import static ca.uptoeleven.status.core.UtcDateTime.nowUtcZoned;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class DefaultIncidentServiceTest {

	@Mock
	private IncidentsRepository incidentsRepository;

	@Mock
	private ServicesDAO servicesDAO;

	@Before
	public void init() {
		initMocks(this);
	}

	@Test
	public void eventFiredWhenIncidentCreated() {
		Incident incident = newIncidentForTest();
		when(incidentsRepository.create(any())).thenReturn(incident);
		EventBus bus = new EventBus();
		CreateIncidentTestListener listener = new CreateIncidentTestListener();
		bus.register(listener);

		DefaultIncidentService service = new DefaultIncidentService(bus, incidentsRepository, servicesDAO);
		service.createIncident(new IncidentCreateModel("foo", "bar", "whatever", "whatever", "whatever", new ArrayList<>(), nowUtcZoned()));

		assertNotNull(listener.getEvent());
		assertEquals(incident, listener.getEvent().getIncident());
	}

	@Data
	class CreateIncidentTestListener {
		private IncidentCreatedEvent event;

		@Subscribe public void onIncidentCreated(IncidentCreatedEvent event) {
			this.event = event;
		}
	}
}
