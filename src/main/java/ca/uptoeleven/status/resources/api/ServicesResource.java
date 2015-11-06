package ca.uptoeleven.status.resources.api;

import ca.uptoeleven.status.db.ServiceStatusesDAO;
import ca.uptoeleven.status.db.ServicesDAO;
import ca.uptoeleven.status.core.Service;
import ca.uptoeleven.status.core.ServiceStatus;
import ca.uptoeleven.status.api.ServiceCreateModel;
import ca.uptoeleven.status.api.ServiceStatusViewModel;
import ca.uptoeleven.status.api.ServiceViewModel;
import com.google.inject.Inject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
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
        List<ServiceViewModel> vms = services.stream()
                .map(service -> map(getServiceStatusesMap(), service))
		        .collect(Collectors.<ServiceViewModel>toList());
       return vms;
    }

	private ServiceViewModel map(Map<String, ServiceStatus> statusMapping, Service service) {
		ServiceStatus status = statusMapping.get(service.getServiceStatusId());
		ServiceStatusViewModel statusViewModel = new ServiceStatusViewModel(status.getId(), status.getName(), status.getDisplayColor());
		return new ServiceViewModel(service.getId(), service.getName(), service.getDescription(), statusViewModel, service.getCreatedAt(), service.getUpdatedAt());
	}

	private Map<String, ServiceStatus> getServiceStatusesMap() {
        Map<String, ServiceStatus> mapping = new HashMap<>();
        List<ServiceStatus> statuses = serviceStatusesDAO.findAll();
        for(ServiceStatus status : statuses) {
            mapping.put(status.getId(), status);
        }
        return mapping;
    }

	@GET
	@Path("/{serviceId}")
	public ServiceViewModel getService(@PathParam("serviceId") String serviceId) {
		Service service = servicesDAO.findById(serviceId);
		return map(getServiceStatusesMap(), service);
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
	    URI uri = UriBuilder
			    .fromResource(ServicesResource.class)
			    .path(ServicesResource.class, "getService")
			    .resolveTemplate("serviceId", service.getId())
			    .build();
	    return Response.created(uri).build();
    }
}
