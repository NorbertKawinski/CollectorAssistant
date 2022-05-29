#!/bin/bash

mkdir volumes

mkdir volumes/mariadb_backups
# Due to binding volumes UID must match that of the container's one
chown 1005:debian volumes/mariadb_backups

mkdir volumes/ca_uploads
chown 1000:debian volumes/ca_uploads

docker-compose up -d --build
