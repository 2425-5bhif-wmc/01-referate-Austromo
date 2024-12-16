package at.htl.keycloakDemo.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;

@Path("/admin/users")
public class UserResource {
    @Inject
    Keycloak keycloak;

    @ConfigProperty(name = "realm.name", defaultValue = "master")
    String realmName;

    @GET
    public Response getUsers() {
        return Response.ok(
            keycloak
                .realm(realmName)
                .users()
                .list()
        ).build();
    }

    @POST
    @Path("/{userName}")
    public Response createUser(@PathParam("userName") String userName) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userName);
        return keycloak.realm(realmName).users().create(user);
    }

    @DELETE
    @Path("/{userName}")
    public Response deleteUser(@PathParam("userName") String userName) {
        var userId = keycloak.realm(realmName).users()
                .searchByUsername(userName, false)
                .stream()
                .findFirst()
                .map(UserRepresentation::getId)
                .orElse("-1");

        return keycloak.realm(realmName).users().delete(userId);
    }
}
