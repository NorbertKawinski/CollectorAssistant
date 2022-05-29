#!/bin/bash

echo "=> Waiting for database being 'ready'"
# echo "=> Debug x $1 x $2 x $3 x $4 x"
# while ! mysql --host="$1" --port="$2" --user="$3" --password="$4" -e "SELECT 1" >/dev/null 2>&1; do
while ! mysql --host="$1" --port="$2" --user="$3" --password="$4" -e "SELECT 1"; do
    echo "===> Still waiting..."
    sleep 3
done

echo "=> Starting WildFly server"
# By default bind server and admin console on all addressess (0.0.0.0 not just 127.0.0.1 as is default)
/opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0
