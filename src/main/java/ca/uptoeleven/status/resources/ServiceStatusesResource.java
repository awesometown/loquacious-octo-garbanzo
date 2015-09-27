package ca.uptoeleven.status.resources;

import ca.uptoeleven.status.db.ServiceStatusesDAO;
import ca.uptoeleven.status.db.models.ServiceStatus;
import ca.uptoeleven.status.resources.models.ServiceStatusViewModel;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("statuses")
@Produces(MediaType.APPLICATION_JSON)
public class ServiceStatusesResource {

    private final ServiceStatusesDAO dao;

    @Inject
    public ServiceStatusesResource(ServiceStatusesDAO dao) {
        this.dao = dao;
    }

    @GET
    public List<ServiceStatusViewModel> getAllStatuses() {
        List<ServiceStatus> statuses = dao.findAllStatuses();
        List<ServiceStatusViewModel> viewModels = statuses.stream()
                .map(status -> new ServiceStatusViewModel(status.getId(), status.getName(), status.getDisplayColor()))
                .collect(Collectors.<ServiceStatusViewModel> toList());
        return viewModels;
    }

    @GET
    @Path("/{statusid}")
    public ServiceStatusViewModel getStatusById(@PathParam("statusid")String statusId) {
        ServiceStatus status =  dao.findById(statusId);
        return new ServiceStatusViewModel(status.getId(), status.getName(), status.getDisplayColor());
    }
}
