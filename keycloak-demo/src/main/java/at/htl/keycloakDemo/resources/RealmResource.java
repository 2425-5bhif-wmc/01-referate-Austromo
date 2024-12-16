package at.htl.keycloakDemo.resources;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;

@Path("/admin/realms")
public class RealmResource {
    @Inject
    Keycloak keycloak;

    @GET
    public Response getAllRealms() {
        return Response.ok(keycloak.realms().findAll().stream().map(RealmDto::representationToDto)).build();
    }

    @POST
    @Path("/{realmName}")
    public Response createRealm(@PathParam("realmName") String realmName) {
        if (realmName == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        var realmRepresentation = new RealmRepresentation();
        realmRepresentation.setId(realmName);
        realmRepresentation.setRealm(realmName);
        realmRepresentation.setEnabled(true);

        keycloak.realms().create(realmRepresentation);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{realmName}")
    @RolesAllowed("custom-admin")
    public Response deleteRealm(@PathParam("realmName") String realmName) {
        //Log.error(keycloak.realms().findAll().stream().map(RealmRepresentation::getRealm).filter(r -> r.equals(realmName)).findFirst().orElse(null));
        keycloak.realms().realm(realmName).remove();
        return Response.ok().build();
    }

    @POST
    @Path("/{realmName}/client/{clientName}")
    public Response createClientInRealm(@PathParam("realmName") String realmName, @PathParam("clientName") String clientName) {
        if (realmName == null || clientName == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        var clientRepresentation = new ClientRepresentation();
        clientRepresentation.setId(clientName);
        clientRepresentation.setEnabled(true);
        clientRepresentation.setAlwaysDisplayInConsole(true);

        return keycloak.realm(realmName).clients().create(clientRepresentation);
    }

    @DELETE
    @Path("/{realmName}/client/{clientName}")
    public Response deleteClientInRealm(@PathParam("realmName") String realmName, @PathParam("clientName") String clientName) {
        if (realmName == null || clientName == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        keycloak.realm(realmName).clients().get(clientName).remove();
        return Response.ok().build();
    }
}
