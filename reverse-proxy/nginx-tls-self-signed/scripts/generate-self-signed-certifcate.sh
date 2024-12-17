#!/bin/bash
# run this from the nginx-tls directory

DOMAIN="echo.localhost"

CERTS_DIR="${PWD}/certs"
KEY_FILE="${CERTS_DIR}/private.key"
SIGN_REQUEST_FILE="${CERTS_DIR}/request.csr"
CERT_FILE="${CERTS_DIR}/certificate.crt"
DHPARAM_FILE="${CERTS_DIR}/dhparam.pem"

openssl genrsa -out $KEY_FILE 4096
openssl req -new -key $KEY_FILE -out $SIGN_REQUEST_FILE -subj "/CN=${DOMAIN}"
openssl x509 -req -days 365 -in $SIGN_REQUEST_FILE -signkey $KEY_FILE -out $CERT_FILE
openssl dhparam -out $DHPARAM_FILE 4096
