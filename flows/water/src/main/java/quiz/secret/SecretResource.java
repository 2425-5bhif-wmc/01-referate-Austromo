package quiz.secret;

import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/secrets")
@ApplicationScoped
public class SecretResource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Authenticated
    public Response getSecret() {
        return Response.ok("Here is something you do not know...").build();
    }
}
