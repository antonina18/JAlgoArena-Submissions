package com.jalgoarena

import com.jalgoarena.data.SubmissionsRepository
import com.jalgoarena.ranking.BasicRankingCalculator
import com.jalgoarena.ranking.BasicScoreCalculator
import com.jalgoarena.ranking.BonusPointsForBestTimeRankingCalculator
import com.jalgoarena.ranking.RankingCalculator
import com.jalgoarena.web.ProblemsClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestOperations
import org.springframework.web.client.RestTemplate
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
open class AppConfiguration {

    @Bean
    open fun rankingCalculator(submissionsRepository : SubmissionsRepository, problemsClient: ProblemsClient): RankingCalculator {
        val scoreCalculator = BasicScoreCalculator()
        val rankingCalculator = BasicRankingCalculator(submissionsRepository, scoreCalculator)

        return BonusPointsForBestTimeRankingCalculator(
                submissionsRepository, rankingCalculator
        )
    }

    @Bean
    open fun restTemplate(): RestOperations = RestTemplate()
}
