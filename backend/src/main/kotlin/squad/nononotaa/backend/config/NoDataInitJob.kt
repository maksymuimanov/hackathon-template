package squad.nononotaa.backend.config

import org.springframework.stereotype.Component

@Component
class NoDataInitJob : ApplicationInitJob {
    override fun execute() {
        // Do nothing, needed only to avoid startup issues
    }
}