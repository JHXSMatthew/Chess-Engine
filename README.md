
# Chess

  
  
 - Java Chess Engine (chessEngine)
 - Spring RESTful API (api)
 - React Frontend (chess-engine_frontend)
  
 

## Install
### Download

    git clone https://github.com/JHXSMatthew/Chess-Engine
and cd into the project folder.

### Build Engine
Firstly, build the engine and update the dependecy of the API.  

    cd api
    bash updateDependency.sh

### Run API
Then run the API. API requires a MySQL database instance to persist data. The default one was used for COMP4920 demo. You can change it from 

> api/src/main/resources/application.properties

It listen on localhost by default, you could change the address and port is listens in the file as well. 

Run api by:
    

    cd api
    mvn spring-boot:run

then return to the project root

    cd ..

### Run frontend API
	cd chess-engine-frontend
	yarn start

