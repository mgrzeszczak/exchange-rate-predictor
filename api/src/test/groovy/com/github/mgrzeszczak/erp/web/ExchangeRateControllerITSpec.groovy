package com.github.mgrzeszczak.erp.web

import com.github.mgrzeszczak.erp.service.ExchangeRatePredictionService
import com.github.mgrzeszczak.erp.service.ExchangeRateSearchService
import io.reactivex.Flowable
import io.reactivex.Single
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

    def "should return known currency codes"() {
        given:
            1 * exchangeRateSearchService.findAvailableCodes() >> Flowable.just("a", "b", "c")
        expect:
            webClient.get()
                    .uri("/api/exchange-rate/codes")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .json("[\"a\", \"b\", \"c\"]")
    }

    def "should return searched results"() {
        given:
            1 * exchangeRateSearchService.findBetween(_, _, _) >> Flowable.empty()
        expect:
            webClient.get()
                    .uri("/api/exchange-rate/USD/2018-01-01/2018-01-01")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .json("[]")
    }

    def "should return prediction results"() {
        given:
            1 * exchangeRatePredictionService.predict(_, _) >> Single.just(Collections.emptyList())
        expect:
            webClient.get()
                    .uri("/api/exchange-rate/predict/USD/7")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .json("[]")
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
