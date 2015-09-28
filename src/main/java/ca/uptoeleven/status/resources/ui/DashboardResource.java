package ca.uptoeleven.status.resources.ui;

import ca.uptoeleven.status.resources.ui.views.DashboardView;
import io.dropwizard.views.View;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("dashboard")
@Produces(MediaType.TEXT_HTML)
public class DashboardResource {

    @GET
    public View getDashboard() {
        return new DashboardView();
    }
}
