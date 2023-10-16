# Missing dependencies
- mockk
- jupiter?

# Missing implementation
- error handler
- testcontainers for database testing
- test that logger works as expected

# To run with docker database
```bash
docker-compose -f docker-compose.yml up
```

# To setup database
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
