# Based on WildFly
# See: https://hub.docker.com/r/jboss/wildfly/
FROM jboss/wildfly

ARG NK_WILDFLY_HOME_DIR=/opt/jboss/wildfly
ARG NK_CA_INSTALL_DIR=/opt/jboss/ca-install

# WildFly (global) configuration params
ARG NK_WILDFLY_ADMIN_USER=administrator
ARG NK_WILDFLY_ADMIN_PASS=qwerty
ARG NK_WILDFLY_CONFIGURE_SCRIPT=$NK_CA_INSTALL_DIR/wildfly.cli
ARG NK_WILDFLY_MYSQL_CONNECTOR_NAME=mysql-connector-java-8.0.20.jar
ARG NK_WILDFLY_LOGGER_PATTERN="%K{level}%d{HH:mm:ss,SSS} [%t] %5p %X{indent} %s%e{} a.t(%F:%L)%n"

# CollectorAssistant-specific (local) configuration params
ARG NK_CA_ARTIFACT_NAME=collector-assistant.war
ARG NK_CA_DATASOURCE_JNDI_NAME=java:/collectorapp/datasources/MainDS
ARG NK_CA_DATASOURCE_HOST_NAME=localhost
ARG NK_CA_DATASOURCE_PORT=3306
ARG NK_CA_DATASOURCE_SCHEMA=collector_assistant
ARG NK_CA_DATASOURCE_USER=root
ARG NK_CA_DATASOURCE_PASS=123456

# We need to do some commands as a root first
USER root

# Sadly, we also need to install mysql client for the startup script to wait for database connection being 'ready'
RUN yum -y install mysql

# Extract files required for installation
ADD ./install/ $NK_CA_INSTALL_DIR/
RUN chown -R jboss:root $NK_CA_INSTALL_DIR/

# Let's switch back to use the same user as wildfly image. This avoids permission issues
USER jboss

# Create administrator account
RUN /opt/jboss/wildfly/bin/add-user.sh $NK_WILDFLY_ADMIN_USER $NK_WILDFLY_ADMIN_PASS --silent

# Prepare cli configuration script (datasource, logging, etc)
RUN sed -i "s+NK_CA_INSTALL_DIR+$NK_CA_INSTALL_DIR+g" $NK_WILDFLY_CONFIGURE_SCRIPT \
    && sed -i "s+NK_WILDFLY_MYSQL_CONNECTOR_NAME+$NK_WILDFLY_MYSQL_CONNECTOR_NAME+g" $NK_WILDFLY_CONFIGURE_SCRIPT \
    && sed -i "s+NK_CA_DATASOURCE_JNDI_NAME+$NK_CA_DATASOURCE_JNDI_NAME+g" $NK_WILDFLY_CONFIGURE_SCRIPT \
    && sed -i "s+NK_CA_DATASOURCE_HOST_NAME+$NK_CA_DATASOURCE_HOST_NAME+g" $NK_WILDFLY_CONFIGURE_SCRIPT \
    && sed -i "s+NK_CA_DATASOURCE_PORT+$NK_CA_DATASOURCE_PORT+g" $NK_WILDFLY_CONFIGURE_SCRIPT \
    && sed -i "s+NK_CA_DATASOURCE_SCHEMA+$NK_CA_DATASOURCE_SCHEMA+g" $NK_WILDFLY_CONFIGURE_SCRIPT \
    && sed -i "s+NK_CA_DATASOURCE_USER+$NK_CA_DATASOURCE_USER+g" $NK_WILDFLY_CONFIGURE_SCRIPT \
    && sed -i "s+NK_CA_DATASOURCE_PASS+$NK_CA_DATASOURCE_PASS+g" $NK_WILDFLY_CONFIGURE_SCRIPT \
    && sed -i "s+NK_WILDFLY_LOGGER_PATTERN+$NK_WILDFLY_LOGGER_PATTERN+g" $NK_WILDFLY_CONFIGURE_SCRIPT
# Actually run the cli script
RUN $NK_CA_INSTALL_DIR/execute-wildfly-cli.sh $NK_WILDFLY_CONFIGURE_SCRIPT

# Don't deploy anything. Artifacts will be deployed by Arquillian automatically
# COPY ./install/$NK_CA_ARTIFACT_NAME /opt/jboss/wildfly/standalone/deployments/

# Setup to autostart the WildFly with the container
# Note: This script also waits for database to be available (That's why we need to pass SQL credentials there).
# Note #2: We're creating ENV from ARG, as ARG variables are not allowed in Docker's CMD command :(
ENV NK_CA_INSTALL_DIR ${NK_CA_INSTALL_DIR}
ENV NK_CA_DATASOURCE_HOST_NAME ${NK_CA_DATASOURCE_HOST_NAME}
ENV NK_CA_DATASOURCE_PORT ${NK_CA_DATASOURCE_PORT}
ENV NK_CA_DATASOURCE_USER ${NK_CA_DATASOURCE_USER}
ENV NK_CA_DATASOURCE_PASS ${NK_CA_DATASOURCE_PASS}
#CMD ["sh", "-c", "${NK_CA_INSTALL_DIR}/start-wildfly.sh", "${NK_CA_DATASOURCE_HOST_NAME2}", "${NK_CA_DATASOURCE_PORT}", "${NK_CA_DATASOURCE_USER}", "${NK_CA_DATASOURCE_PASS}"]
CMD "${NK_CA_INSTALL_DIR}/start-wildfly.sh" "${NK_CA_DATASOURCE_HOST_NAME}" "${NK_CA_DATASOURCE_PORT}" "${NK_CA_DATASOURCE_USER}" "${NK_CA_DATASOURCE_PASS}"