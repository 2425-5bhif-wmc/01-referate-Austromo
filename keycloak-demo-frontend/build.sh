#!/usr/bin/env bash

IMAGE_NAME=ghcr.io/austromo/kc-demo-frontend
PLATFORM=linux/amd64

docker login ghcr.io

docker buildx create \
    --use \
    --name multi-arch \
    --driver=docker-container

docker buildx build \
        --platform $PLATFORM \
        --tag $IMAGE_NAME:latest \
        --file Dockerfile \
        --cache-from=type=local,src=/tmp/.buildx-cache \
        --cache-to=type=local,dest=/tmp/.buildx-cache \
        --output=type=registry \
        --push \
        .

docker buildx rm multi-arch
