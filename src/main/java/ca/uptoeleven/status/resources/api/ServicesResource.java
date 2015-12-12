package ca.uptoeleven.status.resources.api;

import ca.uptoeleven.status.api.ListHolder;
import ca.uptoeleven.status.api.ServiceCreateModel;
import ca.uptoeleven.status.api.ServiceViewModel;
import ca.uptoeleven.status.core.Service;
import ca.uptoeleven.status.db.ServicesDAO;
import com.google.inject.Inject;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static ca.uptoeleven.status.core.UtcDateTime.asUtc;

@Path("/services")
@Produces(MediaType.APPLICATION_JSON)
public class ServicesResource {

	private final ServicesDAO servicesDAO;

	@Inject
	public ServicesResource(ServicesDAO servicesDAO) {
		this.servicesDAO = servicesDAO;
	}

	@GET
	public ListHolder<ServiceViewModel> getServices() {
		List<Service> services = servicesDAO.findAll();
		List<ServiceViewModel> vms = services.stream().map(service -> map(service))
				.collect(Collectors.<ServiceViewModel>toList());
		return new ListHolder<>(vms);
	}

	private ServiceViewModel map(Service service) {
		return new ServiceViewModel(service.getId(), service.getName(), service.getDescription(), service.getServiceStatusId(), asUtc(service.getCreatedAt()), asUtc(service.getUpdatedAt()));
	}

	@GET
	@Path("/{serviceId}")
	public ServiceViewModel getService(@PathParam("serviceId") String serviceId) {
		Service service = servicesDAO.findById(serviceId);
		return map(service);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed("ADMIN")
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
