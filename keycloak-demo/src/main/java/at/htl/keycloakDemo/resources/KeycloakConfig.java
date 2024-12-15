package at.htl.keycloakDemo.resources;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperties;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ConfigProperties(prefix = "quarkus.keycloak.admin-client")
@ApplicationScoped
public class KeycloakConfig {
    /**
     * The Keycloak server URL.
     */
    @ConfigProperty(name = "server-url")
    public String serverUrl;

    /**
     * The realm name to authenticate against.
     */
    @ConfigProperty(name = "realm")
    public String realm;

    /**
     * The client ID used for authentication.
     */
    @ConfigProperty(name = "client-id")
    public String clientId;

    /**
     * The grant type.
     */
    @ConfigProperty(name = "grant-type")
    public String grantType;

    /**
     * The client secret used for authentication.
     */
    @ConfigProperty(name = "client-secret")
    public String clientSecret;
}
