FROM quay.io/keycloak/keycloak:26.0.7 as builder
RUN /opt/keycloak/bin/kc.sh build

FROM quay.io/keycloak/keycloak:26.0.7
COPY --from=builder /opt/keycloak/ /opt/keycloak/
# default entrypoint
