package at.htl.keycloakDemo.resources;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;

import java.util.List;
import java.util.UUID;

@Path("/admin/realms")
public class RealmResource {
    @Inject
    Keycloak keycloak;

    @POST
    @Path("/configured/{realmName}")
    public Response createConfiguredRealm(@PathParam("realmName") String realmName) {
        if (realmName == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        keycloak.realms().findAll().stream()
                .filter(r -> r.getRealm().equals(realmName))
                .findFirst().ifPresent(oldRealm -> keycloak.realms().realm(realmName).remove());

        var realmRepresentation = new RealmRepresentation();
        realmRepresentation.setId(UUID.randomUUID().toString());
        realmRepresentation.setRealm(realmName);
        realmRepresentation.setLoginTheme("custom");
        realmRepresentation.setEnabled(true);

        var quarkusClient = new ClientRepresentation();
        quarkusClient.setId("quarkus-client");
        quarkusClient.setName("quarkus-client");
        quarkusClient.setDescription("[generated] A client for a Quarkus application");
        quarkusClient.setEnabled(true);
        quarkusClient.setAlwaysDisplayInConsole(true);
        quarkusClient.setServiceAccountsEnabled(true);

        var jsClient = new ClientRepresentation();
        jsClient.setId("js-client");
        jsClient.setName("js-client");
        jsClient.setDescription("[generated] A client for a Javascript application");
        jsClient.setEnabled(true);
        jsClient.setAlwaysDisplayInConsole(true);

        realmRepresentation.setClients(List.of(quarkusClient, jsClient));
        keycloak.realms().create(realmRepresentation);
        return Response.ok().build();
    }

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
        realmRepresentation.setId(UUID.randomUUID().toString());
        realmRepresentation.setRealm(realmName);
        realmRepresentation.setEnabled(true);
        keycloak.realms().create(realmRepresentation);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{realmName}")
    @RolesAllowed("custom-admin")
    public Response deleteRealm(@PathParam("realmName") String realmName) {
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
