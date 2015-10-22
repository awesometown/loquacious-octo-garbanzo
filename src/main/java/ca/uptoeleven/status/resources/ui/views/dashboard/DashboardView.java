package ca.uptoeleven.status.resources.ui.views.dashboard;

import ca.uptoeleven.status.api.IncidentViewModel;
import ca.uptoeleven.status.api.ServiceViewModel;
import io.dropwizard.views.View;
import lombok.Getter;
import org.joda.time.DateTime;
import org.ocpsoft.prettytime.Duration;
import org.ocpsoft.prettytime.PrettyTime;
import org.ocpsoft.prettytime.impl.DurationImpl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
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

    public String classForStatus(String incidentStatus) {
        return "alert-success";
    }

    public String prettyDate(ZonedDateTime dateTime) {
	    PrettyTime p = new PrettyTime();
	    Date date = Date.from(dateTime.toInstant());
	    return p.format(date);
    }
}