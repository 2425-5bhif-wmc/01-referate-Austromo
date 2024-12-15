CERTS_DIR="${PWD}/certs"

openssl genrsa -out "${CERTS_DIR}/private.key" 4096
openssl req -new -key "${CERTS_DIR}/private.key" -out "${CERTS_DIR}/request.csr" -subj "/CN=localhost"
openssl x509 -req -days 365 -in "${CERTS_DIR}/request.csr" -signkey "${CERTS_DIR}/private.key" -out "${CERTS_DIR}/certificate.crt"
openssl dhparam -out "${CERTS_DIR}/dhparam.pem" 4096
