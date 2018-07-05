package com.github.mgrzeszczak.erp.web;

import com.github.mgrzeszczak.erp.model.ExchangeRate;
import com.github.mgrzeszczak.erp.service.ExchangeRatePredictionService;
import com.github.mgrzeszczak.erp.service.ExchangeRateSearchService;
import io.reactivex.Flowable;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/exchange-rate")
@Slf4j
public class ExchangeRateController {

    private final ExchangeRatePredictionService exchangeRatePredictionService;
    private final ExchangeRateSearchService exchangeRateSearchService;

    public ExchangeRateController(@NotNull ExchangeRatePredictionService exchangeRatePredictionService,
                                  @NotNull ExchangeRateSearchService exchangeRateSearchService) {
        this.exchangeRatePredictionService = exchangeRatePredictionService;
        this.exchangeRateSearchService = exchangeRateSearchService;
    }

    @GetMapping("/{codes}/{from}/{to}")
    public Flowable<ExchangeRate> findBetween(@PathVariable("from") LocalDate from,
                                              @PathVariable("to") LocalDate to,
                                              @PathVariable("codes") String codes) {
        return exchangeRateSearchService.findBetween(from, to, codes);
    }

    @GetMapping("/codes")
    public Single<List<String>> findCodes() {
        return exchangeRateSearchService.findAvailableCodes()
                .toList();
    }

    @GetMapping("/predict/{codes}/{from}/{to}")
    public Flowable<ExchangeRate> predict(@PathVariable("codes") String codes,
                                          @PathVariable("from") LocalDate from,
                                          @PathVariable(value = "to", required = false) LocalDate to) {
        return Flowable.empty();
    }

}
