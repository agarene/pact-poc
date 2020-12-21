# Pact Poc
# prerequisites
- JDK > 1.8
- Docker
- Postgres
# Steps to run app
  - pact broker must up and run,to run pact broker run **docker-compose -f docker-files/docker-compose.yml**
  - go to consumer app and run **mvnw test install**, it will generates pact files and publish to pact broker,[we can see this in pact broker url after successful](http://localhost:9000).
   ![Alt text](screen-shots/Dashboard.JPG?raw=true "pact dashboard")
  - go to provider app and run **mvnw test**.
  
 
