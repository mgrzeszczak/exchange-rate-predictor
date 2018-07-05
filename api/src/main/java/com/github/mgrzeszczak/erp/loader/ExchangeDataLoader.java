package com.github.mgrzeszczak.erp.loader;

import com.github.mgrzeszczak.erp.model.ExchangeRate;
import com.github.mgrzeszczak.erp.nbp.NbpApi;
import com.github.mgrzeszczak.erp.nbp.NbpExchangeDataResponse;
import com.github.mgrzeszczak.erp.repository.ExchangeRateRepository;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ExchangeDataLoader {

    private static final int BATCH_SIZE = 90;
    private static final String TABLE = "A";

    private final NbpApi nbpApi;
    private final LocalDateRangeGenerator localDateRangeGenerator;
    private final ExchangeRateRepository repository;

    public ExchangeDataLoader(NbpApi nbpApi, LocalDateRangeGenerator localDateRangeGenerator, ExchangeRateRepository repository) {
        this.nbpApi = nbpApi;
        this.localDateRangeGenerator = localDateRangeGenerator;
        this.repository = repository;
    }

    @PostConstruct
    public void preloadData() {
        LocalDate start = LocalDate.of(2018, 1, 2);
        LocalDate end = LocalDate.now();
        Flowable.fromIterable(localDateRangeGenerator.generate(start, end, BATCH_SIZE))
                .subscribeOn(Schedulers.io())
                .map(v -> {
                    log.info("Pulling data between {} and {} for table {}", v.getLeft(), v.getRight(), TABLE);
                    return v;
                })
                .flatMap(range -> nbpApi.findExchangeRatesBetween(TABLE, range.getLeft(), range.getRight()).toFlowable())
                .flatMap(Flowable::fromIterable)
                .flatMap(r -> Flowable.fromIterable(r.getRates()
                        .stream()
                        .map(v -> createExchangeRate(v, r.getEffectiveDate()))
                        .collect(Collectors.toList()))
                )
                .collectInto(new ArrayList<ExchangeRate>(), ArrayList::add)
                .subscribe(repository::saveAll);
    }

    @NotNull
    private ExchangeRate createExchangeRate(@NotNull NbpExchangeDataResponse.NbpExchangeRate exchangeRate, @NotNull LocalDate date) {
        return ExchangeRate.builder()
                .value(exchangeRate.getValue())
                .date(date)
                .currency(exchangeRate.getCurrency())
                .code(exchangeRate.getCode())
                .build();
    }

}
