package ca.uptoeleven.status.resources.api;

import ca.uptoeleven.status.resources.models.ServiceViewModel;
import com.google.common.collect.Lists;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/services")
@Produces(MediaType.APPLICATION_JSON)
public class ServicesResource {

    @GET
    public List<ServiceViewModel> getList() {
        ServiceViewModel vm = new ServiceViewModel("1", "A Service", "A description");
        return Lists.newArrayList(vm);
    }
}
