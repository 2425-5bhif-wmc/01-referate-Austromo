#!/bin/sh

mvn io.quarkus.platform:quarkus-maven-plugin:3.17.4:create \
    -DprojectGroupId=at.htl.leonding \
    -DprojectArtifactId=water \
    -Dextensions='oidc,keycloak-authorization,rest-jackson' \
    -DnoCode
