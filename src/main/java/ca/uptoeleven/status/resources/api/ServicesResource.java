package ca.uptoeleven.status.resources.api;

import ca.uptoeleven.status.db.ServicesDAO;
import ca.uptoeleven.status.db.models.Service;
import ca.uptoeleven.status.resources.models.ServiceCreateModel;
import ca.uptoeleven.status.resources.models.ServiceStatusViewModel;
import ca.uptoeleven.status.resources.models.ServiceViewModel;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("/api/services")
@Produces(MediaType.APPLICATION_JSON)
public class ServicesResource {

    private final ServicesDAO servicesDAO;

    @Inject
    public ServicesResource(ServicesDAO servicesDAO) {
        this.servicesDAO = servicesDAO;
    }

    @GET
    public List<ServiceViewModel> getServices() {
        List<Service> services = servicesDAO.findAll();
        List<ServiceViewModel> vms = services.stream()
                .map(service -> new ServiceViewModel(service.getId(), service.getName(), service.getDescription(), service.getServiceStatusId(), service.getCreatedAt(), service.getUpdatedAt()))
                .collect(Collectors.<ServiceViewModel>toList());
       return vms;
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
