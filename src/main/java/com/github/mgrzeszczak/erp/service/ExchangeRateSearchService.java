package com.github.mgrzeszczak.erp.service;

import com.github.mgrzeszczak.erp.model.ExchangeRate;
import com.github.mgrzeszczak.erp.repository.ExchangeRateRepository;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.IntStream;

@Service
@Slf4j
public class ExchangeRateSearchService {

    private final ExchangeRateRepository exchangeRateRepository;

    public ExchangeRateSearchService(@NotNull ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @PostConstruct
    public void fillData() {
        IntStream.range(1, 20)
                .mapToObj(i -> ExchangeRate.builder()
                        .code("code_" + i)
                        .currency("currency_" + i)
                        .date(LocalDate.of(2018, 3, i))
                        .value(BigDecimal.valueOf(i))
                        .build())
                .forEach(exchangeRateRepository::save);
    }

    @NotNull
    public Flowable<ExchangeRate> findBetween(@NotNull LocalDate from, @NotNull LocalDate to) {
        return exchangeRateRepository.rxFindAllBetween(from, to)
                .observeOn(Schedulers.computation());
    }

}
