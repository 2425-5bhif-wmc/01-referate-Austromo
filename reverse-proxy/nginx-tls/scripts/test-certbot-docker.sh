#!/bin/bash

docker compose run --rm certbot certonly \
	--webroot --webroot-path /var/www/certbot \
	--dry-run \
	-d $DOMAINS \
	--register-unsafely-without-email --agree-tos
