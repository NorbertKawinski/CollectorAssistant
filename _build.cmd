@echo off
setlocal enableextensions enabledelayedexpansion

rem This is a section where you can customize the build process according to your needs
:CUSTOMIZATION

rem Whether to compile the Collector Assistant sources into a .war file
rem The resulting archive will be available in !CA_ARTIFACT!
rem Recognized values: [TRUE, FALSE]
SET COMPILE_CA=TRUE

rem Whether to run automatic tests after the compilation finish.
rem Recognized values: [TRUE, FALSE]
SET TEST_BUILD=FALSE

rem Whether to start the test-server automatically or leave it up to the user.
rem If you frequently run the tests, you might want to manage the server manually and start it just once (faster compile-test loop)
rem as opposed to repeatedly turning it off and on again by this script.
rem Recognized values: [AUTOMATIC, MANUAL]
SET TEST_SERVER_STARTUP=MANUAL

rem Whether to build & run a Docker's container with the Collector Assistant
rem Recognized values: [TRUE, FALSE]
SET START_SERVER=FALSE

rem Once deployed, whether to open Collector Assistant in the browser
rem Recognized values: [TRUE, FALSE]
SET OPEN_IN_BROWSER=FALSE

rem Your customization is complete here. 
rem You can now run this script

rem This section contains more detailed and rarely changed variables.
:CONFIGURATION

rem Location where the compiler will output the resulting CollectorAssistant web-archive
rem Note: Changing this variable WON'T change the output location. It's merely for documentation purpose.
rem       To actually change output location, one must change it somewhere in Maven
SET CA_ARTIFACT=.\target\collector-assistant.war

rem Configuration for Docker
SET DOCKER_BASE_DIR=.\docker
SET DOCKER_CA_ARTIFACT=!DOCKER_BASE_DIR!\resources\CollectorAssistant\deployments\collector-assistant.war

rem Which port will CA be exposed for browser connections
SET CA_PORT=12000

rem Here follows the main script's logic.
:MAIN_SCRIPT

if "!COMPILE_CA!" equ "TRUE" (
	echo "Compiling Collector Assistant"
	
	if "!TEST_BUILD!" equ "TRUE" (
		echo "- Tests enabled"
		
		if "!TEST_SERVER_STARTUP!" equ "AUTOMATIC" (
			echo "- Starting test server..."
			call _run_test_server.cmd
			echo "- Waiting for test server to initialize"
			rem TODO: Find some better way to wait
			ping 127.0.0.1 -n 60 > nul
			echo "- Test server started"
		) else (
			echo "- Test server started manually. No action taken"
		)
		
		echo "- Running Maven"
		call mvn clean package
	) else (
		echo "- Tests disabled"

		echo "- Running Maven"
		call mvn clean package -DskipTests
	)
	
	echo "Compilation done"
)

if not exist !CA_ARTIFACT! (
	echo "CA artifact not found. Aborting further processing"
	GOTO :MAIN_SCRIPT_END
)

echo "- Copying deployment artifacts"
copy /Y !CA_ARTIFACT! !DOCKER_CA_ARTIFACT!

if "!START_SERVER!" equ "TRUE" (
	echo "Starting server..."

	pushd docker
	echo "- Deleting any leftovers from previous installation"
	docker-compose down
	echo "- Building images and starting containers"
	docker-compose up --build --force-recreate -d
	popd
	
	if "!OPEN_IN_BROWSER!" equ "TRUE" (
		echo "TODO: Waiting for CA to be ready for connections"
		
		echo "Opening CA application in the browser"
		start "" http://localhost:!CA_PORT!/ca/
	)
)

:MAIN_SCRIPT_END
endlocal
