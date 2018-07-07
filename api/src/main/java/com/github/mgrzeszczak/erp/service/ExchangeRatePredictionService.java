package com.github.mgrzeszczak.erp.service;

import com.github.mgrzeszczak.erp.dto.PredictionData;
import com.github.mgrzeszczak.erp.model.ExchangeRate;
import com.github.mgrzeszczak.erp.repository.ExchangeRateRepository;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class ExchangeRatePredictionService {

    private static final int WEEK = 7;

    private final ExchangeRateRepository exchangeRateRepository;

    public ExchangeRatePredictionService(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @NotNull
    public Single<List<PredictionData>> predict(@NotNull String codesString, int count) {
        return Single.fromCallable(() -> Arrays.asList(codesString.split(",")))
                .toFlowable()
                .flatMap(v -> exchangeRateRepository.rxFindAllBetween(LocalDate.now().minusDays(2*WEEK), LocalDate.now(), v))
                .observeOn(Schedulers.computation())
                .toList()
                .map(values -> predictNextNDaysByCode(values, count));
    }

    @NotNull
    private List<PredictionData> predictNextNDaysByCode(@NotNull List<ExchangeRate> rates, int count) {
        return rates.stream()
                .collect(Collectors.groupingBy(ExchangeRate::getCode))
                .entrySet()
                .stream()
                .map(entry -> PredictionData.builder()
                        .code(entry.getKey())
                        .real(entry.getValue())
                        .predicted(predictNextNDays(entry.getValue(), count))
                        .build())
                .collect(Collectors.toList());
    }

    @NotNull
    private List<ExchangeRate> predictNextNDays(@NotNull List<ExchangeRate> rates, int count) {
        List<ExchangeRate> data = new ArrayList<>(rates);
        return IntStream.range(0, count)
                .mapToObj(i -> {
                    ExchangeRate prediction = predictNextDay(data);
                    data.add(prediction);
                    return prediction;
                }).collect(Collectors.toList());
    }

    @NotNull
    private ExchangeRate predictNextDay(@NotNull List<ExchangeRate> rates) {
        List<ExchangeRate> lastRates = rates.stream()
                .sorted(Comparator.comparing(ExchangeRate::getDate, Comparator.reverseOrder()))
                .limit(WEEK)
                .collect(Collectors.toList());
        ExchangeRate first = lastRates.get(0);
        List<Double> values = lastRates.stream()
                .map(ExchangeRate::getValue)
                .map(BigDecimal::doubleValue)
                .collect(Collectors.toList());
        SimpleRegression regression = new SimpleRegression();
        IntStream.range(0, values.size())
                .forEach(i -> regression.addData(i, values.get(i)));
        BigDecimal predicted = BigDecimal.valueOf(regression.predict(values.size()));
        return first.toBuilder()
                .value(predicted)
                .date(first.getDate().plusDays(1))
                .build();
    }

}
