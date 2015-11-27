package ca.uptoeleven.status.resources.api;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/isAuthenticated")
public class CredentialsResource {

	/* Called by clients to validate credentials, eg. during a login flow. */
	@RolesAllowed("ADMIN")
	@GET
	public Response areValid() {
		return Response.ok().build();
	}
}
