package ca.uptoeleven.status.resources.ui.views.dashboard;

import ca.uptoeleven.status.api.IncidentViewModel;
import ca.uptoeleven.status.api.ServiceViewModel;
import io.dropwizard.views.View;
import lombok.Getter;
import org.ocpsoft.prettytime.PrettyTime;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Getter
public class DashboardView extends View {

	private final List<ServiceViewModel> services;

	private final List<IncidentViewModel> incidents;

	public DashboardView(List<ServiceViewModel> services, List<IncidentViewModel> incidents) {
		super("dashboard.ftl");
		this.services = services;
		this.incidents = incidents;
	}

	public String prettyDate(LocalDateTime dateTime) {
		PrettyTime p = new PrettyTime();
		ZonedDateTime zoned = ZonedDateTime.of(dateTime, ZoneOffset.UTC);
		Date date = Date.from(zoned.toInstant());
		return p.format(date);
	}
}