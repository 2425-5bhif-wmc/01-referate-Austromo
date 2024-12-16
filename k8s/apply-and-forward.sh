#!/usr/bin/env bash

set -e

_dir=$(dirname $0)

echo -e "\napplying deployments..."
kubectl apply -f ${_dir}/kc-namespace.yml
kubectl apply -f ${_dir}/kc-configmap.yml
kubectl apply -f ${_dir}/kc-pvc.yml
kubectl apply -f ${_dir}/keycloak-db.yml
kubectl apply -f ${_dir}/keycloak.yml
kubectl apply -f ${_dir}/quarkus-backend.yml
kubectl apply -f ${_dir}/js-frontend.yml

echo -e "\nrestarting deployments..."
kubectl --namespace=keycloak-prod rollout restart deployment keycloak-db
kubectl --namespace=keycloak-prod rollout restart deployment keycloak
kubectl --namespace=keycloak-prod rollout restart deployment quarkus-backend
kubectl --namespace=keycloak-prod rollout restart deployment js-frontend

echo -e "\nwaiting for deployments to become available..."
kubectl --namespace=keycloak-prod wait --for=condition=available --timeout=600s deployment/keycloak-db
kubectl --namespace=keycloak-prod wait --for=condition=available --timeout=600s deployment/keycloak
kubectl --namespace=keycloak-prod wait --for=condition=available --timeout=600s deployment/quarkus-backend
kubectl --namespace=keycloak-prod wait --for=condition=available --timeout=600s deployment/js-frontend

echo -e "\nwaiting for pods to come online..."
kubectl --namespace=keycloak-prod wait --for=condition=ready --timeout=600s pod --all

echo -e "\nforwarding ports...."
kubectl --namespace=keycloak-prod port-forward deployment/keycloak 8000:8080 & \
kubectl --namespace=keycloak-prod port-forward deployment/quarkus-backend 8080:8080 & \
kubectl --namespace=keycloak-prod port-forward deployment/js-frontend 3000:80

echo -e "\nPress CTRL-C to exit"
wait
