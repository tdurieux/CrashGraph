# CrashGraph [![Build Status](https://travis-ci.org/tdurieux/CrashGraph.svg)](https://travis-ci.org/tdurieux/CrashGraph)

This project uses the algorithm describes in the paper "Crash graphs: An aggregated view of multiple crashes to improve crash triage." written by Kim, Sunghun and Zimmermann, Thomas and Nagappan, Nachiappan. 

The purpose of this project is to analyze the relevance of this algorithm to 
automatic classify bug reports.

This project uses Eclipse bug reports.

## Execution

```bash
mvn package
java -jar target/CrashGraph-0.0.1-SNAPSHOT.jar <folder_to_eclipse_reports> <threshold>
```

### Example
```bash
mvn package
java -jar target/CrashGraph-0.0.1-SNAPSHOT.jar src/main/resource/reports/ 0.5
```

## Test

 ```bash
mvn test
```