package com.github.mgrzeszczak.erp.web;

import com.github.mgrzeszczak.erp.model.ExchangeRate;
import com.github.mgrzeszczak.erp.service.ExchangeRatePredictionService;
import com.github.mgrzeszczak.erp.service.ExchangeRateSearchService;
import io.reactivex.Flowable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/exchange-rate")
@Slf4j
public class ExchangeRateController {

    private final ExchangeRatePredictionService exchangeRatePredictionService;
    private final ExchangeRateSearchService exchangeRateSearchService;

    public ExchangeRateController(ExchangeRatePredictionService exchangeRatePredictionService, ExchangeRateSearchService exchangeRateSearchService) {
        this.exchangeRatePredictionService = exchangeRatePredictionService;
        this.exchangeRateSearchService = exchangeRateSearchService;
    }

    @GetMapping("/{from}/{to}")
    public Flowable<ExchangeRate> findBetween(@PathVariable("from") LocalDate from, @PathVariable("to") LocalDate to) {
        return exchangeRateSearchService.findBetween(from, to);
    }

    @GetMapping("/predict/{from}/{to}")
    public Flowable<ExchangeRate> predict(@PathVariable("from") LocalDate from, @PathVariable(value = "to", required = false) LocalDate to) {
        return Flowable.empty();
    }

}
