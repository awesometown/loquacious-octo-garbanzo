package ca.uptoeleven.status.resources.api;

import ca.uptoeleven.status.db.ServiceStatusesDAO;
import ca.uptoeleven.status.db.ServicesDAO;
import ca.uptoeleven.status.db.models.Service;
import ca.uptoeleven.status.core.ServiceStatus;
import ca.uptoeleven.status.resources.models.ServiceCreateModel;
import ca.uptoeleven.status.resources.models.ServiceStatusViewModel;
import ca.uptoeleven.status.resources.models.ServiceViewModel;
import com.google.inject.Inject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("/api/services")
@Produces(MediaType.APPLICATION_JSON)
public class ServicesResource {

    private final ServicesDAO servicesDAO;
    private final ServiceStatusesDAO serviceStatusesDAO;

    @Inject
    public ServicesResource(ServicesDAO servicesDAO, ServiceStatusesDAO serviceStatusesDAO) {
        this.servicesDAO = servicesDAO;
        this.serviceStatusesDAO = serviceStatusesDAO;
    }

    @GET
    public List<ServiceViewModel> getServices() {
        List<Service> services = servicesDAO.findAll();
        Map<String, ServiceStatus> statusMapping = getServiceStatusesMap();
        List<ServiceViewModel> vms = services.stream()
                .map(service -> {
                    ServiceStatus status = statusMapping.get(service.getServiceStatusId());
                    ServiceStatusViewModel statusViewModel = new ServiceStatusViewModel(status.getId(), status.getName(), status.getDisplayColor());
                    return new ServiceViewModel(service.getId(), service.getName(), service.getDescription(), statusViewModel, service.getCreatedAt(), service.getUpdatedAt());
                })
                .collect(Collectors.<ServiceViewModel>toList());
       return vms;
    }

    private Map<String, ServiceStatus> getServiceStatusesMap() {
        Map<String, ServiceStatus> mapping = new HashMap<>();
        List<ServiceStatus> statuses = serviceStatusesDAO.findAll();
        for(ServiceStatus status : statuses) {
            mapping.put(status.getId(), status);
        }
        return mapping;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createService(ServiceCreateModel createModel) {
        LocalDateTime now = LocalDateTime.now();

        Service service = new Service(
                UUID.randomUUID().toString(),
                createModel.getName(),
                createModel.getDescription(),
                "ok",
                now,
                now);
        servicesDAO.insert(service);
        return Response.created(URI.create("/"+service.getId())).build();
    }
}
