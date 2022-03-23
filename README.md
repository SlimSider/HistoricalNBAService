# Historical NBA Service

### Summary

This service provides a proxy on top of RapidAPI's Free NBA API and also adds an option to save custom comments for the
games and caches games to reduce response times.

The comments are saved in an in-memory database, which makes it easy to run, but it also means that the data will be
lost after restarting the application.

### Run

To run the service:
  1. Use your local mvn or the mvn wrapper to run the application: ./mvnw spring-boot:run or mvn spring-boot:run.
  2. Or you can open the project in an IDE and start it from there.

You can learn about the API endpoints after running the application at /swagger-ui




