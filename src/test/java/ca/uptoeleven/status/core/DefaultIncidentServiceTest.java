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
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.time.ZonedDateTime;
import java.util.ArrayList;

import static ca.uptoeleven.status.core.EntityHelpers.newIncidentForTest;
import static ca.uptoeleven.status.core.UtcDateTime.nowUtcZoned;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class DefaultIncidentServiceTest {

	@Mock
	private IncidentsRepository mockRepository;

	@Mock
	private ServicesDAO mockServicesDAO;

	@Mock
	private EventBus mockEventBus;

	@Before
	public void init() {
		initMocks(this);
	}

	@Test
	public void startDateKeptWhenProvided() {
		ZonedDateTime startTime = nowUtcZoned().plusHours(1);
		IncidentCreateModel icm = newIncidentCreateModel().withStartTime(startTime);
		DefaultIncidentService service = new DefaultIncidentService(mockEventBus, mockRepository, mockServicesDAO);

		when(mockRepository.create(any())).thenReturn(newIncidentForTest());

		service.createIncident(icm);

		ArgumentCaptor<Incident> model = ArgumentCaptor.forClass(Incident.class);
		verify(mockRepository).create(model.capture());
		assertEquals(model.getValue().getStartTime(), startTime.toLocalDateTime());
	}

	@Test
	public void startDateSetToCreatedTimeIfNotProvided() {
		IncidentCreateModel icm = newIncidentCreateModel().withStartTime(null);
		DefaultIncidentService service = new DefaultIncidentService(mockEventBus, mockRepository, mockServicesDAO);

		when(mockRepository.create(any())).thenReturn(newIncidentForTest());

		service.createIncident(icm);

		ArgumentCaptor<Incident> model = ArgumentCaptor.forClass(Incident.class);
		verify(mockRepository).create(model.capture());
		assertEquals(model.getValue().getStartTime(), model.getValue().getCreatedAt());
	}

	@Test
	public void eventFiredWhenIncidentCreated() {
		Incident incident = newIncidentForTest();
		when(mockRepository.create(any())).thenReturn(incident);
		EventBus bus = new EventBus();
		CreateIncidentTestListener listener = new CreateIncidentTestListener();
		bus.register(listener);

		DefaultIncidentService service = new DefaultIncidentService(bus, mockRepository, mockServicesDAO);
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

	private IncidentCreateModel newIncidentCreateModel() {
		return new IncidentCreateModel("foo", "bar", "whatever", "whatever", "whatever", new ArrayList<>(), nowUtcZoned());
	}
}
