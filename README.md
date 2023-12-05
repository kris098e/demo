# To run application

## Setup database with docker-compose
```bash
docker-compose -f docker-compose.yml up
```
## Setup database
```bash
brew install liquibase

pushd "src/main/resources/"

liquibase update \
  --changelog-file "db/liquibase-changelog.xml"  \
  --url "jdbc:postgresql://localhost:5432/software" \
  --username "postgres" \
  --password "postgres"

popd
```

## generate jooq
```bash
./gradlew generateJooq
```

## run application
```bash
./gradlew run
```

# Generate test coverage report
```bash
./gradlew testCodeCoverageReport
```
