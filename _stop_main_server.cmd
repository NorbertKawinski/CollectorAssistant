@echo off
setlocal enableextensions enabledelayedexpansion

pushd docker
echo "- Stopping server"
docker-compose down
popd

endlocal