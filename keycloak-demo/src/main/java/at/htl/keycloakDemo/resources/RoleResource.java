package at.htl.keycloakDemo.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;

@Path("/admin/roles")
public class RoleResource {
    @Inject
    Keycloak keycloak;

    @GET
    public Response getRoles() {
        //Log.error(keycloak.realm("quarkus").users().count());
        //Log.error(keycloak.realm("quarkus").roles().list());

        //keycloak.realm("my-realm").clients().get("quarkus-client").authorization().getSettings();

        return Response.ok(
            keycloak
                .realm("my-realm")
                .roles()
                .list()
        ).build();
    }
}
