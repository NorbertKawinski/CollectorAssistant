@echo off
setlocal enableextensions enabledelayedexpansion

pushd docker
echo "- Deleting any leftovers from previous installation"
docker-compose -f docker-compose-test.yml down
popd

endlocal