package at.htl.keycloakDemo.resources;

import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;

@Path("/admin")
public class RolesResource {
    @Inject
    Keycloak keycloak;

    @GET
    @Path("/roles")
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

    @POST
    @Path("/realm/{realmName}")
    public Response createRealm(@PathParam("realmName") String realmName) {
        /*var realm = keycloak.realms()
                .findAll()
                .stream()
                .filter(r -> Objects.equals(r.getId(), realmName))
                .findFirst()
                .orElse(null);*/
        if (realmName == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        var realmRepresentation = new RealmRepresentation();
        realmRepresentation.setId(realmName);
        realmRepresentation.setRealm(realmName);
        realmRepresentation.setEnabled(true);

        try {
            keycloak.realms().create(realmRepresentation);
        } catch (Exception e) {
            return Response.status(Response.Status.CONFLICT).entity("Realm already exists").build();
        }

        return Response.ok().build();
    }

    @POST
    @Path("/realm/{realmName}/{clientName}")
    public Response createClientInRealm(@PathParam("realmName") String realmName, @PathParam("clientName") String clientName) {
        if (realmName == null || clientName == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        var clientRepresentation = new ClientRepresentation();
        clientRepresentation.setId(clientName);
        clientRepresentation.setEnabled(true);
        clientRepresentation.setAlwaysDisplayInConsole(true);

        try {
            keycloak.realm(realmName).clients().create(clientRepresentation);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Realm doesn't exist").build();
        }

        return Response.ok().build();
    }

    @DELETE
    @Path("/realm/{realmName}")
    public Response deleteRealm(@PathParam("realmName") String realmName) {
        keycloak.realms().realm(realmName).remove();
        return Response.ok().build();
    }

    @DELETE
    @Path("/realm/{realmName}/{clientName}")
    public Response deleteClientInRealm(@PathParam("realmName") String realmName, @PathParam("clientName") String clientName) {
        if (realmName == null || clientName == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else if (keycloak.realm(realmName) == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Realm doesn't exist").build();
        } else if (keycloak.realm(realmName).clients().get(clientName) == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Client doesn't exist").build();
        }

        Log.error(keycloak.realm(realmName).clients().get(clientName));

        keycloak.realm(realmName).clients().get(clientName).remove();
        return Response.ok().build();
    }
}
