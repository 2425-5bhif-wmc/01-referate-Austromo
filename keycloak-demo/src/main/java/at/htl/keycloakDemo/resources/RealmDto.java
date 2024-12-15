package at.htl.keycloakDemo.resources;

import org.keycloak.representations.idm.RealmRepresentation;

public record RealmDto(String id, String realm) {
    public static RealmDto representationToDto(RealmRepresentation realmRepresentation) {
        return new RealmDto(realmRepresentation.getId(), realmRepresentation.getRealm());
    }
}
