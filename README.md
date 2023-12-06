# Pre-requisites
- java 17
- docker
- docker-compose
- liquibase

# To run backend application

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

# To run frontend application
```bash
pushd "frontend"

pip3 install requests
pip3 install flask

python3 main.py

popd
```

Now go to `http://localhost:4000`, and try logging in with

- username: chairman
- password: chair

