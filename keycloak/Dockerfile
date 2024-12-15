FROM quay.io/keycloak/keycloak:26.0.2 AS builder
RUN /opt/keycloak/bin/kc.sh build

FROM quay.io/keycloak/keycloak:26.0.2
COPY --from=builder /opt/keycloak/ /opt/keycloak/
# default entrypoint
