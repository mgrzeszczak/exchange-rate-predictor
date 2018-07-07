package com.github.mgrzeszczak.erp.repository

import com.github.mgrzeszczak.erp.model.ExchangeRate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

import java.time.LocalDate

@DataJpaTest
class ExchangeRateRepositoryITSpec extends Specification {

    @Autowired
    ExchangeRateRepository exchangeRateRepository

    def "should save ExchangeRate correctly"() {
        given:
            def exchangeRate = ExchangeRate.builder()
                    .code("code")
                    .currency("currency")
                    .value(BigDecimal.ONE)
                    .date(LocalDate.MAX)
                    .build()
            exchangeRateRepository.save(exchangeRate)
        expect:
            exchangeRateRepository.findAll() == [exchangeRate]
    }

    def "should find unique codes"() {
        given:
            def codes = ["a", "b", "c", "c", "b"]
            def rates = codes.collect {
                ExchangeRate.builder()
                        .code(it)
                        .currency("currency")
                        .value(BigDecimal.ONE)
                        .date(LocalDate.MAX)
                        .build()
            }
            exchangeRateRepository.saveAll(rates)
        expect:
            exchangeRateRepository.findDistinctCodes().toSorted() == ["a", "b", "c"]
    }

    def "should find rates for codes between"() {
        given:
            def rates = (1..10).collect() {
                return ExchangeRate.builder()
                        .code(it == 4 ? "code2" : "code")
                        .currency("currency")
                        .value(BigDecimal.ONE)
                        .date(LocalDate.MAX.minusDays(it))
                        .build()
            }
            exchangeRateRepository.saveAll(rates)
        when:
            def result = exchangeRateRepository.findAllBetween(
                    LocalDate.MAX.minusDays(5),
                    LocalDate.MAX.minusDays(2),
                    ["code"]
            )
        then:
            result.collect { it.date }.toSorted() == [LocalDate.MAX.minusDays(5),
                                                      LocalDate.MAX.minusDays(3),
                                                      LocalDate.MAX.minusDays(2)]
    }


}
