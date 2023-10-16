package project.application.repo.setup

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jooq.impl.DefaultDSLContext
import project.factory.JooqConfigurationFactory
import javax.sql.DataSource

abstract class TestConfig {

    private val dbDataSource: DataSource by lazy {
        HikariDataSource(
            HikariConfig().apply {
                maximumPoolSize = 2
                jdbcUrl = "jdbc:postgresql://localhost:5432/software"
                username = "postgres"
                password = "postgres"
            },
        )
    }

    val dslContext by lazy {
        DefaultDSLContext(
            dbDataSource,
            org.jooq.SQLDialect.POSTGRES,
            JooqConfigurationFactory().jooqSettings()
        )
    }
}