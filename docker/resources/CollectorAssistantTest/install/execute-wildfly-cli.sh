#!/bin/bash

# Usage: execute-wildfly-cli.sh <WildFly cli-script path> [WildFly mode] [configuration file]
#
# This script:
# - Starts WildFly server
# - Executes provided cli-script
# - Stops WildFly
#
# Due to the nature of WildFly, executing cli-scripts is possible only on running WildFly server.
# This script helps manages that.
#
# The default mode is 'standalone' and default configuration is based on the mode.
# It can be 'standalone.xml' or 'domain.xml'.

JBOSS_HOME=/opt/jboss/wildfly
JBOSS_CLI=$JBOSS_HOME/bin/jboss-cli.sh
JBOSS_MODE=${2:-"standalone"}
JBOSS_CONFIG=${3:-"$JBOSS_MODE.xml"}

function wait_for_server() {
  until `$JBOSS_CLI -c "ls /deployment" &> /dev/null`; do
    sleep 1
  done
}

echo "=> Starting WildFly server"
$JBOSS_HOME/bin/$JBOSS_MODE.sh -c $JBOSS_CONFIG > /dev/null &

echo "=> Waiting for the server to boot"
wait_for_server

echo "=> Content of commands file ($1)"
cat "$1"

echo "=> Executing the commands"
# echo "the command ===> $JBOSS_CLI -c --file=$1"
$JBOSS_CLI -c --file="$1"

echo "=> Shutting down WildFly"
if [ "$JBOSS_MODE" = "standalone" ]; then
  $JBOSS_CLI -c ":shutdown"
else
  $JBOSS_CLI -c "/host=*:shutdown"
fi

