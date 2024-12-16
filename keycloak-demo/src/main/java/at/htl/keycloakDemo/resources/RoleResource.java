package at.htl.keycloakDemo.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.RoleRepresentation;

@Path("/admin/roles")
public class RoleResource {
    @Inject
    Keycloak keycloak;

    @ConfigProperty(name = "realm.name", defaultValue = "master")
    String realmName;

    @GET
    public Response getRoles() {
        //keycloak.realm("my-realm").clients().get("quarkus-client").authorization().getSettings();

        return Response.ok(
            keycloak
                .realm(realmName)
                .roles()
                .list()
        ).build();
    }

    @POST
    @Path("/{roleName}")
    public Response createRole(@PathParam("roleName") String roleName) {
        RoleRepresentation role = new RoleRepresentation();
        role.setName(roleName);
        keycloak.realm(realmName).roles().create(role);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{roleName}")
    public Response deleteRole(@PathParam("roleName") String roleName) {
        keycloak.realm(realmName).roles().deleteRole(roleName);
        return Response.ok().build();
    }
}
