# Spring Reactive Demo

Minimal Spring Boot WebFlux + Project Reactor demo.

Build:

```powershell
mvn -v
mvn clean package
```

Run:

```powershell
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

Smoke test:

```powershell
# Use PowerShell's Invoke-RestMethod or curl
Invoke-RestMethod http://localhost:8080/stream
# or
curl http://localhost:8080/stream
```

The `/stream` endpoint returns an SSE stream emitting a JSON-like line with current time and sequence number every second.
