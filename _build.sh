
echo "Compiling Collector Assistant"
mvn clean package -DskipTests
echo "Compilation done"

echo "Copying deployment artifacts"
cp ./target/collector-assistant.war ./docker/resources/CollectorAssistant/deployments/collector-assistant.war

echo "Starting server..."
pushd docker || exit
echo "- Deleting any leftovers from previous installation"
docker-compose down
echo "- Building images and starting containers"
docker-compose up --build --force-recreate -d
popd || exit
