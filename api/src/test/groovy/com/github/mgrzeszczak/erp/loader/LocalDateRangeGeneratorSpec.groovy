package com.github.mgrzeszczak.erp.loader

import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

@Unroll
class LocalDateRangeGeneratorSpec extends Specification {

    def classUnderTests = new LocalDateRangeGenerator()

    def "should generate local date range from #start to #end with batch size #step"() {
        when:
            def result = classUnderTests.generate(LocalDate.parse(start), LocalDate.parse(end), step)
            print(result)
        then:
            result.size() == count
        where:
            start        | end          | step || count
            '2018-01-01' | '2018-01-01' | 1    || 1
            '2018-01-01' | '2018-01-03' | 1    || 2
            '2018-01-01' | '2018-01-31' | 30   || 1
            '2018-01-01' | '2018-02-01' | 30   || 2
            '2018-01-01' | '2018-03-04' | 30   || 3
    }

}
