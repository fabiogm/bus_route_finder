# Bus Route Finder

## About

This service provides a REST API for filtering direct bus connections between a departure and an arrival station. Routes are loaded via
a configuration file and given this set of routes this service can answer if there is any route that directly serves two given stations.

## Technical

This is a Spring Boot application using Java 8 and Gradle as build tool.

## Running

Dependencies:
- Java 8
- Gradle

After all dependencies have been installed you can build the application using

```bash
./buid.sh
```

and run it in the background using

```bash
./service.sh start <route file>
```

To stop the service, execute

```bash
./service.sh stop
```

## User Stories

This is a group of stories that guided the development of the MVP.

1. As a bus provider<br />
I want to know if two stations are directly connected<br />

2. As a bus provider<br />
I want to load bus route data files<br />
So that I can dinamically specify bus routes<br />

AC: Should accept the following bus route data file format

```
N
ID station station station ...
ID station ...
...

- N (integer) - the number of bus rotes in the file
- ID (integer) - bus rote ID
- station (integer) - station ID
```

AC: Should support the following volume of data

```
- 100,000 bus routes
- 1,000,000 bus stations
- 1,000 number of stations in a bus route
```

3. As a bus provider<br />
I want to have a web REST API for route checking<br />
So that I can integrate my legacy systems to the new system<br />

## Technical debts

- The graph search can take a lot of time and there is no heuristic in place to abort search.
- Performance testing with big amounts of stations were not performed.
- Command line reading and parsing could be greatly improved. 
- Graph and route concerns could be better separated in or extracted from RoutePlanner class.
- Internal server error and client error reporting is not adherent to REST.
- Documentation should be improved and consistent.
- There is no contract testing.

