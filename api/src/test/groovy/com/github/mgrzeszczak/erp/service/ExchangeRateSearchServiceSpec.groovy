package com.github.mgrzeszczak.erp.service

import com.github.mgrzeszczak.erp.repository.ExchangeRateRepository
import io.reactivex.Flowable
import spock.lang.Specification

import java.time.LocalDate

class ExchangeRateSearchServiceSpec extends Specification {

    def exchangeRateRepository = Mock(ExchangeRateRepository)
    def exchangeRateSearchService = new ExchangeRateSearchService(exchangeRateRepository)

    def "should find all currency codes"() {
        given:
            1 * exchangeRateRepository.rxFindDistinctCodes() >> Flowable.just("code")
        expect:
            exchangeRateSearchService.findAvailableCodes().toList()
                    .blockingGet() == ["code"]

    }

    def "should find exchange rates between"() {
        given:
            1 * exchangeRateRepository.rxFindAllBetween(_, _, _) >> Flowable.empty()
        expect:
            exchangeRateSearchService.findBetween(LocalDate.now(), LocalDate.now(), "")
                    .toList()
                    .blockingGet() == []
    }

}
