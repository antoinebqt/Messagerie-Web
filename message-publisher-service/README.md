# PolyPublisher

## This project uses
- Maven 4.0.0
- Spring 3.1.3
- Java 17
- JDK 19
---

### Description
This service made in Java Spring Boot is responsible for receiving information from users, specially messages, to store them in a Cassandra database and publish them in a Pub/Sub

### Prerequisites
- Having a Google Cloud Platform account
- Having a Google Cloud Pub/Sub with correct topic created
- Having a Cassandra database running, preferably in GCP

### Deploy the App on Google Cloud Platform
- Change the `application.properties` file to match your Cassandra database credentials and Pub/Sub credentials
- Deploy the service inside App Engine following the official [documentation here](https://cloud.google.com/appengine/docs/standard/java-gen2/building-app/deploying-web-service?hl=fr)