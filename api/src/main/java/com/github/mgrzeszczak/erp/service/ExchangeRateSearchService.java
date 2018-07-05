package com.github.mgrzeszczak.erp.service;

import com.github.mgrzeszczak.erp.model.ExchangeRate;
import com.github.mgrzeszczak.erp.repository.ExchangeRateRepository;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;

@Service
@Slf4j
public class ExchangeRateSearchService {

    private final ExchangeRateRepository exchangeRateRepository;

    public ExchangeRateSearchService(@NotNull ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @NotNull
    public Flowable<ExchangeRate> findBetween(@NotNull LocalDate from,
                                              @NotNull LocalDate to,
                                              @NotNull String codes) {
        return Single.just(codes)
                .map(s -> Arrays.asList(s.split(",")))
                .toFlowable()
                .flatMap(c -> exchangeRateRepository.rxFindAllBetween(from, to, c))
                .observeOn(Schedulers.computation());
    }

    @NotNull
    public Flowable<String> findAvailableCodes() {
        return exchangeRateRepository.rxFindDistinctCodes()
                .observeOn(Schedulers.computation());
    }

}
