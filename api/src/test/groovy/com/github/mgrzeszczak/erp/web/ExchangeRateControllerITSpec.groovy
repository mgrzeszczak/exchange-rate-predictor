package com.github.mgrzeszczak.erp.web

import com.github.mgrzeszczak.erp.service.ExchangeRatePredictionService
import com.github.mgrzeszczak.erp.service.ExchangeRateSearchService
import io.reactivex.Flowable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.test.web.reactive.server.WebTestClient
import spock.lang.Specification
import spock.mock.DetachedMockFactory

@WebFluxTest(ExchangeRateController)
@Import(TestConfig.class)
class ExchangeRateControllerITSpec extends Specification {

    @Autowired
    ExchangeRateSearchService exchangeRateSearchService

    @Autowired
    ExchangeRatePredictionService exchangeRatePredictionService

    @Autowired
    WebTestClient webClient

    def "test"() {
        given:
            exchangeRateSearchService.findBetween(_, _, _) >> Flowable.empty()
        expect:
            webClient.get()
                    .uri("/api/exchange-rate/USD/2018-01-01/2018-01-01")
                    .exchange()
                    .expectStatus().isOk()
    }

    @TestConfiguration
    static class TestConfig {

        def mockFactory = new DetachedMockFactory()

        @Bean
        ExchangeRateSearchService exchangeRateSearchService() {
            return mockFactory.Mock(ExchangeRateSearchService)
        }

        @Bean
        ExchangeRatePredictionService exchangeRatePredictionService() {
            return mockFactory.Mock(ExchangeRatePredictionService)
        }

    }

}
