package project.factory

import io.micronaut.context.annotation.EachBean
import io.micronaut.context.annotation.Factory
import org.jooq.DSLContext
import org.jooq.conf.RenderKeywordCase
import org.jooq.conf.RenderNameCase
import org.jooq.conf.RenderQuotedNames
import org.jooq.conf.Settings

@Factory
class JooqConfigurationFactory {

    @EachBean(DSLContext::class)
    fun jooqSettings(): Settings = Settings()
            .withRenderNameCase(RenderNameCase.LOWER)
            .withRenderQuotedNames(RenderQuotedNames.NEVER)
            .withRenderKeywordCase(RenderKeywordCase.UPPER)
            .withExecuteLogging(true)
}