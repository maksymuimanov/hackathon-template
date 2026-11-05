package squad.nononotaa.backend.config

import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import squad.nononotaa.backend.util.logger

@Configuration
class ApplicationConfig {
    val logger = logger()

    @Bean
    fun applicationRunner(
        applicationInitJobs: Iterable<ApplicationInitJob>,
    ): ApplicationRunner = {
        applicationInitJobs.forEach {
            logger.info("Starting init job: {}", it::class.qualifiedName)
            it.execute()
            logger.info("Executed init job: {}", it::class.qualifiedName)
        }
    }
}