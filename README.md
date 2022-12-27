# CS Event Logs Processor Batch

Event logs processor batch is highly performant solution developed using Spring Boot Batch. This batch reads the events available in logfile.txt and stores the result in database. Based on event’s states i.e. STARTED and FINISHED, processor captures the time and store start time, end time, difference in time and alert true or false if difference is more than 4 milliseconds.
Spring batch uses reader, processor and writer components to achieve the objective. This is multithreaded solution in which Steps executes in multithreaded environment using specified throttle limit.

## Pre-requisites to run the batch:

- JDK 1.8
- Apache Maven

Note: JDK and Maven path should be set properly to run the installation


## Installation

- Download the source from GitHub using below command.

```sh
git clone https://github.com/dharmendranikumbh/CSEventLogProcessorBatch.git
```

- Go to CSEventLogProcessorBatch directory.

```sh
cd CSEventLogProcessorBatch
```

- Build artifact and run test using below command.

```sh
mvn clean install
```

- Check Jacoco Unit Test Reports @ CSEventLogProcessorBatch\target\site\jacoco\index.html
- Run the Batch using below command. Replace the <EVENT LOG FILE PATH> with logfile.txt location.

```sh
mvn spring-boot:run -Dspring-boot.run.arguments="--LOG_FILE_NAME=<EVENT LOG FILE PATH>"
```
Example: mvn spring-boot:run -Dspring-boot.run.arguments="--LOG_FILE_NAME=C:\logfile.txt"
