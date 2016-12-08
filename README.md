# Bus Route

## User Stories

### MVP

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
```

where
- `N` (integer) - the number of bus rotes in the file
- `ID` (integer) - bus rote ID
- `station` (integer) - station ID

In another words our file contains an integer `N` in the first line specifying the number of bus routes 
in the file, followed by `N` lines, where each line represents a specific bus route.

AC: Should support the following volume of data

- 100,000 bus routes
- 1,000,000 bus stations
- 1,000 number of stations in a bus route

3. As a bus provider<br />
I want to have a web REST API for route checking<br />
So that I can integrate my lecacy systems to the new system<br />

