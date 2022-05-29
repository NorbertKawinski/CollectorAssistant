@echo off
setlocal enableextensions enabledelayedexpansion

call _stop_test_server.cmd

pushd docker
echo "- Building images and starting containers"
docker-compose -f docker-compose-test.yml up --build --force-recreate -d
popd

endlocal